package cz.muni.fi.services;


import cz.muni.fi.InMemoryDatabaseSpring;

import cz.muni.fi.dto.ComponentDTO;
import cz.muni.fi.dto.MissionDTO;
import cz.muni.fi.dto.RocketDTO;
import cz.muni.fi.dto.UserDTO;
import cz.muni.fi.dto.create.CreateComponentDTO;
import cz.muni.fi.dto.create.CreateMissionDTO;
import cz.muni.fi.dto.update.UpdateComponentDTO;
import cz.muni.fi.dto.update.UpdateMissionDTO;
import cz.muni.fi.dto.update.UpdateRocketDTO;
import cz.muni.fi.entity.Component;
import cz.muni.fi.entity.Mission;
import cz.muni.fi.dto.create.CreateRocketDTO;
import cz.muni.fi.dto.create.CreateUserDTO;
import cz.muni.fi.dto.update.UpdateUserDTO;

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
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }

    public class DozerCustomConfig extends BeanMappingBuilder {

        @Override
        protected void configure() {
            mapping(User.class, UserDTO.class);
            mapping(User.class, CreateUserDTO.class);
            mapping(User.class, UpdateUserDTO.class);
            mapping(Rocket.class, RocketDTO.class);
            mapping(Rocket.class, CreateRocketDTO.class);
            mapping(Rocket.class, UpdateRocketDTO.class);
            mapping(Mission.class, MissionDTO.class);
            mapping(Mission.class, CreateMissionDTO.class);
            mapping(Mission.class, UpdateMissionDTO.class);
            mapping(Component.class, ComponentDTO.class);
            mapping(Component.class, CreateComponentDTO.class);
            mapping(Component.class, UpdateComponentDTO.class);
        }
    }
}

