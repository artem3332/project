package com.example.demojpa.repository;

import com.example.demojpa.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>
{

    @Query("SELECT m from Person m where m.login = :login ")
    Optional<Person> findPerson(String login);

    Optional<Person> findPersonByVkid(Integer vkid);



    boolean existsByVkid(Integer vkid);

    @Modifying
    @Query("DELETE  from Person  where vkid = :vkid ")
    void deletePersonWithVkid(Integer vkid);

}
