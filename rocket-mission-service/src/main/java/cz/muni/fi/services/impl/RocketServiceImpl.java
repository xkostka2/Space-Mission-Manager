package cz.muni.fi.services.impl;

import cz.muni.fi.dao.RocketDao;
import cz.muni.fi.entity.Rocket;
import cz.muni.fi.helpers.ServiceDataAccessException;
import cz.muni.fi.services.RocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of the {@link RocketService}. This class is part of the
 * service module of the application that provides the implementation of the
 * business logic (main logic of the application).
 *
 * @author Tomas Bouma
 */

@Service
public class RocketServiceImpl implements RocketService {
    @Autowired
    private RocketDao rocketDao;

    @Override
    public Rocket addRocket(Rocket rocket) throws DataAccessException {
        try {
            rocketDao.addRocket(rocket);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Error when adding rocket.", e);
        }

        return rocket;
    }

    @Override
    public List<Rocket> findAllRockets() throws DataAccessException {
        try {
            return Collections.unmodifiableList(rocketDao.findAllRockets());
        }  catch (Throwable e) {
            throw new ServiceDataAccessException("Could not find all rockets.", e);
        }
    }

    @Override
    public Rocket findRocketById(Long id) throws DataAccessException {
        try{
            return rocketDao.findRocketById(id);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Error when finding rocket.", e);
        }

    }

    @Override
    public Rocket updateRocket(Rocket rocket) throws DataAccessException {
        try {
            rocketDao.updateRocket(rocket);
        }  catch (Throwable e) {
            throw new ServiceDataAccessException("Error when updating rocket.", e);
        }

        return rocket;
    }

    @Override
    public void removeRocket(Rocket rocket) throws DataAccessException {
        try {
            rocketDao.removeRocket(rocket);
        }  catch (Throwable e) {
            throw new ServiceDataAccessException("Error when removing rocket.", e);
        }
    }
}
