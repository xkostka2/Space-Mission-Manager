package cz.muni.fi.services.config;

import cz.muni.fi.InMemoryDatabaseSpring;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Martin Kazimir
 */
@Configuration
@Import(InMemoryDatabaseSpring.class)
@ComponentScan(basePackages = {"cz.muni.fi.services", "cz.muni.fi.facades"})
public class ServiceConfiguration {
}
