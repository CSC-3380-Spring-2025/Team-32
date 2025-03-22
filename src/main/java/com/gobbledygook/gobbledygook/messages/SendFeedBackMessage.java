package com.gobbledygook.gobbledygook.messages;
import lombok.Data;
import org.springframework.web.socket.TextMessage;
import com.gobbledygook.gobbledygook.GamePhase;
import com.gobbledygook.gobbledygook.GameSession;
import com.gobbledygook.gobbledygook.Player;

/* 
This WebSocket message is used to notify each client whether they received a point in each round or not. 
It is sent after each round. 
The message can contain a different data depending on the type of round and if the player gained a point or not.
 The message is specific to each client.
*/

@Data 
public class SendFeedBackMessage {
    private final GamePhase currentRound = GameSession.getCurrentRound();
    private final boolean win;
    private final Player player;
    public SendFeedBackMessage(GamePhase currentRound, boolean win, Player player){
        if (currentRound == WORD_CHAIN_PHASE && win == true){
            player.points += 1;
            TextMessage message = new TextMessage(player.getUsername() + "Recieved a point for the word chain phase"); 
        }

        if (currentRound == WORD_CHAIN_PHASE && win == false){
            TextMessage message = new TextMessage(player.getUsername() + "Did not receive a point for the word chain phase"); 
        }

        if (currentRound == DEFINITION_PHASE && win == true){
            player.points += 1;
            TextMessage message = new TextMessage(player.getUsername() + "Recieved a point for the definition phase");
        }

        if (currentRound == DEFINITION_PHASE && win == false){
            TextMessage message = new TextMessage(player.getUsername() + "Did not receive a point for the definition phase");
        }
        
        if (currentRound == STORY_PHASE && win == true){
            player.points += 1;
            TextMessage message = new TextMessage(player.getUsername() + "Recieved a point for the story phase");
        }

        if (currentRound == STORY_PHASE && win == false){
            TextMessage message = new TextMessage(player.getUsername() + "Did not receive a point for the story phase");
        } 
    }
        
    }
