package cz.muni.fi.samples;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author Martin Kazimir
 */
@Configuration
@ComponentScan(basePackageClasses = {SampleDataLoadingFacadeImpl.class})
public class MissionManagerWithSamplesConfig {

    final static Logger log = LoggerFactory.getLogger(MissionManagerWithSamplesConfig.class);

    @Autowired
    private SampleDataLoadingFacade sampleDataLoadingFacade;

    @PostConstruct
    public void dataLoading() throws IOException {
        log.debug("dataLoading()");
        sampleDataLoadingFacade.loadAllData();
    }
}
