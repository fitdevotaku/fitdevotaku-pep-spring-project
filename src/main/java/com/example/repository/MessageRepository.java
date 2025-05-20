package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByPostedBy(int postedBy);

    @Modifying
    @Query("UPDATE Message m SET m.messageText = :text WHERE m.messageId = :id")
    int updateMessageText(@Param("id") int id, @Param("text") String text);
}
