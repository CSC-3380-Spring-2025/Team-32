// blue shell power up
package com.gobbledygook.gobbledygook;
import org.springframework.beans.factory.annotation.Autowired;

import com.gobbledygook.gobbledygook.GameSession;

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
            
            topPlayer.score = topPlayer.score - 10; // arbitrarily set to -10, can change if needed

            // uncomment if messege needed 
            // System.out.println(user.getName() + " activated Double or Nothing for Round " + targetRound.getRoundNumber());
        } else {
            System.out.println("No uses left for Double or Nothing!");
        }
    }
}
