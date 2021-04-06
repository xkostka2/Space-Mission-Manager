package cz.muni.fi.dao;

import cz.muni.fi.entity.Mission;

import java.util.List;

/**
 * Data Access Object interface for Mission entity.
 *
 * @author Martin Kazimir
 */
public interface MissionDao {
    /**
     *
     * @param mission mission to add
     */
    void addMission(Mission mission);

    /**
     *
     * @return list of all missions
     */
    List<Mission> findAllMissions();

    /**
     *
     * @param id id of mission to find
     * @return mission with provided id if exists, null otherwise
     */
    Mission findMissionById(Long id);

    /**
     *
     * @param mission mission to update
     */
    void updateMission(Mission mission);

    /**
     *
     * @param mission mission to delete
     */
    void deleteMission(Mission mission);
}
