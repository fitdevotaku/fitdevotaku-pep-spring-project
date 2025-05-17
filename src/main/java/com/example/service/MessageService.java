package com.example.service;

import java.util.List;
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

    public Message updateMessage(Message message) {
        if (messageRepository.existsById(message.getMessageId())) {  // <-- camelCase
            return messageRepository.save(message);
        }
        return null;
    }

    public void deleteMessage(int id) {
        messageRepository.deleteById(id);
    }

    public List<Message> getMessagesByUser(int accountId) {
        return messageRepository.findByAccountId(accountId);
    }
}


