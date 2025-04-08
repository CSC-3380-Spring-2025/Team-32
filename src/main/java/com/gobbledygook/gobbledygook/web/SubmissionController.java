package com.gobbledygook.gobbledygook.web;

import com.gobbledygook.gobbledygook.GameSession;
import com.gobbledygook.gobbledygook.Player;
import com.gobbledygook.gobbledygook.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/submit")
public class SubmissionController {

    @Autowired
    private GameSession session;

    @PostMapping
    public ResponseEntity<String> processSubmission(@RequestBody Submission submission) {
        switch (submission.getType()) {
            case "wordchain":
                session.getCurrentRound().addWordChain(submission.getPlayerId(), submission.getContent());
                validateShiritoriWord(submission.getPlayerId(), submission.getContent());
                break;
            case "definition":
                session.getCurrentRound().addDefinition(submission.getPlayerId(), submission.getContent());
                break;
            case "story":
                session.getCurrentRound().addStory(submission.getPlayerId(), submission.getContent());
                break;
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid submission type");

        }
        return ResponseEntity.status(HttpStatus.OK).body("Submission processed");
    }

    private void validateShiritoriWord(UUID playerId, String word) {
        String targetWord = session.getCurrentRound().getTargetWord();
        Resource resource = new ClassPathResource("wordlist");
        try {
            InputStream inputStream = resource.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            List<String> dictionary = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line);
            }

            if (word.charAt(0) == targetWord.charAt(targetWord.length() - 1) && dictionary.contains(word)) {
                for (Player player : session.getPlayers()) {
                    if (player.getId() == playerId)
                        player.setScore(player.getScore() + 1);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading dictionary");
        }
    }
}
