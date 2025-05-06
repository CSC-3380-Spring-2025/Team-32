/* package com.gobbledygook.gobbledygook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class BlueShellTest {
	private BlueShell subject;
	private GameSession session;
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;

	@BeforeEach
	void setup(){
		session = new GameSession();
		player1 = new Player("kappa", UUID.randomUUID());
		player2 = new Player("kappa2", UUID.randomUUID());
		player3 = new Player("kappa3", UUID.randomUUID());
		player4 = new Player("kappa4", UUID.randomUUID());
		session.addPlayer(player1);
		session.addPlayer(player2);
		session.addPlayer(player3);
		session.addPlayer(player4);
	}

	@Test
	void targetFirstPlayer() {
		player2.setScore(10);
		session.sortPlayers();
		subject = new BlueShell(player4);
		subject.gameSession = session;

		subject.usePowerup();

		assertEquals(0,player2.getScore(),"this is no bueno!");

	}

}
*/