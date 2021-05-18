package cz.muni.fi.controllers;

import cz.muni.fi.ApiUris;
import cz.muni.fi.dto.user.CreateUserDTO;
import cz.muni.fi.dto.user.UpdateUserDTO;
import cz.muni.fi.dto.user.UserDTO;
import cz.muni.fi.exceptions.ResourceAlreadyExistsException;
import cz.muni.fi.exceptions.ResourceNotFoundException;
import cz.muni.fi.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(ApiUris.ROOT_URI_USERS)
public class UserController {

    private final static Logger logger = Logger.getLogger(UserController.class.getName());

    private final UserFacade userFacade;


    @Autowired
    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @RolesAllowed({"MANAGER", "USER"})
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO createUser(@RequestBody CreateUserDTO userCreateDTO) {

        logger.log(Level.INFO, "[REST] creating user");

        try {
            return userFacade.findUserById(userFacade.addUser(userCreateDTO).getId());
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new ResourceAlreadyExistsException();
        }
    }

    @RolesAllowed({"MANAGER", "USER"})
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> findAllUsers() {

        logger.log(Level.INFO, "[REST] finding all users");

        return userFacade.findAllUsers();
    }

    @RolesAllowed({"MANAGER"})
    @RequestMapping(value = "/astronauts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> findAllAstronauts() {

        logger.log(Level.INFO, "[REST] finding all astronauts");

        return userFacade.findAllAstronauts();
    }

    @RolesAllowed({"MANAGER"})
    @RequestMapping(value = "/astronauts/available", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> findAllAvailableAstronauts() {

        logger.log(Level.INFO, "[REST] finding all available astronauts");

        return userFacade.findAllAvailableAstronauts();
    }

    @RolesAllowed({"MANAGER", "USER"})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO findUserById(@PathVariable("id") Long id) {

        logger.log(Level.INFO, "[REST] finding user " + id);

        UserDTO userDTO = userFacade.findUserById(id);
        if (userDTO == null) {
            throw new ResourceNotFoundException();
        }
        return userDTO;
    }

    @RolesAllowed({"MANAGER", "USER"})
    @RequestMapping(value = "/email", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO findUserByEmail(@RequestBody Message email) {

        logger.log(Level.INFO, "[REST] finding user with email " + email);

        UserDTO userDTO = userFacade.findUserByEmail(email.getValue());
        if (userDTO == null) {
            throw new ResourceNotFoundException();
        }
        return userDTO;
    }

    @RolesAllowed({"MANAGER", "USER"})
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO updateUser(@RequestBody UpdateUserDTO updateUserDTO) {

        logger.log(Level.INFO, "[REST] updating user" + updateUserDTO.getId());

        try {
            userFacade.updateUser(updateUserDTO);
            return userFacade.findUserById(updateUserDTO.getId());
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new ResourceAlreadyExistsException();
        }
    }

    @RolesAllowed({"MANAGER", "USER"})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> removeUser(@PathVariable("id") long id) {

        logger.log(Level.INFO, "[REST] deleting user" + id);

        UserDTO user = userFacade.findUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException();
        }

        userFacade.deleteUser(user);
        return userFacade.findAllUsers();
    }

    @RolesAllowed({"MANAGER", "USER"})
    @RequestMapping(value = "/{id}/acceptMission", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO acceptMission(@PathVariable Long id) {

        logger.log(Level.INFO, "[REST] accept mission by user with id " + id);

        UserDTO user = userFacade.findUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException();
        }

        userFacade.acceptAssignedMission(user);
        return userFacade.findUserById(id);
    }

    @RolesAllowed({"MANAGER", "USER"})
    @RequestMapping(value = "{id}/rejectMission", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO rejectMission(@PathVariable Long id, @RequestBody Message explanation) {

        logger.log(Level.INFO, "[REST] reject mission by user with id " + id);

        UserDTO user = userFacade.findUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException();
        }
        userFacade.rejectAssignedMission(user, explanation.getValue());
        return userFacade.findUserById(id);
    }
}