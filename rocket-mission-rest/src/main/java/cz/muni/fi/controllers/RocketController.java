package cz.muni.fi.controllers;

import cz.muni.fi.ApiUris;
import cz.muni.fi.dto.rocket.CreateRocketDTO;
import cz.muni.fi.dto.rocket.RocketDTO;
import cz.muni.fi.dto.rocket.UpdateRocketDTO;
import cz.muni.fi.exceptions.ResourceAlreadyExistsException;
import cz.muni.fi.exceptions.ResourceNotFoundException;
import cz.muni.fi.facade.RocketFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(ApiUris.ROOT_URI_ROCKETS)
public class RocketController {

    private final static Logger logger = Logger.getLogger(ComponentController.class.getName());

    private final RocketFacade rocketFacade;


    @Autowired
    public RocketController(RocketFacade rocketFacade) {
        this.rocketFacade = rocketFacade;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RocketDTO> createRocket(@RequestBody CreateRocketDTO rocketCreateDTO) {

        logger.log(Level.INFO, "[REST] creating rocket");

        try {
            return new ResponseEntity<>(rocketFacade.findRocketById(rocketFacade.addRocket(rocketCreateDTO).getId()), HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new ResourceAlreadyExistsException();
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RocketDTO>> findAllRockets() {

        logger.log(Level.INFO, "[REST] finding all rockets");

        return new ResponseEntity<>(rocketFacade.findAllRockets(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RocketDTO> findRocketById(@PathVariable("id") Long id) {

        logger.log(Level.INFO, "[REST] finding rocket " + id);

        RocketDTO rocketDTO = rocketFacade.findRocketById(id);
        if (rocketDTO == null) {
            throw new ResourceNotFoundException();
        }
        return new ResponseEntity<>(rocketDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RocketDTO> updateRocket(@RequestBody UpdateRocketDTO updateRocketDTO) {

        logger.log(Level.INFO, "[REST] updating rocket" + updateRocketDTO.getId());

        try {
            rocketFacade.updateRocket(updateRocketDTO);
            return new ResponseEntity<>(rocketFacade.findRocketById(updateRocketDTO.getId()), HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new ResourceAlreadyExistsException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RocketDTO>> removeRocket(@PathVariable("id") long id) {

        logger.log(Level.INFO, "[REST] deleting rocket" + id);

        RocketDTO rocket = rocketFacade.findRocketById(id);
        if (rocket == null) {
            throw new ResourceNotFoundException();
        }

        rocketFacade.removeRocket(rocket);
        return new ResponseEntity<>(rocketFacade.findAllRockets(), HttpStatus.OK);
    }
}