package com.gobbledygook.gobbledygook;


import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/* Represents a single round, tracking submissions and votes */
@Data
public class Round {
    private int roundNumber;
    private String targetWord;
    private List<WordChain> wordChainSubmissions; // assuming we'll wanna implement it as a class instead
    private List<Definition> definitions; // i've implemented a definition class
    private List<Story> stories; // assuming we'll want to implement a story class as well
    private Map<UUID, UUID> votes; // Player ID -> Definition ID

    public Round() {
        this.roundNumber = 0;
        try {
            this.targetWord = getRandomWord();
        } catch (IOException e) {
            System.err.println("Error reading the dictionary file: " + e.getMessage());
        }
        this.wordChainSubmissions = new ArrayList<>();
        this.definitions = new ArrayList<>();
        this.stories = new ArrayList<>();
        this.votes = new HashMap<>();
    }

    private static String getRandomWord() throws IOException {
        Resource resource = new ClassPathResource("wordlist");
        InputStream inputStream = resource.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> words = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line);
        }
        Random random = new Random();
        int randomIndex = random.nextInt(words.size());

        return words.get(randomIndex);
    }

    public void addWordChain(UUID playerId, String word) {
        wordChainSubmissions.add(new WordChain(playerId, word));
    }

    public void addDefinition(UUID playerId, String text) {
        definitions.add(new Definition(playerId, text));
    }

    public void addStory(UUID playerId, String text) {
        stories.add(new Story(playerId, text));
    }
    
    public void castVote(UUID playerId, UUID definitionId) {
        votes.put(playerId, definitionId);
    }

    public void processVotes(GameSession gameSession) {
        Map<UUID, Player> playerMap = gameSession.getPlayers().stream()
                .collect(Collectors.toMap(Player::getId, p -> p));
    
        // Track votes and assign points
        for (Map.Entry<UUID, UUID> voteEntry : votes.entrySet()) {
            UUID voterId = voteEntry.getKey();
            UUID votedDefinitionId = voteEntry.getValue();
    
            // Find the definition
            Definition votedDefinition = definitions.stream()
                    .filter(d -> d.getId().equals(votedDefinitionId))
                    .findFirst()
                    .orElse(null);
    
            if (votedDefinition != null) {
                votedDefinition.addVote();
    
                Player voter = playerMap.get(voterId);
                Player owner = playerMap.get(votedDefinition.getOwnerId());
    
                // Award points for voting correctly
                if (votedDefinition.getText().equals(targetWord)) {
                    voter.setScore(voter.getScore() + 1);
                }
    
                // Award points for fooling others
                if (owner != null) {
                    owner.setScore(owner.getScore() + 1);
                }
            }
        }
    
        sendFeedbackToPlayers(gameSession);
    }

    private void sendFeedbackToPlayers(GameSession gameSession) {
        for (Player player : gameSession.getPlayers()) {
            System.out.println("Feedback for " + player.getUsername() + ": Your new score is " + player.getScore());
            // replace this with actual client feedback sending logic.
        }
    }
}
