package cz.muni.fi.facades;

import cz.muni.fi.dto.ComponentDTO;
import cz.muni.fi.dto.create.CreateComponentDTO;
import cz.muni.fi.dto.update.UpdateComponentDTO;
import cz.muni.fi.entity.Component;
import cz.muni.fi.facade.ComponentFacade;
import cz.muni.fi.services.BeanMappingService;
import cz.muni.fi.services.ComponentService;
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
    @Autowired
    ComponentService componentService;

    @Autowired
    BeanMappingService beanMappingService;

    @Override
    public ComponentDTO addComponent(CreateComponentDTO component) {
        Component mappedComponent = beanMappingService.mapTo(component, Component.class);
        return beanMappingService.mapTo(componentService.addComponent(mappedComponent), ComponentDTO.class);
    }

    @Override
    public List<ComponentDTO> findAllComponents() {
        return beanMappingService.mapTo(componentService.findAllComponents(), ComponentDTO.class);
    }

    @Override
    public ComponentDTO findComponentById(Long id) {
        Component c = componentService.findComponentById(id);
        return (c == null) ? null : beanMappingService.mapTo(c, ComponentDTO.class);
    }

    @Override
    public ComponentDTO updateComponent(UpdateComponentDTO component) {
        Component mappedComponent = beanMappingService.mapTo(component, Component.class);
        return beanMappingService.mapTo(componentService.updateComponent(mappedComponent), ComponentDTO.class);
    }

    @Override
    public void removeComponent(ComponentDTO component) {
        componentService.removeComponent(beanMappingService.mapTo(component, Component.class));
    }
}