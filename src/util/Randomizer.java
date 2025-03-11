package util;

import elements.Locateable;
import gameMap.GameMap;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Randomizer {
    private static final Random random = new Random();
    static Set<Integer> idSet = new HashSet<>();
    static Set<Coordinates> coordsSet = new HashSet<>(); //скорее всего это состояние должен хранить класс GameMap
    private static final int maxAttempts = 1000;
    private GameMap gameMap;
    private Iterator<Integer> randomFreeSlotsIterator;
    private static AtomicInteger idGenerator = new AtomicInteger(0);

    public Randomizer(GameMap gameMap) {
        this.gameMap = gameMap;

    }

    public static int setRandomId() {
        return idGenerator.getAndIncrement();
    }

    public void reinitializeFreeCells() {
        int width = GameMap.getWidth();
        int height = GameMap.getHeight();
        int size = width * height;
        List<Integer> numList = new ArrayList<>();
        for (int i = 0; i <= size; i++) {
            numList.add(i);
        }
        Map<Coordinates, Locateable> gameMapEntity = gameMap.getGameMapEntity();
        numList.sort((o1, o2) -> {
            if (gameMapEntity.containsKey(generateCoordinates(o1, width)) &&
                    !gameMapEntity.containsKey(generateCoordinates(o2, width))) {
                return 1;
            }

            if (!gameMapEntity.containsKey(generateCoordinates(o1, width)) &&
                    gameMapEntity.containsKey(generateCoordinates(o2, width))) {
                return -1;
            }
            return Integer.compare(o1, o2);
        });
        numList = numList.subList(0, numList.size() - gameMapEntity.size());
        Collections.shuffle(numList);
        randomFreeSlotsIterator = numList.iterator();
    }

    public Coordinates generateCoordinates(Integer n, Integer width) {
        int x = n % width;
        int y = n / width;
        return new Coordinates(x, y);
    }

    public Coordinates getRandomCoordinates() {
        if(randomFreeSlotsIterator.hasNext()) {
            return generateCoordinates(randomFreeSlotsIterator.next(), GameMap.getWidth());
        }
        throw new IllegalStateException("Unexpected. Free slots out");
    }
}
