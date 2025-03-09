package com.gobbledygook.gobbledygook;


import lombok.Data;

import java.util.UUID;

@Data
public class Player {
    private UUID id;
    private String username;
    private int score;

    public Player(String username, UUID id) {
        this.username = username;
        this.id = id;
        this.score = 0;
    }
}