package cz.muni.fi.services.config;


import cz.muni.fi.InMemoryDatabaseSpring;

import cz.muni.fi.dto.component.ComponentDTO;
import cz.muni.fi.dto.mission.MissionDTO;
import cz.muni.fi.dto.rocket.RocketDTO;
import cz.muni.fi.dto.user.UserDTO;
import cz.muni.fi.dto.component.CreateComponentDTO;
import cz.muni.fi.dto.mission.CreateMissionDTO;
import cz.muni.fi.dto.component.UpdateComponentDTO;
import cz.muni.fi.dto.mission.UpdateMissionDTO;
import cz.muni.fi.dto.rocket.UpdateRocketDTO;
import cz.muni.fi.entity.Component;
import cz.muni.fi.entity.Mission;
import cz.muni.fi.dto.rocket.CreateRocketDTO;
import cz.muni.fi.dto.user.CreateUserDTO;
import cz.muni.fi.dto.user.UpdateUserDTO;

import cz.muni.fi.entity.Rocket;
import cz.muni.fi.entity.User;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
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
            mapping(CreateUserDTO.class, User.class);
            mapping(UpdateUserDTO.class, User.class);

            mapping(Rocket.class, RocketDTO.class, TypeMappingOptions.mapNull(false))
                    .fields(field("requiredComponents").accessible(true),
                            field("requiredComponents").accessible(true));
            mapping(CreateRocketDTO.class, Rocket.class, TypeMappingOptions.mapNull(false))
                    .fields(field("requiredComponents").accessible(true),
                            field("requiredComponents").accessible(true));
            mapping(UpdateRocketDTO.class, Rocket.class)
                    .fields(field("requiredComponents").accessible(true),
                            field("requiredComponents").accessible(true));

            mapping(Mission.class, MissionDTO.class)
                    .fields(field("users").accessible(true),
                            field("users").accessible(true))
                    .fields(field("rockets").accessible(true),
                            field("rockets").accessible(true))
                    .fields(field("components").accessible(true),
                            field("components").accessible(true));
            mapping(CreateMissionDTO.class, Mission.class)
                    .fields(field("users").accessible(true),
                            field("users").accessible(true))
                    .fields(field("rockets").accessible(true),
                            field("rockets").accessible(true))
                    .fields(field("components").accessible(true),
                            field("components").accessible(true));
            mapping(UpdateMissionDTO.class, Mission.class)
                    .fields(field("users").accessible(true),
                            field("users").accessible(true))
                    .fields(field("rockets").accessible(true),
                            field("rockets").accessible(true))
                    .fields(field("components").accessible(true),
                            field("components").accessible(true));

            mapping(Component.class, ComponentDTO.class);
            mapping(CreateComponentDTO.class, Component.class);
            mapping(UpdateComponentDTO.class, Component.class);
        }
    }
}