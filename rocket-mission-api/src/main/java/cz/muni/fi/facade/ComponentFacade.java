package cz.muni.fi.facade;

import cz.muni.fi.dto.component.ComponentDTO;
import cz.muni.fi.dto.component.CreateComponentDTO;
import cz.muni.fi.dto.component.UpdateComponentDTO;

import java.util.List;

/**
 * Component facade interface.
 *
 * @author Martin Kostka
 */
public interface ComponentFacade {
    /**
     * Add given component to the rocket
     * @param component component to be added
     * @return Added Component
     */
    ComponentDTO addComponent(CreateComponentDTO component);

    /**
     * Find all components
     * @return list of all components
     */
    List<ComponentDTO> findAllComponents();

    /**
     * Find component with given id
     * @param id id off component to find
     */
    ComponentDTO findComponentById(Long id);

    /**
     * Update given component
     * @param component component to be updated
     * @return Updated ComponentDTO
     */
    ComponentDTO updateComponent(UpdateComponentDTO component);

    /**
     * Remove given component
     * @param component component to be removed
     */
    void removeComponent(ComponentDTO component);
}
