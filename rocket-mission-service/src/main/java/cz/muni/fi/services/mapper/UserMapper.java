package cz.muni.fi.services.mapper;

import cz.muni.fi.dto.user.CreateUserDTO;
import cz.muni.fi.dto.user.UpdateUserDTO;
import cz.muni.fi.dto.user.UserDTO;
import cz.muni.fi.entity.User;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Maps User entity related DTOs to their Entity counterparts.
 *
 * @author Martin Hořelka (469003)
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);

    CreateUserDTO userToCreateUserDTO(User user);
    User createUserDTOToUser(CreateUserDTO createUserDTO);

    UpdateUserDTO userToUpdateUserDTO(User user);
    User updateUserDTOToUser(UpdateUserDTO updateUserDTO);

    List<UserDTO> usersToUserDTOs(List<User> users);
}
