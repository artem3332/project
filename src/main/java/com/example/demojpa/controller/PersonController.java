package com.example.demojpa.controller;

import com.example.demojpa.aop.Loggable;
import com.example.demojpa.entity.Notification;
import com.example.demojpa.entity.Person;
import com.example.demojpa.exception.BusinessException;
import com.example.demojpa.request.PostPersonRequest;
import com.example.demojpa.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Scope("singleton")
@Slf4j
@RestController
@RequestMapping("/persons")
public class PersonController {


    @Autowired
    private PersonService personService;

    @Loggable
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody PostPersonRequest p) throws BusinessException
    {

        log.info("Create person");
        personService.createPerson(p);
        return ResponseEntity.ok("Пользователь успешно создан!");
    }

    @Loggable
    @GetMapping("/allNotification/{vkid}")
    public  ResponseEntity<List<Notification>> getByPersonVKIdNotification(@PathVariable Integer vkid) throws BusinessException {
        log.info("All Notification by vkid");
        return ResponseEntity.ok(personService.getByPersonVKIdNotification(vkid));
    }



    @Loggable
    @GetMapping("/getPerson/{vkid}")
    public ResponseEntity<Person> getPerson(@PathVariable Integer vkid)throws BusinessException
    {
        log.info("Person find by vkid");
        return ResponseEntity.ok(personService.getPersonByVkId(vkid));
    }

    @GetMapping("/allPerson")
    public ResponseEntity<List<Person>> allPerson()
    {
        log.info("All person");
        return ResponseEntity.ok(personService.allPerson());
    }

    @DeleteMapping("/delete1/{id}")
    public ResponseEntity<?> deletePersonById(@PathVariable Long id) throws BusinessException
    {
        log.info("Delete person");
        personService.deletePersonById(id);
        return ResponseEntity.ok("Пользователь успешно удалён!");
    }


    @Loggable
    @DeleteMapping("/delete2/{vkid}")
    public ResponseEntity<?> deletePersonByVkId(@PathVariable Integer vkid) throws BusinessException
    {
        log.info("Delete person");
        personService.deletePersonByVkId(vkid);
        return ResponseEntity.ok("Пользователь успешно удалён!");
    }




}