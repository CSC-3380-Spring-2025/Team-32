package com.gobbledygook.gobbledygook.messages;
import lombok.Data;

import java.util.List;

import org.springframework.web.socket.TextMessage;
import com.gobbledygook.gobbledygook.GamePhase;
import com.gobbledygook.gobbledygook.GameSession;
/*
 * This WebSocket message is broadcasted, along with the definitions themselves, to all clients after the definition submission phase is over. 
 * Before the message is sent, the fake definitions must be shuffled with the real definition.
 * 
 */

@Data    
public class ShowDefinitionPhaseMessage { 
    private final GamePhase phase = GamePhase.DEFINITION_PHASE;
    private String realDefinition;
    
    
    public ShowDefinitionPhaseMessage(String realDefinition) {
        GameSession.fakeDefinitions.append(realDefinition);
        Collections.shuffle(GameSession.fakeDefinitions);
        TextMessage message = new TextMessage("Now that the definitions are submitted, they will be shown on screen");
        for(int i : definitionsList){
            Textmessage newMessage = new Message(fakeDefinitions.get(i));
            
            
        }

        }
    
    }

