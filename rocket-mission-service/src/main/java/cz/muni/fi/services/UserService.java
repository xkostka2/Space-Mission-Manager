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
     * @return added user
     */
    User addUser(User user) throws DataAccessException;

    /**
     * Update user in database
     *
     * @param user instance of user
     * @return updated user
     */
    User updateUser(User user) throws DataAccessException;

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

    /**
     * Confirm mission for given user, set missionAccepted to true
     * @param user user which is accepting assigned mission
     */
    void acceptAssignedMission(User user) throws DataAccessException;

    /**
     * Reject mission for given user and add explanation why,
     * leave missionAccepted to false and add explanation,
     * also removes the astronaut from the mission's list of astronauts.
     * @param user user which is rejecting assigned mission
     * @param explanation explanation why user did not accept mission
     */
    void rejectAssignedMission(User user, String explanation)
        throws DataAccessException, IllegalArgumentException;

    /**
     * Authenticates user
     * @param user user
     * @param password password
     * @return true if successful, otherwise false
     */

    boolean authenticate(User user, String password) throws DataAccessException;
}
