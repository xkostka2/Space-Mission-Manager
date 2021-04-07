package cz.muni.fi.dao;

import cz.muni.fi.InMemoryDatabaseSpring;
import cz.muni.fi.entity.Rocket;
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
 * Tests for RocketDao implementation class.
 *
 * @author Martin Kostka
 */
@ContextConfiguration(classes = InMemoryDatabaseSpring.class)
public class RocketDaoImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private RocketDao rocketDao;

    private Rocket sputnik;
    private Rocket saturnV;

    @BeforeClass
    public void beforeClass() {
        sputnik = new Rocket();
        sputnik.setName("Sputnik");
        rocketDao.addRocket(sputnik);

        saturnV = new Rocket();
        saturnV.setName("Saturn V");
        rocketDao.addRocket(saturnV);
    }

    @AfterClass
    public void afterClass() {
        for (Rocket rocket : rocketDao.findAllRockets()) {
            rocketDao.removeRocket(rocket);
        }
    }

    @Test
    public void testAddRocket() {
        Rocket vulcan = new Rocket();
        vulcan.setName("Vulcan");
        rocketDao.addRocket(vulcan);

        assertThat(rocketDao.findRocketById(vulcan.getId())).isEqualTo(vulcan);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNullRocket() {
        rocketDao.addRocket(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddRocketNullName() {
        Rocket vulcan = new Rocket();
        rocketDao.addRocket(vulcan);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddRocketNotNullId() {
        Rocket vulcan = new Rocket();
        vulcan.setName("Vulcan");
        vulcan.setId(10L);
        rocketDao.addRocket(vulcan);
    }

    @Test
    public void testFindAllRockets() {
        List<Rocket> allRockets = rocketDao.findAllRockets();

        assertThat(allRockets.size()).isGreaterThanOrEqualTo(2);
        assertThat(allRockets.contains(sputnik)).isTrue();
        assertThat(allRockets.contains(saturnV)).isTrue();
    }

    @Test
    public void testFindRocketById() {
        Rocket foundRocket = rocketDao.findRocketById(sputnik.getId());

        assertThat(foundRocket).isEqualTo(sputnik);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindRocketByIdNullId() {
        rocketDao.findRocketById(null);
    }

    @Test
    public void testFindNonExistingRocket() {
        List<Rocket> allRockets = rocketDao.findAllRockets();
        Rocket max = allRockets.
                stream().
                max(Comparator.comparing(Rocket::getId)).get();

        Rocket foundRocket = rocketDao.findRocketById(max.getId() + 1);

        assertThat(foundRocket).isNull();
    }

    @Test
    public void testUpdateRocket() {
        sputnik.setName("Russian quality");
        rocketDao.updateRocket(sputnik);

        assertThat(rocketDao.findRocketById(sputnik.getId())).isEqualTo(sputnik);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateNullRocket() {
        rocketDao.updateRocket(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateRocketNullName() {
        sputnik.setName(null);
        rocketDao.updateRocket(sputnik);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateRocketNullId() {
        sputnik.setId(null);
        rocketDao.updateRocket(sputnik);
    }

    @Test
    public void testRemoveRocket() {
        Long saturnVId = saturnV.getId();
        rocketDao.removeRocket(saturnV);

        assertThat(rocketDao.findRocketById(saturnVId)).isNull();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRemoveNullRocket() {
        rocketDao.removeRocket(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRemoveRocketNullId() {
        saturnV.setId(null);
        rocketDao.removeRocket(saturnV);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeNonExistingRocket() {
        Rocket shield = new Rocket();
        shield.setName("shield");
        shield.setId(1000L);
        rocketDao.removeRocket(shield);
    }
}
