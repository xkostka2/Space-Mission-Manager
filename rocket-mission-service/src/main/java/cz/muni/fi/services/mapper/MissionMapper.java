package cz.muni.fi.services.mapper;

import cz.muni.fi.dto.mission.CreateMissionDTO;
import cz.muni.fi.dto.mission.MissionDTO;
import cz.muni.fi.dto.mission.UpdateMissionDTO;
import cz.muni.fi.entity.Mission;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Maps Mission entity related DTOs to their Entity counterparts.
 *
 * @author Martin Hořelka (469003)
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface MissionMapper {
    MissionDTO missionToMissionDTO(Mission mission, @Context CycleAvoidingMappingContext context);
    Mission missionDTOToMission(MissionDTO missionDTO, @Context CycleAvoidingMappingContext context);

    CreateMissionDTO missionToCreateMissionDTO(Mission mission, @Context CycleAvoidingMappingContext context);
    Mission createMissionDTOToMission(CreateMissionDTO createMissionDTO, @Context CycleAvoidingMappingContext context);

    UpdateMissionDTO missionToUpdateMissionDTO(Mission mission, @Context CycleAvoidingMappingContext context);
    Mission updateMissionDTOToMission(UpdateMissionDTO updateMissionDTO, @Context CycleAvoidingMappingContext context);

    List<MissionDTO> missionsToMissionDTOs(List<Mission> missions, @Context CycleAvoidingMappingContext context);
}
