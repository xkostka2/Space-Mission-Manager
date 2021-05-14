package cz.muni.fi.facades;



import cz.muni.fi.dto.user.UserDTO;
import cz.muni.fi.dto.user.UserDTO;
import cz.muni.fi.dto.user.UpdateUserDTO;
import cz.muni.fi.dto.user.UserDTO;
import cz.muni.fi.dto.user.CreateUserDTO;
import cz.muni.fi.dto.user.UpdateUserDTO;
import cz.muni.fi.dto.user.CreateUserDTO;
import cz.muni.fi.dto.mission.CreateMissionDTO;
import cz.muni.fi.dto.user.CreateUserDTO;
import cz.muni.fi.dto.user.UpdateUserDTO;
import cz.muni.fi.dto.user.UserDTO;
import cz.muni.fi.dto.mission.MissionDTO;
import cz.muni.fi.dto.user.UserDTO;
import cz.muni.fi.dto.user.UserDTO;
import cz.muni.fi.entity.User;
import cz.muni.fi.entity.User;
import cz.muni.fi.entity.User;
import cz.muni.fi.entity.User;
import cz.muni.fi.enums.LevelOfExperience;
import cz.muni.fi.enums.MissionProgress;
import cz.muni.fi.enums.Role;
import cz.muni.fi.facade.UserFacade;
import cz.muni.fi.facade.MissionFacade;
import cz.muni.fi.facade.UserFacade;
import cz.muni.fi.facade.UserFacade;
import cz.muni.fi.services.impl.UserServiceImpl;
import cz.muni.fi.services.mapper.UserMapperImpl;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class UserFacadeImplTest {
    @Mock
    private UserServiceImpl userService = new UserServiceImpl();

    @Mock
    private UserMapperImpl userMapper;

    @Autowired
    private UserFacade userFacade;

    private CreateUserDTO createUserDTO;

    private CreateUserDTO createUserDTO1;
    private CreateUserDTO createUserDTO2;

    private UserDTO userDTO;
    private UserDTO userDTO1;
    private UserDTO userDTO2;

    private User user;
    private User user1;
    private User user2;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.userFacade = new UserFacadeImpl(userService, userMapper);

        createUserDTO = getCreateUserDTO("create user");
        user = new User();
        user.setName("create user");
        user.setId(3L);

        createUserDTO1 = getCreateUserDTO("user 1");
        createUserDTO2 = getCreateUserDTO("user 2");

        user1 = new User();
        user1.setName("user 1");
        user1.setId(1L);

        user2 = new User();
        user2.setName("user 2");
        user2.setId(2L);

        given(userMapper.createUserDTOToUser(createUserDTO)).willReturn(user);
        given(userMapper.createUserDTOToUser(createUserDTO1)).willReturn(user1);
        given(userMapper.createUserDTOToUser(createUserDTO2)).willReturn(user2);

        given(userService.addUser(user)).willReturn(user);
        given(userService.addUser(user1)).willReturn(user1);
        given(userService.addUser(user2)).willReturn(user2);

        given(userService.findUserById(1L)).willReturn(user1);
        given(userService.findUserById(2L)).willReturn(user2);
        given(userService.findUserById(3L)).willReturn(user);

        userDTO = getUserDTO("create user", 3L);
        userDTO1 = getUserDTO("user 1", 1L);
        userDTO1.setMission(getMissionDTO("simple mission", 1L));
        userDTO1.setMissionAccepted(true);
        userDTO2 = getUserDTO("user 2", 2L);
        userDTO1.setRole(Role.ASTRONAUT);

        given(userMapper.userToUserDTO(user)).willReturn(userDTO);
        given(userMapper.userToUserDTO(user1)).willReturn(userDTO1);
        given(userMapper.userToUserDTO(user2)).willReturn(userDTO2);
    }

    private CreateUserDTO getCreateUserDTO(String name) {
        CreateUserDTO userDTO = new CreateUserDTO();
        userDTO.setName(name);
        return userDTO;
    }

    private UpdateUserDTO getUpdateUserDTO(String name, Long id) {
        UpdateUserDTO userDTO = new UpdateUserDTO();
        userDTO.setName(name);
        userDTO.setId(id);
        return userDTO;
    }

    private UserDTO getUserDTO(String name, Long id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(name);
        userDTO.setId(id);
        return userDTO;
    }
    
    @Test
    public void testAddUser() {
        List<User> userList = Arrays.asList(user1, user2);
        List<UserDTO> userDTOList = Arrays.asList(userDTO1, userDTO2);

        given(userService.findAllUsers()).willReturn(userList);
        given(userMapper.usersToUserDTOs(userList)).willReturn(userDTOList);

        assertThat(userFacade.findAllUsers()).hasSize(2).contains(userDTO1, userDTO2);
        UserDTO userDTO = userFacade.findUserById(userFacade.addUser(createUserDTO).getId());
        assertThat(userDTO).isEqualToIgnoringGivenFields(createUserDTO, "id");

        userList = Arrays.asList(user1, user2, user);
        userDTOList = Arrays.asList(userDTO1, userDTO2, userDTO);

        given(userService.findAllUsers()).willReturn(userList);

        given(userMapper.usersToUserDTOs(userList)).willReturn(userDTOList);

        assertThat(userFacade.findAllUsers()).hasSize(3).contains(userDTO);
    }

    @Test
    public void testUpdateUser() {
        UpdateUserDTO updateUserDTO = getUpdateUserDTO(userDTO1.getName(), userDTO1.getId());
        updateUserDTO.setName("update");
        user1.setName("update");
        given(userMapper.updateUserDTOToUser(updateUserDTO)).willReturn(user1);
        given(userService.updateUser(user1)).willReturn(user1);

        userFacade.updateUser(updateUserDTO);
        assertThat(userFacade.findUserById(updateUserDTO.getId()).getId()).isEqualTo(updateUserDTO.getId());
    }

    @Test
    public void testDeleteUser() {
        List<User> userList = Arrays.asList(user1, user2);
        List<UserDTO> userDTOList = Arrays.asList(userDTO1, userDTO2);

        given(userService.findAllUsers()).willReturn(userList);
        given(userMapper.usersToUserDTOs(userList)).willReturn(userDTOList);

        assertThat(userFacade.findAllUsers()).hasSize(2).contains(userDTO1, userDTO2);

        userFacade.deleteUser(userDTO2);
        userList = Arrays.asList(user1);
        userDTOList = Arrays.asList(userDTO1);

        given(userService.findAllUsers()).willReturn(userList);
        given(userMapper.usersToUserDTOs(userList)).willReturn(userDTOList);

        assertThat(userFacade.findAllUsers()).hasSize(1).contains(userDTO1);
    }

    @Test
    public void testFindAllUsers() {
        List<User> userList = Arrays.asList(user1, user2);
        List<UserDTO> userDTOList = Arrays.asList(userDTO1, userDTO2);

        given(userService.findAllUsers()).willReturn(userList);
        given(userMapper.usersToUserDTOs(userList)).willReturn(userDTOList);

        assertThat(userFacade.findAllUsers()).hasSize(2).contains(userDTO1, userDTO2);
    }

    @Test
    public void testFindAllAstronauts() {
        List<User> userList = Arrays.asList(user1);
        List<UserDTO> userDTOList = Arrays.asList(userDTO1);

        given(userService.findAllUsers()).willReturn(userList);
        given(userMapper.usersToUserDTOs(userList)).willReturn(userDTOList);

        assertThat(userFacade.findAllUsers()).hasSize(1).contains(userDTO1);
    }

    @Test
    public void testFindUserById() {
        given(userService.findUserById(userDTO1.getId())).willReturn(user1);
        given(userService.findUserById(userDTO2.getId())).willReturn(user2);

        assertThat(userFacade.findUserById(userDTO1.getId())).isEqualTo(userDTO1);
        assertThat(userFacade.findUserById(userDTO2.getId())).isEqualTo(userDTO2);
    }

    @Test
    public void testRejectMission() {
        userFacade.rejectAssignedMission(userDTO, "I am Covid");
        assertThat(userFacade.findUserById(userDTO.getId()).getMission()).isNull();
        assertThat(userFacade.findUserById(userDTO.getId()).getMissionAccepted()).isFalse();
    }

    @Test
    public void testAcceptMission() {
        userFacade.acceptAssignedMission(userDTO1);
        assertThat(userFacade.findUserById(userDTO1.getId()).getMission()).isNotNull();
        assertThat(userFacade.findUserById(userDTO1.getId()).getMissionAccepted()).isTrue();

    }

    private MissionDTO getMissionDTO(String name, Long id) {
        MissionDTO missionDTO = new MissionDTO();
        missionDTO.setName(name);
        missionDTO.setId(id);
        return missionDTO;
    }
}