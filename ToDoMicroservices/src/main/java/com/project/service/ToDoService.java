package com.project.service;

import com.project.dto.ToDoDTO;
import com.project.exception.ToDoException;
import com.project.exception.UserNotInDatabaseException;
import com.project.model.ToDo;

import java.util.List;

public interface ToDoService {

    List<ToDoDTO> getToDos(String email) throws Exception;

    Integer addToDo(ToDoDTO toDoDTO);

    void deleteToDo(Integer id) throws ToDoException;
}
