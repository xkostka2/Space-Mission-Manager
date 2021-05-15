package cz.muni.fi.facades;

import cz.muni.fi.dto.user.CreateUserDTO;
import cz.muni.fi.dto.user.UpdateUserDTO;
import cz.muni.fi.dto.user.UserDTO;
import cz.muni.fi.entity.User;
import cz.muni.fi.facade.UserFacade;
import cz.muni.fi.services.UserService;
import cz.muni.fi.services.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the {@link UserFacade}.
 *
 * @author Martin Hořelka (469003)
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    private final UserMapper userMapper;
    private final UserService userService;

    @Autowired
    public UserFacadeImpl(UserService userService, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Override
    public UserDTO addUser(CreateUserDTO user) {
        User mappedUser = userMapper.createUserDTOToUser(user);
        return userMapper.userToUserDTO(userService.addUser(mappedUser));
    }

    @Override
    public UserDTO updateUser(UpdateUserDTO user) {
        User mappedUser = userMapper.updateUserDTOToUser(user);
        return userMapper.userToUserDTO(userService.updateUser(mappedUser));
    }

    @Override
    public void deleteUser(UserDTO user) {
        User mappedUser = userMapper.userDTOToUser(user);
        userService.deleteUser(mappedUser);
    }

    @Override
    @Transactional(readOnly=true)
    public List<UserDTO> findAllUsers() {
        return userMapper.usersToUserDTOs(userService.findAllUsers());
    }

    @Override
    @Transactional(readOnly=true)
    public List<UserDTO> findAllAstronauts() {
        return userMapper.usersToUserDTOs(userService.findAllAstronauts());
    }

    @Override
    @Transactional(readOnly=true)
    public UserDTO findUserById(Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return null;
        }
        return userMapper.userToUserDTO(user);
    }

    @Override
    @Transactional(readOnly=true)
    public List<UserDTO> findAllAvailableAstronauts() {
        return userMapper.usersToUserDTOs(userService.findAllAvailableAstronauts());
    }

    @Override
    @Transactional(readOnly=true)
    public UserDTO findUserByEmail(String email) {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            return null;
        }
        return userMapper.userToUserDTO(user);
    }

    @Override
    public void acceptAssignedMission(UserDTO user) {
        userService.acceptAssignedMission(userMapper.userDTOToUser(user));
    }

    @Override
    public void rejectAssignedMission(UserDTO user, String explanation) {
        userService.rejectAssignedMission(userMapper.userDTOToUser(user), explanation);
    }
}
