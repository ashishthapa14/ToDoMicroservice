package com.project.mapper;

import com.project.dto.ToDoDTO;
import com.project.model.ToDo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-10T23:28:06+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ToDoMapperImpl implements ToDoMapper {

    @Override
    public ToDoDTO entityToDTO(ToDo toDo) {
        if ( toDo == null ) {
            return null;
        }

        ToDoDTO toDoDTO = new ToDoDTO();

        toDoDTO.setDateTime( toDo.getDateTime() );
        toDoDTO.setDescription( toDo.getDescription() );
        toDoDTO.setFkUser( toDo.getFkUser() );
        toDoDTO.setId( toDo.getId() );
        toDoDTO.setPriority( toDo.getPriority() );

        return toDoDTO;
    }

    @Override
    public ToDo dtoToEntity(ToDoDTO ToDosDTO) {
        if ( ToDosDTO == null ) {
            return null;
        }

        ToDo.ToDoBuilder toDo = ToDo.builder();

        toDo.description( ToDosDTO.getDescription() );
        toDo.fkUser( ToDosDTO.getFkUser() );
        toDo.priority( ToDosDTO.getPriority() );

        return toDo.build();
    }
}
