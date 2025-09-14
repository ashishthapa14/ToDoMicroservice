package com.project.mapper;

import com.project.dto.UserDTO;
import com.project.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-10T23:28:06+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO usersToUsersDTO(User users) {
        if ( users == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setEmail( users.getEmail() );
        userDTO.setName( users.getName() );

        return userDTO;
    }

    @Override
    public User usersDTOToUsers(UserDTO usersDTO) {
        if ( usersDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( usersDTO.getEmail() );
        user.name( usersDTO.getName() );
        user.password( usersDTO.getPassword() );

        return user.build();
    }
}
