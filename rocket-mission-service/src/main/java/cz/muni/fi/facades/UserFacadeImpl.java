package cz.muni.fi.facades;

import cz.muni.fi.dto.CreateUserDTO;
import cz.muni.fi.dto.UpdateUserDTO;
import cz.muni.fi.dto.UserDTO;
import cz.muni.fi.entity.User;
import cz.muni.fi.facade.UserFacade;
import cz.muni.fi.services.BeanMappingService;
import cz.muni.fi.services.UserService;
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
    @Autowired
    BeanMappingService beanMappingService;

    @Autowired
    UserService userService;

    @Override
    public UserDTO addUser(CreateUserDTO user) {
        User mappedUser = beanMappingService.mapTo(user, User.class);
        return beanMappingService.mapTo(userService.addUser(mappedUser), UserDTO.class);
    }

    @Override
    public UserDTO updateUser(UpdateUserDTO user) {
        User mappedUser = beanMappingService.mapTo(user, User.class);
        return beanMappingService.mapTo(userService.updateUser(mappedUser), UserDTO.class);
    }

    @Override
    public void deleteUser(UserDTO user) {
        User mappedUser = beanMappingService.mapTo(user, User.class);
        userService.deleteUser(mappedUser);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return beanMappingService.mapTo(userService.findAllUsers(), UserDTO.class);
    }

    @Override
    public List<UserDTO> findAllAstronauts() {
        return beanMappingService.mapTo(userService.findAllAstronauts(), UserDTO.class);
    }

    @Override
    public UserDTO findUserById(Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return null;
        }
        return beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> findAllAvailableAstronauts() {
        return beanMappingService.mapTo(userService.findAllAvailableAstronauts(), UserDTO.class);
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            return null;
        }
        return beanMappingService.mapTo(user, UserDTO.class);
    }
}
