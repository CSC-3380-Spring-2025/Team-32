package com.gobbledygook.gobbledygook;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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

    @GetMapping("/getStories")
    /* This endpoint is to get a list of definitions for players to vote on */
    public List<Story> getFunnyStories() {
        return gameSession.getCurrentRound().getStories();
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
    public ResponseEntity<String> getPowerup() {
        /* Gives a random powerup to the requesting user, represented just as a String.
        The frontend receives this message and renders an appropriate screen. The user
        then gets to press the Use Powerup button, which sends a POST request to the server
         */
        final String[] powerups = {"double_or_nothing", "blue_shell", "bonus_points"};
        Random random = new Random();
        return ResponseEntity.ok(powerups[random.nextInt(3)]);
    }

    @PostMapping("/usePowerup")
    public ResponseEntity<String> usePowerup(@RequestParam UUID playerId, @RequestParam String powerup) {
        if (powerup.equals("double_or_nothing")) {
            /* 50/50 chance of doubling players points or starting from zero */
            Random random = new Random();
            Player player = gameSession.getPlayerById(playerId);
            if (random.nextBoolean()) {
                player.setScore(player.getScore()*2);
                gameSession.sortPlayers();
                return ResponseEntity.ok(powerup + ": Double Points!");
            } else {
                player.setScore(0);
                gameSession.sortPlayers();
                return ResponseEntity.ok(powerup + ": You lost it all :(");
            }
        } else if (powerup.equals("blue_shell")) {
            // subtract 2 points from the top player
            Player topPlayer = gameSession.getPlayers().get(0);
            topPlayer.setScore(topPlayer.getScore()-2);
            gameSession.sortPlayers();
            return ResponseEntity.ok(powerup);
            /* subtract points from player in first place */
        } else if (powerup.equals("bonus_points")) {
            /* Add two points to score */
            Player player = gameSession.getPlayerById(playerId);
            player.setScore(player.getScore()+2);
            gameSession.sortPlayers();
            return ResponseEntity.ok(powerup);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/reset")
    public void resetGame() {
        gameSession.reset();
    }
    

}
