package com.project.service;

import com.project.dto.ToDoDTO;
import com.project.exception.ToDoException;
import com.project.exception.UserNotInDatabaseException;
import com.project.mapper.ToDoMapper;
import com.project.model.ToDo;
import com.project.repository.ToDoDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ToDoServiceImple implements ToDoService {

    private final ToDoMapper toDoMapper;
    private final ToDoDao toDoDao;

    @Override @Transactional(readOnly = true)
    public List<ToDoDTO> getToDos(String email) throws Exception {
        List<ToDo> toDoList = toDoDao.findByFkUser(email);
        if (toDoList.isEmpty()) {
            throw new Exception("No todos found for user " + email);
        }
        log.info("Returning the size of List {}", toDoList.size());
        return toDoList.stream()
                .map(toDoMapper::entityToDTO)
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addToDo(ToDoDTO toDo) {
        ToDo toDoEntity = toDoMapper.dtoToEntity(toDo);
        return toDoDao.save(toDoEntity).getId();//work
    }

    @Override
    @Transactional
    public void deleteToDo(Integer id) throws ToDoException {
        Optional<ToDo> optionalToDo = toDoDao.findById(id);
        ToDo toDo = optionalToDo.orElseThrow(()->new ToDoException("Id Does Not Exist"));
        toDoDao.deleteById(id);
    }
}
