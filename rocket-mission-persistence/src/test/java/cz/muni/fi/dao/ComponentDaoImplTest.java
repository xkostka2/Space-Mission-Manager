package cz.muni.fi.dao;

import cz.muni.fi.InMemoryDatabaseSpring;
import cz.muni.fi.entity.Component;
import cz.muni.fi.enums.ComponentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * Tests for ComponentDao implementation class.
 *
 * @author Martin Kazimir
 */
@ContextConfiguration(classes = InMemoryDatabaseSpring.class)
public class ComponentDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ComponentDao componentDao;

    private Component rocketBooster;
    private Component foodSupplies;

    @BeforeClass
    public void beforeClass() {
        LocalDateTime localDateTime = LocalDateTime.now();
        rocketBooster = new Component();
        rocketBooster.setName("rocket booster");
        rocketBooster.setType(ComponentType.ROCKET);
        rocketBooster.setReadyDate(localDateTime.plusMonths(6).atZone(ZoneId.of("GMT")));
        rocketBooster.setReadyToUse(false);
        componentDao.addComponent(rocketBooster);

        foodSupplies = new Component();
        foodSupplies.setName("food supplies");
        foodSupplies.setType(ComponentType.MISSION);
        foodSupplies.setReadyDate(localDateTime.plusMonths(5).atZone(ZoneId.of("GMT")));
        foodSupplies.setReadyToUse(false);
        componentDao.addComponent(foodSupplies);
    }

    @AfterClass
    public void afterClass() {
        for (Component component : componentDao.findAllComponents()) {
            componentDao.removeComponent(component);
        }
    }

    @Test
    public void testAddComponent() {
        Component stabilizer = new Component();
        stabilizer.setName("stabilizer");
        stabilizer.setType(ComponentType.ROCKET);
        stabilizer.setReadyDate(LocalDateTime.now().atZone(ZoneId.of("GMT")));
        stabilizer.setReadyToUse(false);
        componentDao.addComponent(stabilizer);

        assertThat(componentDao.findComponentById(stabilizer.getId())).isEqualTo(stabilizer);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNullComponent() {
        componentDao.addComponent(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddComponentNullName() {
        Component stabilizer = new Component();
        stabilizer.setType(ComponentType.ROCKET);
        stabilizer.setReadyDate(LocalDateTime.now().atZone(ZoneId.of("GMT")));
        stabilizer.setReadyToUse(false);
        componentDao.addComponent(stabilizer);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddComponentNotNullId() {
        Component stabilizer = new Component();
        stabilizer.setName("stabilizer");
        stabilizer.setType(ComponentType.ROCKET);
        stabilizer.setReadyDate(LocalDateTime.now().atZone(ZoneId.of("GMT")));
        stabilizer.setReadyToUse(false);
        stabilizer.setId(10L);
        componentDao.addComponent(stabilizer);
    }

    @Test
    public void testFindAllComponents() {
        List<Component> allComponents = componentDao.findAllComponents();

        assertThat(allComponents.size()).isGreaterThanOrEqualTo(2);
        assertThat(allComponents.contains(rocketBooster)).isTrue();
        assertThat(allComponents.contains(foodSupplies)).isTrue();
    }

    @Test
    public void testFindComponentById() {
        Component foundComponent = componentDao.findComponentById(rocketBooster.getId());

        assertThat(foundComponent).isEqualTo(rocketBooster);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindComponentByIdNullId() {
        componentDao.findComponentById(null);
    }

    @Test
    public void testFindNonExistingComponent() {
        List<Component> allComponents = componentDao.findAllComponents();
        Component max = allComponents.
                stream().
                max(Comparator.comparing(Component::getId)).get();

        Component foundComponent = componentDao.findComponentById(max.getId() + 1);

        assertThat(foundComponent).isNull();
    }

    @Test
    public void testUpdateComponent() {
        rocketBooster.setName("solid rocket booster");
        componentDao.updateComponent(rocketBooster);

        assertThat(componentDao.findComponentById(rocketBooster.getId())).isEqualTo(rocketBooster);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateNullComponent() {
        componentDao.updateComponent(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateComponentNullName() {
        rocketBooster.setName(null);
        componentDao.updateComponent(rocketBooster);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateComponentNullId() {
        rocketBooster.setId(null);
        componentDao.updateComponent(rocketBooster);
    }

    @Test
    public void testRemoveComponent() {
        Long foodSuppliesId = foodSupplies.getId();
        componentDao.removeComponent(foodSupplies);

        assertThat(componentDao.findComponentById(foodSuppliesId)).isNull();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRemoveNullComponent() {
        componentDao.removeComponent(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRemoveComponentNullId() {
        foodSupplies.setId(null);
        componentDao.removeComponent(foodSupplies);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeNonExistingComponent() {
        Component shield = new Component();
        shield.setName("shield");
        shield.setId(1000L);
        componentDao.removeComponent(shield);
    }
}
