package cz.muni.fi.facades;

import cz.muni.fi.dto.rocket.CreateRocketDTO;
import cz.muni.fi.dto.rocket.RocketDTO;
import cz.muni.fi.dto.rocket.UpdateRocketDTO;
import cz.muni.fi.entity.Rocket;
import cz.muni.fi.facade.RocketFacade;
import cz.muni.fi.services.BeanMappingService;
import cz.muni.fi.services.RocketService;
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
    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private RocketService rocketService;

    @Override
    public RocketDTO addRocket(CreateRocketDTO rocket) {
        Rocket mappedRocket = beanMappingService.mapTo(rocket, Rocket.class);
        return beanMappingService.mapTo(rocketService.addRocket(mappedRocket), RocketDTO.class);
    }

    @Override
    @Transactional(readOnly=true)
    public List<RocketDTO> findAllRockets() {
        return beanMappingService.mapTo(rocketService.findAllRockets(), RocketDTO.class);
    }

    @Override
    @Transactional(readOnly=true)
    public RocketDTO findRocketById(Long id) {
        Rocket rocket = rocketService.findRocketById(id);
        if (rocket == null) {
            return null;
        }
        return beanMappingService.mapTo(rocket, RocketDTO.class);
    }

    @Override
    public RocketDTO updateRocket(UpdateRocketDTO rocket) {
        Rocket mappedRocket = beanMappingService.mapTo(rocket, Rocket.class);
        rocketService.updateRocket(mappedRocket);

        return beanMappingService.mapTo(rocketService.updateRocket(mappedRocket), RocketDTO.class);
    }

    @Override
    public void removeRocket(RocketDTO rocket) {
        Rocket mappedRocket = beanMappingService.mapTo(rocket, Rocket.class);
        rocketService.removeRocket(mappedRocket);
    }
}
