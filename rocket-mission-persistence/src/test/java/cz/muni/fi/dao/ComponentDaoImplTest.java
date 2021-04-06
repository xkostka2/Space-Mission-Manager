package cz.muni.fi.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Tests for ComponentDao implementation class.
 *
 * @author Martin Kazimir
 */
@ContextConfiguration(classes = cz.muni.fi.InMemoryDatabaseSpring.class)
public class ComponentDaoImplTest {

    @Autowired
    private ComponentDao componentDao;


}
