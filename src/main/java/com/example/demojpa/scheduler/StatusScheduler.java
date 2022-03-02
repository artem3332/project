package com.example.demojpa.scheduler;

import com.example.demojpa.entity.Person;
import com.example.demojpa.entity.Purpose;
import com.example.demojpa.repository.PersonRepository;
import com.example.demojpa.repository.PurposeRepository;
import com.example.demojpa.request.PurposeRequest;
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
    PurposeRepository purposeRepository;

    @Autowired
    PersonRepository personRepository;



    @Value("${dempjpa.scheduler.purpose.url}")
    private String url;


    @Value("${status-scheduler.enabled}")
    private Boolean enabled;

    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedRateString = "${status-scheduler.period}")
    public void  updateStatus()
    {
        if (!enabled)
            return;
        log.info("StatusScheduler is running");


        for (Person person : personRepository.findAll())
        {
            for (Purpose purpose: person.getPurposes())
            {
                if (purpose.getTime().getHour()==LocalDateTime.now().getHour()
                        &&  purpose.getTime().getMinute()==LocalDateTime.now().getMinute()
                        &&  purpose.getTime().getDayOfMonth()==LocalDateTime.now().getDayOfMonth())
                {
                    RestTemplate restTemplate = new RestTemplate();
                    PurposeRequest purposeRequest=new PurposeRequest(purpose.getPurpose(),purpose.getTime());
                    restTemplate.postForObject(url + person.getVkid(),purposeRequest,String.class);
                    purposeRepository.delete(purpose);




                }



            }


        }
    }



}
