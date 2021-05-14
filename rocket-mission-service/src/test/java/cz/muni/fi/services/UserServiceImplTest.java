package cz.muni.fi.services;

import cz.muni.fi.dao.MissionDao;
import cz.muni.fi.dao.UserDao;
import cz.muni.fi.entity.Mission;
import cz.muni.fi.entity.User;
import cz.muni.fi.enums.Role;
import cz.muni.fi.helpers.ServiceDataAccessException;
import cz.muni.fi.services.impl.UserServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    @Mock
    private UserDao userDao;
    @Mock
    private MissionDao missionDao;

    @InjectMocks
    private final UserService userService = new UserServiceImpl();

    private Long idCounter;
    private final Map<Long, User> users = new HashMap<>();
    private final Map<Long, Mission> missions = new HashMap<>();

    @BeforeClass
    public void beforeClass() {
        MockitoAnnotations.initMocks(this);

        when(userDao.addUser(any(User.class))).then(invoke -> {
            User user = invoke.getArgumentAt(0, User.class);
            if(user == null) throw new IllegalArgumentException("Null user");
            if (user.getId() != null) {
                throw new IllegalArgumentException("User already exist");
            }
            if(checkUserNames(user.getName())){
                throw new IllegalArgumentException("User name already exist");
            }
            long id = idCounter;
            user.setId(id);
            users.put(id, user);
            idCounter++;
            return user;
        });

        when(userDao.updateUser(any(User.class))).then(invoke -> {
            User user = invoke.getArgumentAt(0, User.class);

            if (!users.keySet().contains(user.getId()) || user.getId() == null) {
                throw new IllegalArgumentException("User was not created yet");
            }
            users.replace(user.getId(), user);
            return user;
        });

        doAnswer(invoke -> {
            User user = invoke.getArgumentAt(0, User.class);

            if (!users.keySet().contains(user.getId()) || user.getId() == null) {
                throw new IllegalArgumentException("User was not created yet");
            }
            users.remove(user.getId(), user);
            return user;
        }).when(userDao).deleteUser(any(User.class));

        when(userDao.findAllUsers())
                .then(invoke -> Collections.unmodifiableList(new ArrayList<>(users.values())));

        when(userDao.findAllAstronauts()).then(invoke -> {
            ArrayList astronauts = new ArrayList<>();
            for(User u : users.values()) {
                if (u.getRole() != Role.MANAGER) {
                    astronauts.add(u);
                }
            }
            return Collections.unmodifiableList(astronauts);
        });

        when(userDao.findUserById(any(Long.class))).then(invoke -> {
            Long index = invoke.getArgumentAt(0, Long.class);
            if(index == null){
                throw new IllegalArgumentException("Null user id");
            }
            if(users.keySet().contains(index)) {
                return users.get(index);
            } else {
                throw new IllegalArgumentException("Not existing user");
            }
        });

        when(userDao.findAllAvailableAstronauts()).then(invoke -> {
            ArrayList astronauts = new ArrayList<>();
            for(User u : users.values()) {
                if (u.getRole() != Role.MANAGER && (u.getMission() == null)) {
                    astronauts.add(u);
                }
            }
            return Collections.unmodifiableList(astronauts);
        });

        when(missionDao.addMission(any(Mission.class))).then(invoke -> {
            Mission mission = invoke.getArgumentAt(0, Mission.class);

            if (mission.getId() != null) {
                throw new IllegalArgumentException("Mission already exists");
            }
            long id = idCounter;
            mission.setId(id);
            missions.put(id, mission);
            idCounter++;
            return mission;
        });
    }


    @BeforeMethod
    public void beforeTest() {
        users.clear();
        User user1 = createUser("user1");
        User user2 = createUser("user2");
        User user3 = createUser("astronaut3");
        User user4 = createUser("astronaut4");
        User user5 = createUser("astronaut5");
        user1.setId(1L);
        user2.setId(2L);
        user3.setId(3L);
        user4.setId(4L);
        user5.setId(4L);

        users.put(1L, user1);
        users.put(2L, user2);
        users.put(3L, user3);
        users.put(4L, user4);
        users.put(5L, user5);
        idCounter = 10L;
    }

    @Test
    public void addNewUser() {
        int sizeBefore = users.size();
        User user = createUser("user");
        userService.addUser(user);
        assertThat(users.values()).hasSize(sizeBefore + 1)
                .contains(user);
    }
    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void addNullUser() {
        userService.addUser(null);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void addExistingUser(){
        User user = createUser("user");
        User anotherUser = createUser("user");
        userService.addUser(user);
        userService.addUser(anotherUser);
    }

    @Test
    public void acceptMission(){
        User u = prepareAstronautWithMission();

        userService.acceptAssignedMission(u);
        assertThat(u.missionStatusPending()).isFalse();
        assertThat(u.getMissionAccepted()).isTrue();
        assertThat(u.getMissionExplanation()).isNullOrEmpty();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void acceptMissionNullUser(){
        userService.acceptAssignedMission(null);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void acceptMissionNonExistingUser(){
        User u = createUser("DoesnoTexist");
        Mission m = MissionServiceImplTest.createMission("mission1");
        m.addUser(u);
        missionDao.addMission(m);
        userService.acceptAssignedMission(u);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void acceptNotAssignedMission(){
        userService.acceptAssignedMission(users.get(4L));
    }

    @Test
    public void rejectMission(){
        User u = prepareAstronautWithMission();
        userService.rejectAssignedMission(u,"Because cake is a lie");
        assertThat(u.missionStatusPending()).isFalse();
        assertThat(u.getMissionAccepted()).isFalse();
        assertThat(u.getMissionExplanation()).isNotNull().isNotEmpty().isEqualTo("Because cake is a lie");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void rejectMissionNullUser(){
        userService.rejectAssignedMission(null,"");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void rejectMissionNullExplanation(){
        User u = prepareAstronautWithMission();
        userService.rejectAssignedMission(u,null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void rejectMissionEmptyExplanation(){
        User u = prepareAstronautWithMission();
        userService.rejectAssignedMission(u,"");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void rejectNotAssignedMission(){
        userService.rejectAssignedMission(users.get(4L),"I have no mission assigned, but still...");
    }

    @Test
    public void updateUserName(){
        User u1 = createUser("Franta");
        u1 = userDao.addUser(u1);
        User updatedUser = createUser("Pepa");
        updatedUser.setId(u1.getId());

        assertThat(userDao.findUserById(u1.getId()).getName()).isEqualTo(u1.getName());
        userService.updateUser(updatedUser);
        assertThat(userDao.findUserById(u1.getId()).getName()).isEqualTo(updatedUser.getName());
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateNullUser(){
        userService.updateUser(null);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void updateNonExistingUser(){
        User u = createUser("NotinDB");
        userService.updateUser(u);

    }

    @Test
    public void deleteUser(){
        User u = userDao.findUserById(1L);
        int sizeBefore = userDao.findAllUsers().size();
        assertThat(userDao.findAllUsers()).contains(u);
        userService.deleteUser(u);
        assertThat(userDao.findAllUsers()).doesNotContain(u).hasSize(sizeBefore - 1);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void deleteNullUser(){
        userService.deleteUser(null);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void deleteNonExistingUser(){
        User u = createUser("NotinDB");
        userService.deleteUser(u);
    }

    @Test
    public void findAllUsers(){
        for (User u : users.values()) {
            assertThat(userService.findAllUsers()).contains(u);
        }
    }

    @Test
    public void findAllAstronauts(){
        for (User u : users.values()) {
            if(u.getRole() != Role.MANAGER){
                assertThat(userService.findAllAstronauts()).contains(u);
            }
        }
    }

    @Test
    public void findUserById(){
        assertThat(userService.findUserById(1L)).isEqualTo(users.get(1L));
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void findNonExistingUserById(){
        userService.findUserById(idCounter + 10);
    }

    @Test(expectedExceptions = ServiceDataAccessException.class)
    public void findUserByNullId(){
        userService.findUserById(null);
    }

    @Test
    public void findAllAvailableAstronauts() {
        for (User u : users.values()) {
            if(u.getRole() != Role.MANAGER && u.getMission() == null){
                assertThat(userService.findAllAstronauts()).contains(u);
            }
        }
    }

    private User prepareAstronautWithMission(){
        Mission m = MissionServiceImplTest.createMission("mission1");
        User u = users.get(3L);
        m.addUser(u);
        missionDao.addMission(m);

        assertThat(u.getMissionAccepted()).isFalse();
        assertThat(u.missionStatusPending()).isTrue();
        return u;
    }

    private boolean checkUserNames(String name){
        for(User m : users.values()){
            if(m.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    static User createUser(String name) {
        User user = new User();
        user.setName(name);
        user.setEmail(name + "@example.com");
        user.setPassword(name);
        return user;
    }
}
