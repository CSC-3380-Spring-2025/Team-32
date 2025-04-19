package com.gobbledygook.gobbledygook;

import lombok.Data;

import java.util.UUID;

@Data
public class MinigameResult {
    private UUID playerId;
    private int score;
}
