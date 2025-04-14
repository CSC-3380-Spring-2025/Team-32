// blue shell power up
package com.gobbledygook.gobbledygook;

public class BlueShell extends PowerUp {
    private Round targetRound; // Store the round when the power-up is activated

    public BlueShell(Player ownerOfPowerup) {
        super(ownerOfPowerup, "Blue Shell", 3);
    }

    public void usePowerup() {
        if (numberOfUsesLeft > 0) {
            numberOfUsesLeft--;

            topPlayer = gameSession.getTopPlayer(); // get top player
            topPlayer.score = topPlayer.score - 10; // arbitrarily set to -10, can change if needed

            // uncomment if messege needed 
            // System.out.println(user.getName() + " activated Double or Nothing for Round " + targetRound.getRoundNumber());
        } else {
            System.out.println("No uses left for Double or Nothing!");
        }
    }
}
