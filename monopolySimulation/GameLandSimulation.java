package monopolySimulation;

public class GameLandSimulation extends TurnLandSimulation {
	public void simulate(int turns, int repeat) {
		this.player = new Player(this.board.properties());
		for (int i = 0; i < repeat; i++) { simulateGame(turns); }
		calcData();
	}

	private void simulateGame(int totalTurns) {
		int i = 0;
		while (i < totalTurns) {
			i += 1;
			DiceRoll.resetDoublesInARow();
			doTurn(DiceRoll.doublesInARow());
		}
	}
}
