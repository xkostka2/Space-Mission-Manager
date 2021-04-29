package cz.muni.fi.facades;

import cz.muni.fi.dto.ComponentDTO;
import cz.muni.fi.dto.MissionDTO;
import cz.muni.fi.dto.RocketDTO;
import cz.muni.fi.dto.UserDTO;
import cz.muni.fi.dto.create.CreateComponentDTO;
import cz.muni.fi.dto.create.CreateMissionDTO;
import cz.muni.fi.dto.create.CreateRocketDTO;
import cz.muni.fi.dto.create.CreateUserDTO;
import cz.muni.fi.dto.update.UpdateMissionDTO;
import cz.muni.fi.enums.LevelOfExperience;
import cz.muni.fi.enums.MissionProgress;
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

import java.time.*;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Mission facade layer tests.
 *
 * @author Martin Hořelka (469003)
 */
@DirtiesContext
@ContextConfiguration(classes = ServiceConfiguration.class)
public class MissionFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private MissionFacade missionFacade;

    @Autowired
    private RocketFacade rocketFacade;

    @Autowired
    private ComponentFacade componentFacade;

    @Autowired
    private UserFacade userFacade;

    private CreateMissionDTO createMissionDTO;
    private UpdateMissionDTO updateMissionDTO;
    private MissionDTO mission1;
    private MissionDTO mission2;

    @BeforeMethod
    public void setUp() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setName("name");
        createUserDTO.setEmail("name@mail.com");
        createUserDTO.setPassword("pass");
        createUserDTO.setLevelOfExperience(LevelOfExperience.VETERAN);
        createUserDTO.setRole(Role.MANAGER);
        UserDTO user = userFacade.addUser(createUserDTO);

        createMissionDTO = getCreateMissionDTO("Apollo10");
        createMissionDTO.setUsers(Collections.singleton(userFacade.findUserById(user.getId())));
        CreateMissionDTO createMissionDTO1 = getCreateMissionDTO("Apollo11");
        createMissionDTO1.setMissionProgress(MissionProgress.IN_PROGRESS);
        mission1 = missionFacade.findMissionById(missionFacade.addMission(createMissionDTO1).getId());
        createMissionDTO1 = getCreateMissionDTO("Apollo12");
        createMissionDTO1.setMissionProgress(MissionProgress.FINISHED);
        mission2 = missionFacade.findMissionById(missionFacade.addMission(createMissionDTO1).getId());

        updateMissionDTO = getUpdateMissionDTO("Apollo13");
        createMissionDTO.setUsers(Collections.singleton(userFacade.findUserById(user.getId())));

    }

    private RocketDTO getRockets(String name) {
        CreateComponentDTO createComponentDTO = TestHelper.getCreateComponentDTO(name);
        ComponentDTO component = componentFacade.addComponent(createComponentDTO);
        CreateRocketDTO createRocketDTO = TestHelper.getCreateRocketDTO(name);
        createRocketDTO.setRequiredComponents(Collections.singleton(componentFacade.findComponentById(component.getId())));
        return rocketFacade.findRocketById(rocketFacade.addRocket(createRocketDTO).getId());
    }

    private CreateMissionDTO getCreateMissionDTO(String name) {
        CreateMissionDTO createMissionDTO = TestHelper.getCreateMissionDTO(name);
        createMissionDTO.setRockets(Collections.singleton(getRockets(name)));
        return createMissionDTO;
    }

    private UpdateMissionDTO getUpdateMissionDTO(String name) {
        UpdateMissionDTO updateMissionDTO = TestHelper.getUpdateMissionDTO(name);
        for (RocketDTO rocket : Collections.singleton(getRockets(name))) {
            updateMissionDTO.addRocket(rocket);
        }
        return updateMissionDTO;
    }

    @AfterMethod
    public void afterMethod() throws Exception {
        for (MissionDTO mission : missionFacade.findAllMissions()) {
            missionFacade.deleteMission(mission);
        }
        for (UserDTO user : userFacade.findAllUsers()) {
            userFacade.deleteUser(user);
        }
        for (RocketDTO rocket : rocketFacade.findAllRockets()) {
            rocketFacade.removeRocket(rocket);
        }
        for (ComponentDTO component : componentFacade.findAllComponents()) {
            componentFacade.removeComponent(component);
        }
        
        assertThat(missionFacade.findAllMissions()).isEmpty();
        assertThat(userFacade.findAllUsers()).isEmpty();
        assertThat(rocketFacade.findAllRockets()).isEmpty();
        assertThat(componentFacade.findAllComponents()).isEmpty();
        createMissionDTO = null;
        mission1 = null;
        mission2 = null;
    }

    @Test
    public void testCreateMission() throws Exception {
        assertThat(missionFacade.findAllMissions()).hasSize(2);
        MissionDTO missionDTO = missionFacade.findMissionById(missionFacade.addMission(createMissionDTO).getId());
        assertThat(missionDTO).isEqualToIgnoringGivenFields(createMissionDTO, "id");
        assertThat(missionFacade.findAllMissions()).contains(missionDTO);
    }

    @Test
    public void testDeleteMission() throws Exception {
        assertThat(missionFacade.findAllMissions()).hasSize(2).contains(mission1, mission2);
        missionFacade.deleteMission(mission1);
        assertThat(missionFacade.findAllMissions()).hasSize(1).contains(mission2);
    }

    @Test
    public void testFindAllMissions() throws Exception {
        assertThat(missionFacade.findAllMissions()).hasSize(2).contains(mission1, mission2);
    }

    @Test
    public void testFindAllActiveMissions() throws Exception {
        assertThat(missionFacade.findAllMissions(MissionProgress.IN_PROGRESS)).hasSize(1).contains(mission1);
        assertThat(missionFacade.findAllMissions(MissionProgress.FINISHED)).hasSize(1).contains(mission2);
        createMissionDTO.setMissionProgress(MissionProgress.IN_PROGRESS);
        Long id = missionFacade.addMission(createMissionDTO).getId();
        assertThat(missionFacade.findAllMissions(MissionProgress.IN_PROGRESS)).hasSize(2)
                .contains(mission1, missionFacade.findMissionById(id));
    }

    @Test
    public void testFindMissionById() throws Exception {
        assertThat(missionFacade.findMissionById(mission1.getId())).isEqualTo(mission1);
        assertThat(missionFacade.findMissionById(mission2.getId())).isEqualTo(mission2);
    }

    @Test
    public void testUpdateMission() throws Exception {
        mission1.setDestination("another destination");
        MissionDTO missionDTO = missionFacade.findMissionById(missionFacade.updateMission(updateMissionDTO).getId());
        assertThat(missionDTO).isEqualToIgnoringGivenFields(updateMissionDTO, "id");
        assertThat(missionFacade.findAllMissions()).contains(missionDTO);
    }

    @Test
    public void testArchive() throws Exception {
        missionFacade.archive(mission1, ZonedDateTime.now().minusDays(10), "It's over!");
        assertThat(missionFacade.findMissionById(mission1.getId()))
                .hasFieldOrPropertyWithValue("missionProgress", MissionProgress.FINISHED);
        assertThat(missionFacade.findMissionById(mission1.getId()).getResult()).isNotEmpty();
    }
}
