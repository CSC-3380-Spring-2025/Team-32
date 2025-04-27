package com.gobbledygook.gobbledygook;

import java.util.UUID;

import lombok.Data;
@Data
public class Story {
    private UUID playerId;
    private String text;
    private int votes;

    public Story(UUID playerId, String text) {
        this.playerId = playerId;
        this.text = text;
        this.votes = 0;
    }

    public void addVote() {
        this.votes++;
    }

}
