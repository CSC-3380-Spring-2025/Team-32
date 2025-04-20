// blue shell power up
package com.gobbledygook.gobbledygook;
import org.springframework.beans.factory.annotation.Autowired;

public class BlueShell extends PowerUp {
    
    @Autowired 
    GameSession gameSession;
    /*
    private Round targetRound; 
    Store the round when the power-up is activated
    */
    public BlueShell(Player ownerOfPowerup) {
        super(ownerOfPowerup, "Blue Shell", 3);
        this.gameSession = gameSession;
    }

    public boolean usePowerup() {
        if (numberOfUsesLeft > 0) {
            numberOfUsesLeft--;

            Player topPlayer = gameSession.getTopPlayer(); // get top player
            topPlayer.setScore(topPlayer.getScore() - 10); // reduce score

            // Optional log
            // System.out.println(ownerOfPowerup.getName() + " activated Blue Shell!");

            return true; // success
        } else {
            System.out.println("No uses left for Blue Shell!");
            return false; // failed to use
        }
    }
}
