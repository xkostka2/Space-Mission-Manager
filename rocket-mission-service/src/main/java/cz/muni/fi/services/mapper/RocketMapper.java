package cz.muni.fi.services.mapper;

import cz.muni.fi.dto.rocket.CreateRocketDTO;
import cz.muni.fi.dto.rocket.RocketDTO;
import cz.muni.fi.dto.rocket.UpdateRocketDTO;
import cz.muni.fi.entity.Rocket;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Maps Rocket entity related DTOs to their Entity counterparts.
 *
 * @author Martin Hořelka (469003)
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface RocketMapper {
    RocketDTO rocketToRocketDTO(Rocket rocket, @Context CycleAvoidingMappingContext context);
    Rocket rocketDTOToRocket(RocketDTO rocketDTO, @Context CycleAvoidingMappingContext context);

    CreateRocketDTO rocketToCreateRocketDTO(Rocket rocket, @Context CycleAvoidingMappingContext context);
    Rocket createRocketDTOToRocket(CreateRocketDTO createRocketDTO, @Context CycleAvoidingMappingContext context);

    UpdateRocketDTO rocketToUpdateRocketDTO(Rocket rocket, @Context CycleAvoidingMappingContext context);
    Rocket updateRocketDTOToRocket(UpdateRocketDTO updateRocketDTO, @Context CycleAvoidingMappingContext context);

    List<RocketDTO> rocketsToRocketDTOs(List<Rocket> rockets, @Context CycleAvoidingMappingContext context);
}
