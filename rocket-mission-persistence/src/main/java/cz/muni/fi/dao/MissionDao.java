package cz.muni.fi.dao;

import cz.muni.fi.entity.Mission;
import cz.muni.fi.enums.MissionProgress;

import java.util.List;

/**
 * Data Access Object interface for Mission entity.
 *
 * @author Martin Kazimir
 */
public interface MissionDao {
    /**
     * Persists mission into database
     *
     * @param mission mission to add
     */
    void addMission(Mission mission);

    /**
     * Returns all missions
     *
     * @return list of all missions
     */
    List<Mission> findAllMissions();

    /**
     * Returns all missions with given progress state
     *
     * @param progress progress of missions to find
     * @return list of all missions with given progress
     */
    List<Mission> findAllMissions(MissionProgress progress);

    /**
     * Returns mission with given id
     *
     * @param id id of mission to find
     * @return mission with provided id if exists, null otherwise
     */
    Mission findMissionById(Long id);

    /**
     * Updates mission in database
     *
     * @param mission mission to update
     */
    void updateMission(Mission mission);

    /**
     * Deletes mission from database
     *
     * @param mission mission to remove
     */
    void removeMission(Mission mission);
}
