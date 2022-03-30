package com.example.demojpa.repository;


import com.example.demojpa.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long>
{

    @Query(value = "SELECT * from Notification t where t.notification=:notification and t.user_id=:userId", nativeQuery = true)
    Optional<Notification> findNotification(String notification, Long userId);

    @Modifying
    Optional<Notification> findNotificationByUserId(Long userId);



    @Query(value="SELECT * FROM Notification t WHERE t.notification=:notification",nativeQuery = true)
    Optional<Notification> findNotificationByNotification(String notification);

    @Modifying
    void deleteByUserId(Long userId);


    @Modifying
    @Query("DELETE FROM Notification m WHERE  m.notification=:notification and m.userId=:userId")
    void deleteNotificationByName(String notification, Long userId);


}
