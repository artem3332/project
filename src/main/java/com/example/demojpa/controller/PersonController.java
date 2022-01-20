package com.example.demojpa.controller;

import com.example.demojpa.exception.PersonAlreadyExists;
import com.example.demojpa.request.CreateRequestPerson;
import com.example.demojpa.entity.Person;
import com.example.demojpa.repository.PersonRepository;
import com.example.demojpa.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personserv;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Person> get(@PathVariable Long id)
    {
        log.info("Get Person id");
        return ResponseEntity.ok(personRepository.findById(id).get());
    }


    @GetMapping("/")
    public ResponseEntity<List<Person>> all()
    {
        log.info("All Person");
        return ResponseEntity.ok(personRepository.findAll());
    }


    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateRequestPerson p) {

        try {
            log.info("Create Person");
            personserv.create(p);
            return ResponseEntity.ok("Пользователь успешно создан!");
        } catch (PersonAlreadyExists ex) {
            log.error("Person already exists",ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь уже создан!");
        } catch (Exception e) {
            log.error("DB Exception",e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь не создан!");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (personRepository.existsById(id)) {
            log.info("Delete Person id");
            personRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            log.error("Not delete Person id");
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/conclusion")
    public Stream<String> conclus()
    {
        log.info("Conclusion");
        return personserv.conclusion();
    }


}













