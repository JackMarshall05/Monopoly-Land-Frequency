package monopolySimulation;

import java.util.ArrayList;
import java.util.List;;

public record Board(List<Property> properties) {

	public static final PropertySet BROWN = new PropertySet("Brown", 2, 50);
	public static final PropertySet LIGHT_BLUE = new PropertySet("Light Blue", 3, 50);
	public static final PropertySet PINK = new PropertySet("Pink", 3, 100);
	public static final PropertySet ORANGE = new PropertySet("Orange", 3, 100);
	public static final PropertySet RED = new PropertySet("Red", 3, 150);
	public static final PropertySet YELLOW = new PropertySet("Yellow", 3, 150);
	public static final PropertySet GREEN = new PropertySet("Green", 3, 200);
	public static final PropertySet DARK_BLUE = new PropertySet("Dark Blue", 2, 200);

	public static final PropertySet STATION = new PropertySet("Stations", 4, null);
	public static final PropertySet UTILITY = new PropertySet("Utilities", 2, null);
	
	public static int boardSize = 0;

	public static final List<PropertySet> SETS = List.of(BROWN, LIGHT_BLUE, PINK, ORANGE, RED, YELLOW, GREEN, DARK_BLUE,
			STATION, UTILITY);

	public static List<Property> initBoard() {
	    List<Property> board = new ArrayList<>();

	    board.add(new Property("GO", PropertyType.MISC, null, 0, null));
	    board.add(new Property("Old Kent Road", PropertyType.REG, BROWN, 60, new int[]{2, 4, 10, 30, 90, 160, 250}));
	    board.add(new Property("Community Chest 1", PropertyType.MISC, null, 0, null));
	    board.add(new Property("Whitechapel Road", PropertyType.REG, BROWN, 60, new int[]{4, 8, 20, 60, 180, 320, 450}));

	    board.add(new Property("Income Tax", PropertyType.MISC, null, 0, null));

	    board.add(new Property("King's Cross Station", PropertyType.STATION, STATION, 200, new int[]{25, 50, 100, 200}));

	    board.add(new Property("The Angel Islington", PropertyType.REG, LIGHT_BLUE, 100, new int[]{6, 12, 30, 90, 270, 400, 550}));
	    board.add(new Property("Chance 1", PropertyType.MISC, null, 0, null));
	    board.add(new Property("Euston Road", PropertyType.REG, LIGHT_BLUE, 100, new int[]{6, 12, 30, 90, 270, 400, 550}));
	    board.add(new Property("Pentonville Road", PropertyType.REG, LIGHT_BLUE, 120, new int[]{8, 16, 40, 100, 300, 450, 600}));

	    board.add(new Property("Jail / Just Visiting", PropertyType.MISC, null, 0, null));

	    board.add(new Property("Pall Mall", PropertyType.REG, PINK, 140, new int[]{10, 20, 50, 150, 450, 625, 750}));
	    board.add(new Property("Electric Company", PropertyType.UTILITY, UTILITY, 150, new int[]{28, 70}));
	    board.add(new Property("Whitehall", PropertyType.REG, PINK, 140, new int[]{10, 20, 50, 150, 450, 625, 750}));
	    board.add(new Property("Northumberland Avenue", PropertyType.REG, PINK, 160, new int[]{12, 24, 60, 180, 500, 700, 900}));

	    board.add(new Property("Marylebone Station", PropertyType.STATION, STATION, 200, new int[]{25, 50, 100, 200}));

	    board.add(new Property("Bow Street", PropertyType.REG, ORANGE, 180, new int[]{14, 28, 70, 200, 550, 750, 950}));
	    board.add(new Property("Community Chest 2", PropertyType.MISC, null, 0, null));
	    board.add(new Property("Marlborough Street", PropertyType.REG, ORANGE, 180, new int[]{14, 28, 70, 200, 550, 750, 950}));
	    board.add(new Property("Vine Street", PropertyType.REG, ORANGE, 200, new int[]{16, 32, 80, 220, 600, 800, 1000}));

	    board.add(new Property("Free Parking", PropertyType.MISC, null, 0, null));

	    board.add(new Property("Strand", PropertyType.REG, RED, 220, new int[]{18, 36, 90, 250, 700, 875, 1050}));
	    board.add(new Property("Chance 2", PropertyType.MISC, null, 0, null));
	    board.add(new Property("Fleet Street", PropertyType.REG, RED, 220, new int[]{18, 36, 90, 250, 700, 875, 1050}));
	    board.add(new Property("Trafalgar Square", PropertyType.REG, RED, 240, new int[]{20, 40, 100, 300, 750, 925, 1100}));

	    board.add(new Property("Fenchurch St Station", PropertyType.STATION, STATION, 200, new int[]{25, 50, 100, 200}));

	    board.add(new Property("Leicester Square", PropertyType.REG, YELLOW, 260, new int[]{22, 44, 110, 330, 800, 975, 1150}));
	    board.add(new Property("Coventry Street", PropertyType.REG, YELLOW, 260, new int[]{22, 44, 110, 330, 800, 975, 1150}));
	    board.add(new Property("Water Works", PropertyType.UTILITY, UTILITY, 150, new int[]{28, 70}));
	    board.add(new Property("Piccadilly", PropertyType.REG, YELLOW, 280, new int[]{26, 52, 130, 390, 900, 1100, 1275}));

	    board.add(new Property("Go to Jail", PropertyType.MISC, null, 0, null));

	    board.add(new Property("Regent Street", PropertyType.REG, GREEN, 300, new int[]{28, 56, 150, 450, 1000, 1200, 1400}));
	    board.add(new Property("Oxford Street", PropertyType.REG, GREEN, 300, new int[]{28, 56, 150, 450, 1000, 1200, 1400}));
	    board.add(new Property("Community Chest 3", PropertyType.MISC, null, 0, null));
	    board.add(new Property("Bond Street", PropertyType.REG, GREEN, 320, new int[]{35, 70, 175, 500, 1100, 1300, 1500}));

	    board.add(new Property("Liverpool Street Station", PropertyType.STATION, STATION, 200, new int[]{25, 50, 100, 200}));

	    board.add(new Property("Chance 3", PropertyType.MISC, null, 0, null));
	    board.add(new Property("Park Lane", PropertyType.REG, DARK_BLUE, 350, new int[]{35, 70, 175, 500, 1100, 1300, 1500}));
	    board.add(new Property("Super Tax", PropertyType.MISC, null, 0, null));
	    board.add(new Property("Mayfair", PropertyType.REG, DARK_BLUE, 400, new int[]{50, 100, 200, 600, 1400, 1700, 2000}));
	    
	    boardSize = board.size();
	            
	    return board;
	}
}
