package cz.muni.fi.facades;

import cz.muni.fi.dto.rocket.CreateRocketDTO;
import cz.muni.fi.dto.rocket.RocketDTO;
import cz.muni.fi.dto.rocket.UpdateRocketDTO;
import cz.muni.fi.entity.Rocket;
import cz.muni.fi.facade.RocketFacade;
import cz.muni.fi.services.RocketService;
import cz.muni.fi.services.mapper.MissionMapper;
import cz.muni.fi.services.mapper.RocketMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the {@link RocketFacade}.
 *
 * @author Tomas Bouma (469275)
 */
@Service
@Transactional
public class RocketFacadeImpl implements RocketFacade {

    private final RocketMapper rocketMapper;

    private final RocketService rocketService;

    @Autowired
    public RocketFacadeImpl(RocketService rocketService, RocketMapper rocketMapper) {
        this.rocketService = rocketService;
        this.rocketMapper = rocketMapper;
    }

    @Override
    public RocketDTO addRocket(CreateRocketDTO rocket) {
        Rocket mappedRocket = rocketMapper.createRocketDTOToRocket(rocket);
        return rocketMapper.rocketToRocketDTO(rocketService.addRocket(mappedRocket));
    }

    @Override
    @Transactional(readOnly=true)
    public List<RocketDTO> findAllRockets() {
        return rocketMapper.rocketsToRocketDTOs(rocketService.findAllRockets());
    }

    @Override
    @Transactional(readOnly=true)
    public RocketDTO findRocketById(Long id) {
        Rocket rocket = rocketService.findRocketById(id);
        if (rocket == null) {
            return null;
        }
        return rocketMapper.rocketToRocketDTO(rocket);
    }

    @Override
    public RocketDTO updateRocket(UpdateRocketDTO rocket) {
        Rocket mappedRocket = rocketMapper.updateRocketDTOToRocket(rocket);
        rocketService.updateRocket(mappedRocket);

        return rocketMapper.rocketToRocketDTO(rocketService.updateRocket(mappedRocket));
    }

    @Override
    public void removeRocket(RocketDTO rocket) {
        Rocket mappedRocket = rocketMapper.rocketDTOToRocket(rocket);
        rocketService.removeRocket(mappedRocket);
    }
}
