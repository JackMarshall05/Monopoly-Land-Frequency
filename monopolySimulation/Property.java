package monopolySimulation;

public record Property(String name, PropertyType type, PropertySet set, int cost, int[] rent) {
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Property p)) return false;
        return name.equalsIgnoreCase(p.name) && type == p.type;
    }
}
