package com.example.demojpa.service;

import com.example.demojpa.entity.Person;
import com.example.demojpa.entity.Purpose;
import com.example.demojpa.entity.Status;
import com.example.demojpa.exception.BusinessException;
import com.example.demojpa.exception.ErrorCode;
import com.example.demojpa.repository.PersonRepository;
import com.example.demojpa.request.PersonRequest;
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
    private PurposeService purposeService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;


    public void create(PersonRequest request) throws BusinessException {

        if (personRepository.findPerson(request.getLogin()).isPresent() || personRepository.findPersonByVkid(request.getVkid()).isPresent()) {
            throw new BusinessException(ErrorCode.PERSON_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
        Person per;

        if(request.getPassword()!=null) {
             per = new Person(request.getLogin(), encryptedService.encrypted(request.getPassword()), request.getEmail(), request.getVkid());
        }
        else{
             per = new Person(request.getLogin(), null, request.getEmail(), request.getVkid());

        }
        personRepository.save(per);
    }




    public Person get(Integer vkid) throws BusinessException {
        return personRepository.findPersonByVkid(vkid).orElseThrow(() -> new BusinessException(ErrorCode.PERSON_NOT_FOUND, HttpStatus.NOT_FOUND));
    }


    public List<Purpose> getByPersonVKIdPurpose(Integer vkid) throws BusinessException {
        return personRepository.findPersonByVkid(vkid)
                .orElseThrow(() -> new BusinessException(ErrorCode.PERSON_NOT_FOUND, HttpStatus.NOT_FOUND))
                .getPurposes().stream().filter(n-> n.getStatus()==Status.PROCESS).toList();
    }



    public List<Person> all() {
        return personRepository.findAll();
    }

    public void deleteId(Long id) throws BusinessException {

        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        } else {
            throw new BusinessException(ErrorCode.PERSON_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

    }

    @Transactional
    public void deleteVkId(Integer vkid) throws BusinessException {
        personRepository.findPersonByVkid(vkid)
                .ifPresentOrElse(p -> {
                    purposeService.deleteByUserId(p.getId());
                    personRepository.deleteById(p.getId());
                }, () -> {
                    throw new BusinessException(ErrorCode.PERSON_NOT_FOUND, HttpStatus.NOT_FOUND);
                });
    }



}
