package cz.muni.fi.controllers;

import cz.muni.fi.ApiUris;
import cz.muni.fi.dto.mission.CreateMissionDTO;
import cz.muni.fi.dto.mission.UpdateMissionDTO;
import cz.muni.fi.dto.mission.MissionDTO;
import cz.muni.fi.exceptions.ResourceAlreadyExistsException;
import cz.muni.fi.exceptions.ResourceNotFoundException;
import cz.muni.fi.facade.MissionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(ApiUris.ROOT_URI_MISSIONS)
public class MissionController {

    private final static Logger logger = Logger.getLogger(ComponentController.class.getName());

    private final MissionFacade missionFacade;


    @Autowired
    public MissionController(MissionFacade missionFacade) {
        this.missionFacade = missionFacade;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MissionDTO> createMission(@RequestBody CreateMissionDTO missionCreateDTO) {

        logger.log(Level.INFO, "[REST] creating mission");

        try {
            return new ResponseEntity<>(missionFacade.findMissionById(missionFacade.addMission(missionCreateDTO).getId()), HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new ResourceAlreadyExistsException();
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MissionDTO>> findAllMissions() {

        logger.log(Level.INFO, "[REST] finding all missions");

        return new ResponseEntity<>(missionFacade.findAllMissions(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MissionDTO> findMissionById(@PathVariable("id") Long id) {

        logger.log(Level.INFO, "[REST] finding mission " + id);

        MissionDTO missionDTO = missionFacade.findMissionById(id);
        if (missionDTO == null) {
            throw new ResourceNotFoundException();
        }
        return new ResponseEntity<>(missionDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MissionDTO> updateMission(@RequestBody UpdateMissionDTO updateMissionDTO) {

        logger.log(Level.INFO, "[REST] updating mission" + updateMissionDTO.getId());

        try {
            missionFacade.updateMission(updateMissionDTO);
            return new ResponseEntity<>(missionFacade.findMissionById(updateMissionDTO.getId()), HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new ResourceAlreadyExistsException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MissionDTO>> removeMission(@PathVariable("id") long id) {

        logger.log(Level.INFO, "[REST] deleting mission" + id);

        MissionDTO mission = missionFacade.findMissionById(id);
        if (mission == null) {
            throw new ResourceNotFoundException();
        }

        missionFacade.deleteMission(mission);
        return new ResponseEntity<>(missionFacade.findAllMissions(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/archive", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MissionDTO> archive(@PathVariable("id") long id, @RequestBody Message comment) {

        logger.log(Level.INFO, "[REST] archiving mission" + id);

        MissionDTO mission = missionFacade.findMissionById(id);
        if (mission == null) {
            throw new ResourceNotFoundException();
        }

        missionFacade.archive(mission, ZonedDateTime.now(), comment.getValue());
        return new ResponseEntity<>(missionFacade.findMissionById(id), HttpStatus.OK);
    }


}