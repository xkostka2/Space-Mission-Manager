package cz.muni.fi.facades;

import cz.muni.fi.dto.component.ComponentDTO;
import cz.muni.fi.dto.rocket.RocketDTO;
import cz.muni.fi.dto.component.CreateComponentDTO;
import cz.muni.fi.dto.rocket.CreateRocketDTO;
import cz.muni.fi.dto.rocket.UpdateRocketDTO;
import cz.muni.fi.facade.ComponentFacade;
import cz.muni.fi.facade.RocketFacade;
import cz.muni.fi.services.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * Created by Martin Kostka
 *
 * @author Martin Kostka
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class RocketFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private RocketFacade rocketFacade;
    @Autowired
    private ComponentFacade componentFacade;
    private CreateRocketDTO createRocketDTO;
    private RocketDTO rocketDTO1;
    private RocketDTO rocketDTO2;
    @BeforeMethod
    public void beforeMethod() {
        createRocketDTO = getCreateRocketDTO("create rocket");
        ComponentDTO comp1 = componentFacade.addComponent(getCreateComponentDTO("component1"));
        ComponentDTO comp2 = componentFacade.addComponent(getCreateComponentDTO("component2"));
        rocketDTO1 = rocketFacade.findRocketById(rocketFacade.addRocket(getCreateRocketDTO("rocket1")).getId());
        rocketDTO1.setRequiredComponents(Collections.singleton(comp1));
        rocketDTO2 = rocketFacade.findRocketById(rocketFacade.addRocket(getCreateRocketDTO("rocket2")).getId());
        rocketDTO2.setRequiredComponents(Collections.singleton(comp2));
    }
    private CreateComponentDTO getCreateComponentDTO(String name) {
        CreateComponentDTO componentDTO = new CreateComponentDTO();
        componentDTO.setName(name);
        return componentDTO;
    }
    private CreateRocketDTO getCreateRocketDTO(String name) {
        CreateRocketDTO rocketDTO = new CreateRocketDTO();
        rocketDTO.setName(name);
        return rocketDTO;
    }
    private UpdateRocketDTO getUpdateRocketDTO(String name, Long id) {
        UpdateRocketDTO rocketDTO = new UpdateRocketDTO();
        rocketDTO.setName(name);
        rocketDTO.setId(id);
        return rocketDTO;
    }
    @AfterMethod
    public void afterMethod() {
        for (RocketDTO rocket :
                rocketFacade.findAllRockets()) {
            rocketFacade.removeRocket(rocket);
        }
        for (ComponentDTO craftComponent :
                componentFacade.findAllComponents()) {
            componentFacade.removeComponent(craftComponent);
        }
        assertThat(rocketFacade.findAllRockets()).isEmpty();
        assertThat(componentFacade.findAllComponents()).isEmpty();
        createRocketDTO = null;
        rocketDTO1 = null;
        rocketDTO2 = null;
    }
    @Test
    public void testAddRocket() {
        assertThat(rocketFacade.findAllRockets()).hasSize(2);
        RocketDTO rocketDTO = rocketFacade.findRocketById(rocketFacade.addRocket(createRocketDTO).getId());
        assertThat(rocketDTO).isEqualToIgnoringGivenFields(createRocketDTO, "id");
        assertThat(rocketFacade.findAllRockets()).contains(rocketDTO);
    }
    @Test
    public void testRemoveRocket() {
        assertThat(rocketFacade.findAllRockets()).hasSize(2).contains(rocketDTO1, rocketDTO2);
        rocketFacade.removeRocket(rocketDTO1);
        assertThat(rocketFacade.findAllRockets()).hasSize(1).contains(rocketDTO2);
    }
    @Test
    public void testFindAllRockets() {
        assertThat(rocketFacade.findAllRockets()).hasSize(2).contains(rocketDTO1, rocketDTO2);
    }
    @Test
    public void testFindRocketById() {
        assertThat(rocketFacade.findRocketById(rocketDTO1.getId())).isEqualTo(rocketDTO1);
        assertThat(rocketFacade.findRocketById(rocketDTO2.getId())).isEqualTo(rocketDTO2);
    }
    @Test
    public void testUpdateRocket() {
        UpdateRocketDTO rocket = getUpdateRocketDTO(rocketDTO1.getName(), rocketDTO1.getId());
        rocket.setRequiredComponents(rocketDTO1.getRequiredComponents());
        rocket.setName("UPDATED");
        rocketFacade.updateRocket(rocket);
        assertThat(rocketFacade.findRocketById(rocket.getId())).isEqualToComparingFieldByField(rocket);
    }
}