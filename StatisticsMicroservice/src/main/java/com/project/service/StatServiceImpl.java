package com.project.service;

import com.project.dao.StatDao;
import com.project.model.Stat;
import com.project.service.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class StatServiceImpl implements StatService{

    private final StatDao statDao;

    @Override
    @Transactional
    public List<Stat> getStat(String jwt, String email) {
        // 1. Call ToDoMicroservice to get List<ToDos>
        List<LinkedHashMap> todos = getNewDataFromToDoMicroservice(jwt);
        // 2. Calculate statistics
        String statisticsDescr = "No statistics available";
        if (todos != null && !todos.isEmpty()) {

            int lowPriorityTodos = 0;
            int highPriorityTodos = 0;

            for (LinkedHashMap todo : todos) {

                String priority = (String) todo.get("priority");
                if (Constants.LOW_PRIORITY.equalsIgnoreCase(priority)) lowPriorityTodos++;
                if (Constants.HIGH_PRIORITY.equalsIgnoreCase(priority)) highPriorityTodos++;
            }

            statisticsDescr = "You have <b>" + lowPriorityTodos + " low priority</b> ToDos and <b>"
                    + highPriorityTodos + " high priority</b> ToDos";

        }

        // 3. Save statistics if first time or a day has passed
        List<Stat> statistics = statDao.getLastStatistics(email);
        log.info(Integer.toString(statistics.size()));
        boolean shouldSave = false;

        if (statistics.isEmpty()) {
            // first time, save
            shouldSave = true;
        } else {
            // check if more than 1 day has passed
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(statistics.get(0).getDateTime(), LocalDateTime.now());
            long days = duration.toDays();
            if (days >= 0) {
                shouldSave = true;
            }
        }

        if (shouldSave) {
            Stat newStat = new Stat(null, statisticsDescr, LocalDateTime.now(), email);
            log.info(newStat.toString());
            statDao.save(newStat);
            statistics = statDao.getLastStatistics(email); // refresh statistics
        }

        // 4. Return the list
        return statistics;
    }


    private List<LinkedHashMap> getNewDataFromToDoMicroservice(String jwt) {
        // prepare headers with JWT
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwt);
        HttpEntity<?> request = new HttpEntity<>(headers);

        // call ToDoMicroservice, expecting List<LinkedHashMap>
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<List<LinkedHashMap>> responseEntity = restTemplate.exchange(
                    "http://localhost:8383/todo-app/showToDos", // Use the configured URL
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<List<LinkedHashMap>>() {}
            );
            return responseEntity.getBody();
        } catch (Exception e) {
            // Log the error and return an empty list
            // In a real application, you might want to use a logger
            e.printStackTrace();
            log.error("Error fetching data from ToDoMicroservice: " + e.getMessage());
            return null;
        }
    }

}
