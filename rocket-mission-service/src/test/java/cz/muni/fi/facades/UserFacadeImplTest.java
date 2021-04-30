package cz.muni.fi.facades;


import cz.muni.fi.dto.*;

import cz.muni.fi.dto.create.CreateComponentDTO;
import cz.muni.fi.dto.create.CreateMissionDTO;
import cz.muni.fi.dto.create.CreateRocketDTO;
import cz.muni.fi.dto.create.CreateUserDTO;
import cz.muni.fi.dto.update.UpdateUserDTO;
import cz.muni.fi.entity.User;
import cz.muni.fi.enums.Role;
import cz.muni.fi.facade.ComponentFacade;
import cz.muni.fi.facade.MissionFacade;

import cz.muni.fi.facade.RocketFacade;
import cz.muni.fi.facade.UserFacade;
import cz.muni.fi.services.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private ComponentFacade componentFacade;
    @Autowired
    MissionFacade missionFacade;
    @Autowired
    RocketFacade rocketFacade;


    private UserDTO user;

    public CreateUserDTO createUser() {
        CreateUserDTO userCreateDTO = new CreateUserDTO();
        userCreateDTO.setName("name");
        userCreateDTO.setEmail("name@mail.com");
        userCreateDTO.setPassword("pass");
        return userCreateDTO;
    }

    public UpdateUserDTO updateUser() {
        UpdateUserDTO userUpdateDTO = new UpdateUserDTO();
        userUpdateDTO.setName("name");
        userUpdateDTO.setEmail("updated@mail.com");
        userUpdateDTO.setPassword("pass");
        return userUpdateDTO;
    }

    @BeforeMethod
    public void setUp() throws Exception {
        CreateUserDTO user = createUser();
        this.user = userFacade.addUser(user);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        for (UserDTO user : userFacade.findAllUsers()) {
            userFacade.deleteUser(user);
        }
        for (MissionDTO mission : missionFacade.findAllMissions()) {
            missionFacade.deleteMission(mission);
        }
        for (RocketDTO spacecraft : rocketFacade.findAllRockets()) {
            rocketFacade.removeRocket(spacecraft);
        }
        for (ComponentDTO cc : componentFacade.findAllComponents()) {
            componentFacade.removeComponent(cc);
        }
    }

    @Test
    public void testAddUser() {
        int sizeBefore = userFacade.findAllUsers().size();
        CreateUserDTO user = createUser();
        user.setEmail("sfdljsdfl@mail.cz");
        UserDTO userDto = userFacade.addUser(user);
        assertThat(userFacade.findAllUsers()).hasSize(sizeBefore + 1);
        assertThat(userFacade.findAllUsers()).contains(userDto);
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserDTO updatedUser = userFacade.updateUser(updateUser());
        assertThat(updatedUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void testDeleteUser() throws Exception {
        int sizeBefore = userFacade.findAllUsers().size();
        userFacade.deleteUser(user);
        assertThat(userFacade.findAllUsers()).hasSize(sizeBefore - 1);
        assertThat(userFacade.findAllUsers()).doesNotContain(user);
    }

    @Test
    public void testFindAllUsers() throws Exception {
        int sizeBefore = userFacade.findAllUsers().size();
        CreateUserDTO user1 = createUser();
        user1.setEmail("1@users.com");
        UserDTO addedUser1 = userFacade.addUser(user1);
        CreateUserDTO user2 = createUser();
        user2.setEmail("2@users.com");
        UserDTO addedUser2 = userFacade.addUser(user2);
        assertThat(userFacade.findAllUsers()).hasSize(sizeBefore + 2);
        assertThat(userFacade.findAllUsers()).contains(addedUser1);
        assertThat(userFacade.findAllUsers()).contains(addedUser2);
    }

    @Test
    public void testFindAllAstronauts() throws Exception {
        int sizeBefore = userFacade.findAllAstronauts().size();
        CreateUserDTO user1 = createUser();
        user1.setEmail("1@users.com");
        userFacade.addUser(user1);
        CreateUserDTO user2 = createUser();
        user2.setEmail("2@users.com");
        user2.setRole(Role.MANAGER);
        userFacade.addUser(user2);
        assertThat(userFacade.findAllAstronauts()).hasSize(sizeBefore + 1);
    }

    @Test
    public void testFindUserById() throws Exception {
        assertThat(userFacade.findUserById(user.getId())).isEqualTo(user);
    }

//    @Test
//    public void testAcceptMission() throws Exception {
//        CreateMissionDTO missionCreateDTO = TestUtils.getMissionCreateDTO("Enterprise");
//        CreateComponentDTO craftComponentCreateDTO = TestUtils.getCraftComponentCreateDTO("Enterprise");
//        Long componentId = craftComponentFacade.addComponent(craftComponentCreateDTO);
//        CreateRocketDTO rocketCreateDTO = TestUtils.getSpacecraftCreateDTO("Enterprise");
//        spacecraftCreateDTO.setComponents(Collections.singleton(craftComponentFacade.findComponentById(componentId)));
//        missionCreateDTO.setSpacecrafts(Collections.singleton(spacecraftFacade.findSpacecraftById(spacecraftFacade.addSpacecraft(spacecraftCreateDTO))));
//        Long id = missionFacade.createMission(missionCreateDTO);
//        UserCreateDTO userCreateDTO = createUser();
//        userCreateDTO.setEmail("ASH@mail.com");
//        userCreateDTO.setMission(missionFacade.findMissionById(id));
//        Long us = userFacade.addUser(userCreateDTO);
//        userFacade.acceptAssignedMission(userFacade.findUserById(us));
//        assertThat(userFacade.findUserById(us).getMission()).isNotNull();
//        assertThat(userFacade.findUserById(us).getAcceptedMission()).isTrue();
//
//    }
//
//    @Test
//    public void testRejectMission() throws Exception {
//        CreateMissionDTO missionCreateDTO = TestUtils.getMissionCreateDTO("Enterprise");
//        CreateComponentDTO componentCreateDTO = TestUtils.getCraftComponentCreateDTO("Enterprise");
//        Long componentId = componentFacade.addComponent(craftComponentCreateDTO);
//        CreateRocketDTO spacecraftCreateDTO = TestUtils.getSpacecraftCreateDTO("Enterprise");
//        spacecraftCreateDTO.setComponents(Collections.singleton(craftComponentFacade.findComponentById(componentId)));
//        missionCreateDTO.setSpacecrafts(Collections.singleton(spacecraftFacade.findSpacecraftById(spacecraftFacade.addSpacecraft(spacecraftCreateDTO))));
//        Long id = missionFacade.createMission(missionCreateDTO);
//        CreateUserDTO userCreateDTO = createUser();
//        userCreateDTO.setEmail("ASH@mail.com");
//        userCreateDTO.setMission(missionFacade.findMissionById(id));
//        Long us = userFacade.addUser(userCreateDTO);
//        userFacade.rejectAssignedMission(userFacade.findUserById(us), "My mom won't let me go.");
//        assertThat(userFacade.findUserById(us).getMission()).isNull();
//        assertThat(userFacade.findUserById(us).getAcceptedMission()).isFalse();
//
//    }
}