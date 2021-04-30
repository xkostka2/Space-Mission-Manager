package cz.muni.fi.facade;

import cz.muni.fi.dto.create.CreateRocketDTO;
import cz.muni.fi.dto.RocketDTO;
import cz.muni.fi.dto.update.UpdateRocketDTO;

import java.util.List;

/**
 * Rocket facade interface.
 *
 * @author Tomas Bouma (469275)
 */


public interface RocketFacade {
    /**
     * Create new entity in the database
     *
     * @param rocket Rocket to add
     */
    RocketDTO addRocket(CreateRocketDTO rocket);

    /**
     * Finds all entities of type Rocket
     */
    List<RocketDTO> findAllRockets();

    /**
     * Find entity with given id in the database
     *
     * @param id id of component to find
     */
    RocketDTO findRocketById(Long id);

    /**
     * Update entity in the database
     *
     * @param rocket rocket to update
     */
    RocketDTO updateRocket(UpdateRocketDTO rocket);

    /**
     * Delete entity from the database
     *
     * @param rocket rocket to remove
     */
    void removeRocket(RocketDTO rocket);
}