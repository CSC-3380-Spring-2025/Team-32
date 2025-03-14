package com.gobbledygook.gobbledygook.web;

import com.gobbledygook.gobbledygook.GameSession;
import com.gobbledygook.gobbledygook.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
