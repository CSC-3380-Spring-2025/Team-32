package com.gobbledygook.gobbledygook;


/* This class is not meant to be used yet. It's purpose is to document how the main game loop works
    Each functionality will be implemented later on.
 */
public class GameLoop {
    public static void mainLoop() {
        boolean gameOver = false;
        boolean firstRound = true;
        while (!gameOver) {
            /* Sends lobby page and listens for requests to join. After timer is up, initialize GameSession object and return */
            if (firstRound) {
                lobbyPhase();
            }
            /* Sends WORD_CHAIN_PHASE message to all players, along with the target word. Listens for word chain submissions and processes them */
            wordChainPhase();
            /* Sends DEFINITION_PHASE message and listens for definition submissions */
            definitionPhase();
            /* generic voting procedure that sends VOTING_PHASE message to clients along with list of choices
            Then listens for votes from clients and tallies them up.
             */
            // votingPhase();
            /* Sends STORY_PHASE message and listens for story submissions */
            storyPhase();
            /* same as previous voting phase, but the options are the story submissions instead of definitions */
            // votingPhase();
            /* Signals the end of the round to all players */
            roundEnd();
            firstRound = false;
        }
    }

    /* stub methods so that the class compiles - will implement later */
    static void lobbyPhase() {}
    static void wordChainPhase() {}
    static void definitionPhase() {}
    static void votingPhase(GameSession gameSession) {
        System.out.println("Voting phase has ended. Processing votes...");
        gameSession.getCurrentRound().processVotes(gameSession);
    }
    static void storyPhase() {}
    static void roundEnd() {}

}
