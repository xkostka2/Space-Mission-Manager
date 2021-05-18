package cz.muni.fi.facades;

import cz.muni.fi.dto.user.CreateUserDTO;
import cz.muni.fi.dto.user.UpdateUserDTO;
import cz.muni.fi.dto.user.UserDTO;
import cz.muni.fi.entity.User;
import cz.muni.fi.facade.UserFacade;
import cz.muni.fi.services.BeanMappingService;
import cz.muni.fi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.codec.digest.DigestUtils;

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
    private BeanMappingService beanMappingService;

    @Autowired
    private UserService userService;

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
    @Transactional(readOnly=true)
    public List<UserDTO> findAllUsers() {
        return beanMappingService.mapTo(userService.findAllUsers(), UserDTO.class);
    }

    @Override
    @Transactional(readOnly=true)
    public List<UserDTO> findAllAstronauts() {
        return beanMappingService.mapTo(userService.findAllAstronauts(), UserDTO.class);
    }

    @Override
    @Transactional(readOnly=true)
    public UserDTO findUserById(Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return null;
        }
        return beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    @Transactional(readOnly=true)
    public List<UserDTO> findAllAvailableAstronauts() {
        return beanMappingService.mapTo(userService.findAllAvailableAstronauts(), UserDTO.class);
    }

    @Override
    @Transactional(readOnly=true)
    public UserDTO findUserByEmail(String email) {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            return null;
        }
        return beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public void acceptAssignedMission(UserDTO user) {
        userService.acceptAssignedMission(beanMappingService.mapTo(user, User.class));
    }

    @Override
    public void rejectAssignedMission(UserDTO user, String explanation) {
        userService.rejectAssignedMission(beanMappingService.mapTo(user, User.class), explanation);
    }

    @Override
    public boolean authenticate(String email, String unencryptedPassword) {
        String md5Hex = DigestUtils
                .md5Hex(unencryptedPassword).toUpperCase();
        User user = userService.findUserByEmail(email);
        return user != null && userService.authenticate(user, md5Hex);
    }

}
