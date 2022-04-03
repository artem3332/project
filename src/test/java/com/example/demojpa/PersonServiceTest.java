package com.example.demojpa;


import com.example.demojpa.entity.Person;
import com.example.demojpa.exception.BusinessException;
import com.example.demojpa.repository.PersonRepository;
import com.example.demojpa.request.PostPersonRequest;
import com.example.demojpa.service.PersonService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private EncryptedService encryptedService;

    private PostPersonRequest postPersonRequest;


    @Test
    public void create() {
        postPersonRequest=new PostPersonRequest("login","password","mail@mail.ru",912345678);
        boolean isPerson =personService.createPerson(postPersonRequest);
        Assert.assertTrue(isPerson);
        Mockito.verify(personRepository,Mockito.times(1)).findPerson(postPersonRequest.getLogin());
        Mockito.verify(encryptedService,Mockito.times(1)).encrypted(postPersonRequest.getPassword());
        Mockito.verify(personRepository,Mockito.times(1)).save(ArgumentMatchers.any(Person.class));
    }

    @Test
    public void allPerson() {
        List<Person> personList=personService.allPerson();
        Assert.assertTrue(personList!=null);

    }
    @Test
    public void getPersonByVkId() throws BusinessException {

        postPersonRequest=new PostPersonRequest("login1","password1","fartem333@mail.ru",912345678);
        boolean isPerson =personService.createPerson(postPersonRequest);
        personService.getPersonByVkId(postPersonRequest.getVkid());

    }




}