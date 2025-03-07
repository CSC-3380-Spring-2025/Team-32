package com.gobbledygook.gobbledygook.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gobbledygook.gobbledygook.GamePhase;
import com.gobbledygook.gobbledygook.GameSession;
import com.gobbledygook.gobbledygook.Player;
import com.gobbledygook.gobbledygook.ServerWebSocketHandler;
import com.gobbledygook.gobbledygook.messages.WordChainMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/join")
public class JoinGameController {

    @Autowired
    private GameSession session;

    @Autowired
    private ServerWebSocketHandler webSocketHandler;

    /* Takes URL parameter username, checks if that username is already in the session, and adds the player
    Returns nothing if player already exists, returns representation of the new Player object if successful.
     */
    @PostMapping
    public Player addPlayer(@RequestParam String username) {
        UUID id = UUID.nameUUIDFromBytes(username.getBytes());
        List<Player> currentPlayers = session.getPlayers();
        for (Player player : currentPlayers) {
            if (player.getId().equals(id)) {
                return null;
            }
        }

        Player newPlayer = new Player(username, id);
        session.addPlayer(newPlayer);

        /* TODO: decide on max number of players allowed and implement a way to choose the word at random from a large set */
        if (session.getPlayers().size() == 4) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                webSocketHandler.sendMessage(objectMapper.writeValueAsString(new WordChainMessage("foobaz")));
            } catch (IOException e) {
                System.err.println("Error sending WebSocket message");
            }
        }

        return newPlayer;
    }

}
