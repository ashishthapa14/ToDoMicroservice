package com.project.controller;

import com.project.model.Stat;
import com.project.service.StatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
public class StatController {

    private final StatService statService;

    @PostMapping("/getStat")
    public ResponseEntity<List<Stat>> getStatistics(@RequestParam(value = "jwt") String jwt, @RequestParam(value = "email") String email){
        return new ResponseEntity<>(statService.getStat(jwt,email), HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test(){
        return "Stat works correctly";
    }
}
