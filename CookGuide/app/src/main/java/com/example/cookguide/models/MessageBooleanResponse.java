package com.example.cookguide.models;

public class MessageBooleanResponse {
    private Boolean message;

    public MessageBooleanResponse(Boolean message) {
        this.message = message;
    }

    public Boolean getMessage() {
        return message;
    }

    public void setMessage(Boolean message) {
        this.message = message;
    }
}
