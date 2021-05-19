//package cz.muni.fi.facades;
//
//import cz.muni.fi.dto.component.ComponentDTO;
//import cz.muni.fi.dto.component.CreateComponentDTO;
//import cz.muni.fi.dto.component.UpdateComponentDTO;
//import cz.muni.fi.entity.Component;
//import cz.muni.fi.facade.ComponentFacade;
//import cz.muni.fi.services.impl.ComponentServiceImpl;
//import cz.muni.fi.services.mapper.ComponentMapperImpl;
//import cz.muni.fi.services.mapper.CycleAvoidingMappingContext;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.mockito.BDDMockito.given;
//import static org.assertj.core.api.Assertions.assertThat;
//
///**
// * @author Martin Kazimir
// */
//public class ComponentFacadeImplTest {
//
//    @Mock
//    private final ComponentServiceImpl componentService = new ComponentServiceImpl();
//
//    @Mock
//    private ComponentMapperImpl componentMapper;
//
//    @Mock
//    private CycleAvoidingMappingContext cycleAvoidingMappingContext;
//    private ComponentFacade componentFacade;
//
//    private CreateComponentDTO createComponentDTO;
//
//    private ComponentDTO componentDTO1;
//    private ComponentDTO componentDTO2;
//
//    private Component component;
//    private Component component1;
//    private Component component2;
//
//    @BeforeMethod
//    public void init() {
//        MockitoAnnotations.initMocks(this);
//        this.componentFacade = new ComponentFacadeImpl(componentService, componentMapper);
//
//        createComponentDTO = getCreateComponentDTO("create component");
//        component = new Component();
//        component.setName("create component");
//        component.setId(3L);
//
//        CreateComponentDTO createComponentDTO1 = getCreateComponentDTO("component 1");
//        CreateComponentDTO createComponentDTO2 = getCreateComponentDTO("component 2");
//
//        component1 = new Component();
//        component1.setName("component 1");
//        component1.setId(1L);
//
//        component2 = new Component();
//        component2.setName("component 2");
//        component2.setId(2L);
//
//        given(componentMapper.createComponentDTOToComponent(createComponentDTO, cycleAvoidingMappingContext)).willReturn(component);
//        given(componentMapper.createComponentDTOToComponent(createComponentDTO1, cycleAvoidingMappingContext)).willReturn(component1);
//        given(componentMapper.createComponentDTOToComponent(createComponentDTO2, cycleAvoidingMappingContext)).willReturn(component2);
//
//        given(componentService.addComponent(component)).willReturn(component);
//        given(componentService.addComponent(component1)).willReturn(component1);
//        given(componentService.addComponent(component2)).willReturn(component2);
//
//        given(componentService.findComponentById(1L)).willReturn(component1);
//        given(componentService.findComponentById(2L)).willReturn(component2);
//        given(componentService.findComponentById(3L)).willReturn(component);
//
//        ComponentDTO componentDTO = getComponentDTO("create component", 3L);
//        componentDTO1 = getComponentDTO("component 1", 1L);
//        componentDTO2 = getComponentDTO("component 2", 2L);
//
//        given(componentMapper.componentToComponentDTO(component, cycleAvoidingMappingContext)).willReturn(componentDTO);
//        given(componentMapper.componentToComponentDTO(component1, cycleAvoidingMappingContext)).willReturn(componentDTO1);
//        given(componentMapper.componentToComponentDTO(component2, cycleAvoidingMappingContext)).willReturn(componentDTO2);
//    }
//
//    @Test
//    public void testAddComponent()  {
//        List<Component> componentList = Arrays.asList(component1, component2);
//        List<ComponentDTO> componentDTOList = Arrays.asList(componentDTO1, componentDTO2);
//
//        given(componentService.findAllComponents()).willReturn(componentList);
//        given(componentMapper.componentsToComponentDTOs(componentList, cycleAvoidingMappingContext)).willReturn(componentDTOList);
//
//        assertThat(componentFacade.findAllComponents()).hasSize(2).contains(componentDTO1, componentDTO2);
//        ComponentDTO componentDTO = componentFacade.findComponentById(componentFacade.addComponent(createComponentDTO).getId());
//        assertThat(componentDTO).isEqualToIgnoringGivenFields(createComponentDTO, "id");
//
//        componentList = Arrays.asList(component1, component2, component);
//        componentDTOList = Arrays.asList(componentDTO1, componentDTO2, componentDTO);
//
//        given(componentService.findAllComponents()).willReturn(componentList);
//
//        given(componentMapper.componentsToComponentDTOs(componentList, cycleAvoidingMappingContext)).willReturn(componentDTOList);
//
//        assertThat(componentFacade.findAllComponents()).hasSize(3).contains(componentDTO);
//    }
//
//    @Test
//    public void testFindAllComponents() {
//        List<Component> componentList = Arrays.asList(component1, component2);
//        List<ComponentDTO> componentDTOList = Arrays.asList(componentDTO1, componentDTO2);
//
//        given(componentService.findAllComponents()).willReturn(componentList);
//        given(componentMapper.componentsToComponentDTOs(componentList, cycleAvoidingMappingContext)).willReturn(componentDTOList);
//
//        assertThat(componentFacade.findAllComponents()).hasSize(2).contains(componentDTO1, componentDTO2);
//    }
//
//    @Test
//    public void testFindComponentById() {
//        given(componentService.findComponentById(componentDTO1.getId())).willReturn(component1);
//        given(componentService.findComponentById(componentDTO2.getId())).willReturn(component2);
//
//        assertThat(componentFacade.findComponentById(componentDTO1.getId())).isEqualTo(componentDTO1);
//        assertThat(componentFacade.findComponentById(componentDTO2.getId())).isEqualTo(componentDTO2);
//    }
//
//    @Test
//    public void testUpdateComponent() {
//        UpdateComponentDTO updateComponentDTO = getUpdateComponentDTO(componentDTO1.getName(), componentDTO1.getId());
//        updateComponentDTO.setName("update");
//        component1.setName("update");
//        given(componentMapper.updateComponentDTOToComponent(updateComponentDTO, cycleAvoidingMappingContext)).willReturn(component1);
//        given(componentService.updateComponent(component1)).willReturn(component1);
//
//        componentFacade.updateComponent(updateComponentDTO);
//        assertThat(componentFacade.findComponentById(updateComponentDTO.getId()).getId()).isEqualTo(updateComponentDTO.getId());
//    }
//
//    @Test
//    public void testRemoveComponent() {
//        List<Component> componentList = Arrays.asList(component1, component2);
//        List<ComponentDTO> componentDTOList = Arrays.asList(componentDTO1, componentDTO2);
//
//        given(componentService.findAllComponents()).willReturn(componentList);
//        given(componentMapper.componentsToComponentDTOs(componentList, cycleAvoidingMappingContext)).willReturn(componentDTOList);
//
//        assertThat(componentFacade.findAllComponents()).hasSize(2).contains(componentDTO1, componentDTO2);
//
//        componentFacade.removeComponent(componentDTO2);
//        componentList = Collections.singletonList(component1);
//        componentDTOList = Collections.singletonList(componentDTO1);
//
//        given(componentService.findAllComponents()).willReturn(componentList);
//        given(componentMapper.componentsToComponentDTOs(componentList, cycleAvoidingMappingContext)).willReturn(componentDTOList);
//
//        assertThat(componentFacade.findAllComponents()).hasSize(1).contains(componentDTO1);
//    }
//
//    private CreateComponentDTO getCreateComponentDTO(String name) {
//        CreateComponentDTO componentDTO = new CreateComponentDTO();
//        componentDTO.setName(name);
//        return componentDTO;
//    }
//
//    private UpdateComponentDTO getUpdateComponentDTO(String name, Long id) {
//        UpdateComponentDTO componentDTO = new UpdateComponentDTO();
//        componentDTO.setName(name);
//        componentDTO.setId(id);
//        return componentDTO;
//    }
//
//    private ComponentDTO getComponentDTO(String name, Long id) {
//        ComponentDTO componentDTO = new ComponentDTO();
//        componentDTO.setName(name);
//        componentDTO.setId(id);
//        return componentDTO;
//    }
//}
