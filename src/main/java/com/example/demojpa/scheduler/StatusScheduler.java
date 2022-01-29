package com.example.demojpa.scheduler;

import com.example.demojpa.entity.Purpose;
import com.example.demojpa.repository.PurposeRepository;
import com.example.demojpa.service.DefaultEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class StatusScheduler
{
    @Autowired
    PurposeRepository purposeRepository;

    @Autowired
    private DefaultEmailService emailService;

    @Value("${status-scheduler.enabled}")
    private Boolean enabled;

    @Scheduled(timeUnit = TimeUnit.HOURS, fixedRateString = "${status-scheduler.period}")
    public void  updateStatus()
    {
        if (!enabled)
            return;
        log.info("StatusScheduler is running");

        for (Purpose subgoal : purposeRepository.findAll()) {
            if (Duration.between(subgoal.getTime(), LocalDateTime.now()).toDays()< subgoal.getDays()) {
                subgoal.setStatus(Purpose.Status.FAILED);
                purposeRepository.save(subgoal);
                log.info("Purpose {} Failed", subgoal);
                emailService.sendSimpleEmail(subgoal.getEmail(), "Failed", "Задача "+subgoal.getPurpose() +" провалена!");
            }
        }
    }

}
