package cz.muni.fi.services;

//import cz.muni.fi.config.ServiceConfiguration;

import cz.muni.fi.dto.ComponentDTO;
import cz.muni.fi.dto.MissionDTO;

import cz.muni.fi.dto.RocketDTO;
import cz.muni.fi.dto.UserDTO;
import cz.muni.fi.entity.Component;

import cz.muni.fi.entity.Mission;
import cz.muni.fi.entity.Rocket;
import cz.muni.fi.entity.User;
import cz.muni.fi.facades.TestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Tomas Bouma
 *
 * @author Tomas Bouma
 */

@ContextConfiguration(classes = ServiceConfiguration.class)

public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BeanMappingService beanMappingService;

    private List<User> users = new ArrayList<>();
    private List<Mission> missions = new ArrayList<>();
    private List<Rocket> rockets = new ArrayList<>();
    private List<Component> components = new ArrayList<>();

    private Mission mission;
    private Rocket rocket;
    private Component component;
    private User user;

    @BeforeMethod
    public void beforeTest() {

        missions.clear();
        users.clear();
        components.clear();
        rockets.clear();

        mission = MissionServiceImplTest.createMission("test");
        Mission mission1 = MissionServiceImplTest.createMission("test1");
        Mission mission2 = MissionServiceImplTest.createMission("test2");

        user = TestUtils.createUser("name", mission);

        rocket = TestUtils.createRocket("spacecraft", mission);
        Rocket rocket1 = TestUtils.createRocket("spacecraft1", mission1);
        Rocket rocket2 = TestUtils.createRocket("spacecraft2", mission1);
        Rocket rocket3 = TestUtils.createRocket("spacecraft3", mission2);

        component = TestUtils.createComponent("craft", rocket);
        Component craftComponent1 = TestUtils.createComponent("craft1", rocket1);
        Component craftComponent2 = TestUtils.createComponent("craft2", rocket2);
        Component craftComponent3 = TestUtils.createComponent("craft3", rocket3);
        Component craftComponent4 = TestUtils.createComponent("craft4", rocket3);

        mission.setId(1L);
        user.setId(1L);
        rocket.setId(1L);
        component.setId(1L);

        users.add(user);

        missions.add(mission1);
        missions.add(mission2);

        rockets.add(rocket1);
        rockets.add(rocket2);
        rockets.add(rocket3);

        components.add(craftComponent1);
        components.add(craftComponent2);
        components.add(craftComponent3);
        components.add(craftComponent4);

    }

    @Test
    public void shouldMapMission() {
        MissionDTO missionDTO = beanMappingService.mapTo(mission, MissionDTO.class);
        assertThat(missionDTO).isNotNull();
        assertThat(missionDTO.getName()).isEqualTo(mission.getName());
        assertThat(missionDTO.getId()).isEqualTo(mission.getId());
        assertThat(missionDTO.getUsers()).hasSize(1);
        assertThat(missionDTO.getRockets()).hasSize(1);

        UserDTO userDTO = missionDTO.getUsers().iterator().next();
        RocketDTO spacecraftDTO = missionDTO.getRockets().iterator().next();

        assertThat(missionDTO.getUsers()).containsExactly(userDTO);
        assertThat(userDTO.getMission()).isEqualToComparingFieldByField(missionDTO);

        assertThat(missionDTO.getRockets()).containsExactly(spacecraftDTO);
        assertThat(spacecraftDTO.getMission()).isEqualToComparingFieldByField(missionDTO);
    }

    @Test
    public void shouldMapSpacecraft() {
        RocketDTO rocketDTO = beanMappingService.mapTo(rocket, RocketDTO.class);
        assertThat(rocketDTO).isNotNull();
        MissionDTO missionDTO = rocketDTO.getMission();
        assertThat(missionDTO).isNotNull();

        assertThat(rocketDTO.getName()).isEqualTo(rocketDTO.getName());
        assertThat(rocketDTO.getId()).isEqualTo(rocketDTO.getId());

        assertThat(missionDTO.getRockets().iterator().next()).isEqualToComparingFieldByField(rocketDTO);
    }


    @Test
    public void shouldMapComponent() {
        ComponentDTO componentDTO = beanMappingService.mapTo(component, ComponentDTO.class);

        assertThat(componentDTO.getName()).isEqualTo(component.getName());
        assertThat(componentDTO.getId()).isEqualTo(component.getId());
    }

    @Test
    public void shouldMapUser() {
        UserDTO userDTO = beanMappingService.mapTo(user, UserDTO.class);
        assertThat(userDTO.getName()).isEqualTo(user.getName());
        assertThat(userDTO.getId()).isEqualTo(user.getId());
    }


    @Test
    public void shouldMapInnerMissions() {
        List<MissionDTO> missionDTOList = beanMappingService.mapTo(missions, MissionDTO.class);

        assertThat(missionDTOList).hasSize(2);
        assertThat(missionDTOList.get(0).getName()).isEqualTo("test1");
        assertThat(missionDTOList.get(0).getRockets()).hasSize(2);
    }

    @Test
    public void shouldMapInnerRockets() {
        List<RocketDTO> rocketDTOList = beanMappingService.mapTo(rockets, RocketDTO.class);

        assertThat(rocketDTOList).hasSize(3);
        assertThat(rocketDTOList.get(0).getName()).isEqualTo("spacecraft1");
        assertThat(rocketDTOList.get(0).getMission().getName()).isEqualTo("test1");
    }

    @Test
    public void shouldMapInnerComponents() {
        List<ComponentDTO> componentDTOList = beanMappingService.mapTo(components, ComponentDTO.class);

        assertThat(componentDTOList.get(0).getName()).isEqualTo("craft1");
        assertThat(componentDTOList.get(0).getRocket().getName()).isEqualTo("spacecraft1");
    }

    @Test
    public void shouldMapInnerUsers() {
        List<UserDTO> userDTOList = beanMappingService.mapTo(users, UserDTO.class);

        assertThat(userDTOList.get(0).getName()).isEqualTo("name");
        assertThat(userDTOList.get(0).getMission().getName()).isEqualTo("test");
    }
}