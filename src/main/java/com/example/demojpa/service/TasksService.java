package com.example.demojpa.service;

import com.example.demojpa.entity.Task;
import com.example.demojpa.exception.TasksAlreadyExists;
import com.example.demojpa.exception.PersonNotFound;
import com.example.demojpa.repository.PersonRepository;
import com.example.demojpa.repository.TasksRepository;
import com.example.demojpa.request.CreateRequestTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class TasksService {

    @Autowired
    private TasksRepository taskrepo;

    @Autowired
    private PersonRepository personrepo;


    public Task creat(CreateRequestTasks tas, Long userid) throws TasksAlreadyExists, PersonNotFound
    {

        if (taskrepo.findTask(tas.getTask(), userid) != null)
        {
            throw new TasksAlreadyExists();
        }
        else if (!personrepo.existsById(userid))
        {
            throw new PersonNotFound();
        }
        else
        {
            Task tasks = new Task(tas.getTask(), false);
            tasks.setUserId(userid);
            tasks.setStatus(Task.Status.PROCESS);
            tasks.setTime(LocalDateTime.now());
            return taskrepo.save(tasks);
        }
    }

    public void get(Long id)
    {
        Task tasks = taskrepo.findById(id).get();
        tasks.setReadiness(true);
        tasks.setStatus(Task.Status.COMPLETED); //todo ?????
        taskrepo.save(tasks);
    }

    public void all()
    {
        LocalDateTime now = LocalDateTime.now();

        for (Task task : taskrepo.findAll()) {
            if (Duration.between(task.getTime(), now).toMinutes() < 1) {
                task.setStatus(Task.Status.FAILED);
                taskrepo.save(task);
            }
            else
            {
                task.setStatus(Task.Status.COMPLETED);
                taskrepo.save(task);
            }
        }
    }



}
