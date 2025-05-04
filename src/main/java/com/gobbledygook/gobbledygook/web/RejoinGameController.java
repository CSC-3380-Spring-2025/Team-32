package com.gobbledygook.gobbledygook.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gobbledygook.gobbledygook.GameSession;
import com.gobbledygook.gobbledygook.Player;
import com.gobbledygook.gobbledygook.ServerWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/rejoin")
public class RejoinGameController {

    @Autowired
    private GameSession session;

    @Autowired
    private ServerWebSocketHandler webSocketHandler;

//    @Autowired
//    JdbcTemplate jdbcTemplate;

    /* Takes URL parameter username, checks if that username is already in the session, and adds the player
    Returns nothing if player already exists, returns representation of the new Player object if successful.
     */
    @PostMapping
    public ResponseEntity<String> readyPlayer(@RequestParam UUID id) {
        Player player = session.getPlayerById(id);
        if(player == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"User not found\"}");

        }
        session.getRejoinPlayers().add(player);

        if (session.getRejoinPlayers().size() == 4) {
            try {
                webSocketHandler.sendMessage("Game is restarting");
            } catch (IOException e) {
                System.err.println("Error sending WebSocket message");
            }
            session.reset();
        }
        
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Successfully joined game\", \"id\": \"" + id.toString() + "\"}");
    }
    
//    public void wordSelect {
//        String Query = ("Select word FROM words ORDER BY RANDOM() Limit 1");
//        private string word = jdbcTemplate.query(Query);
//        webSocketHandler.sendMessage(word);
//    }

}
