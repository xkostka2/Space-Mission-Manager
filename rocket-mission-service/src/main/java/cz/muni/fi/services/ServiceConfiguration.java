package cz.muni.fi.services;


import cz.muni.fi.InMemoryDatabaseSpring;
import cz.muni.fi.dto.CreateRocketDTO;
import cz.muni.fi.dto.RocketDTO;
import cz.muni.fi.dto.UserDTO;
import cz.muni.fi.entity.Rocket;
import cz.muni.fi.entity.User;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import java.util.Collections;

@Configuration
@Import(InMemoryDatabaseSpring.class)
@ComponentScan(basePackages = "cz.muni.fi")
public class ServiceConfiguration {
    @Bean
    public Mapper dozer(){
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.setMappingFiles(Collections.singletonList("dozerJdk8Converters.xml"));
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }


    public class DozerCustomConfig extends BeanMappingBuilder {

        @Override
        protected void configure() {
            mapping(User.class, UserDTO.class);
            mapping(Rocket.class, RocketDTO.class);
            mapping(Rocket.class, CreateRocketDTO.class);
        }
    }
}

