package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

@RestController
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    // Register new account...
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        Account createdAccount = accountService.addAccount(account);
        if (createdAccount != null) {
            return ResponseEntity.ok(createdAccount);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    // Login existing account
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account incoming) {
        Account foundLogin = accountService.login(incoming.getUsername(), incoming.getPassword());
        if (foundLogin != null) {
            return ResponseEntity.ok(foundLogin);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    // Create message
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMsg = messageService.addMessage(message);
        if (createdMsg != null) {
            return ResponseEntity.ok(createdMsg);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    // Get all messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    // Get message by id
    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable("id") int id) {
        Message msg = messageService.getMessageById(id);
        if (msg != null) {
            return ResponseEntity.ok(msg);
        } else {
            return ResponseEntity.ok().build(); // empty body
        }
    }

    // Update message by id
    @PatchMapping("/messages/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable("id") int id, @RequestBody Message message) {
        message.setMessageId(id);
        Message updated = messageService.updateMessage(message);
        if (updated != null) {
            Message fresh = messageService.getMessageById(id);
            return ResponseEntity.ok(fresh);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    // Delete message by id
    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Message> deleteMessage(@PathVariable("id") int id) {
        Message toDelete = messageService.getMessageById(id);
        messageService.deleteMessage(id);
        if (toDelete != null) {
            return ResponseEntity.ok(toDelete);
        } else {
            return ResponseEntity.ok().build(); // empty body
        }
    }

    // Get all messages by user accountId
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable("accountId") int accountId) {
        return ResponseEntity.ok(messageService.getMessagesByUser(accountId));
    }
}
