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
        rocketBooster = new Component();
        rocketBooster.setName("rocket booster");
        rocketBooster.setType(ComponentType.ROCKET);
        componentDao.addComponent(rocketBooster);

        foodSupplies = new Component();
        foodSupplies.setName("food supplies");
        foodSupplies.setType(ComponentType.MISSION);
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
        componentDao.addComponent(stabilizer);

        assertThat(componentDao.findComponentById(stabilizer.getId())).isEqualTo(stabilizer);
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

    @Test
    public void testUpdateComponent() {
        rocketBooster.setName("solid rocket booster");
        componentDao.updateComponent(rocketBooster);

        assertThat(componentDao.findComponentById(rocketBooster.getId())).isEqualTo(rocketBooster);
    }

    @Test
    public void testRemoveComponent() {
        Long foodSuppliesId = foodSupplies.getId();
        componentDao.removeComponent(foodSupplies);

        assertThat(componentDao.findComponentById(foodSuppliesId)).isNull();
    }
}
