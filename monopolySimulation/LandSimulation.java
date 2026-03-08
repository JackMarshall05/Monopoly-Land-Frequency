package monopolySimulation;

import java.util.Map;

public class LandSimulation {
	protected Board board = new Board(Board.initBoard());
	protected Player player = new Player(board.properties());

	private boolean includeAllLandings = false;

	/*
	 * shows window with the frequencies of the properties as the land value of the property
	 */
	public void showFrequencies() {
		BoardVisualizer.showBoard(board.properties(), player.playerLandStats.getFrequencies(), player.playerLandStats.getSetFrequencies());
	}
	
	/*
     * shows window with the ratios of the properties compared to the average land frequency as the land value of the property
     */
	public void showRatios() {
		BoardVisualizer.showBoard(board.properties(), player.playerLandStats.getRatios(), player.playerLandStats.getSetRatios());
	}
	
	/*
     * shows window with the normalized of the properties with most landed as 1 and if a property is never landed on 0 as the land value of the property
     */
	public void showNormalized() {
		BoardVisualizer.showBoard(board.properties(), player.playerLandStats.getNormalized(), player.playerLandStats.getSetNormalized());
	}
	
	/*
     * shows window with the percent of total landings as the land value of the property
     */
	public void showPercent() {
		BoardVisualizer.showBoard(board.properties(), player.playerLandStats.getPercent(), player.playerLandStats.getSetPercent());
	}

	/*
	 * sets includeAll landings to either true or false
	 * if true the calculation will include every time a player touches a property
	 * if false the calculation will only count property landings it ends its turn on
	 * @param boolean
	 * 
	 */
	public void setIncludeAllLandings(boolean bool) {
		this.includeAllLandings = bool;
	}
	
	/*
	 * calculates all the data using the frequency of the player landing on particlar tiles
	 * should be called once the simulation has been completed
	 */
	public void calcData() {
		this.player.playerLandStats.calcRatios();
		this.player.playerLandStats.calcNormalized();
		this.player.playerLandStats.calcPercent();
		this.player.playerLandStats.calcSetFrequency();
		this.player.playerLandStats.calcSetRatios();
		this.player.playerLandStats.calcSetNormalized();
		this.player.playerLandStats.calcSetPercent();
	}
	
	/*
	 * does the entire turn of a player
	 * is recursively called if rolled doubles unless three have been rolled in a row
	 * @param 0
	 * should be first called with 0 
	 */
	protected void doTurn(int previousDoublesInARow) {
	    if (previousDoublesInARow < 0) {
	        throw new IllegalArgumentException("Can't have negative double in a row : " + previousDoublesInARow);
	    }
		if (previousDoublesInARow >= 3) {
			landOnJail();
			return;
		}

		int roll = DiceRoll.getDiceRoll();
		boolean rolledDouble = DiceRoll.doublesInARow() != previousDoublesInARow;

		if (DiceRoll.doublesInARow() != previousDoublesInARow
				&& DiceRoll.doublesInARow() + 1 == previousDoublesInARow) {
			throw new IllegalStateException(
					"previous doubles and current doubles are not equal and precious doubles is not one less than current doubles. "
							+ "Something has happened to count a double more or less than it should have : "
							+ DiceRoll.doublesInARow() + " : " + previousDoublesInARow);
		}
		
		if (player.inJail()) { 
			if (rolledDouble) {
				player.leaveJail();
			} else {
				if (player.turnsInJail() == 3) {
					player.leaveJail();
				} else {
					player.addTurnInJail();
					return;
				}
			}
		}

		doDiceRoll(roll);
		simulateTurn();

		if (rolledDouble && !player.inJail()) { 
			doTurn(previousDoublesInARow + 1); 
		}
	}

	/*
	 * simulates the result of the turn once the dice have been rolled
	 * eg. go to jail if on jail or draw chance card if on chance
	 */
	protected void simulateTurn() {
		// 1) Landing on "Go to Jail" square → goes directly to Jail
		if (locatedGoToJail()) {
			landOnJail();
			return;
		}

		// 2) Landing on Community Chest
		if (locatedCommunityChest()) {
			landOnCommunityChest();
			return;
		}

		// 3) Landing on Chance
		if (locatedChance()) {
			landOnChance();
			return;
		}

		// 4) Regular landing
		player.playerLandStats.addVisit(board.properties().get(player.playerLocation()));
	}

