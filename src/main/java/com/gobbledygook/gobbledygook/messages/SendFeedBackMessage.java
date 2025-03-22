package com.gobbledygook.gobbledygook.messages;
import lombok.Data;
import org.springframework.web.socket.TextMessage;
import com.gobbledygook.gobbledygook.GamePhase;
import com.gobbledygook.gobbledygook.GameSession;

/* 
This WebSocket message is used to notify each client whether they received a point in each round or not. 
It is sent after each round. 
The message can contain a different data depending on the type of round and if the player gained a point or not.
 The message is specific to each client.
*/

@Data 
public class SendFeedBackMessage {
    private final GamePhase currentRound = GameSession.getCurrentRound();
    private final point;
    
    switch(currentRound) {
        case WORD_CHAIN_PHASE:
        case DEFINITION_PHASE,
        case STORY_PHASE,
        case VOTING_PHASE,
        case ROUND_START,
        case ROUND_END,

    }
    
    TextMessage message = new TextMessage("");
        
    }
