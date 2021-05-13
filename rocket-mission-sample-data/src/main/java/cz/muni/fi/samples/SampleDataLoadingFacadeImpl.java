package cz.muni.fi.samples;

import cz.muni.fi.enums.ComponentType;
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
import java.time.ZonedDateTime;

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
    public void loadData(){
        //TODO create sample data
        log.info("data loading starting");

        cz.muni.fi.entity.Component component1 = new cz.muni.fi.entity.Component();
        component1.setName("wing");
        component1.setType(ComponentType.ROCKET);
        component1.setReadyToUse(false);
//        component1.setReadyDate(ZonedDateTime.now().plusDays(10));
        componentService.addComponent(component1);
        log.info("component1 loaded");

        cz.muni.fi.entity.Component component2 = new cz.muni.fi.entity.Component();
        component2.setName("food");
        component2.setType(ComponentType.MISSION);
        component2.setReadyToUse(false);
//        component2.setReadyDate(ZonedDateTime.now().plusDays(2));
        componentService.addComponent(component2);
        log.info("component1 loaded");

        cz.muni.fi.entity.Component component3 = new cz.muni.fi.entity.Component();
        component3.setName("engine");
        component3.setType(ComponentType.ROCKET);
        component3.setReadyToUse(true);
        componentService.addComponent(component3);
        log.info("component1 loaded");

        cz.muni.fi.entity.Component component4 = new cz.muni.fi.entity.Component();
        component4.setName("oxygen supply");
        component4.setType(ComponentType.MISSION);
        component4.setReadyToUse(true);
        componentService.addComponent(component4);
        log.info("component1 loaded");

        log.info(componentService.findAllComponents().toString());
    }
}
