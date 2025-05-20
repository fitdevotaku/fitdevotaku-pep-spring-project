package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int id) {
        return messageRepository.findById(id).orElse(null);
    }

    public List<Message> getMessagesByUser(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
    @Transactional
    public boolean updateMessage(Message msg) {

        String txt = msg.getMessageText();
        if (txt == null || txt.trim().isEmpty() || txt.length() > 255) {  // <-- camelCase
            return false;
        }

        if (!messageRepository.existsById(msg.getMessageId())) {
            return false;
        }
        messageRepository.save(msg);
        return true;
    }

    public void deleteMessage(int id) {
        messageRepository.deleteById(id);
    }


    public boolean deleteMessageById(int id) {

        if (!messageRepository.existsById(id)) {
            return false;
        }

        messageRepository.deleteById(id);
        return true;
    }

   
}


