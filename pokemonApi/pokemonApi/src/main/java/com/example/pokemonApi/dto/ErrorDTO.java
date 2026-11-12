package com.example.pokemonApi.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorDTO {

    private String message;
    private List<String> details;
    private LocalDateTime timestamp;

    public ErrorDTO() {
    }

    public ErrorDTO(String message, List<String> details, LocalDateTime timestamp) {
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
