package cz.muni.fi.controllers;


import cz.muni.fi.ApiUris;
import cz.muni.fi.dto.component.ComponentDTO;
import cz.muni.fi.dto.component.CreateComponentDTO;
import cz.muni.fi.dto.component.UpdateComponentDTO;
import cz.muni.fi.enums.ComponentType;
import cz.muni.fi.exceptions.ResourceAlreadyExistsException;
import cz.muni.fi.exceptions.ResourceNotFoundException;
import cz.muni.fi.facade.ComponentFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Martin Kazimir
 */

@RestController
@RequestMapping(ApiUris.ROOT_URI_COMPONENTS)
public class ComponentController {

    private final static Logger logger = Logger.getLogger(ComponentController.class.getName());
    
    private final ComponentFacade componentFacade;

    @Autowired
    public ComponentController(ComponentFacade componentFacade) {
        this.componentFacade = componentFacade;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ComponentDTO createComponent(@RequestBody CreateComponentDTO componentCreateDTO) {

        logger.log(Level.INFO, "[REST] creating component");

        try {
            return componentFacade.findComponentById(componentFacade.addComponent(componentCreateDTO).getId());
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new ResourceAlreadyExistsException();
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ComponentDTO> findAllComponents() {

        logger.log(Level.INFO, "[REST] finding all components");

        return componentFacade.findAllComponents();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ComponentDTO findComponentById(@PathVariable("id") Long id) {

        logger.log(Level.INFO, "[REST] finding component " + id);

        ComponentDTO componentDTO = componentFacade.findComponentById(id);
        if (componentDTO == null) {
            throw new ResourceNotFoundException();
        }
        return componentDTO;
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ComponentDTO updateComponent(@RequestBody UpdateComponentDTO updateComponentDTO) {

        logger.log(Level.INFO, "[REST] updating component" + updateComponentDTO.getId());

        try {
            componentFacade.updateComponent(updateComponentDTO);
            return componentFacade.findComponentById(updateComponentDTO.getId());
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new ResourceAlreadyExistsException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ComponentDTO> removeComponent(@PathVariable("id") long id) {

        logger.log(Level.INFO, "[REST] deleting component" + id);

        ComponentDTO component = componentFacade.findComponentById(id);
        if (component == null) {
            throw new ResourceNotFoundException();
        }

        componentFacade.removeComponent(component);
        return componentFacade.findAllComponents();
    }


    @RequestMapping(value = "/available", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ComponentDTO> findAllAvailableComponents() {

        logger.log(Level.INFO, "[REST] finding all available components");

        List<ComponentDTO> availableComponentsDTO = new ArrayList<>();
        for (ComponentDTO componentDTO : componentFacade.findAllComponents()) {
            if (componentDTO.getType().equals(ComponentType.MISSION) && componentDTO.getMission() == null) {
                availableComponentsDTO.add(componentDTO);
            }
            if (componentDTO.getType().equals(ComponentType.ROCKET) && componentDTO.getRocket() == null) {
                availableComponentsDTO.add(componentDTO);
            }
        }
        return Collections.unmodifiableList(availableComponentsDTO);
    }
}
