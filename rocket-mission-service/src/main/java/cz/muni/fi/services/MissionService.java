package cz.muni.fi.services;

import cz.muni.fi.entity.Mission;
import cz.muni.fi.enums.MissionProgress;
import org.springframework.dao.DataAccessException;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * An interface that defines a service access to the {@link Mission} entity.
 *
 * @author Martin Kazimir
 */
public interface MissionService {
    /**
     * Persist mission into database.
     *
     * @param mission instance of mission
     * @return added mission
     */
    Mission addMission(Mission mission) throws DataAccessException;

    /**
     * Update mission in database
     *
     * @param mission instance of mission
     * @return updated mission
     */
    Mission updateMission(Mission mission) throws DataAccessException;

    /**
     * Delete mission from database
     *
     * @param mission instance of mission
     */
    void deleteMission(Mission mission) throws DataAccessException;

    /**
     * Find all missions
     *
     * @return List of all missions
     */
    List<Mission> findAllMissions() throws DataAccessException;

    /**
     * Find all missions by progress
     * @param progress progress of the mission
     * @return list of the missions with given progress
     */
    List<Mission> findAllMissions(MissionProgress progress) throws DataAccessException;

    /**
     * Find mission by id
     *
     * @param id mission id
     * @return Mission instance of mission
     */
    Mission findMissionById(Long id) throws DataAccessException;

    /**
     * Archive given mission
     * Set the end date and result of the mission
     *
     * @param mission mission to archive
     * @param endDate end date of the mission, must be in past
     * @param archiveComment final comment of the mission
     */
    void archive(Mission mission, ZonedDateTime endDate, String archiveComment);
}
