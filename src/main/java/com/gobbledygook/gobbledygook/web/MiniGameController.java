package com.gobbledygook.gobbledygook.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gobbledygook.gobbledygook.GameSession;
import com.gobbledygook.gobbledygook.MinigameResult;


@RestController
@RequestMapping("/minigame")
public class MiniGameController {
     @Autowired
    private GameSession session;

    @PostMapping
    public ResponseEntity<String> processMinigame(@RequestBody MinigameResult result) {
        for (var player:session.getPlayers()){
            if (player.getId().equals(result.getPlayerId())){
                player.setScore(player.getScore() + result.getScore());

            }
        }
        return ResponseEntity.ok().body("Score Updated");
    }

}


