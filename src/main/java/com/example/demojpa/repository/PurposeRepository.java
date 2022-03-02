package com.example.demojpa.repository;


import com.example.demojpa.entity.Purpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurposeRepository extends JpaRepository<Purpose,Long>
{

    @Query(value = "SELECT * from purpose t where t.purpose=:purpose and t.user_id=:userId", nativeQuery = true)
    Optional<Purpose> findPurpose(String purpose, Long userId);

    @Modifying
    void deleteByUserId(Long userId);
}
