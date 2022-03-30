package com.example.demojpa.service;


import com.example.demojpa.entity.Notification;
import com.example.demojpa.entity.Status;
import com.example.demojpa.exception.BusinessException;
import com.example.demojpa.exception.ErrorCode;
import com.example.demojpa.repository.NotificationRepository;
import com.example.demojpa.repository.PersonRepository;
import com.example.demojpa.request.DeleteNotificationRequest;
import com.example.demojpa.request.PostNotificationRequest;
import com.example.demojpa.request.PutNotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PersonRepository personRepository;




    public void creatBotNotification(PostNotificationRequest postNotificationRequest, Integer vkid) throws BusinessException {
        if(notificationRepository.findNotification(postNotificationRequest.getNotification(),personRepository.findPersonByVkid(vkid).get().getId()).isPresent())
        {
            throw new BusinessException(ErrorCode.PURPOSE_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
        else if (!personRepository.findPersonByVkid(vkid).isPresent())
        {
            throw new BusinessException(ErrorCode.PERSON_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        else
        {
            Notification notification = new Notification(postNotificationRequest.getNotification(),postNotificationRequest.getStatus(),postNotificationRequest.getTime());
            notification.setUserId(personRepository.findPersonByVkid(vkid).get().getId());
            notification.setStatus(Status.PROCESS);


            notificationRepository.save(notification);


        }
    }


    public void creatNotification(PostNotificationRequest postNotificationRequest, Long userid) throws BusinessException
    {
        if(notificationRepository.findNotification(postNotificationRequest.getNotification(),userid).isPresent())
        {
            throw new BusinessException(ErrorCode.PURPOSE_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
        else if (!personRepository.findById(userid).isPresent())
        {
            throw new BusinessException(ErrorCode.PERSON_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        else
        {
            Notification notification = new Notification(postNotificationRequest.getNotification(),postNotificationRequest.getStatus(),postNotificationRequest.getTime());
            notification.setUserId(userid);
            notification.setStatus(Status.PROCESS);
            notification.setTime(LocalDateTime.now());
            notification.setEmail(personRepository.getById(userid).getEmail());
            notificationRepository.save(notification);
        }
    }

    public Notification getNotificationByUserId(Long id) throws BusinessException
    {
        return  notificationRepository.findById(id).orElseThrow(()-> new BusinessException(ErrorCode.PURPOSE_NOT_FOUND,HttpStatus.NOT_FOUND));
    }

    public List<Notification> allNotification(Long id)
    {

        return personRepository.getById(id).getNotifications();
    }


    public void deleteNotificationByUserId(Long id) {
        notificationRepository.deleteByUserId(id);
    }

    @Transactional
    public void deletePurposeByName(DeleteNotificationRequest deletePurposeRequest) {
        personRepository.findPersonByVkid(deletePurposeRequest.getUserId().intValue())
                .ifPresent(p -> notificationRepository.deleteNotificationByName(deletePurposeRequest.getNotification(), p.getId()));
    }

    @Transactional
    public void putStatusNotification(PutNotificationRequest putNotificationRequest)
    {
        Optional<Notification> purpose = notificationRepository.findNotificationByNotification(putNotificationRequest.getNotification());
        purpose.get().setStatus(Status.COMPLETED);
        notificationRepository.save(purpose.get());

    }

    @Transactional
    public void putTimeNotification(PutNotificationRequest putNotificationRequest)
    {
        Optional<Notification> purpose = notificationRepository.findNotificationByNotification(putNotificationRequest.getNotification());
        purpose.get().setTime(purpose.get().getTime().plusMinutes(10));
        notificationRepository.save(purpose.get());

    }


    public void addSubGoal(PostNotificationRequest n, Long id) throws BusinessException
    {
        Optional<Notification> optional = notificationRepository.findById(id);


        if(optional.isPresent())
        {

            Notification mainPurpose = optional.get();
            boolean match = mainPurpose.getSubGoals().stream().anyMatch(subGoal -> subGoal.getNotification().equals(n.getNotification()));
            if (match)
            {
                throw  new BusinessException(ErrorCode.SUBGOAL_ALREADY_EXISTS,HttpStatus.BAD_REQUEST);
            }

            Notification subPurpose = new Notification(n.getNotification(),n.getStatus(),n.getTime());
            subPurpose.setTime(LocalDateTime.now());
            subPurpose.setEmail(mainPurpose.getEmail());
            subPurpose.setStatus(Status.PROCESS);
            mainPurpose.getSubGoals().add(subPurpose);
            notificationRepository.save(mainPurpose);
        }

        else
        {
            throw new BusinessException(ErrorCode.PURPOSE_NOT_FOUND,HttpStatus.NOT_FOUND);
        }
    }

    public void toChangeStatusSubGoal(Long id) throws BusinessException
    {
        if(notificationRepository.existsById(id))
        {
            Notification purpose = notificationRepository.getById(id);
            purpose.setStatus(Status.COMPLETED);
            notificationRepository.save(purpose);
        }
        else
        {
            throw new BusinessException(ErrorCode.PURPOSE_NOT_FOUND,HttpStatus.NOT_FOUND);
        }




    }


    }




