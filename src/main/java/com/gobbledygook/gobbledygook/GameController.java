package com.gobbledygook.gobbledygook;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> castVote(@RequestParam UUID playerId) {
        /* We might not even need the voting code in the Round object */
        Player player = gameSession.getPlayerById(playerId);
        player.setScore(player.getScore()+1);
//        gameSession.getCurrentRound().castVote(playerId, definitionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getDefinitions")
    /* This endpoint is to get a list of definitions for players to vote on */
    public List<Definition> getDefinitions() {
        return gameSession.getCurrentRound().getDefinitions();
    }

    @GetMapping("/getWord")
    public String getWord() {
        return gameSession.getCurrentRound().getTargetWord();
    }

    @PostMapping("/submitWordChain")
    public String addWordChain(@RequestParam UUID playerId, @RequestParam String submittedWordString) {
        gameSession.getCurrentRound().addWordChain(playerId, submittedWordString);
        return "Word submited successfully!";
    }

    @GetMapping("/getPhase")
    public String getPhase() {
        return gameSession.getState().name();
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
