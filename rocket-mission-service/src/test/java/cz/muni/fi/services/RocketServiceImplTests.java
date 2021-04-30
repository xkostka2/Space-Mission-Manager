package cz.muni.fi.services;

import cz.muni.fi.dao.RocketDao;
import cz.muni.fi.entity.Rocket;
import cz.muni.fi.services.impl.RocketServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class RocketServiceImplTests {
    @Mock
    private RocketDao rocketDao;

    @Autowired
    @InjectMocks
    private RocketService rocketService = new RocketServiceImpl();


    private Long counter = 10L;
    private Map<Long, Rocket> rockets = new HashMap<>();


    private Rocket rocket1;
    private Rocket rocket2;
    private Rocket rocket3;


    @BeforeClass
    public void beforeClass() {
        MockitoAnnotations.initMocks(this);

        when(rocketDao.addRocket(any(Rocket.class))).then(invoke -> {
            Rocket mockedRocket = invoke.getArgumentAt(0, Rocket.class);
            if (mockedRocket == null) {
                throw new IllegalArgumentException("Rocket is null");
            }
            if (mockedRocket.getId() != null) {
                throw new IllegalArgumentException("Rocket is already in DB");
            }
            if (mockedRocket.getName() == null) {
                throw new IllegalArgumentException("Rocket name is null");
            }
            if (checkRocketsNameDuplicity(mockedRocket.getName(), -1L)) {
                throw new IllegalArgumentException("Rocket with this name already exist");
            }
            long index = counter;
            counter++;
            mockedRocket.setId(index);
            rockets.put(index, mockedRocket);
            return mockedRocket;
        });

        when(rocketDao.updateRocket(any(Rocket.class))).then(invoke -> {
            Rocket mockedRocket = invoke.getArgumentAt(0, Rocket.class);
            if (mockedRocket == null) {
                throw new IllegalArgumentException("Rocket is null");
            }
            if (mockedRocket.getId() == null) {
                throw new IllegalArgumentException("Rocket is not in DB");
            }
            if (mockedRocket.getName() == null) {
                throw new IllegalArgumentException("Rocket name is null");
            }
            if (checkRocketsNameDuplicity(mockedRocket.getName(), mockedRocket.getId())) {
                throw new IllegalArgumentException("Rocket with this name already exist");
            }
            rockets.replace(mockedRocket.getId(), mockedRocket);
            return mockedRocket;
        });

        when(rocketDao.findRocketById(anyLong())).then(invoke -> {
            Long id = invoke.getArgumentAt(0, Long.class);
            if (id == null) {
                throw new IllegalArgumentException("id is null");
            }
            return rockets.get(id);
        });

        when(rocketDao.findAllRockets())
                .then(invoke -> Collections.unmodifiableList(new ArrayList<>(rockets.values())));
    }

    @BeforeMethod
    public void beforeTest() {
        rockets.clear();
        rocket1 = new Rocket();
        rocket2 = new Rocket();
        rocket3 = new Rocket();

        rocket1.setName("Rocket1");
        rocket2.setName("Rocket2");
        rocket3.setName("Rocket3");

        rocket1.setId(1L);
        rocket2.setId(2L);
        rocket3.setId(3L);

        rockets.put(1L, rocket1);
        rockets.put(2L, rocket2);
        rockets.put(3L, rocket3);
    }


    @Test
    public void createRocket() throws DataAccessException {
        int sizeBefore = rockets.values().size();
        Rocket rocket = new Rocket();
        rocket.setName("Name");
        rocketService.addRocket(rocket);
        assertThat(rockets.values()).hasSize(sizeBefore + 1)
                .contains(rocket);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createNullRocket() {
        rocketService.addRocket(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createRocketNullName() {
        rocketService.addRocket(new Rocket());
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createRocketDuplicateName() {
        Rocket rocket = rocket1;
        rocketService.addRocket(rocket);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createRocketWithIdNotNull() {
        Rocket rocket = new Rocket();
        rocket.setName("Name");
        rocket.setId(1000L);
        rocketService.addRocket(rocket);
    }

    @Test
    public void updateRocket() throws DataAccessException {
        rocket1.setName("updated rocket");
        rocketService.updateRocket(rocket1);

        Rocket updatedRocket = rockets.get(rocket1.getId());

        assertThat(updatedRocket.getName()).isEqualTo("updated rocket");
        assertThat(updatedRocket).isEqualToComparingFieldByField(rocket1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateNullRocket() {
        rocketService.updateRocket(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateRocketNullName() {
        rocket1.setName(null);
        rocketService.updateRocket(rocket1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateRocketDuplicatedName() {
        rocket2.setName(rocket1.getName());
        rocketService.updateRocket(rocket2);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateRocketNullId() {
        rocketService.updateRocket(new Rocket());
    }

    @Test
    public void deleteRocket() {
        int sizeBefore = rockets.values().size();
        rocketService.removeRocket(rocket1);

        assertThat(rockets.values()).doesNotContain(rocket1).hasSize(sizeBefore - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNullRocket() {
        rocketService.removeRocket(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void deleteRocketWithNullId() {
        Rocket rocket = new Rocket();
        rocketService.removeRocket(rocket);
    }

    @Test
    public void deleteRocketNotInDB() {
        int sizeBefore = rockets.values().size();
        Rocket rocket = new Rocket();
        rocket.setName("rocket");
        rocket.setId(counter * 2L);
        rocketService.removeRocket(rocket);

        assertThat(rockets.values()).hasSize(sizeBefore)
                .doesNotContain(rocket);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void findRocketWithNullId() {
        rocketService.findRocketById(null);
    }

    @Test
    public void findRocketNotInDB() {
        assertThat(rocketService.findRocketById(10000L)).isNull();
    }

    @Test
    public void findRocketById() {
        long id = rocket1.getId();
        Rocket rocket = rocketService.findRocketById(id);

        assertThat(rocket).isEqualToComparingFieldByField(rocket1);
    }

    @Test
    public void findAllRockets() {
        assertThat(rocketService.findAllRockets())
                .containsOnly(rocket1, rocket2, rocket3);
    }

    private boolean checkRocketsNameDuplicity(String name, long id) {
        for (Rocket s : rockets.values()) {
            if (s.getName().equals(name) && s.getId() != id) {
                return true;
            }
        }
        return false;
    }
}
