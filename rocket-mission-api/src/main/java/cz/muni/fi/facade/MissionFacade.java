package cz.muni.fi.facade;

import cz.muni.fi.dto.MissionDTO;
import cz.muni.fi.enums.MissionProgress;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * MissionDTO facade interface.
 *
 * @author Martin Kazimir
 */
public interface MissionFacade {
    /**
     * Save given mission
     *
     * @param mission instance of mission
     * @return added mission
     */
    MissionDTO addMission(MissionDTO mission);

    /**
     * Update given mission
     *
     * @param mission instance of mission
     * @return updated mission
     */
    MissionDTO updateMission(MissionDTO mission);

    /**
     * Delete given mission
     *
     * @param mission instance of mission
     */
    void deleteMission(MissionDTO mission);

    /**
     * List all missions
     *
     * @return List of all missions
     */
    List<MissionDTO> findAllMissions();

    /**
     * List all missions with given progress
     * @param progress progress of the mission
     * @return list of the missions with given progress
     */
    List<MissionDTO> findAllMissions(MissionProgress progress);

    /**
     * Find mission with given id
     *
     * @param id mission id
     * @return MissionDTO instance of mission
     */
    MissionDTO findMissionById(Long id);

    /**
     * Archives given mission
     * Set the end date and result of the mission
     *
     * @param mission mission to archive
     * @param endDate end date of the mission, must be in past
     * @param archiveComment final comment of the mission
     */
    void archive(MissionDTO mission, ZonedDateTime endDate, String archiveComment);
}
