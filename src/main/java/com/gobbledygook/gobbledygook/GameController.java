package com.gobbledygook.gobbledygook;

import lombok.Data;

@RestController
@RequestMapping("/game")
public class GameController {
    private final GameSession gameSession;

    // Injecting the global GameSession bean
    public GameController(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    @PostMapping("/submitVote")
    public String castVote(@RequestParam UUID playerId, @RequestParam UUID definitionId) {
        gameSession.getCurrentRound().castVote(playerId, definitionId);
        return "Vote registered successfully!";
    }
}
