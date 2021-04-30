package cz.muni.fi.services;

import cz.muni.fi.entity.Rocket;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Rocket Service Interface.
 *
 * @author Tomas Bouma (469275)
 */
public interface RocketService {
    /**
     * Create new entity in the database
     * @param rocket Rocket to add
     */
    Rocket addRocket(Rocket rocket) throws DataAccessException;

    /**
     * Finds all entities of type Rocket
     */
    List<Rocket> findAllRockets() throws DataAccessException;

    /**
     * Find entity with given id in the database
     * @param id id of component to find
     */
    Rocket findRocketById(Long id) throws DataAccessException;

    /**
     * Update entity in the database
     * @param rocket rocket to update
     */
    Rocket updateRocket(Rocket rocket) throws DataAccessException;

    /**
     * Delete entity from the database
     * @param rocket rocket to remove
     */
    void removeRocket(Rocket rocket) throws DataAccessException;

}