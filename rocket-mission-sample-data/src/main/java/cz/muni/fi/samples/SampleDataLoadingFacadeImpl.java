package cz.muni.fi.samples;

import cz.muni.fi.entity.Mission;

import cz.muni.fi.entity.Rocket;
import cz.muni.fi.entity.User;
import cz.muni.fi.enums.ComponentType;
import cz.muni.fi.enums.LevelOfExperience;
import cz.muni.fi.enums.MissionProgress;
import cz.muni.fi.enums.Role;
import cz.muni.fi.services.ComponentService;
import cz.muni.fi.services.MissionService;
import cz.muni.fi.services.RocketService;
import cz.muni.fi.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author Martin Kazimir
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private UserService userService;
    @Autowired
    private ComponentService componentService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private RocketService rocketService;


    private cz.muni.fi.entity.Component component1;
    private cz.muni.fi.entity.Component component2;
    private cz.muni.fi.entity.Component component3;
    private cz.muni.fi.entity.Component component4;

    private Mission mission1;
    private Mission mission2;
    private Mission mission3;
    private Mission mission4;

    private User user1;
    private User user2;
    private User user3;
    private User user4;

    private Rocket rocket1;
    private Rocket rocket2;
    private Rocket rocket3;
    private Rocket rocket4;

    private final PasswordEncoder encoder = new Argon2PasswordEncoder();

    @Override
    public void loadAllData() {
        log.info("data loading starting");
        loadAllComponents();
        loadAllUsers();
        loadAllMissions();
        loadAllRockets();
        loadRelationships();
    }

    @Override
    public void loadAllComponents() {
        log.info("Components loading starting");

        component1 = new cz.muni.fi.entity.Component();
        component1.setName("wing");
        component1.setType(ComponentType.ROCKET);
        component1.setReadyToUse(false);
        component1.setReadyDate(ZonedDateTime.now().plusDays(10));
        componentService.addComponent(component1);
        log.info("component1 loaded");

        component2 = new cz.muni.fi.entity.Component();
        component2.setName("food");
        component2.setType(ComponentType.MISSION);
        component2.setReadyToUse(false);
        component2.setReadyDate(ZonedDateTime.now().plusDays(2));
        componentService.addComponent(component2);
        log.info("component1 loaded");

        component3 = new cz.muni.fi.entity.Component();
        component3.setName("engine");
        component3.setType(ComponentType.ROCKET);
        component3.setReadyToUse(true);
        componentService.addComponent(component3);
        log.info("component1 loaded");

        component4 = new cz.muni.fi.entity.Component();
        component4.setName("oxygen supply");
        component4.setType(ComponentType.MISSION);
        component4.setReadyToUse(true);
        componentService.addComponent(component4);
        log.info("component1 loaded");

        log.info(componentService.findAllComponents().toString());
    }

    @Override
    public void loadAllMissions() {
        mission1 = new Mission();
        mission1.setMissionProgress(MissionProgress.IN_PROGRESS);
        mission1.setName("Discovering Aliens");
        mission1.setEta(ZonedDateTime.now());
        mission1.setDestination("Mars");
        mission1.setStartedDate(ZonedDateTime.now().minusDays(2));
        mission1.setFinishedDate(ZonedDateTime.now().plusDays(2));
        missionService.addMission(mission1);
        log.info("mission1 loaded");

        mission2 = new Mission();
        mission2.setMissionProgress(MissionProgress.PLANNED);
        mission2.setName("Discovering Humnas");
        mission2.setDestination("Jupyter");
        missionService.addMission(mission2);
        log.info("mission2 loaded");

        mission3 = new Mission();
        mission3.setMissionProgress(MissionProgress.FINISHED);
        mission3.setName("Discovering Cars");
        mission3.setDestination("Cars");
        mission3.setStartedDate(ZonedDateTime.now().minusDays(4));
        mission3.setFinishedDate(ZonedDateTime.now().minusDays(2));
        mission3.setResult("Discovered diamonds");
        missionService.addMission(mission3);
        log.info("mission3 loaded");

        mission4 = new Mission();
        mission4.setMissionProgress(MissionProgress.IN_PROGRESS);
        mission4.setName("Discovering Fresh Food");
        mission4.setEta(ZonedDateTime.now());
        mission4.setDestination("Suu");
        mission1.setEta(ZonedDateTime.now());
        mission4.setStartedDate(ZonedDateTime.now().minusDays(2));
        mission4.setFinishedDate(ZonedDateTime.now().plusDays(2));
        missionService.addMission(mission4);
        log.info("mission4 loaded");

        log.info(missionService.findAllMissions().toString());
    }


    @Override
    public void loadAllUsers() {
        user1 = new User();
        user1.setName("John");
        user1.setEmail("john@gmail.com");
        user1.setLevelOfExperience(LevelOfExperience.ROOKIE);
        user1.setPassword(encoder.encode("tralala123"));
        user1.setRole(Role.ASTRONAUT);
        userService.addUser(user1);
        log.info("user1 loaded");

        user2 = new User();
        user2.setName("David");
        user2.setEmail("david@gmail.com");
        user2.setLevelOfExperience(LevelOfExperience.VETERAN);
        user2.setPassword(encoder.encode("tralala321"));
        user2.setRole(Role.ASTRONAUT);
        userService.addUser(user2);
        log.info("user2 loaded");

        user3 = new User();
        user3.setName("jiri");
        user3.setEmail("jiri@gmail.com");
        user3.setLevelOfExperience(LevelOfExperience.KING_OF_GALAXY);
        user3.setPassword(encoder.encode("taspokeqw"));

        user3.setRole(Role.ASTRONAUT);
        userService.addUser(user3);
        log.info("user3 loaded");

        user4 = new User();
        user4.setName("THE BOSS");
        user4.setEmail("boss@gmail.com");
        user4.setPassword(encoder.encode("bosspassword"));
        user4.setLevelOfExperience(LevelOfExperience.ROOKIE);
        user4.setRole(Role.MANAGER);
        userService.addUser(user4);
        log.info("user4 loaded");
    }


    @Override
    public void loadAllRockets() {
        rocket1 = new Rocket();
        rocket1.setName("LOLOLO");
        rocketService.addRocket(rocket1);
        log.info("rocket1 loaded");

        rocket2 = new Rocket();
        rocket2.setName("TOTOTO");
        rocketService.addRocket(rocket2);
        log.info("rocket2 loaded");

        rocket3 = new Rocket();
        rocket3.setName("GOGOGO");
        rocketService.addRocket(rocket3);
        log.info("rocket3 loaded");

        rocket4 = new Rocket();
        rocket4.setName("BOBOBO");
        rocketService.addRocket(rocket4);
        log.info("rocket4 loaded");
    }

    @Override
    public void loadRelationships() {
        rocket1.setMission(mission1);
        rocket2.setMission(mission2);
        rocket3.setMission(mission3);
        rocket4.setMission(mission4);

        mission1.addUser(user1);
        mission1.addUser(user2);
        mission1.addUser(user4);
        mission2.addUser(user3);

        user1.setMissionAccepted(false);
        user2.setMissionAccepted(true);
        user3.setMissionAccepted(true);
        user4.setMissionAccepted(true);

        rocket1.setRequiredComponents(new HashSet(Arrays.asList(component1, component2)));
        rocket2.setRequiredComponents(new HashSet(Arrays.asList(component3)));
        mission4.addComponent(component4);
    }
}
