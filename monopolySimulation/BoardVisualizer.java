package monopolySimulation;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class BoardVisualizer extends JPanel {
	private final List<Property> board;
	private final Map<Property, ? extends Number> values;
	private final Map<PropertySet, ? extends Number> setValues;

	public BoardVisualizer(List<Property> board, Map<Property, ? extends Number> values, Map<PropertySet, ? extends Number> setValues) {
		this.board = board;
		this.values = values;
		this.setValues = setValues;
		setPreferredSize(new Dimension(1000, 800)); // extra space for set cards
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		int tilesPerSide = 11;
		int tileSize = 60;

		// Draw board tiles clockwise starting from bottom-left
		for (int i = 0; i < board.size(); i++) {
			int x = 0, y = 0;

			if (i < tilesPerSide) { // bottom row
				x = (tilesPerSide - 1 - i) * tileSize;
				y = (tilesPerSide - 1) * tileSize;
			} else if (i < tilesPerSide * 2 - 1) { // left column
				x = 0;
				y = (tilesPerSide * 2 - 2 - i) * tileSize;
			} else if (i < tilesPerSide * 3 - 2) { // top row
				x = (i - (tilesPerSide * 2 - 2)) * tileSize;
				y = 0;
			} else { // right column
				x = (tilesPerSide - 1) * tileSize;
				y = (i - (tilesPerSide * 3 - 3)) * tileSize;
			}

			drawTile(g2, x + 20, y + 20, tileSize, board.get(i));
		}

		// Set card layout
		int cardWidth = 200, cardHeight = 100;
		int padding = 5;
		int cardsPerRow = 2;
		int rows = (int) Math.ceil(Board.SETS.size() / (double) cardsPerRow);

		// Center the cards within the board grid
		int boardSize = tilesPerSide * tileSize;
		int boardOffsetX = 20;
		int boardOffsetY = 20;

		int centerX = boardOffsetX + boardSize / 2;
		int centerY = boardOffsetY + boardSize / 2;

		int totalCardWidth = cardsPerRow * cardWidth + (cardsPerRow - 1) * padding;
		int totalCardHeight = rows * cardHeight + (rows - 1) * padding;

		int centerStartX = centerX - totalCardWidth / 2;
		int centerStartY = centerY - totalCardHeight / 2;

		for (int i = 0; i < Board.SETS.size(); i++) {
			int row = i / cardsPerRow;
			int col = i % cardsPerRow;

			int x = centerStartX + col * (cardWidth + padding);
			int y = centerStartY + row * (cardHeight + padding);

			drawSetCard(g2, x, y, cardWidth, cardHeight, Board.SETS.get(i));
		}
	}

	private <T extends Number> void drawTile(Graphics2D g2, int x, int y, int size, Property property) {
		Number value = values.get(property);

		Color fillColour = squareColour(value);

		g2.setColor(fillColour);
		g2.fillRect(x, y, size, size);
		g2.setColor(Color.BLACK);
		g2.drawRect(x, y, size, size);

		g2.setFont(new Font("Arial", Font.BOLD, 5));
		g2.drawString(property.name(), x + 2, y + 15);
		g2.setFont(new Font("Arial", Font.BOLD, 15));
		g2.drawString(value.toString(), x + 2, y + size - 12);
	}

	private <T extends Number> void drawSetCard(Graphics2D g2, int x, int y, int width, int height, PropertySet set) {
	    Number value = setValues.get(set);
	    String str = value.toString();
	    float valueFloat = Float.parseFloat(str);

	    Color color = squareColour(value);
	    
	    int[] averageRent = set.calcAverageRent(board);
	    int averageCost = set.calcAverageCost(board);
	    int[] invest = set.calcInvest(board);

	    g2.setColor(color);
	    g2.fillRoundRect(x, y, width, height, 30, 30);
	    g2.setColor(Color.BLACK);
	    g2.drawRoundRect(x, y, width, height, 30, 30);

	    g2.setFont(new Font("Arial", Font.BOLD, 8));
	    g2.drawString("Set : " + set.name(), x + 5, y + 12);
	    g2.drawString("Land/Set : " + (value != null ? value.toString() : "N/A"), x + 5, y + 24);
	    g2.drawString("Land/Prop: " + (value != null ? String.format("%.3f", valueFloat / set.numProperties()) : "N/A"), x + 5, y + 36);
	    g2.drawString("AVG Cost : " + (value != null ? averageCost : "N/A"), x + 5, y + 48);

	    g2.drawString("House     Rent    Invest", x + 70, y + 12);
	    for (int i = 0; i < 7; i++) {
	        g2.drawString("Level " + i, x + 70, y + 24 + 12 * i);
	        g2.drawString("" + averageRent[i], x + 105, y + 24 + 12 * i);
	        g2.drawString("" + invest[i], x + 140, y + 24 + 12 * i);
	    }

	    g2.drawLine(x + 100, y + 5, x + 100, y + height - 5);
	    g2.drawLine(x + 125, y + 5, x + 125, y + height - 5);
	}


	private <T> Color squareColour(T value) {
		if (!(value instanceof Float)) { return new Color(255, 255, 255); }
		// ▪ Clamp between 0.5 and 2.0
		float clamped = Math.max(0.5f, Math.min(2.0f, (float) value));
		// ▪ Normalize to [0..1]
		float t = (clamped - 0.5f) / 1.5f;

		// ▪ Interpolate in RGB: pick light (#0eff00) and dark (#063b00)
		int r1 = 0x0e, g1 = 0xff, b1 = 0x00; // Light green (#0eff00)
		int r2 = 0x06, g2 = 0x3b, b2 = 0x00; // Dark green (#063b00)

		int red = (int) (r1 + (r2 - r1) * t);
		int green = (int) (g1 + (g2 - g1) * t);
		int blue = (int) (b1 + (b2 - b1) * t);

		return new Color(red, green, blue);
	}

	public static <T extends Number> void showBoard(List<Property> board, Map<Property, T> values, Map<PropertySet, T> setValues) {
		JFrame frame = new JFrame("Monopoly Landing Frequency Visualiser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new BoardVisualizer(board, values, setValues));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
