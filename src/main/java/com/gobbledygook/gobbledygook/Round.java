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

    public void processVotes(GameSession gameSession) {
        // Map to track player scores
        Map<UUID, Player> playerMap = gameSession.getPlayers().stream()
                .collect(Collectors.toMap(Player::getId, p -> p));
    
        // Determine the correct definition (assuming it's stored in targetWord)
        String correctDefinition = targetWord;
    
        for (Map.Entry<UUID, Integer> voteEntry : votes.entrySet()) {
            UUID voterId = voteEntry.getKey();
            UUID votedDefinitionOwnerId = voteEntry.getValue();
    
            Player voter = playerMap.get(voterId);
            Player definitionOwner = playerMap.get(votedDefinitionOwnerId);
    
            // Award points if the vote was for the correct definition
            if (fakeDefinitions.get(votedDefinitionOwnerId) == null) {
                voter.setScore(voter.getScore() + 1);
            }
    
            // Award points if the player's fake definition was chosen
            if (definitionOwner != null) {
                definitionOwner.setScore(definitionOwner.getScore() + 1);
            }
        }
    
        // Send feedback to all players
        sendFeedbackToPlayers(gameSession);
    }

    private void sendFeedbackToPlayers(GameSession gameSession) {
        for (Player player : gameSession.getPlayers()) {
            System.out.println("Feedback for " + player.getUsername() + ": Your new score is " + player.getScore());
            // replace this with actual client feedback sending logic.
        }
    }
}
