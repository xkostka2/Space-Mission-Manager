package cz.muni.fi.services.impl;

import cz.muni.fi.dao.ComponentDao;
import cz.muni.fi.dao.MissionDao;
import cz.muni.fi.dao.RocketDao;
import cz.muni.fi.entity.Component;
import cz.muni.fi.entity.Mission;
import cz.muni.fi.entity.Rocket;
import cz.muni.fi.helpers.ServiceDataAccessException;
import cz.muni.fi.services.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Implementation of the {@link cz.muni.fi.services.ComponentService}. This class is part of the service
 * module of the application, that provides the implementation of the business logic.
 *
 * @author Martin Kostka
 */
@Service
public class ComponentServiceImpl implements ComponentService {
    @Autowired
    private ComponentDao componentDao;

    @Autowired
    private MissionDao missionDao;

    @Autowired
    private RocketDao rocketDao;

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public Component addComponent(Component component) throws DataAccessException {
        try {
            return componentDao.addComponent(component);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Error when adding component.", e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Component> findAllComponents() throws DataAccessException {
        try {
            return Collections.unmodifiableList(componentDao.findAllComponents());
        }  catch (Throwable e) {
            throw new ServiceDataAccessException("Could not find all components.", e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public Component findComponentById(Long id) throws DataAccessException {
        try{
            return componentDao.findComponentById(id);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Error when finding component.", e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public Component updateComponent(Component component) throws DataAccessException {
        try {
            return componentDao.updateComponent(component);
        }  catch (Throwable e) {
            throw new ServiceDataAccessException("Error when updating component.", e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public void removeComponent(Component component) throws DataAccessException {
        try {
            Mission mission = component.getMission();
            if (mission != null) {
                mission.removeComponent(component);
                missionDao.updateMission(mission);
            }

            Rocket rocket = component.getRocket();
            if (rocket != null) {
                Set<Component> componentSet = rocket.getRequiredComponents();
                componentSet.remove(component);
                component.setRocket(null);
                componentDao.updateComponent(component);
                rocket.setRequiredComponents(componentSet);
                rocketDao.updateRocket(rocket);
            }
            componentDao.removeComponent(component);
        }  catch (Throwable e) {
            throw new ServiceDataAccessException("Error when removing component.", e);
        }
    }
}
