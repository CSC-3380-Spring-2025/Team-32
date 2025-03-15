package com.gobbledygook.gobbledygook.web;

import com.gobbledygook.gobbledygook.GameSession;
import com.gobbledygook.gobbledygook.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    @Autowired
    private GameSession session;

    @GetMapping
    public List<Player> getLeaderboard() {
        /* Instead of sorting when scores change, we sort when the leaderboard is requested */
        session.sortPlayers();
        return session.getPlayers();
    }
}
