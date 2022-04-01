package com.example.demojpa.service;

import com.example.demojpa.entity.Notification;
import com.example.demojpa.entity.Person;
import com.example.demojpa.entity.Status;
import com.example.demojpa.exception.BusinessException;
import com.example.demojpa.exception.ErrorCode;
import com.example.demojpa.repository.PersonRepository;
import com.example.demojpa.request.PostPersonRequest;
import com.example.demojpa.service.EncryptedService;
import com.example.demojpa.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonService {


    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EncryptedService encryptedService;

    @Autowired
    private NotificationService notificationService;




    public boolean createPerson(PostPersonRequest postPersonRequest) throws BusinessException {

        if (personRepository.findPerson(postPersonRequest.getLogin()).isPresent()) {
            throw new BusinessException(ErrorCode.PERSON_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
        Person per;

        if(postPersonRequest.getPassword()!=null) {
             per = new Person(postPersonRequest.getLogin(), encryptedService.encrypted(postPersonRequest.getPassword()), postPersonRequest.getEmail(), postPersonRequest.getVkid());
        }
        else{
             per = new Person(postPersonRequest.getLogin(),null, postPersonRequest.getEmail(), postPersonRequest.getVkid());

        }
        personRepository.save(per);
        return true;
    }




    public Person getPersonByVkId(Integer vkid) throws BusinessException {
        return personRepository.findPersonByVkid(vkid).orElseThrow(() -> new BusinessException(ErrorCode.PERSON_NOT_FOUND, HttpStatus.NOT_FOUND));
    }


    public List<Notification> getByPersonVKIdNotification(Integer vkid) throws BusinessException {
        return personRepository.findPersonByVkid(vkid)
                .orElseThrow(() -> new BusinessException(ErrorCode.PERSON_NOT_FOUND, HttpStatus.NOT_FOUND))
                .getNotifications().stream().filter(n-> n.getStatus()==Status.PROCESS).toList();
    }



    public List<Person> allPerson() {
        return personRepository.findAll();
    }

    public boolean deletePersonById(Long id) throws BusinessException {

        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        } else {
            throw new BusinessException(ErrorCode.PERSON_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return true;

    }

    @Transactional
    public boolean deletePersonByVkId(Integer vkid) throws BusinessException {
        personRepository.findPersonByVkid(vkid)
                .ifPresentOrElse(p -> {
                    notificationService.deleteNotificationByUserId(p.getId());
                    personRepository.deleteById(p.getId());
                }, () -> {
                    throw new BusinessException(ErrorCode.PERSON_NOT_FOUND, HttpStatus.NOT_FOUND);
                });
        return true;
    }



}
