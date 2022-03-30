package com.example.demojpa.scheduler;

import com.example.demojpa.entity.Notification;
import com.example.demojpa.entity.Person;
import com.example.demojpa.entity.Status;
import com.example.demojpa.repository.PersonRepository;
import com.example.demojpa.repository.NotificationRepository;
import com.example.demojpa.request.PostNotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class StatusScheduler
{
    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    PersonRepository personRepository;



    @Value("${dempjpa.scheduler.purpose.url}")
    private String url;


    @Value("${status-scheduler.enabled}")
    private Boolean enabled;

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRateString = "${status-scheduler.period}")
    public void  updateStatus()
    {
        if (!enabled)
            return;
        log.info("StatusScheduler is running");



        for (Person person : personRepository.findAll())
        {
            for (Notification notification: person.getNotifications())
            {
                if (notification.getTime().getHour()==LocalDateTime.now().getHour()
                        &&  notification.getTime().getMinute()==LocalDateTime.now().getMinute()
                        &&  notification.getTime().getDayOfMonth()==LocalDateTime.now().getDayOfMonth()
                        &&  notification.getStatus()== Status.PROCESS)
                {
                    RestTemplate restTemplate = new RestTemplate();
                    PostNotificationRequest postNotificationRequest=new PostNotificationRequest(notification.getNotification(),notification.getStatus(),notification.getTime());
                    restTemplate.postForObject(url + person.getVkid(),postNotificationRequest,String.class);




                }



            }


        }
    }



}