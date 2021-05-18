package cz.muni.fi.samples;

import java.io.IOException;

/**
 * Populates database with sample data.
 *
 * @author Martin Kazimit
 */
public interface SampleDataLoadingFacade {

    void loadAllData() throws IOException;

    void loadAllComponents() throws IOException;

    void loadAllMissions() throws IOException;

    void loadAllUsers() throws IOException;

    void loadAllRockets() throws IOException;

    void loadRelationships() throws IOException;
}
