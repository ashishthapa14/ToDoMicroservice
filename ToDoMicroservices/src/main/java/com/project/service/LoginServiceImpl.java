package com.project.service;

import com.project.dto.UserDTO;
import com.project.exception.UserNotInDatabaseException;
import com.project.exception.UserNotLoggedException;
import com.project.mapper.UserMapper;
import com.project.model.User;
import com.project.repository.UserDao;
import com.project.utility.EncryptUtils;
import com.project.utility.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LoginServiceImpl implements LoginService{

    private final UserDao userDao;
    private final EncryptUtils encryptUtils;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    @Override
    public UserDTO getUserFromDb(String email, String pwd) throws UserNotInDatabaseException {
        Optional<User> userOptional = userDao.findUserByEmail(email);
        User user = userOptional.orElseThrow(() -> new UserNotInDatabaseException("Wrong Email or Password"));

        if(!encryptUtils.decrypt(user.getPassword()).equals(pwd)){
            throw new UserNotInDatabaseException("Wrong Email or Password");
        }

        return UserMapper.INSTANCE.usersToUsersDTO(user);
    }

    @Override @Transactional(rollbackFor = UnsupportedEncodingException.class)
    public String createJwt(String email, String name, Date date) throws UnsupportedEncodingException {
        date.setTime(date.getTime()+ (300*1000));
        return jwtUtils.generateJwt(email,name,date);
    }

    @Override
    public Map<String, Object> verifyJwtAndGetData(HttpServletRequest request) throws UnsupportedEncodingException, UserNotLoggedException {
        String jwt = jwtUtils.getJwtFromHttpRequest(request);
        if(jwt == null){
            throw new UserNotLoggedException("User not logged! Login First.");
        }
        return jwtUtils.jwt2Map(jwt);
    }

    @Override
    public String addUser(UserDTO userDTO) throws UserNotInDatabaseException {
        Optional<User> userOptional = userDao.findUserByEmail(userDTO.getEmail());
        if(userOptional.isPresent()) throw new UserNotInDatabaseException("User Already Exists");

        String encryptedPwd = encryptUtils.encrypt(userDTO.getPassword());
        userDTO.setPassword(encryptedPwd);

        User user = userMapper.usersDTOToUsers(userDTO);
        return userDao.save(user).getEmail();
    }

}
