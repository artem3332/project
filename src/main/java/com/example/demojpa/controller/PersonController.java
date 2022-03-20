package com.example.demojpa.controller;

import com.example.demojpa.aop.Loggable;
import com.example.demojpa.entity.Person;
import com.example.demojpa.entity.Purpose;
import com.example.demojpa.exception.BusinessException;
import com.example.demojpa.request.PersonRequest;
import com.example.demojpa.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
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
    public ResponseEntity<?> create(@Valid @RequestBody PersonRequest p) throws BusinessException
    {

        log.info("Create person");
        personService.create(p);
        return ResponseEntity.ok("Пользователь успешно создан!");
    }

    @Loggable
    @GetMapping("/purposeAll/{vkid}")
    public  ResponseEntity<List<Purpose>> getByPersonVKIdPurposes(@PathVariable Integer vkid) throws BusinessException {
        log.info("All Purpose vkid");
        return ResponseEntity.ok(personService.getByPersonVKIdPurpose(vkid));
    }



    @Loggable
    @GetMapping("/{vkid}")
    public ResponseEntity<Person> get(@PathVariable Integer vkid)throws BusinessException
    {
        log.info("Find by vkid");
        return ResponseEntity.ok(personService.get(vkid));
    }

    @GetMapping("/")
    public ResponseEntity<List<Person>> all()
    {
        log.info("All person");
        return ResponseEntity.ok(personService.all());
    }

    @DeleteMapping("/delete1/{id}")
    public ResponseEntity<?> deleteid(@PathVariable Long id) throws BusinessException
    {
        log.info("Delete person");
        personService.deleteId(id);
        return ResponseEntity.ok("Пользователь успешно удалён!");
    }


    @Loggable
    @DeleteMapping("/delete2/{vkid}")
    public ResponseEntity<?> deletevkid(@PathVariable Integer vkid) throws BusinessException
    {
        log.info("Delete person");
        personService.deleteVkId(vkid);
        return ResponseEntity.ok("Пользователь успешно удалён!");
    }




}