package util;

import elements.Locatable;
import gameMap.GameMap;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Randomizer {
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
        int width = GameMap.getRow() - 1;
        int height = GameMap.getColumn() - 1;
        int size = width * height;
        List<Integer> numList = new ArrayList<>();
        for (int i = 0; i <= size; i++) {
            numList.add(i);
        }
        Map<Coordinates, Locatable> gameMapEntity = gameMap.getGameMapLocatable();
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
            return generateCoordinates(randomFreeSlotsIterator.next(), GameMap.getRow());
        }
        throw new IllegalStateException("Unexpected. Free slots out");
    }
}
