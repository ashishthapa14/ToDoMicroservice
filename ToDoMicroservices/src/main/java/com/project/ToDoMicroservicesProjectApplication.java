package com.project;

import com.project.model.ToDo;
import com.project.model.User;
import com.project.repository.ToDoDao;
import com.project.repository.UserDao;
import com.project.utility.EncryptUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@Slf4j
@AllArgsConstructor
@OpenAPIDefinition
public class ToDoMicroservicesProjectApplication{

    public static void main(String[] args) {
        SpringApplication.run(ToDoMicroservicesProjectApplication.class, args);
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**") // apply to all endpoints
//                        .allowedOrigins("http://localhost:3000") // frontend origin
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                        .allowedHeaders("*")
//                        .exposedHeaders("jwt"); // 👈 expose your custom header
//            }
//        };
//    }

}
