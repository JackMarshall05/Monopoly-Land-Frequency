package monopolySimulation;

public class DiceRoll {
	private static int doublesInARow = 0;

	public static int getDiceRoll() {
		int roll1 = getDieRoll();
		int roll2 = getDieRoll();
		if (roll1 == roll2) { addDoublesInARow(); }
		return roll1 + roll2;
	}

	public static int getDieRoll() {
		return (int) (Math.random() * 6) + 1;
	}

	public static int doublesInARow() {
		if (doublesInARow > 3 || doublesInARow < 0) {
			throw new IllegalStateException(
					"You are trying to get doublesInARow but it is outside expected range : "
							+ doublesInARow);
		}
		return doublesInARow;
	}

	public static void resetDoublesInARow() {
		if (doublesInARow > 3 || doublesInARow < 0) {
			throw new IllegalStateException(
					"Doubles in a Row is outside the expected range when it was reset. Before reset it was : "
							+ doublesInARow);
		}
		doublesInARow = 0;
	}
	
	public static void setDoublesInARow(int amount) {
		if (doublesInARow > 3 || doublesInARow < 0) {
			throw new IllegalStateException(
					"Doubles in a Row is outside the expected range when it was reset. Before reset it was : "
							+ doublesInARow);
		}
		if (amount > 3 || amount < 0) {
			throw new IllegalArgumentException(
					"Doubles in a Row is outside the expected range when it was reset to " + amount + ". Before reset it was : "
							+ doublesInARow);
		}
		doublesInARow = 0;
	}

	public static void addDoublesInARow() {
		if (doublesInARow >= 3 || doublesInARow < 0) {
			throw new IllegalStateException(
					"Doubles in a Row is outside the expected Range and you want to add to it is currently : "
							+ doublesInARow + " and you want to add one.");
		}
		doublesInARow++;
	}
}
