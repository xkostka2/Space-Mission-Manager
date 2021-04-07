package cz.muni.fi.dao;

import cz.muni.fi.InMemoryDatabaseSpring;
import cz.muni.fi.entity.User;
import cz.muni.fi.enums.LevelOfExperience;
import cz.muni.fi.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for UserDao implementation class.
 *
 * @author Martin Hořelka (469003)
 */
@ContextConfiguration(classes = InMemoryDatabaseSpring.class)
public class UserDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserDao userDao;

    private User manager;
    private User astronaut;
    private User jimmy;

    @BeforeClass
    public void beforeClass() {
        manager = new User();
        manager.setName("Manager");
        manager.setRole(Role.MANAGER);
        manager.setLevelOfExperience(LevelOfExperience.ROOKIE);
        manager.setEmail("manager@space-mission.com");
        manager.setPassword("mAnAgEr");
        userDao.addUser(manager);

        astronaut = new User();
        astronaut.setName("Astronaut");
        astronaut.setRole(Role.ASTRONAUT);
        astronaut.setLevelOfExperience(LevelOfExperience.KING_OF_GALAXY);
        astronaut.setEmail("astronaut@space-mission.com");
        astronaut.setPassword("AstrOnAUt");
        userDao.addUser(astronaut);

        jimmy = new User();
        jimmy.setName("Jimmy");
        jimmy.setRole(Role.ASTRONAUT);
        jimmy.setLevelOfExperience(LevelOfExperience.VETERAN);
        jimmy.setEmail("jimmy@space-mission.com");
        jimmy.setPassword("jImmy");
        userDao.addUser(jimmy);
    }

    @AfterClass
    public void afterClass() {
        for (User user : userDao.findAllUsers()) {
            userDao.deleteUser(user);
        }
    }

    @Test
    public void testAddUser() {
        User jack = new User();
        jack.setName("Jack");
        jack.setRole(Role.MANAGER);
        jack.setLevelOfExperience(LevelOfExperience.VETERAN);
        jack.setEmail("jack@space-mission.com");
        jack.setPassword("jAck");
        userDao.addUser(jack);

        assertThat(userDao.findUserById(jack.getId())).isEqualTo(jack);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNullUser() {
        userDao.addUser(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddUserNullName() {
        User jack = new User();
        jack.setRole(Role.MANAGER);
        jack.setLevelOfExperience(LevelOfExperience.VETERAN);
        jack.setEmail("jack@space-mission.com");
        jack.setPassword("jAck");
        userDao.addUser(jack);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddUserNotNullId() {
        User jack = new User();
        jack.setId(1L);
        jack.setName("Jack");
        jack.setRole(Role.MANAGER);
        jack.setLevelOfExperience(LevelOfExperience.VETERAN);
        jack.setEmail("jack@space-mission.com");
        jack.setPassword("jAck");
        userDao.addUser(jack);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddUserNullRole() {
        User jack = new User();
        jack.setName("Jack");
        jack.setLevelOfExperience(LevelOfExperience.VETERAN);
        jack.setEmail("jack@space-mission.com");
        jack.setPassword("jAck");
        userDao.addUser(jack);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddUserNullLevelOfExperience() {
        User jack = new User();
        jack.setName("Jack");
        jack.setRole(Role.MANAGER);
        jack.setEmail("jack@space-mission.com");
        jack.setPassword("jAck");
        userDao.addUser(jack);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddUserNullEmail() {
        User jack = new User();
        jack.setName("Jack");
        jack.setRole(Role.MANAGER);
        jack.setLevelOfExperience(LevelOfExperience.VETERAN);
        jack.setPassword("jAck");
        userDao.addUser(jack);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddUserWrongPatternEmail() {
        User jack = new User();
        jack.setName("Jack");
        jack.setRole(Role.MANAGER);
        jack.setLevelOfExperience(LevelOfExperience.VETERAN);
        jack.setEmail("jackspace-mission.com");
        jack.setPassword("jAck");
        userDao.addUser(jack);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddUserNullPassword() {
        User jack = new User();
        jack.setName("Jack");
        jack.setRole(Role.MANAGER);
        jack.setLevelOfExperience(LevelOfExperience.VETERAN);
        jack.setEmail("jack@space-mission.com");
        userDao.addUser(jack);
    }

    @Test
    public void testUpdateUser() {
        manager.setName("CEO");
        userDao.updateUser(manager);

        assertThat(userDao.findUserById(manager.getId())).isEqualTo(manager);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateNullUser() {
        userDao.updateUser(null);
    }

    @Test
    public void testDeleteUser() {
        Long astronautId = astronaut.getId();
        userDao.deleteUser(astronaut);

        assertThat(userDao.findUserById(astronautId)).isNull();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNullUser() {
        userDao.deleteUser(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteUserNullId() {
        astronaut.setId(null);
        userDao.deleteUser(astronaut);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNonExistingUser() {
        User jack = new User();
        jack.setName("shield");
        jack.setId(100L);
        userDao.deleteUser(jack);
    }

    @Test
    public void testFindAllUsers() {
        List<User> allUsers = userDao.findAllUsers();

        assertThat(allUsers.contains(manager)).isTrue();
        assertThat(allUsers.contains(jimmy)).isTrue();
    }

    @Test
    public void testFindAllAstronauts() {
        List<User> allAstronauts = userDao.findAllAstronauts();

        assertThat(allAstronauts.contains(manager)).isFalse();
        assertThat(allAstronauts.contains(jimmy)).isTrue();
    }

    @Test
    public void testFindAllAvailableAstronauts() {
        List<User> allAvailableAstronauts = userDao.findAllAvailableAstronauts();

        assertThat(allAvailableAstronauts.contains(manager)).isFalse();
        assertThat(allAvailableAstronauts.contains(jimmy)).isTrue();
    }

    @Test
    public void testFindUserById() {
        User foundUser = userDao.findUserById(manager.getId());

        assertThat(foundUser).isEqualTo(manager);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindUserByIdNullId() {
        userDao.findUserById(null);
    }

    @Test
    public void testFindNonExistingUser() {
        List<User> allUsers = userDao.findAllUsers();
        User max = allUsers
                .stream()
                .max(Comparator.comparing(User::getId)).get();

        User foundUser = userDao.findUserById(max.getId() + 1);

        assertThat(foundUser).isNull();
    }

    @Test
    public void testFindUserByEmail() {
        User foundUser = userDao.findUserByEmail(manager.getEmail());

        assertThat(foundUser).isEqualTo(manager);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindUserByEmailNullEmail() {
        userDao.findUserByEmail(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindUserByWrongPatternEmail() {
        userDao.findUserByEmail("wrong-email.com");
    }

    @Test
    public void testFindNonExistingUserByEmail() {
        User foundUser = userDao.findUserByEmail("non.existing@space-mission.com");

        assertThat(foundUser).isNull();
    }
}
