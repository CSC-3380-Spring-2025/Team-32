package com.gobbledygook.gobbledygook;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* Tracks the state of the game, including the list of players, the current round, and the phase of the current round */
@Data
public class GameSession {
    private UUID id;
    private List<Player> players;
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
}