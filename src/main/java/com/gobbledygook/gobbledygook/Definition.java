package com.gobbledygook.gobbledygook;

import lombok.Data;
import java.util.UUID;

@Data
public class Definition {
    private UUID id; // Unique ID for the definition
    private UUID ownerId; // The player who submitted it
    private String text; // The definition text
    private int votes; // Count of votes received

    public Definition(UUID ownerId, String text) {
        this.id = UUID.randomUUID(); // Unique ID for tracking
        this.ownerId = ownerId;
        this.text = text;
        this.votes = 0;
    }

    public void addVote() {
        this.votes++;
    }
}
