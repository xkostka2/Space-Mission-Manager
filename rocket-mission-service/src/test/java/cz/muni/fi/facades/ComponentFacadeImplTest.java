package cz.muni.fi.facades;

import cz.muni.fi.dto.component.ComponentDTO;
import cz.muni.fi.dto.component.CreateComponentDTO;
import cz.muni.fi.dto.component.UpdateComponentDTO;
import cz.muni.fi.facade.ComponentFacade;
import cz.muni.fi.services.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author Martin Kazimir
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ComponentFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private ComponentFacade componentFacade;
    private CreateComponentDTO createComponentDTO;
    private ComponentDTO componentDTO1;
    private ComponentDTO componentDTO2;
    @BeforeMethod
    public void init() {
        createComponentDTO = getCreateComponentDTO("create component");
        componentDTO1 = componentFacade.findComponentById(componentFacade.addComponent(getCreateComponentDTO("component 1")).getId());
        componentDTO2 = componentFacade.findComponentById(componentFacade.addComponent(getCreateComponentDTO("component 2")).getId());
    }
    @AfterMethod
    public void destroyAll() {
        for (ComponentDTO component : componentFacade.findAllComponents()) {
            componentFacade.removeComponent(component);
        }
        assertThat(componentFacade.findAllComponents()).isEmpty();
        createComponentDTO = null;
        componentDTO1 = null;
        componentDTO2 = null;
    }
    @Test
    public void testAddComponent()  {
        assertThat(componentFacade.findAllComponents()).hasSize(2).contains(componentDTO1, componentDTO2);
        ComponentDTO componentDTO = componentFacade.findComponentById(componentFacade.addComponent(createComponentDTO).getId());
        assertThat(componentDTO).isEqualToIgnoringGivenFields(createComponentDTO, "id");
        componentFacade.findAllComponents();
        assertThat(componentFacade.findAllComponents()).hasSize(3).contains(componentDTO);
    }
    @Test
    public void testFindAllComponents() {
        assertThat(componentFacade.findAllComponents()).hasSize(2).contains(componentDTO1, componentDTO2);
    }
    @Test
    public void testFindComponentById() {
        assertThat(componentFacade.findComponentById(componentDTO1.getId())).isEqualTo(componentDTO1);
        assertThat(componentFacade.findComponentById(componentDTO2.getId())).isEqualTo(componentDTO2);
    }
    @Test
    public void testUpdateComponent() {
        UpdateComponentDTO updateComponentDTO = getUpdateComponentDTO(componentDTO1.getName(), componentDTO1.getId());
        updateComponentDTO.setName("update");
        componentFacade.updateComponent(updateComponentDTO);
        assertThat(componentFacade.findComponentById(updateComponentDTO.getId())).isEqualToComparingFieldByField(updateComponentDTO);
    }
    @Test
    public void testRemoveComponent() {
        assertThat(componentFacade.findAllComponents()).hasSize(2).contains(componentDTO1, componentDTO2);
        componentFacade.removeComponent(componentDTO2);
        assertThat(componentFacade.findAllComponents()).hasSize(1).contains(componentDTO1);
    }

    private CreateComponentDTO getCreateComponentDTO(String name) {
        CreateComponentDTO componentDTO = new CreateComponentDTO();
        componentDTO.setName(name);
        return componentDTO;
    }
    private UpdateComponentDTO getUpdateComponentDTO(String name, Long id) {
        UpdateComponentDTO componentDTO = new UpdateComponentDTO();
        componentDTO.setName(name);
        componentDTO.setId(id);
        return componentDTO;
    }
}
