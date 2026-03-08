package monopolySimulation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PlayerLandStats {
    private HashMap<Property, Integer> propertyFrequency = new HashMap<Property, Integer>();
    private HashMap<Property, Float> propertyRatio = new HashMap<Property, Float>();
    private HashMap<Property, Float> propertyNormalized = new HashMap<Property, Float>();
    private HashMap<Property, Float> propertyPercent = new HashMap<Property, Float>();

    private HashMap<PropertySet, Integer> setFrequency = new HashMap<PropertySet, Integer>();
    private HashMap<PropertySet, Float> setRatio = new HashMap<PropertySet, Float>();
    private HashMap<PropertySet, Float> setNormalized = new HashMap<PropertySet, Float>();
    private HashMap<PropertySet, Float> setPercent = new HashMap<PropertySet, Float>();

    public PlayerLandStats(List<Property> board) {
        if(board == null) {
            throw new IllegalArgumentException("Given Board is Null");
        }
        if(board.isEmpty()) {
            throw new IllegalArgumentException("Player given Empty Board");
        }
        // Initialize frequency for each property to 0
        for (Property p : board) {
            propertyFrequency.put(p, 0);
            propertyRatio.put(p, (float) 0);
            propertyNormalized.put(p, (float) 0);
            propertyPercent.put(p, (float) 0);
        }
        for (PropertySet set : Board.SETS) {
            setFrequency.put(set, 0);
            setRatio.put(set, (float) 0);
            setPercent.put(set, (float) 0);
        }
    }

    // Reset all frequencies to zero
    public void resetFrequency() {
        for (Property p : propertyFrequency.keySet()) { propertyFrequency.put(p, 0); }
    }

    // Increment the visit count for the given property
    public void addVisit(Property property) {
        propertyFrequency.put(property, propertyFrequency.getOrDefault(property, 0) + 1);
    }

    public void calcRatios() {
        int total = 0;
        int neverLanded = 0;

        for (int count : propertyFrequency.values()) {
            if (count == 0) { neverLanded += 1; }
            total += count;
        }

        propertyRatio.clear(); // Make sure old data is cleared

        if (propertyFrequency.isEmpty() || total == 0) {
            // Avoid division by zero
            for (Property property : propertyFrequency.keySet()) { propertyRatio.put(property, 0f); }
            return;
        }

        float average = (float) total / (propertyFrequency.size() - neverLanded);

        for (Map.Entry<Property, Integer> entry : propertyFrequency.entrySet()) {
            float ratio = entry.getValue() / average;
            int round = Math.round(ratio * 1000);
            float rounded = (float) round / 1000;
            propertyRatio.put(entry.getKey(), rounded);
        }
    }

    public void calcNormalized() {
        propertyNormalized.clear();

        if (propertyFrequency.isEmpty()) {
            throw new IllegalStateException("Cannot normalize: propertyFrequency is empty.");
        }

        int max = propertyFrequency.values().stream().mapToInt(Integer::intValue).max().orElse(1); // avoid division by
                                                                                                    // zero

        for (Map.Entry<Property, Integer> entry : propertyFrequency.entrySet()) {
            float normalized = max == 0 ? 0f : (float) entry.getValue() / max;
            int round = Math.round(normalized * 1000);
            float rounded = (float) round / 1000;
            propertyNormalized.put(entry.getKey(), rounded);
        }
    }

    public void calcPercent() {

        int total = 0;

        for (int count : propertyFrequency.values()) { total += count; }

        for (Map.Entry<Property, Integer> entry : propertyFrequency.entrySet()) {
            float percent = total == 0 ? 0f : (float) entry.getValue() / total;
            int round = Math.round(percent * 1000);
            float rounded = (float) round / 10;
            propertyPercent.put(entry.getKey(), rounded);
        }
    }

    public void calcSetFrequency() {
        setFrequency.clear();

        for (PropertySet set : Board.SETS) { setFrequency.put(set, 0); }

        for (Entry<Property, Integer> entry : propertyFrequency.entrySet()) {
            if (entry.getKey().set() == null) { continue; }
            setFrequency.put(entry.getKey().set(), setFrequency.get(entry.getKey().set()) + entry.getValue());
        }
    }

    public void calcSetRatios() {
        int total = 0;

        for (int count : setFrequency.values()) {
            total += count;
        }

        setRatio.clear(); // Make sure old data is cleared

        if (setFrequency.isEmpty()) {
            throw new IllegalStateException("Cannot normalize: propertyFrequency is empty.");
        }

        float average = (float) total / setFrequency.size();

        for (Map.Entry<PropertySet, Integer> entry : setFrequency.entrySet()) {
            float ratio = entry.getValue() / average;
            int round = Math.round(ratio * 1000);
            float rounded = (float) round / 1000;
            setRatio.put(entry.getKey(), rounded);
        }
    }

    public void calcSetNormalized() {
        setNormalized.clear();

        if (setFrequency.isEmpty()) {
            throw new IllegalStateException("Cannot normalize: propertyFrequency is empty.");
        }

        int max = setFrequency.values().stream().mapToInt(Integer::intValue).max().orElse(1);

        for (Map.Entry<PropertySet, Integer> entry : setFrequency.entrySet()) {
            float normalized = max == 0 ? 0f : (float) entry.getValue() / max;
            int round = Math.round(normalized * 1000);
            float rounded = (float) round / 1000;
            setNormalized.put(entry.getKey(), rounded);
        }
    }
    
    public void calcSetPercent() {
        if (setFrequency.isEmpty()) {
            throw new IllegalStateException("Cannot normalize: propertyFrequency is empty.");
        }

        int total = 0;

        for (int count : setFrequency.values()) { total += count; }
        
        for (Map.Entry<PropertySet, Integer> entry : setFrequency.entrySet()) {
            float percent = total == 0 ? 0f : (float) entry.getValue() / total;
            int round = Math.round(percent * 1000);
            float rounded = (float) round / 10;
            setPercent.put(entry.getKey(), rounded);
        }
    }

    public Map<Property, Integer> getFrequencies() {
        return Collections.unmodifiableMap(propertyFrequency);
    }

    public Map<Property, Float> getRatios() {
        return Collections.unmodifiableMap(propertyRatio);
    }

    public Map<Property, Float> getNormalized() {
        return Collections.unmodifiableMap(propertyNormalized);
    }
    
    public Map<Property, Float> getPercent() {
        return Collections.unmodifiableMap(propertyPercent);
    }

    public Map<PropertySet, Integer> getSetFrequencies() {
        return Collections.unmodifiableMap(setFrequency);
    }

    public Map<PropertySet, Float> getSetRatios() {
        return Collections.unmodifiableMap(setRatio);
    }

    public Map<PropertySet, Float> getSetNormalized() {
        return Collections.unmodifiableMap(setNormalized);
    }
    
    public Map<PropertySet, Float> getSetPercent() {
        return Collections.unmodifiableMap(setPercent);
    }
}
