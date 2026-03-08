package monopolySimulation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Player {
    public PlayerLandStats playerLandStats;
    
	protected int playerLocation = 0;

	protected int turnsInJail = 0;

	protected boolean inJail = false;
	
	private int money = 1500;

	public Player(List<Property> board) {
		if(board == null) {
			throw new IllegalArgumentException("Given Board is Null");
		}
		if(board.isEmpty()) {
			throw new IllegalArgumentException("Player given Empty Board");
		}
		if(money < 0) {
		    throw new IllegalArgumentException("Can't have Negative Money");
		}
		playerLandStats = new PlayerLandStats(board);
	}

	public void leaveJail() {
		resetTurnsInJail();
		this.inJail = false;
	}

	public void enterJail() {
		if (this.inJail) { throw new IllegalStateException("Trying to enter jail but already in jail"); }
		this.inJail = true;
	}

	public boolean inJail() {
		return this.inJail;
	}

	public int turnsInJail() {
		if (turnsInJail < 0 || turnsInJail > 3) {
			throw new IllegalStateException("Invalid number of turns in jail: " + turnsInJail);
		}
		return this.turnsInJail;
	}

	public void addTurnInJail() {
		if (this.turnsInJail < 0 || this.turnsInJail > 2) {
			throw new IllegalStateException(
					"turns in jail has to be 0, 1 or 2 to add to it but is : " + this.turnsInJail);
		}
		this.turnsInJail++;
	}

	public void resetTurnsInJail() {
		if (this.turnsInJail < 0 || this.turnsInJail > 3) {
			throw new IllegalStateException(
					"turns in jail has to be 0, 1, 2 or 3 but when it was reset it was : " + this.turnsInJail);
		}
		this.turnsInJail = 0;
	}

	public void setTurnsInJail(int amount) {
		if (this.turnsInJail < 0 || this.turnsInJail > 3) {
			throw new IllegalStateException(
					"turns in jail has to be 0, 1, 2 or 3 but when it was reset it was : " + this.turnsInJail);
		}
		if (amount < 0 || amount > 3) {
			throw new IllegalStateException(
					"turns in jail has to be 0, 1, 2 or 3 but are trying to reset it to : " + amount);
		}
		this.turnsInJail = amount;
	}

	public int playerLocation() {
		if (playerLocation < 0 || playerLocation > Board.boardSize) {
			throw new IllegalArgumentException(
					"player location can't be less that 0 or greater than the number of properties : " + playerLocation
							+ " on board size " + Board.boardSize);
		}
		return playerLocation;
	}

	public void setPlayerLocation(int newLocation) {
		if (newLocation < 0 || newLocation > Board.boardSize) {
			throw new IllegalArgumentException(
					"new location can't be less that 0 or greater than the number of properties : " + newLocation
							+ " on board size " + Board.boardSize);
		}
		this.playerLocation = newLocation;
	}

	public void movePlayerLocation(int move, int boardSize) {
		this.playerLocation += move;
		this.playerLocation %= boardSize;
	}
}
