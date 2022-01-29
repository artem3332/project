package com.example.demojpa.service;

import com.example.demojpa.entity.Person;
import com.example.demojpa.exception.BusinessException;
import com.example.demojpa.exception.ErrorCode;
import com.example.demojpa.repository.PersonRepository;
import com.example.demojpa.request.CreateRequestPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class PersonService {


    @Autowired
    private PersonRepository personRepository;

    public void create(CreateRequestPerson request) throws BusinessException {

        if (personRepository.findPerson(request.getLogin()).isPresent()) {
            throw new BusinessException(ErrorCode.PERSON_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }

        Person per = new Person(request.getLogin(), request.getParol(), request.getEmail());
        personRepository.save(per);
    }

    public Person get(Long id) throws BusinessException {
        return personRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.PERSON_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public List<Person> all() {
        return personRepository.findAll();
    }

    public void delete(Long id) throws BusinessException {

        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        } else {
            throw new BusinessException(ErrorCode.PERSON_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

    }

    public Stream<String> conclusion() {
        return personRepository.findAll().stream()
                .filter(p -> p.getPurposes().size() >= 1)
                .filter(p -> p.getEmail().contains("@mail.ru"))
                .map(Person::getLogin);

    }

}
