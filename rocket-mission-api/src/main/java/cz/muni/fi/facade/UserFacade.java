package cz.muni.fi.facade;

import cz.muni.fi.dto.UserDTO;

import java.util.List;

/**
 * User facade interface.
 *
 * @author Martin Hořelka (469003)
 */
public interface UserFacade {

    /**
     * Persist user into DB
     *
     * @param user instance of user
     */
    void addUser(UserDTO user);

    /**
     * Update user in DB
     *
     * @param user instance of user
     */
    void updateUser(UserDTO user);

    /**
     * Delete user from DB
     *
     * @param user instance of user
     */
    void deleteUser(UserDTO user);

    /**
     * Find all users
     *
     * @return list of all users
     */
    List<UserDTO> findAllUsers();

    /**
     * Find all available astronauts
     *
     * @return List of available astronauts
     */
    List<UserDTO> findAllAstronauts();

    /**
     * Find user by id
     *
     * @param id user id
     * @return found UserDTO
     */
    UserDTO findUserById(Long id);

    /**
     * Find all available astronauts
     *
     * @return list of available astronauts
     */
    List<UserDTO> findAllAvailableAstronauts();

    /**
     * Find user by email
     *
     * @param email user email
     * @return found UserDTO
     */
    UserDTO findUserByEmail(String email);
}