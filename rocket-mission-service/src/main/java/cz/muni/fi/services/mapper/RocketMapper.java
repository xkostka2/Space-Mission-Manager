package cz.muni.fi.services.mapper;

import cz.muni.fi.dto.rocket.CreateRocketDTO;
import cz.muni.fi.dto.rocket.RocketDTO;
import cz.muni.fi.dto.rocket.UpdateRocketDTO;
import cz.muni.fi.entity.Rocket;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Maps Rocket entity related DTOs to their Entity counterparts.
 *
 * @author Martin Hořelka (469003)
 */
@Mapper
public interface RocketMapper {
    RocketDTO rocketToRocketDTO(Rocket rocket);
    Rocket rocketDTOToRocket(RocketDTO rocketDTO);

    CreateRocketDTO rocketToCreateRocketDTO(Rocket rocket);
    Rocket createRocketDTOToRocket(CreateRocketDTO createRocketDTO);

    UpdateRocketDTO rocketToUpdateRocketDTO(Rocket rocket);
    Rocket updateRocketDTOToRocket(UpdateRocketDTO updateRocketDTO);

    List<RocketDTO> rocketsToRocketDTOs(List<Rocket> rockets);
}
