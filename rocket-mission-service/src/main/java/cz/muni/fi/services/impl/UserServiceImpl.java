package cz.muni.fi.services.impl;

import cz.muni.fi.dao.UserDao;
import cz.muni.fi.entity.User;
import cz.muni.fi.helpers.ServiceDataAccessException;
import cz.muni.fi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of the {@link UserService}. This class is part of the service
 * module of the application, that provides the implementation of the business logic.
 *
 * @author Martin Hořelka (469003)
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User addUser(User user) {
        try {
            return userDao.addUser(user);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not create a user", e);
        }
    }

    @Override
    public User updateUser(User user) {
        try {
            return userDao.updateUser(user);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not update a user", e);
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            userDao.deleteUser(user);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not delete a user", e);
        }
    }

    @Override
    public List<User> findAllUsers() {
        try {
            return Collections.unmodifiableList(userDao.findAllUsers());
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not find all users", e);
        }
    }

    @Override
    public List<User> findAllAstronauts() {
        try {
            return Collections.unmodifiableList(userDao.findAllAstronauts());
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not find all astronauts", e);
        }
    }

    @Override
    public User findUserById(Long id) {
        try {
            return userDao.findUserById(id);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not find user by id", e);
        }
    }

    @Override
    public List<User> findAllAvailableAstronauts() {
        try {
            return Collections.unmodifiableList(userDao.findAllAvailableAstronauts());
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not find all available astronauts", e);
        }
    }

    @Override
    public User findUserByEmail(String email) {
        try {
            return userDao.findUserByEmail(email);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not find user by email", e);
        }
    }
}
