package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.Test;

import monopolySimulation.Board;
import monopolySimulation.DiceRoll;
import monopolySimulation.GameLandSimulation;
import monopolySimulation.Player;
import monopolySimulation.Property;

public class FrequencyTests {
	@Test public void diceRoll() {
		int result = DiceRoll.getDiceRoll();
		
		assertTrue(result >= 2);
		assertTrue(result <= 12);
	}
	
	@Test public void dieRoll() {
		int result = DiceRoll.getDieRoll();
		
		assertTrue(result >= 1);
		assertTrue(result <= 6);
	}
	
	@Test public void boardSize() {
		GameLandSimulation game = new GameLandSimulation();
		assertEquals(40, game.board().properties().size());
	}
	
	@Test public void noNullProperty() {
		GameLandSimulation game = new GameLandSimulation();
		for(Property property : game.board().properties()) {
			assertNotEquals(null, property);
		}
	}
	
	@Test public void noEmptyPropertyName() {
		GameLandSimulation game = new GameLandSimulation();
		for(Property property : game.board().properties()) {
			assertNotEquals("", property.name());
		}
	}
	
	@Test public void uniquePropertyNames() {
		GameLandSimulation game = new GameLandSimulation();
		for(Property property1 : game.board().properties()) {
			int count = 0;
			for(Property property2 : game.board().properties()) {
				if(property1.name().equals(property2.name())) {
					count++;
				}
			}
			assertEquals(0, count-1);
		}
	}
	
	@Test public void playerGivenEmptyBoard() {
		try {
			new Player(new ArrayList<Property>());
			assert false;
		}catch(IllegalArgumentException e) {}
	}
	
	@Test public void playerGivenNullBoard() {
		try {
			new Player(null);
			assert false;
		}catch(IllegalArgumentException e) {}
	}
	
	@Test public void playerGivenGoodBoard() {
		try {
			new Player(new Board(Board.initBoard()).properties());
		}catch(Exception e) {assert false;}
	}
	
	@Test public void visitTests() {
		Board board = new Board(Board.initBoard());
		Player player = new Player(board.properties());
		Property firstProperty = board.properties().get(0);
		player.playerLandStats.addVisit(firstProperty);
		assertEquals(1, player.playerLandStats.getFrequencies().get(firstProperty));
		player.playerLandStats.addVisit(firstProperty);
		assertEquals(2, player.playerLandStats.getFrequencies().get(firstProperty));
		player.playerLandStats.addVisit(firstProperty);
		assertEquals(3, player.playerLandStats.getFrequencies().get(firstProperty));
	}
	
	/*
	 * Test ideas:
	 * all the throws in player
	 * create a Test property sets and test all the calc methods
	 */
}
