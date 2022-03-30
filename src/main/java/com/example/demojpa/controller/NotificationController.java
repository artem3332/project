package com.example.demojpa.controller;


import com.example.demojpa.aop.Loggable;
import com.example.demojpa.entity.Notification;
import com.example.demojpa.exception.BusinessException;
import com.example.demojpa.repository.PersonRepository;
import com.example.demojpa.request.PutNotificationRequest;
import com.example.demojpa.request.DeleteNotificationRequest;
import com.example.demojpa.request.PostNotificationRequest;
import com.example.demojpa.service.DefaultEmailService;
import com.example.demojpa.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private DefaultEmailService defaultEmailService;

    @Autowired
    private PersonRepository personRepository;


    @Loggable
    @PostMapping("/createBot/{vkid}")
    public ResponseEntity<?> createNotificationBot(@RequestBody PostNotificationRequest postNotificationRequest, @PathVariable Integer vkid) throws BusinessException
    {
        log.info("Create bot Notification");
        notificationService.creatBotNotification(postNotificationRequest,vkid);
        return ResponseEntity.ok("Цель успешно создана!");
    }


    @PostMapping("/create/{userid}")
    public ResponseEntity<?> createNotification(@RequestBody PostNotificationRequest postNotificationRequest, @PathVariable Long userid) throws BusinessException
    {
        log.info("Create notification");
        notificationService.creatNotification(postNotificationRequest, userid);
        defaultEmailService.sendSimpleEmail(personRepository.getById(userid).getEmail(),
                "Purpose", "Цель успешно "+postNotificationRequest.getNotification()
                        + " поставлена," + "на её выполнение вам"+2 +"дня");

        return ResponseEntity.ok("Цель успешно создана!");
    }

    @GetMapping("/getNotification/{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable Long id) throws BusinessException
    {
        log.info("Get notification id");
        return ResponseEntity.ok(notificationService.getNotificationByUserId(id));
    }

    @GetMapping("/allNotification/{id}")
    public ResponseEntity<List<Notification>> allNotifications(@PathVariable Long id)
    {
        log.info("All notifications");
        return ResponseEntity.ok(notificationService.allNotification(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotificationById(@PathVariable Long id) throws BusinessException
    {
        log.info("Delete notification");
        notificationService.deleteNotificationByUserId(id);
        return ResponseEntity.ok("Цель успешно удалена!");
    }

    @Loggable
    @DeleteMapping("/deleteNotification")
    public ResponseEntity<?> deleteNotificationByName(@RequestBody @Valid DeleteNotificationRequest deleteNotificationRequest)
    {
        log.info("Delete n  otification");
        notificationService.deletePurposeByName(deleteNotificationRequest);
        return ResponseEntity.ok("Цель успешно удалена!");
    }


    @PostMapping("/subgoal/create")
    public ResponseEntity<?> addSubGoal(@RequestBody PostNotificationRequest postNotificationRequest, @RequestParam Long id) throws BusinessException
    {
        log.info("Add sub-goal");
        notificationService.addSubGoal(postNotificationRequest,id);
        return ResponseEntity.ok("Подзадача успешно добавлена!");
    }

    @PostMapping("/subgoal/put/{id}")
    public ResponseEntity<?> putSubGoal(@PathVariable Long id) throws BusinessException
    {
        log.info("Put status sub_goal");
        notificationService.toChangeStatusSubGoal(id);
        return ResponseEntity.ok("Задача успешно выполнена");
    }

    @Loggable
    @PutMapping("/putStatus")
    public  ResponseEntity<?> putStatusNotification(@Valid @RequestBody PutNotificationRequest putNotificationRequest)
    {
        log.info("Put status notification");
        notificationService.putStatusNotification(putNotificationRequest);
        return ResponseEntity.ok("Статус успешно изменён на Completed");


    }

    @Loggable
    @PutMapping("/putTime")
    public  ResponseEntity<?> putTimeNotification(@Valid @RequestBody PutNotificationRequest putNotificationRequest)
    {
        log.info("Put time notification");
        notificationService.putTimeNotification(putNotificationRequest);
        return ResponseEntity.ok("Время увеличено на 10 минут");


    }












}
