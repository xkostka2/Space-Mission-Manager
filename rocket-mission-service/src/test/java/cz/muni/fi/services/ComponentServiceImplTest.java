package cz.muni.fi.services;

import cz.muni.fi.dao.ComponentDao;
import cz.muni.fi.entity.Component;
import cz.muni.fi.services.impl.ComponentServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * @author Martin Kazimir
 */
public class ComponentServiceImplTest {
    @Mock
    private ComponentDao componentDao;

    @InjectMocks
    private final ComponentService componentService = new ComponentServiceImpl();

    private Component component1;
    private Component component2;

    private Long counter = 10L;
    private final Map<Long, Component> components = new HashMap<>();

    @BeforeClass
    public void beforeClass() {
        MockitoAnnotations.initMocks(this);

        when(componentDao.addComponent(any(Component.class))).then(invoke -> {
            Component mockedComponent = invoke.getArgumentAt(0, Component.class);

            if (mockedComponent == null) {
                throw new IllegalArgumentException("Component is null");
            }
            if (mockedComponent.getId() != null) {
                throw new IllegalArgumentException("Component is already in DB");
            }
            if (mockedComponent.getName() == null) {
                throw new IllegalArgumentException("Components name cant be null");
            }
            if (checkNameAlreadyExists(mockedComponent.getName(), -1L)) {
                throw new IllegalArgumentException("Components name already exist");
            }
            long id = counter;
            counter++;
            mockedComponent.setId(id);
            components.put(id, mockedComponent);
            return mockedComponent;
        });

        when(componentDao.updateComponent(any(Component.class))).then(invoke -> {
            Component mockedComponent = invoke.getArgumentAt(0, Component.class);

            if (mockedComponent == null) {
                throw new IllegalArgumentException("Component is null");
            }
            if (mockedComponent.getId() == null) {
                throw new IllegalArgumentException("Component is not in DB");
            }
            if (mockedComponent.getName() == null) {
                throw new IllegalArgumentException("Components name cant be null");
            }
            if (checkNameAlreadyExists(mockedComponent.getName(), mockedComponent.getId())) {
                throw new IllegalArgumentException("Components name already exist");
            }
            components.replace(mockedComponent.getId(), mockedComponent);
            return mockedComponent;
        });

        doAnswer(invoke -> {
            Component mockedComponent = invoke.getArgumentAt(0, Component.class);
            if (mockedComponent.getId() == null || !components.containsKey(mockedComponent.getId())) {
                throw new IllegalArgumentException("Component was not created yet");
            }
            components.remove(mockedComponent.getId(), mockedComponent);
            return mockedComponent;
        }).when(componentDao).removeComponent(any(Component.class));

        when(componentDao.findComponentById(any())).then(invoke -> {
            Long id = invoke.getArgumentAt(0, Long.class);
            if (id == null) {
                throw new IllegalArgumentException("id is null");
            }
            return components.get(id);
        });

        when(componentDao.findAllComponents()).then(invoke ->
                Collections.unmodifiableList(new ArrayList<>(components.values())));
    }

    private boolean checkNameAlreadyExists(String name, long id) {
        for (Component c : components.values()) {
            if (c.getName().equals(name) && c.getId() != id) {
                return true;
            }
        }
        return false;
    }

    @BeforeMethod
    public void init() {
        components.clear();

        component1 = createComponent("component1");
        component2 = createComponent("component2");

        component1.setId(1L);
        component2.setId(2L);

        components.put(1L, component1);
        components.put(2L, component2);
    }

    @Test
    public void createComponentTest() {
        int sizeBefore = components.values().size();
        Component component = createComponent("test add");
        componentService.addComponent(component);
        assertThat(components.containsValue(component));
        assertThat(components.values().size()).isEqualTo(sizeBefore + 1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createNullComponent() {
        componentService.addComponent(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createComponentWithNullName() {
        componentService.addComponent(new Component());
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createComponentDuplicatedName() {
        Component component = createComponent(component1.getName());
        componentService.addComponent(component);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createComponentWithIdNotNull() {
        Component component = createComponent("random");
        component.setId(counter + 50L);
        componentService.addComponent(component);
    }

    @Test
    public void updateComponent() {
        component1.setName("updated component");
        componentService.updateComponent(component1);
        Component updated = components.get(component1.getId());
        assertThat(updated.getName()).isEqualTo("updated component");
        assertThat(updated).isEqualToComparingFieldByField(component1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateNullComponent() {
        componentService.updateComponent(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateComponentWithNullName() {
        component1.setName(null);
        componentService.updateComponent(component1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateComponentWithDuplicatedName() {
        component1.setName(component2.getName());
        componentService.updateComponent(component1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateComponentWithNullId() {
        component1.setId(null);
        componentService.updateComponent(component1);
    }

    @Test
    public void deleteComponent() {
        int sizeBefore = components.values().size();
        componentService.removeComponent(component1);

        assertThat(components.values()).hasSize(sizeBefore - 1)
                .doesNotContain(component1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void deleteNullComponent() {
        componentService.removeComponent(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void deleteComponentWithNullId() {
        componentService.removeComponent(new Component());
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void deleteComponentNotInDb() {
        Component component = createComponent("imaginary");
        component.setId(100000L);
        componentService.removeComponent(component);
    }

    @Test
    public void findAllComponents() {
        assertThat(componentService.findAllComponents())
                .containsExactlyInAnyOrder(component1, component2);
    }

    @Test
    public void findComponentById() {
        Component component = componentService.findComponentById(component1.getId());
        assertThat(component).isEqualToComparingFieldByField(component1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void findComponentsByNullId() {
        componentService.findComponentById(null);
    }

    @Test
    public void findComponentByIdNotInDB() {
        assertThat(componentService.findComponentById(100000L)).isNull();
    }


    private Component createComponent(String name) {
        Component component = new Component();
        component.setName(name);
        return component;
    }
}
