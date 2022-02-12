package com.example.demojpa.controller;

import com.example.demojpa.entity.Person;
import com.example.demojpa.entity.Purpose;
import com.example.demojpa.exception.BusinessException;
import com.example.demojpa.request.PersonRequest;
import com.example.demojpa.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody PersonRequest p) throws BusinessException
    {
        log.info("Create person");
        personService.create(p);
        return ResponseEntity.ok("Пользователь успешно создан!");
    }

    @GetMapping("/purposeAll/{vkid}")
    public  ResponseEntity<List<Purpose>> getByPersonVKIdPurposes(@PathVariable Integer vkid) throws BusinessException {
        log.info("All Purpose vkid");
        return ResponseEntity.ok(personService.getByPersonVKIdPurpose(vkid));
    }



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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws BusinessException
    {
        log.info("Delete person");
        personService.delete(id);
        return ResponseEntity.ok("Пользователь успешно удалён!");
    }

    @GetMapping("/conclusion")
    public Stream<String> conclus()
    {
        log.info("Conclusion");
        return personService.conclusion();
    }

}













