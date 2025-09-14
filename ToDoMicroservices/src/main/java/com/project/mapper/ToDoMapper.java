package com.project.mapper;

import com.project.dto.ToDoDTO;
import com.project.model.ToDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ToDoMapper {

    ToDoMapper INSTANCE = Mappers.getMapper(ToDoMapper.class);

    //Map Entity -> DTO
    ToDoDTO entityToDTO(ToDo toDo);

    //Map DTO -> Entity
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "dateTime",ignore = true)
    ToDo dtoToEntity(ToDoDTO ToDosDTO);
}