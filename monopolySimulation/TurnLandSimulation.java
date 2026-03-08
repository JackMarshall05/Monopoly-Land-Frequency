package monopolySimulation;

public class TurnLandSimulation extends LandSimulation {
	public void simulate(int startLocation, int turns, int repeat, int previousDoublesInARow, int turnsInJail) {
		for (int i = 0; i < repeat; i++) {
			player.leaveJail(); // make sure that jail is reset

			player.setPlayerLocation(startLocation); // move player back to intended start

			player.setTurnsInJail(turnsInJail);

			if (board.properties().get(player.playerLocation()).name().equals("\"Jail / Just Visiting\"")) {
				player.enterJail();
			} // if the player has turns in jail then they must be in jail

			DiceRoll.setDoublesInARow(previousDoublesInARow);

			for (int j = 0; j < turns; j++) { 
				DiceRoll.resetDoublesInARow();
				doTurn(DiceRoll.doublesInARow()); 
			}
		}
		calcData();
	}
}
