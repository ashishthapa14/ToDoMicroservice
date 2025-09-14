package com.project.mapper;

import com.project.dto.UserDTO;
import com.project.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    //Map Entity -> DTO
    @Mapping(target = "password",ignore = true)
    UserDTO usersToUsersDTO(User users);

    //Map DTO -> Entity
    User usersDTOToUsers(UserDTO usersDTO);
}
