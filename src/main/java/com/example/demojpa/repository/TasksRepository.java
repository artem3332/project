package com.example.demojpa.repository;


import com.example.demojpa.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksRepository extends JpaRepository<Task,Long>
{

    @Query(value = "SELECT * from task t where t.task=:task and t.user_id=:userId", nativeQuery = true)
    Task findTask(String task, Long userId);

}
