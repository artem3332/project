package com.example.demojpa;

import com.example.demojpa.entity.Notification;
import com.example.demojpa.entity.Status;
import com.example.demojpa.exception.BusinessException;
import com.example.demojpa.repository.NotificationRepository;
import com.example.demojpa.repository.PersonRepository;
import com.example.demojpa.request.PostNotificationRequest;
import com.example.demojpa.service.NotificationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @MockBean
    private NotificationRepository notificationRepository;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private PostNotificationRequest postNotificationRequest;

    @Test
    public void createBotNotification() throws BusinessException {
        postNotificationRequest =new PostNotificationRequest("Notification", Status.PROCESS, LocalDateTime.now());
        notificationService.createBotNotification(postNotificationRequest,200098257);
        Mockito.verify(notificationRepository,Mockito.times(1)).findNotification(postNotificationRequest.getNotification(),
                personRepository.findPersonByVkid(12345).get().getId());
        Mockito.verify(personRepository,Mockito.times(1)).findPersonByVkid(12345);
        Mockito.verify(notificationRepository,Mockito.times(1)).save(ArgumentMatchers.any(Notification.class));

    }




}
