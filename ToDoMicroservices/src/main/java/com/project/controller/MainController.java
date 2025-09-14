package com.project.controller;

import com.project.dto.ToDoDTO;
import com.project.dto.UserDTO;
import com.project.exception.UserNotInDatabaseException;
import com.project.exception.UserNotLoggedException;
import com.project.model.ToDo;
import com.project.model.User;
import com.project.service.LoginService;
import com.project.service.ToDoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "jwt")
public class MainController {

    private final LoginService loginService;
    private final ToDoService toDoService;

    @PostMapping(value = "/login")
    public ResponseEntity<UserDTO> login(@RequestParam(value = "email") String email,
                                        @RequestParam(value = "password") String pwd) throws UserNotInDatabaseException, UnsupportedEncodingException {
        UserDTO user = loginService.getUserFromDb(email, pwd);

        String jwt = loginService.createJwt(email, user.getName(), new Date());
        return ResponseEntity.status(HttpStatus.OK).header("jwt", jwt).body(user);
    }

    @GetMapping(value = "/showToDos")
    public ResponseEntity<List<ToDoDTO>> showToDos(HttpServletRequest request) throws UnsupportedEncodingException, UserNotLoggedException, Exception {
        Map<String, Object> userData = loginService.verifyJwtAndGetData(request);
        return ResponseEntity.status(HttpStatus.OK).body(toDoService.getToDos((String) userData.get("email")));
    }

    @PostMapping(value = "/newToDo")
    public ResponseEntity<String> newToDo(HttpServletRequest request,@RequestBody @Valid ToDoDTO toDo) throws UnsupportedEncodingException, UserNotLoggedException {
        log.info(toDo.getFkUser());
        loginService.verifyJwtAndGetData(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(toDoService.addToDo(toDo)+ " successfully added");
    }

    @DeleteMapping(value = "/deleteToDo/{id}")
    public ResponseEntity<String> deleteToDo(HttpServletRequest request, @PathVariable(name = "id") Integer ToDoId) throws Exception {
        loginService.verifyJwtAndGetData(request);
        toDoService.deleteToDo(ToDoId);
        return ResponseEntity.status(HttpStatus.OK).body("To Do Correctly Deleted");
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> addUser(@RequestBody @Valid UserDTO user) throws UserNotInDatabaseException {
        log.info(user.getEmail());
        String email = loginService.addUser(user);
        return new ResponseEntity<>("User successfully added" ,HttpStatus.CREATED);
    }
}
