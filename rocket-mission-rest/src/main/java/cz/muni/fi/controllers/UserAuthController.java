package cz.muni.fi.controllers;
import cz.muni.fi.ApiUris;
import cz.muni.fi.dto.user.AuthUserDTO;
import cz.muni.fi.dto.user.CreateUserDTO;
import cz.muni.fi.dto.user.UserDTO;
import cz.muni.fi.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.logging.Logger;

/**
 * Rest interface for User-Auth
 *
 * @author Tomas Bouma
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_AUTH)
public class UserAuthController {

    private final static Logger logger = Logger.getLogger(ComponentController.class.getName());

    private final UserFacade userFacade;


    @Autowired
    public UserAuthController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    /**
     * User login
     * @return Resource<UserDTO>
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> loginUser(@RequestBody AuthUserDTO authUserDTO){
        UserDTO userDTO = userFacade.login(authUserDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
        /**
         * User logout
         */
        @GetMapping("/logout")
        public ResponseEntity<Void> logout(){
            SecurityContextHolder.clearContext();
            return ResponseEntity.noContent().build();
        }
}
