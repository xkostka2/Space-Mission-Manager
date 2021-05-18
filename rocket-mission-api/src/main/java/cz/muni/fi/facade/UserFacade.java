package cz.muni.fi.facade;

import cz.muni.fi.dto.user.CreateUserDTO;
import cz.muni.fi.dto.user.UpdateUserDTO;
import cz.muni.fi.dto.user.UserDTO;

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
     * @return added user
     */
    UserDTO addUser(CreateUserDTO user);

    /**
     * Update user in DB
     *
     * @param user instance of user
     * @return updated user
     */
    UserDTO updateUser(UpdateUserDTO user);

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

    /**
     * Confirm mission for given user, set missionAccepted to true
     * @param user user which is accepting assigned mission
     */
    void acceptAssignedMission(UserDTO user);

    /**
     * Reject mission for given user and add explanation why,
     * leave missionAccepted to false and add explanation,
     * also removes the astronaut from the mission's list of astronauts.
     * @param user user which is rejecting assigned mission
     * @param explanation explanation why user did not accept mission
     */
    void rejectAssignedMission(UserDTO user, String explanation);

    /**
     * Authenticates email and password
     * @param email email
     * @param unencryptedPassword password
     * @return userDTO
     */

    boolean authenticate(String email, String unencryptedPassword);
}
