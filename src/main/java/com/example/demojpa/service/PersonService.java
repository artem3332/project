package com.example.demojpa.service;

import com.example.demojpa.entity.Person;
import com.example.demojpa.exception.PersonAlreadyExists;
import com.example.demojpa.repository.PersonRepository;
import com.example.demojpa.repository.TasksRepository;
import com.example.demojpa.request.CreateRequestPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Stream;

@Service
public class PersonService {


    

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TasksRepository tasksRepository;

//    @Scheduled
    public Person create(CreateRequestPerson request) throws PersonAlreadyExists
    {

        if (personRepository.findPerson(request.getLogin()) != null)
        {
            throw new PersonAlreadyExists();
        }
        else
        {
            Person per = new Person(request.getLogin(), request.getParol(),request.getEmail());
            return personRepository.save(per);
        }

    }

    public Stream<String> conclusion()
    {

        return personRepository.findAll().stream()
                .filter(p -> p.getTodos().size() >= 1)
                .filter(p -> p.getEmail().contains("@mail.ru"))
                .map(Person::getLogin);

    }

}
