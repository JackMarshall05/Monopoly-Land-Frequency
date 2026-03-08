package monopolySimulation;

public class Main {
	public static void main(String[] args) {
		
		/*TurnLandSimulation turn = new TurnLandSimulation();
		turn.setIncludeAllLandings(true);
		turn.simulate(0, 1, 100000, 0, 0);
		turn.showRatios();*/
		
		GameLandSimulation game = new GameLandSimulation();
		game.setIncludeAllLandings(true);
		game.simulate(40, 100000);
		game.showRatios();
	}
}
