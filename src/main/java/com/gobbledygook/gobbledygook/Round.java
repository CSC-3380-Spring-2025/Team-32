package com.gobbledygook.gobbledygook;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* Represents a single round, tracking submissions and votes */
public class Round {
    private int roundNumber;
    private String targetWord;
    /* UUID in the follows Maps correspond to players */
    private Map<UUID, String> wordChainSubmissions;
    private Map<UUID, String> fakeDefinitions;
    private Map<UUID, Integer> votes;
    private Map<UUID, String> stories;

    public Round() {
        this.roundNumber = 0;
        this.targetWord = "";
        this.wordChainSubmissions = new HashMap<>();
        this.fakeDefinitions = new HashMap<>();
        this.votes = new HashMap<>();
        this.stories = new HashMap<>();
    }

    /* method to track votes */
    public void castVote(UUID playerId, UUID definitionId) {
        votes.put(playerId, definitionId);
    }
}
