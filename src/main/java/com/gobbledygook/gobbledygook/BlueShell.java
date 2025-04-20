// blue shell power up
package com.gobbledygook.gobbledygook;

public class BlueShell extends PowerUp {
    private Round targetRound; // Store the round when the power-up is activated

    public BlueShell(Player ownerOfPowerup) {
        super(ownerOfPowerup, "Blue Shell", 3);
    }

    public boolean usePowerup() {
        if (numberOfUsesLeft > 0) {
            numberOfUsesLeft--;
            
            //Player topPlayer = gameSession.getTopPlayer(); // get top player
            //topPlayer.setScore(topPlayer.getScore() - 10); // arbitrarily set to -10, can change if needed
            return true;
            // uncomment if messege needed 
            // System.out.println(user.getName() + " activated BlueShell " + targetRound.getRoundNumber());
        } else {
            System.out.println("No uses left for Blue Shell!");
            return false;
        }
    }
}
