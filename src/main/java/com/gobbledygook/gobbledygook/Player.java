package com.gobbledygook.gobbledygook;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Player {
    private UUID id;
    private String username;
    private int score;
    private List<PowerUp> powerups;

    public Player(String username, UUID id) {
        this.username = username;
        this.id = id;
        this.score = 0;
        this.powerups = new ArrayList<>();
    }
    public int getVisibleScore(){
        // If player has masked powerup, return lower score then real score.
        if (powerups.stream().anyMatch(p -> p instanceof MaskPlayerScorePowerUp m && m.isActivated())){
            return score/2;
        }
        return score;
    }
}