package com.gobbledygook.gobbledygook.messages;
import lombok.Data;
import org.springframework.web.socket.TextMessage;
import com.gobbledygook.gobbledygook.GamePhase;

@Data    
public class StartDefinitionPhaseMessage {
    private final GamePhase phase = GamePhase.DEFINITION_PHASE;
    private String word;
    public StartDefinitionPhaseMessage(String word) {
        this.word = word;
        TextMessage message = new TextMessage("Now that we know how the word is spelled, we will need to figure out what it means. Create a fake definition for " + word + " and try to figure out the real definition that's mixed in with the others. Good luck!");
        }
    }





    

