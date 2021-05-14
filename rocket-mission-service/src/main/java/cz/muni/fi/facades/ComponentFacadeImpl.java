package cz.muni.fi.facades;

import cz.muni.fi.dto.component.ComponentDTO;
import cz.muni.fi.dto.component.CreateComponentDTO;
import cz.muni.fi.dto.component.UpdateComponentDTO;
import cz.muni.fi.entity.Component;
import cz.muni.fi.facade.ComponentFacade;
import cz.muni.fi.services.ComponentService;
import cz.muni.fi.services.mapper.ComponentMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the {@link ComponentFacade}.
 *
 * @author Martin Kostka
 */


public class ComponentFacadeImpl implements ComponentFacade {
    private final ComponentService componentService;

    private final ComponentMapper componentMapper;

    public ComponentFacadeImpl(ComponentService componentService, ComponentMapper componentMapper) {
        this.componentService = componentService;
        this.componentMapper = componentMapper;
    }

    @Override
    public ComponentDTO addComponent(CreateComponentDTO component) {
        Component mappedComponent = componentMapper.createComponentDTOToComponent(component);

        return componentMapper.componentToComponentDTO(componentService.addComponent(mappedComponent));
    }

    @Override
    @Transactional(readOnly=true)
    public List<ComponentDTO> findAllComponents() {
        return componentMapper.componentsToComponentDTOs(componentService.findAllComponents());
    }

    @Override
    @Transactional(readOnly=true)
    public ComponentDTO findComponentById(Long id) {
        Component c = componentService.findComponentById(id);
        return (c == null) ? null : componentMapper.componentToComponentDTO(c);
    }

    @Override
    public ComponentDTO updateComponent(UpdateComponentDTO component) {
        Component mappedComponent = componentMapper.updateComponentDTOToComponent(component);
        return componentMapper.componentToComponentDTO(componentService.updateComponent(mappedComponent));
    }

    @Override
    public void removeComponent(ComponentDTO component) {
        componentService.removeComponent(componentMapper.componentDTOToComponent(component));
    }
}
