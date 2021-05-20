package cz.muni.fi.services.impl;

import cz.muni.fi.dao.UserDao;
import cz.muni.fi.entity.User;
import cz.muni.fi.enums.Role;
import cz.muni.fi.helpers.ServiceDataAccessException;
import cz.muni.fi.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
    @PreAuthorize("hasRole('ROLE_USER')")
    public User addUser(User user) {
        try {
            return userDao.addUser(user);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not create a user", e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public User updateUser(User user) {
        try {
            return userDao.updateUser(user);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not update a user", e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteUser(User user) {
        try {
            userDao.deleteUser(user);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not delete a user", e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<User> findAllUsers() {
        try {
            return Collections.unmodifiableList(userDao.findAllUsers());
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not find all users", e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<User> findAllAstronauts() {
        try {
            return Collections.unmodifiableList(userDao.findAllAstronauts());
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not find all astronauts", e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public User findUserById(Long id) {
        try {
            return userDao.findUserById(id);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not find user by id", e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<User> findAllAvailableAstronauts() {
        try {
            return Collections.unmodifiableList(userDao.findAllAvailableAstronauts());
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not find all available astronauts", e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public User findUserByEmail(String email) {
        try {
            return userDao.findUserByEmail(email);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not find user by email", e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public void acceptAssignedMission(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        if (user.getMission() == null) {
            throw new IllegalArgumentException("User does not have pending mission status");
        }
        if (user.missionStatusPending()) {
            user.setMissionAccepted(true);
        } else {
            throw new IllegalArgumentException("User does not have pending mission status");
        }
        try {
            userDao.updateUser(user);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not update a user", e);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public void rejectAssignedMission(User user, String explanation) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        if (explanation == null || explanation.isEmpty()) {
            throw new IllegalArgumentException("Explanation must not be null");
        }
        if (user.missionStatusPending()) {
            user.setMissionRejectedExplanation(explanation);
            user.setMission(null);
        } else {
            throw new IllegalArgumentException("User does not have pending mission status");
        }
        try {
            userDao.updateUser(user);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Can not update a user", e);
        }
    }


    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public User login(String email, String password) {
        User user = findUserByEmail(email);
        passwordCheck(user.getPassword(), password);
        setSecurityContext(user.getPassword(), password, user.getRole() == Role.MANAGER);
        return user;
    }

    private void passwordCheck(String passwordHash, String password){
        String md5Hex = DigestUtils
                .md5Hex(password);

        if (!md5Hex.equals(passwordHash))
            throw new ServiceDataAccessException("Wrong password/login combination");
    }


    private void setSecurityContext(String name, String password, boolean isManager){
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (isManager) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        }
        org.springframework.security.core.userdetails.User springUser
                = new org.springframework.security.core.userdetails.User(name, password, authorities);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                springUser.getUsername(),
                springUser.getPassword(),
                springUser.getAuthorities()));
    }
}
