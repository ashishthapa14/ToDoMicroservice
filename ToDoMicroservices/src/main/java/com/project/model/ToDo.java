package com.project.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String description;

    @CreationTimestamp
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String priority;

    @Column(nullable = false)
    private String fkUser;
}
