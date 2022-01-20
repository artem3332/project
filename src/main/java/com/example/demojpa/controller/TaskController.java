package com.example.demojpa.controller;


import com.example.demojpa.entity.Task;
import com.example.demojpa.exception.TasksAlreadyExists;
import com.example.demojpa.exception.PersonNotFound;
import com.example.demojpa.repository.PersonRepository;
import com.example.demojpa.repository.TasksRepository;
import com.example.demojpa.request.CreateRequestTasks;
import com.example.demojpa.service.DefaultEmailService;
import com.example.demojpa.service.TasksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private DefaultEmailService emailService;

    @Autowired
    private TasksService taskserv;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateRequestTasks todo, @RequestParam Long userid) {
        try {

            log.info("Create Task");
            emailService.sendSimpleEmail(personRepository.getById(userid).getEmail(), "Задача", "Задача "+todo.getTask() +" создана!");
            taskserv.creat(todo, userid);
            return ResponseEntity.ok("Задача успешно создана!");

        }
        catch (TasksAlreadyExists ex)
        {
            log.error("Task already exists",ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Задача уже создана!");
        }
        catch (PersonNotFound exe)
        {
            log.error("Person not found",exe);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь ещё не создан!");
        }
        catch (Exception e)
        {
            log.error("Exception",e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Задача не создана!");
        }
    }


    @GetMapping("/")
    public ResponseEntity<List<Task>> all()
    {
        log.info("All Task");
        taskserv.all();
        return ResponseEntity.ok(tasksRepository.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Task> get(@PathVariable Long id)
    {
        log.info("Get Task id");
        taskserv.get(id);
        return ResponseEntity.ok(tasksRepository.findById(id).get());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        if (tasksRepository.existsById(id)) {
            log.info("Delete Task id");
            tasksRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            log.error("Not Delete Task id");
            return ResponseEntity.notFound().build();
        }
    }






}
