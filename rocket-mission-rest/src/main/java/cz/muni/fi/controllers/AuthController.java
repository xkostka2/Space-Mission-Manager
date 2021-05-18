package cz.muni.fi.controllers;

import cz.muni.fi.ApiUris;
import cz.muni.fi.config.MyUserPrincipal;
import cz.muni.fi.dto.user.UserDTO;
import cz.muni.fi.dto.user.UserLoginDTO;
import cz.muni.fi.exceptions.ResourceNotFoundException;
import cz.muni.fi.facade.UserFacade;
import cz.muni.fi.helpers.ServiceDataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(ApiUris.ROOT_URI_AUTH)
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserFacade userFacade;
    /**
     * Authenticates user
     *
     * @param user UserLoginDTO
     * @return UserDTO
     */

    @RolesAllowed({"MANAGER", "USER"})
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO authenticate(@RequestBody UserLoginDTO user, HttpServletResponse response, Authentication authentication) {
        logger.debug("[REST] authenticate()");
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        user.setPassword(((MyUserPrincipal) authentication.getPrincipal()).getPassword());
        try {
            if (userFacade.authenticate(user.getEmail(), user.getPassword())) {
                return userFacade.findUserByEmail(user.getEmail());
            }
        } catch (ServiceDataAccessException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return null;
    }
}