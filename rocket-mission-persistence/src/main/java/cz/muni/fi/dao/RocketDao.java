package cz.muni.fi.dao;

import cz.muni.fi.entity.Rocket;

import java.util.List;

/**
 * Rocket DAO interface.
 *
 * @author Martin Hořelka (469003)
 */
public interface RocketDao {

    /**
     * @param rocket rocket to add
     */
    Rocket addRocket(Rocket rocket);

    /**
     * @return list of all rockets
     */
    List<Rocket> findAllRockets();

    /**
     * @param id id of the the rocket
     * @return rocket with the given id if exists, null otherwise
     */
    Rocket findRocketById(Long id);

    /**
     * @param rocket rocket to update
     */
    Rocket updateRocket(Rocket rocket);

    /**
     * @param rocket rocket to remove
     */
    void removeRocket(Rocket rocket);
}
