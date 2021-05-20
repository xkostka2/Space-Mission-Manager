package cz.muni.fi.facades;

import cz.muni.fi.dto.user.AuthUserDTO;
import cz.muni.fi.dto.user.CreateUserDTO;
import cz.muni.fi.dto.user.UpdateUserDTO;
import cz.muni.fi.dto.user.UserDTO;
import cz.muni.fi.entity.User;
import cz.muni.fi.facade.UserFacade;
import cz.muni.fi.services.UserService;
import cz.muni.fi.services.mapper.CycleAvoidingMappingContext;
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
    private CycleAvoidingMappingContext cycleAvoidingMappingContext;
    private final UserMapper userMapper;
    private final UserService userService;

    @Autowired
    public UserFacadeImpl(UserService userService, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.cycleAvoidingMappingContext = new CycleAvoidingMappingContext();
    }

    @Override
    public UserDTO addUser(CreateUserDTO user) {
        User mappedUser = userMapper.createUserDTOToUser(user, cycleAvoidingMappingContext);
        return userMapper.userToUserDTO(userService.addUser(mappedUser), cycleAvoidingMappingContext);
    }

    @Override
    public UserDTO updateUser(UpdateUserDTO user) {
        User mappedUser = userMapper.updateUserDTOToUser(user, cycleAvoidingMappingContext);
        return userMapper.userToUserDTO(userService.updateUser(mappedUser), cycleAvoidingMappingContext);
    }

    @Override
    public void deleteUser(UserDTO user) {
        User mappedUser = userMapper.userDTOToUser(user, cycleAvoidingMappingContext);
        userService.deleteUser(mappedUser);
    }

    @Override
    @Transactional(readOnly=true)
    public List<UserDTO> findAllUsers() {
        return userMapper.usersToUserDTOs(userService.findAllUsers(), cycleAvoidingMappingContext);
    }

    @Override
    @Transactional(readOnly=true)
    public List<UserDTO> findAllAstronauts() {
        return userMapper.usersToUserDTOs(userService.findAllAstronauts(), cycleAvoidingMappingContext);
    }

    @Override
    @Transactional(readOnly=true)
    public UserDTO findUserById(Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return null;
        }
        return userMapper.userToUserDTO(user, cycleAvoidingMappingContext);
    }

    @Override
    @Transactional(readOnly=true)
    public List<UserDTO> findAllAvailableAstronauts() {
        return userMapper.usersToUserDTOs(userService.findAllAvailableAstronauts(), cycleAvoidingMappingContext);
    }

    @Override
    @Transactional(readOnly=true)
    public UserDTO findUserByEmail(String email) {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            return null;
        }
        return userMapper.userToUserDTO(user, cycleAvoidingMappingContext);
    }

    @Override
    public void acceptAssignedMission(UserDTO user) {
        userService.acceptAssignedMission(userMapper.userDTOToUser(user, cycleAvoidingMappingContext));
    }

    @Override
    public void rejectAssignedMission(UserDTO user, String explanation) {
        userService.rejectAssignedMission(userMapper.userDTOToUser(user, cycleAvoidingMappingContext), explanation);
    }

    @Override
    public UserDTO login(AuthUserDTO user) {
        return userMapper.userToUserDTO(userService.login(user.getEmail(), user.getPassword()), cycleAvoidingMappingContext);
    }
}
