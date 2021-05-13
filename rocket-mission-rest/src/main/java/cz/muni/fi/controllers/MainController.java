package cz.muni.fi.controllers;

import cz.muni.fi.ApiUris;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Martin Kazimir
 */
@RestController
public class MainController {
    private static final Logger logger = Logger.getLogger(MainController.class.getName());


    @RequestMapping( method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Map<String, String> getResources() {

        return Map.of(
                "users_uri", ApiUris.ROOT_URI_USERS,
                "components_uri", ApiUris.ROOT_URI_COMPONENTS,
                "rockets_uri", ApiUris.ROOT_URI_ROCKETS,
                "missions_uri", ApiUris.ROOT_URI_MISSIONS
        );
    }
}