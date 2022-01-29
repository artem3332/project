package com.example.demojpa.repository;


import com.example.demojpa.entity.Comment;
import com.example.demojpa.entity.Person;
import com.example.demojpa.entity.Purpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>
{

    Optional<Comment> findByComment(String comment);
}
