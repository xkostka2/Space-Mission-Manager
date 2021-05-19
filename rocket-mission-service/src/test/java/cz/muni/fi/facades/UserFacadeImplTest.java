package cz.muni.fi.facades;

import cz.muni.fi.dto.mission.CreateMissionDTO;
import cz.muni.fi.dto.user.CreateUserDTO;
import cz.muni.fi.dto.user.UpdateUserDTO;
import cz.muni.fi.dto.component.ComponentDTO;
import cz.muni.fi.dto.mission.MissionDTO;
import cz.muni.fi.dto.rocket.RocketDTO;
import cz.muni.fi.dto.user.UserDTO;
import cz.muni.fi.enums.LevelOfExperience;
import cz.muni.fi.enums.MissionProgress;
import cz.muni.fi.enums.Role;
import cz.muni.fi.facade.ComponentFacade;
import cz.muni.fi.facade.MissionFacade;
import cz.muni.fi.facade.RocketFacade;
import cz.muni.fi.facade.UserFacade;
import cz.muni.fi.services.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
        userCreateDTO.setRole(Role.ASTRONAUT);
        userCreateDTO.setPassword("pass");
        userCreateDTO.setLevelOfExperience(LevelOfExperience.KING_OF_GALAXY);
        return userCreateDTO;
    }
    public UpdateUserDTO updateUser() {
        UpdateUserDTO userUpdateDTO = new UpdateUserDTO();
        userUpdateDTO.setName("namasdase");
        userUpdateDTO.setEmail("updated@mail.com");
        userUpdateDTO.setRole(Role.ASTRONAUT);
        userUpdateDTO.setLevelOfExperience(LevelOfExperience.KING_OF_GALAXY);
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
        user.setName("aseq2we");
        UserDTO userDto = userFacade.addUser(user);
        assertThat(userFacade.findAllUsers()).hasSize(sizeBefore + 1);
        assertThat(userFacade.findAllUsers()).contains(userDto);
    }
    @Test
    public void testUpdateUser() throws Exception {
        UpdateUserDTO userForUpdate = updateUser();
        userForUpdate.setId(user.getId());
        UserDTO updatedUser = userFacade.updateUser(userForUpdate);
        assertThat(updatedUser.getEmail()).isEqualTo(updateUser().getEmail());
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
        user1.setName("aseq2123we");
        UserDTO addedUser1 = userFacade.addUser(user1);
        CreateUserDTO user2 = createUser();
        user2.setEmail("2@users.com");
        user2.setName("asdaseq2we");
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
        user1.setName("321aseq2we");
        userFacade.addUser(user1);
        CreateUserDTO user2 = createUser();
        user2.setEmail("2@users.com");
        user2.setName("asyrteq2we");
        user2.setRole(Role.MANAGER);
        userFacade.addUser(user2);
        assertThat(userFacade.findAllAstronauts()).hasSize(sizeBefore + 1);
    }
    @Test
    public void testFindUserById() throws Exception {
        assertThat(userFacade.findUserById(user.getId())).isEqualTo(user);
    }
    @Test
    public void testRejectMission() throws Exception {
        CreateMissionDTO missionCreateDTO = MissionFacadeImplTest.getCreateMissionDTO("someasf", MissionProgress.IN_PROGRESS);
        MissionDTO mission = missionFacade.addMission(missionCreateDTO);
        CreateUserDTO userCreateDTO = createUser();
        userCreateDTO.setEmail("ASH@mail.com");
        userCreateDTO.setName("ASasdasH");
        userCreateDTO.setMission(mission);
        UserDTO us = userFacade.addUser(userCreateDTO);
        us.setMission(mission);
        userFacade.rejectAssignedMission(us, "I am Covid");
        assertThat(userFacade.findUserById(us.getId()).getMission()).isNull();
        assertThat(userFacade.findUserById(us.getId()).getMissionAccepted()).isFalse();
    }
    @Test
    public void testAcceptMission() throws Exception {
        CreateMissionDTO missionCreateDTO = MissionFacadeImplTest.getCreateMissionDTO("someasf", MissionProgress.IN_PROGRESS);
        MissionDTO mission = missionFacade.addMission(missionCreateDTO);
        CreateUserDTO userCreateDTO = createUser();
        userCreateDTO.setEmail("ASH@mail.com");
        userCreateDTO.setName("ASH");
        userCreateDTO.setMission(mission);
        UserDTO us = userFacade.addUser(userCreateDTO);
        us.setMission(mission);
        userFacade.acceptAssignedMission(us);
        assertThat(userFacade.findUserById(us.getId()).getMission()).isNotNull();
        assertThat(userFacade.findUserById(us.getId()).getMissionAccepted()).isTrue();
    }
}