package cz.muni.fi.samples;

import java.io.IOException;

/**
 * Populates database with sample data.
 *
 * @author Martin Kazimit
 */
public interface SampleDataLoadingFacade {

    void loadData() throws IOException;
}
