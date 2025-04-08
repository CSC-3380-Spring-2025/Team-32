package com.gobbledygook.gobbledygook;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getWord")
    public String getWord() {
        return gameSession.getCurrentRound().getTargetWord();
    }

    @GetMapping("/getPowerup")
    public String getPowerup() {
        /* Gives a random powerup to the requesting user, represented just as a String.
        The frontend receives this message and renders an appropriate screen. The user
        then gets to press the Use Powerup button, which sends a POST request to the server
         */
        final String[] powerups = {"double_or_nothing", "blue_shell", "hide_name"};
        Random random = new Random();
        return powerups[random.nextInt(3)];
    }

    @PostMapping("/usePowerup")
    public void usePowerup(@RequestParam UUID playerId, @RequestParam String powerup) {
        if (powerup == "double_or_nothing") {
            /* 50/50 chance of doubling players points or starting from zero */
            Random random = new Random();
            Player player = gameSession.getPlayerById(playerId);
            if (random.nextBoolean()) {
                player.setScore(player.getScore()*2);
            } else {
                player.setScore(0);
            }
            gameSession.sortPlayers();
        } else if (powerup == "blue_shell") {
            // subtract 2 points from the top player
            Player topPlayer = gameSession.getPlayers().get(0);
            topPlayer.setScore(topPlayer.getScore()-2);
            gameSession.sortPlayers();
            /* subtract points from player in first place */
        } else if (powerup == "hide_name") {
            /* hide the requesting player's name from the leaderboard, making them immune to blue shell */
        }
    }
}
