package com.gobbledygook.gobbledygook.messages;

import com.gobbledygook.gobbledygook.GamePhase;
import lombok.Data;

@Data
public class WordChainMessage {
    private final GamePhase phase = GamePhase.WORD_CHAIN_PHASE;
    private String word;

    public WordChainMessage(String word) {
        this.word = word;
    }
}
