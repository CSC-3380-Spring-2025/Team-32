/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.gobbledygook.gobbledygook.web;
import com.gobbledygook.gobbledygook.Definition;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gobbledygook.gobbledygook.GamePhase;
import com.gobbledygook.gobbledygook.GameSession;
import com.gobbledygook.gobbledygook.Player;
import com.gobbledygook.gobbledygook.ServerWebSocketHandler;
import com.gobbledygook.gobbledygook.messages.WordChainMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/define")
public class SubmitDefinitionEndpoint {

    @Autowired
    private GameSession session;

    @Autowired
    private ServerWebSocketHandler webSocketHandler;

    /* Takes URL parameter username, checks if that username is already in the session, and adds the player
    Returns nothing if player already exists, returns representation of the new Player object if successful.
     */
    @PostMapping
    public Definition addDefinition(@RequestParam String definition, String username) {
        UUID id = UUID.nameUUIDFromBytes(username.getBytes());
        Definition newDefinition = new Definition(id,definition);

        // TODO input validation, but it shouldn't be super necessary since the chance of a dupe definition is extremely low.
        session.addFakeDefinition(newDefinition);
        return newDefinition;
    }
}
