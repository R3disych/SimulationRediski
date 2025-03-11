package actions;

import elements.Alive;
import elements.Locateable;
import elements.animals.Creature;
import elements.animals.Herbivore;
import elements.animals.Predator;
import elements.staticObjects.Grass;
import elements.staticObjects.Rock;
import elements.staticObjects.Tree;
import gameMap.GameMap;
import gameMap.GameMapUI;
import util.Coordinates;
import util.PathFinder;
import util.Randomizer;

import java.util.*;

public class Action {
    private GameMap gameMap;
    private Map<Coordinates, Locateable> gameMapEntity;
    private Randomizer randomizer;
    private GameMapUI gameMapUI;

    public Action(GameMap gameMap, Randomizer randomizer) {
        this.gameMap = gameMap;
        this.gameMapEntity = gameMap.getGameMapEntity();
        this.randomizer = randomizer;
        this.gameMapUI = new GameMapUI(gameMap);
    }

    public void initSim() {
        int i;
        for(i = 0; i < 13; ++i) {
            gameMap.addEntity(new Predator(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();

        for(i = 0; i < 26; ++i) {
            gameMap.addEntity(new Herbivore(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();

        for(i = 0; i < 13; ++i) {
            gameMap.addEntity(new Grass(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();

        for(i = 0; i < 13; ++i) {
            gameMap.addEntity(new Rock(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();

        for(i = 0; i < 26; ++i) {
            gameMap.addEntity(new Tree(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();
    }

    public void makeTurn() {
        PathFinder pathFinder = new PathFinder(gameMap);
        Map<Coordinates, Locateable> gameMapEntity = gameMap.getGameMapEntity();

        List<Creature> entitiesToMove = new ArrayList<>();
        for (Locateable entity : gameMapEntity.values()) {
            if (entity instanceof Creature creature) {
                creature.reinitInitiative();
                entitiesToMove.add(creature);
                System.out.println("Добавил кричу");
            }
        }

        if (entitiesToMove.isEmpty()) {
            return;
        }

        entitiesToMove.sort(Comparator.comparingInt(Creature::getInitiative).reversed());
        for (Creature creature : entitiesToMove) {
            if (!creature.isDead()) {
                creature.move(gameMap, pathFinder);
            }
        }
    }

    public void clearMap() {
        Map<Coordinates, Locateable> gameMapCopy = new HashMap<>(gameMap.getGameMapEntity());
        for (Map.Entry<Coordinates, Locateable> entry : gameMapCopy.entrySet()) {
            Coordinates coordinates = entry.getKey();
            Locateable entity = entry.getValue();
            if (entity instanceof Alive alive) {
                boolean dead = alive.isDead();
                System.out.println(dead);
                if (dead) {
                    gameMap.getGameMapEntity().remove(coordinates);
                    System.out.println("Я удалил мертвого");
                }
            }
        }
    }

    public void fillGameMap(int i) {
        if(i % 5 == 0) {
            if (gameMap.getNumOfGrass() < 10) {
                for(int y = 0; gameMap.getNumOfGrass() < 10; y++) {
                    gameMap.addEntity(new Grass(randomizer.getRandomCoordinates()));
                }
            }

            if (gameMap.getNumOfHerbivore() < 26) {
                for(int y = 0; gameMap.getNumOfHerbivore() < 26; y++) {
                    gameMap.addEntity(new Herbivore(randomizer.getRandomCoordinates()));
                }
            }

            if (gameMap.getNumOfPredator() < 13) {
                for(int y = 0; gameMap.getNumOfPredator() < 13; y++) {
                    gameMap.addEntity(new Predator(randomizer.getRandomCoordinates()));
                }
            }
        }
    }
}
