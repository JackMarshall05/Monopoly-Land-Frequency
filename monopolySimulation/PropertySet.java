package monopolySimulation;

import java.util.List;

public record PropertySet(String name, int numProperties, Integer houseCost) {
	public int[] calcAverageRent(List<Property> properties) {
		int[] averageRent = new int[7];

		for (Property property : properties) {
			if (property.set() == null) { continue; }
			if (property.set().equals(this)) {
				for (int i = 0; i < averageRent.length; i++) {
					try {
						averageRent[i] += property.rent()[i];
					} catch (ArrayIndexOutOfBoundsException e) {
					}
				}
			}
		}

		for (int i = 0; i < averageRent.length; i++) {
			try {
				averageRent[i] /= numProperties;
			} catch (ArrayIndexOutOfBoundsException e) {
			}
		}

		return averageRent;
	}
	
	public int calcAverageCost(List<Property> properties) {
		int averageCost = 0;

		for (Property property : properties) {
			if (property.set() == null) { continue; }
			if (property.set().equals(this)) {
				averageCost += property.cost();
			}
		}

		return averageCost/numProperties;
	}
	
	public int[] calcInvest(List<Property> properties) {
		int[] invest = new int[7];
		
		if(houseCost != null) {
			invest[0] = calcAverageCost(properties);
			invest[1] = calcAverageCost(properties) * numProperties;
			for(int i = 0; i < 5; i++) {
				invest[i + 2] = invest[1] + (i+1) * houseCost * numProperties;
			}
		}else {
			if(name == "Stations") {
				for(int i = 0; i < 4; i++) {
					invest[i] = calcAverageCost(properties) * (i + 1);
				}
			}
			if(name == "Utilities") {
				for(int i = 0; i < 2; i++) {
					invest[i] = calcAverageCost(properties) * (i + 1);
				}
			}
		}
		
		return invest;
	}
}
