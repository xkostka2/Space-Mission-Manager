package cz.muni.fi.samples;

import cz.muni.fi.services.ComponentService;
import cz.muni.fi.services.MissionService;
import cz.muni.fi.services.RocketService;
import cz.muni.fi.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * @author Martin Kazimir
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private UserService userService;
    @Autowired
    private ComponentService componentService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private RocketService rocketService;


    @Override
    public void loadData() throws IOException {
        //TODO create sample data
    }
}
