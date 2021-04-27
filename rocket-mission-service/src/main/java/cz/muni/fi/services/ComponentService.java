package cz.muni.fi.services;

import cz.muni.fi.entity.Component;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * An interface that defines a service access to the {@link Component} entity.
 *
 * @author Martin Kostka
 */
public interface ComponentService {
    /**
     * Create new Component in the database
     * @param component Component to add
     * @return Newly added Component
     */
    Component addComponent(Component component) throws DataAccessException;

    List<Component> findAllComponents() throws DataAccessException;

    /**
     * Find Component with given id in the database
     * @param id id of component to find
     */
    Component findComponentById(Long id) throws DataAccessException;

    /**
     * Update Component in the database
     * @param component component to update
     * @return Updated Component
     */
    Component updateComponent(Component component) throws DataAccessException;

    /**
     * Delete Component from the database
     * @param component component to remove
     */
    void removeComponent(Component component) throws DataAccessException;
}
