// blue shell power up
package com.gobbledygook.gobbledygook;
import org.springframework.beans.factory.annotation.Autowired;

public class BlueShell extends PowerUp {
    
    @Autowired 
    GameSession gameSession;

    private Round targetRound; // Store the round when the power-up is activated

    public BlueShell(Player ownerOfPowerup) {
        super(ownerOfPowerup, "Blue Shell", 3);
    }

        
    public boolean usePowerup() {
        if (numberOfUsesLeft > 0) {
            numberOfUsesLeft--;

            Player topPlayer = gameSession.getTopPlayer(); // get top player
            int score = topPlayer.getScore();
            score -= 10; // arbitrarily set to -10, can change if needed
            topPlayer.setScore(score);
            
            BlueShell.targetRound = gameSession.getCurrentRound();
            // uncomment if message needed 
            // System.out.println(user.getName() + " activated Double or Nothing for Round " + targetRound.getRoundNumber());
            return true;
        } else {
            System.out.println("No uses left for Double or Nothing!");
            return false;
        }
    }
}
