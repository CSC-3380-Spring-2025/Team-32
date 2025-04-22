package com.gobbledygook.gobbledygook;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/* Tracks the state of the game, including the list of players, the current round, and the phase of the current round */
@Data
public class GameSession {
    private UUID id;
    /* players must always be sorted based on Player.score */
    private List<Player> players;
    private List<Definition> fakeDefinitions;
    private Round currentRound;
    private GamePhase state;

    public GameSession() {
        this.id = UUID.randomUUID();
        this.players = new ArrayList<>();
        this.currentRound = new Round();
        this.state = GamePhase.ROUND_START;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Player getPlayerById(UUID id) {
        for (Player player : players) {
            if (player.getId().equals(id))
                return player;
        }
      
        throw new RuntimeException("Player's ID not found:" + id);
    }

    public void sortPlayers() {
        players.sort(new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return p2.getVisibleScore() - p1.getVisibleScore();
            }
        });
    }

    public Player getTopPlayer() {
        return players.get(0);
    }

    public void addFakeDefinition(Definition definition){
        fakeDefinitions.add(definition);
    }

    // interates thru players' powerups, and calls onGameStateChange for the powerups (so they activate at the right game state)
    public void setState(GamePhase newState) {
        this.state = newState;
    
        for (Player player : players) {
//            for (PowerUp powerUp : player.getPowerUps()) {
//                // this is so it only activates at the *end* of the round, and not the middle of it
//                if (powerUp instanceof DoubleOrNothing) {
//                    ((DoubleOrNothing) powerUp).onGameStateChange(this, player);
//                }
//            }
        }
    }

}
