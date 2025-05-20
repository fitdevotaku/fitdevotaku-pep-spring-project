package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.example.dto.MessageDTO;
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
            // returns the 409 duplicate user account message/status
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); 
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
    public ResponseEntity<Message> createMessage(@Valid @RequestBody MessageDTO dto, BindingResult result) {
        // Bean-validation check
        if (result.hasErrors()) {
            return ResponseEntity.status(400).build();
        }

        // User existence check
        Integer userId = dto.getPostedBy();
        if (accountService.getAccountById(userId) == null) {
            // test expects 400 when that user isn't in the DB
            return ResponseEntity.status(400).build();
        }

        // Map DTO → Entity
        Message msg = new Message();
        msg.setPostedBy(userId);
        msg.setMessageText(dto.getMessageText());
        msg.setTimePostedEpoch(dto.getTimePostedEpoch());

        // Save
        Message created = messageService.addMessage(msg);

        if (created != null) {
            return ResponseEntity.ok(created);
        } else {
            // fallback to 400 if, for whatever reason, save fails
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
    public ResponseEntity<Integer> updateMessage(@PathVariable("id") int id, @RequestBody Message payload) {

       payload.setMessageId(id);
       boolean updated = messageService.updateMessage(payload);

       if (updated) {
            return ResponseEntity.ok(id); 
       } else {
            // validation failed-- id also could not be found
            return ResponseEntity.badRequest().build(); // 400
       }
    }

    // Delete message by id
    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable("id") int id) {
        // searching for messages
        Message msg = messageService.getMessageById(id);
        
        // nothing to delete, cant find id
        if (msg == null) {
            return ResponseEntity.ok().build();
        }
        // delete the entity -- success
        messageService.deleteMessage(id);
        return ResponseEntity.ok(1);

    }

    // Get all messages by user accountId
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable("accountId") int accountId) {
        return ResponseEntity.ok(messageService.getMessagesByUser(accountId));
    }
}
