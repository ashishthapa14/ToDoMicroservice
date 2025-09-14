package com.project.service;

import com.project.dto.UserDTO;
import com.project.exception.UserNotInDatabaseException;
import com.project.exception.UserNotLoggedException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

public interface LoginService {
    UserDTO getUserFromDb(String email, String pwd) throws UserNotInDatabaseException;

    String createJwt(String email, String name, Date date) throws UnsupportedEncodingException;

    Map<String, Object> verifyJwtAndGetData(HttpServletRequest request)
            throws UnsupportedEncodingException, UserNotLoggedException;
    String addUser(UserDTO userDTO) throws UserNotInDatabaseException;
}
