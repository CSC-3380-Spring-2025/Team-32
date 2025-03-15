package com.gobbledygook.gobbledygook;

import lombok.Data;

import java.util.UUID;

@Data
public class WordChain {
    private UUID playerId;
    private String word;

    public WordChain(UUID playerId, String word) {
        this.playerId = playerId;
        this.word = word;
    }
}
