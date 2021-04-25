package cz.muni.fi.services;

import cz.muni.fi.entity.User;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * An interface that defines a service access to the {@link User} entity.
 *
 * @author Martin Hořelka (469003)
 */
public interface UserService {

    /**
     * Persist user into database.
     *
     * @param user instance of user
     */
    void addUser(User user) throws DataAccessException;

    /**
     * Update user in database
     *
     * @param user instance of user
     */
    void updateUser(User user) throws DataAccessException;

    /**
     * Delete user from database
     *
     * @param user instance of user
     */
    void deleteUser(User user) throws DataAccessException;

    /**
     * Find all users
     *
     * @return List of all users
     */
    List<User> findAllUsers() throws DataAccessException;

    /**
     * Find all astronauts
     *
     * @return list of all astronauts
     */
    List<User> findAllAstronauts() throws DataAccessException;

    /**
     * Find user by id
     *
     * @param id user id
     * @return User instance of user
     */
    User findUserById(Long id) throws DataAccessException;

    /**
     * Find all available astronauts
     *
     * @return list of available astronauts
     */
    List<User> findAllAvailableAstronauts() throws DataAccessException;

    /**
     * Find user by email
     *
     * @param email user email
     * @return user instance of user
     */
    User findUserByEmail(String email) throws DataAccessException;
}
