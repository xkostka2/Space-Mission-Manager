package cz.muni.fi.facades;

import cz.muni.fi.dto.component.ComponentDTO;
import cz.muni.fi.dto.component.CreateComponentDTO;
import cz.muni.fi.dto.component.UpdateComponentDTO;
import cz.muni.fi.entity.Component;
import cz.muni.fi.facade.ComponentFacade;
import cz.muni.fi.services.ComponentService;
import cz.muni.fi.services.mapper.ComponentMapper;
import cz.muni.fi.services.mapper.CycleAvoidingMappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the {@link ComponentFacade}.
 *
 * @author Martin Kostka
 */
@Service
@Transactional
public class ComponentFacadeImpl implements ComponentFacade {

    private CycleAvoidingMappingContext cycleAvoidingMappingContext;
    private final ComponentService componentService;
    private final ComponentMapper componentMapper;

    @Autowired
    public ComponentFacadeImpl(ComponentService componentService, ComponentMapper componentMapper) {
        this.componentService = componentService;
        this.componentMapper = componentMapper;
        this.cycleAvoidingMappingContext = new CycleAvoidingMappingContext();
    }

    @Override
    public ComponentDTO addComponent(CreateComponentDTO component) {
        Component mappedComponent = componentMapper.createComponentDTOToComponent(component, cycleAvoidingMappingContext);

        return componentMapper.componentToComponentDTO(componentService.addComponent(mappedComponent), cycleAvoidingMappingContext);
    }

    @Override
    @Transactional(readOnly=true)
    public List<ComponentDTO> findAllComponents() {
        return componentMapper.componentsToComponentDTOs(componentService.findAllComponents(), cycleAvoidingMappingContext);
    }

    @Override
    @Transactional(readOnly=true)
    public ComponentDTO findComponentById(Long id) {
        Component c = componentService.findComponentById(id);
        return (c == null) ? null : componentMapper.componentToComponentDTO(c, cycleAvoidingMappingContext);
    }

    @Override
    public ComponentDTO updateComponent(UpdateComponentDTO component) {
        Component mappedComponent = componentMapper.updateComponentDTOToComponent(component, cycleAvoidingMappingContext);
        return componentMapper.componentToComponentDTO(componentService.updateComponent(mappedComponent), cycleAvoidingMappingContext);
    }

    @Override
    public void removeComponent(ComponentDTO component) {
        componentService.removeComponent(componentMapper.componentDTOToComponent(component, cycleAvoidingMappingContext));
    }
}
