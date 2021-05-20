package cz.muni.fi.controllers;

import cz.muni.fi.ApiUris;
import cz.muni.fi.dto.user.CreateUserDTO;
import cz.muni.fi.dto.user.UpdateUserDTO;
import cz.muni.fi.dto.user.UserDTO;
import cz.muni.fi.exceptions.ResourceAlreadyExistsException;
import cz.muni.fi.exceptions.ResourceNotFoundException;
import cz.muni.fi.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(ApiUris.ROOT_URI_USERS)
public class UserController {

    private final static Logger logger = Logger.getLogger(ComponentController.class.getName());

    private final UserFacade userFacade;


    @Autowired
    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createComponent(@RequestBody CreateUserDTO userCreateDTO) {

        logger.log(Level.INFO, "[REST] creating user");

        try {
            return new ResponseEntity<>(userFacade.findUserById(userFacade.addUser(userCreateDTO).getId()), HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new ResourceAlreadyExistsException();
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> findAllUsers() {

        logger.log(Level.INFO, "[REST] finding all users");

        return new ResponseEntity<>(userFacade.findAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/astronauts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> findAllAstronauts() {

        logger.log(Level.INFO, "[REST] finding all astronauts");

        return new ResponseEntity<>(userFacade.findAllAstronauts(), HttpStatus.OK);
    }

    @RequestMapping(value = "/astronauts/available", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> findAllAvailableAstronauts() {

        logger.log(Level.INFO, "[REST] finding all available astronauts");

        return new ResponseEntity<>(userFacade.findAllAvailableAstronauts(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> findUserById(@PathVariable("id") Long id) {

        logger.log(Level.INFO, "[REST] finding user " + id);

        UserDTO userDTO = userFacade.findUserById(id);
        if (userDTO == null) {
            throw new ResourceNotFoundException();
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/email", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> findUserByEmail(@RequestBody Message email) {

        logger.log(Level.INFO, "[REST] finding user with email " + email);

        UserDTO userDTO = userFacade.findUserByEmail(email.getValue());
        if (userDTO == null) {
            throw new ResourceNotFoundException();
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {

        logger.log(Level.INFO, "[REST] updating user" + updateUserDTO.getId());

        try {
            userFacade.updateUser(updateUserDTO);
            return new ResponseEntity<>(userFacade.findUserById(updateUserDTO.getId()), HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new ResourceAlreadyExistsException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> removeUser(@PathVariable("id") long id) {

        logger.log(Level.INFO, "[REST] deleting user" + id);

        UserDTO user = userFacade.findUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException();
        }

        userFacade.deleteUser(user);
        return new ResponseEntity<>(userFacade.findAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/acceptMission", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> acceptMission(@PathVariable Long id) {

        logger.log(Level.INFO, "[REST] accept mission by user with id " + id);

        UserDTO user = userFacade.findUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException();
        }

        userFacade.acceptAssignedMission(user);
        return new ResponseEntity<>(userFacade.findUserById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}/rejectMission", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> rejectMission(@PathVariable Long id, @RequestBody Message explanation) {

        logger.log(Level.INFO, "[REST] reject mission by user with id " + id);

        UserDTO user = userFacade.findUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException();
        }
        userFacade.rejectAssignedMission(user, explanation.getValue());
        return new ResponseEntity<>(userFacade.findUserById(id), HttpStatus.OK);
    }
}