	/*
	 * moves player to jail and adds visit
	 * calls enterJail() method in player
	 */
	protected void landOnJail() {
		if (includeAllLandings) { player.playerLandStats.addVisit(board.properties().get(player.playerLocation())); }
		moveToProperty("Jail / Just Visiting");
		player.playerLandStats.addVisit(board.properties().get(player.playerLocation())); // count Jail
		player.enterJail();
	}

	/*
     * moves player to communityChest and adds visit
     * draws community card and resolves the result of the card
     * 
     * 1/16 chance go to Go
     * 1/16 chance go to Jail
     * 14/16 non move card
     */
	protected void landOnCommunityChest() {
		int random = (int) (Math.random() * 16);

		// if I'm including all landings then I add one at the start incase I move
		// because of the community chest card
		if (includeAllLandings) { player.playerLandStats.addVisit(board.properties().get(player.playerLocation())); }

		if (random == 0) {
			moveToProperty("GO");
		} else if (random == 1) { moveToProperty("Jail / Just Visiting"); }

		// if I'm not including all landings I disregard this as I should always add
		// visit at the end
		if (!locatedCommunityChest() || !includeAllLandings) { player.playerLandStats.addVisit(board.properties().get(player.playerLocation())); }
	}

	
	/*
     * moves player to chance and adds visit
     * draws community card and resolves the result of the card
     * 
     * 1/16 chance go to Go
     * 1/16 chance go to Jail
     * 1/16 chance go to Trafalgar Square
     * 1/16 chance go to Mayfair
     * 1/16 chance go to Pall Mall
     * 1/16 chance go back 3 spaces
     * 1/16 chance go to Kings Cross Station
     * 1/16 chance go to next station
     * 1/16 chance go to next utility
     * 7/16 non move card
     */
	protected void landOnChance() {
		int random = (int) (Math.random() * 16);

		// if I'm including all landings then I add one at the start incase I move
		// because of the chance card
		if (includeAllLandings) { player.playerLandStats.addVisit(board.properties().get(player.playerLocation())); }

		if (random == 0) {
			moveToProperty("GO");
		} else if (random == 1) {
			moveToProperty("Jail / Just Visiting");
		} else if (random == 2) {
			moveToProperty("Trafalgar Square");
		} else if (random == 3) {
			moveToProperty("Mayfair");
		} else if (random == 4) {
			moveToProperty("Pall Mall");
		} else if (random == 5) {
			player.setPlayerLocation((player.playerLocation() + board.properties().size() - 3) % board.properties().size());
		} else if (random == 6) {
			moveToProperty("King's Cross Station");
		} else if (random == 7 || random == 8) {
			moveToNext(PropertyType.STATION);
		} else if (random == 9) { moveToNext(PropertyType.UTILITY); }

		// if I'm not including all landings I disregard this as I should always add
		// visit at the end
		if (!locatedChance() || !includeAllLandings) { player.playerLandStats.addVisit(board.properties().get(player.playerLocation())); }
	}

	/*
	 * calls setPlayerLocation() in player to set their player location to the property
	 * @param String name of property
	 */
	private void moveToProperty(String name) {
		for (int i = 0; i < board.properties().size(); i++) {
			if (board.properties().get(i).name().equals(name)) {
				player.setPlayerLocation(i);;
				return;
			}
		}
		throw new IllegalStateException("Could not find property " + name);
	}

	private void moveToNext(PropertyType type) {
		int currentLocation = player.playerLocation();
		int i = player.playerLocation() + 1;
		while (i != currentLocation) {
			if (board.properties().get(i).type().equals(type)) {
				player.setPlayerLocation(i);;
				return;
			}
			i += 1;
			i %= board.properties().size();
		}
	}

	protected boolean locatedGoToJail() {
		return board.properties().get(player.playerLocation()).name().equals("Go to Jail");
	}

	protected boolean locatedChance() {
		return board.properties().get(player.playerLocation()).name().contains("Chance");
	}

	protected boolean locatedCommunityChest() {
		return board.properties().get(player.playerLocation()).name().contains("Community Chest");
	}

	protected void doDiceRoll(int move) {
		player.movePlayerLocation(move, board.properties().size());
	}

	public void printFrequency() {
		Map<Property, Integer> landFrequency = player.playerLandStats.getFrequencies();
		for (Property property : board.properties()) {
			System.out.println(property.name() + " was landed on " + landFrequency.get(property));
		}
	}

	public void printRatios() {
		Map<Property, Float> landRatios = player.playerLandStats.getRatios();
		for (Map.Entry<Property, Float> entry : landRatios.entrySet()) {
			System.out.println(entry.getKey().name() + " was landed on " + entry.getValue());
		}
	}

	public Board board() {
		return board;
	}
}
