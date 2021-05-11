package cz.muni.fi.services.mapper;

import cz.muni.fi.dto.mission.CreateMissionDTO;
import cz.muni.fi.dto.mission.MissionDTO;
import cz.muni.fi.dto.mission.UpdateMissionDTO;
import cz.muni.fi.entity.Mission;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Maps Mission entity related DTOs to their Entity counterparts.
 *
 * @author Martin Hořelka (469003)
 */
@Mapper(componentModel = "spring")
public interface MissionMapper {
    MissionDTO missionToMissionDTO(Mission mission);
    Mission missionDTOToMission(MissionDTO missionDTO);

    CreateMissionDTO missionToCreateMissionDTO(Mission mission);
    Mission createMissionDTOToMission(CreateMissionDTO createMissionDTO);

    UpdateMissionDTO missionToUpdateMissionDTO(Mission mission);
    Mission updateMissionDTOToMission(UpdateMissionDTO updateMissionDTO);

    List<MissionDTO> missionsToMissionDTOs(List<Mission> missions);
}
