package com.gobbledygook.gobbledygook.web;

import com.gobbledygook.gobbledygook.GameSession;
import com.gobbledygook.gobbledygook.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/join")
public class JoinGameController {

    @Autowired
    private GameSession session;

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
        return newPlayer;
    }

}
