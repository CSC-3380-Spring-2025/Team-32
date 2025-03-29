package com.gobbledygook.gobbledygook;

import lombok.Data;

//@Data
public class DoubleOrNothing extends PowerUp {
    private Round targetRound; // Store the round when the power-up is activated

    public DoubleOrNothing(Player ownerOfPowerup) {
        super(ownerOfPowerup, "Double or Nothing", 3);
    }

//    @Override
    public void usePowerup() {
        if (numberOfUsesLeft > 0) {
            numberOfUsesLeft--;

//            targetRound = gameSession.getCurrentRound(); // Store the current round

            // uncomment if messege needed 
            // System.out.println(user.getName() + " activated Double or Nothing for Round " + targetRound.getRoundNumber());
        } else {
            System.out.println("No uses left for Double or Nothing!");
        }
    }

    // is called whenever the game state is changed
    public void onGameStateChange(GameSession gameSession, Player user) {
//        if (targetRound != null && gameSession.getCurrentRound() == targetRound
//            && gameSession.getState() == GameState.ROUND_END) {
//
//            user.setScore(user.getScore() * 2);
//            System.out.println(user.getName() + "'s score doubled with double or nothing!" // regular messege
//            // messege for debugging
            // System.out.println(user.getName() + "'s score doubled to " + user.getScore() + " at the end of Round " + targetRound.getRoundNumber());

            // Reset targetRound after applying the effect
            targetRound = null;
        }

}

