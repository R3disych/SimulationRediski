package util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Randomizer {
    private static final Random random = new Random();
    static Set<Integer> idSet = new HashSet<>();
    static Set<Coordinates> coordsSet = new HashSet<>(); //скорее всего это состояние должен хранить класс GameMap
    private static final int maxAttempts = 100;

    public static int setRandomId() {
        for(int i = 0; i < maxAttempts; i++) {
            int id = random.nextInt(10000);
            if(!(idSet.contains(id))) {
                idSet.add(id);
                return id;
            }
        }
        return -1;
    }

    public static Coordinates setRandomCoordinates() {
        for(int i = 0; i < maxAttempts; i++) {
            int x = random.nextInt(30);
            int y = random.nextInt(30);
            Coordinates newCoords = new Coordinates(x, y);
            if(!coordsSet.contains(newCoords)) {
                coordsSet.add(new Coordinates(x, y));
                return newCoords;
            } else {
                setRandomCoordinates();
            }
        }
        return null; //лучше так не делать
    }

    public static void addCoordinates(Coordinates coords) {
        coordsSet.add(coords);
    }

    public static void removeCoordinates(Coordinates coords) {
        coordsSet.remove(coords);
    }
}
