package com.example.dto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MessageDTO {
    private Integer postedBy;

    @NotBlank(message = "Message text cannot be blank")
    @Size(max = 255, message = "Message text must be under 255 characters")
    private String messageText;

    private Long timePostedEpoch;

    // Getters and Setters
    public Integer getPostedBy() { 
        return postedBy; 
    }

    public void setPostedBy(Integer postedBy) { 
        this.postedBy = postedBy; 
    }

    public String getMessageText() { 
        return messageText; 
    }

    public void setMessageText(String messageText) { 
        this.messageText = messageText; 
    }

    public Long getTimePostedEpoch() { 
        return timePostedEpoch; 
    }

    public void setTimePostedEpoch(Long timePostedEpoch) { 
        this.timePostedEpoch = timePostedEpoch; 
    }
}
