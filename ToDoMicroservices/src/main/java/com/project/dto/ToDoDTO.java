package com.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.validator.ValidPriority;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ToDoDTO {

    private Integer id;
    @NotEmpty(message = "toDo.description.empty")
    private String description;
    private LocalDateTime dateTime;

    @ValidPriority(message = "toDo.priority.invalid")
    private String priority;

    @NotEmpty(message = "toDo.user.empty")
    @JsonProperty("email")
    private String fkUser;
}
