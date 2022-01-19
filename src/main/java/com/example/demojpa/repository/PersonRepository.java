package com.example.demojpa.repository;

import com.example.demojpa.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>
{

    @Query("SELECT m from Person m where m.login = :login")
    Person findPerson(String login);

}
