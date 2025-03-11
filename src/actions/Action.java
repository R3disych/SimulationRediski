package actions;

import elements.Alive;
import elements.Locatable;
import elements.Movable;
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
    private Map<Coordinates, Locatable> gameMapLocateable;
    private Randomizer randomizer;
    private GameMapUI gameMapUI;

    public Action(GameMap gameMap, Randomizer randomizer, GameMapUI gameMapUI) {
        this.gameMap = gameMap;
        this.gameMapLocateable = gameMap.getGameMapLocatable();
        this.randomizer = randomizer;
        this.gameMapUI = gameMapUI;
    }

    public void initSimulation() {
        int i;
        for (i = 0; i < 13; ++i) {
            gameMap.addEntityOnMap(new Predator(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();

        for (i = 0; i < 26; ++i) {
            gameMap.addEntityOnMap(new Herbivore(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();

        for (i = 0; i < 13; ++i) {
            gameMap.addEntityOnMap(new Grass(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();

        for (i = 0; i < 13; ++i) {
            gameMap.addEntityOnMap(new Rock(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();

        for (i = 0; i < 26; ++i) {
            gameMap.addEntityOnMap(new Tree(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();
    }

    public void makeTurn() {
        restoreStamina();
        PathFinder pathFinder = new PathFinder(gameMap);
        Map<Coordinates, Locatable> gameMapEntity = gameMap.getGameMapLocatable();

        List<Movable> entitiesToMove = new ArrayList<>();
        for (Locatable entity : gameMapEntity.values()) {
            if (entity instanceof Movable movable && !movable.isDead()) {
                movable.reinitInitiative();
                entitiesToMove.add(movable);
            }
        }

        if (entitiesToMove.isEmpty()) {
            return;
        }

        entitiesToMove.sort(Comparator.comparingInt(Movable::getInitiative).reversed());
        for (Movable creature : entitiesToMove) {
            if (!creature.isDead()) {
                creature.move(gameMap, pathFinder);
                gameMapUI.repaint();
            }
        }
    }

   public void clearMap() {
        Map<Coordinates, Locatable> gameMapCopy = new HashMap<>(gameMap.getGameMapLocatable());
        for (Map.Entry<Coordinates, Locatable> entry : gameMapCopy.entrySet()) {
            Coordinates coordinates = entry.getKey();
            Locatable entity = entry.getValue();
            if (entity instanceof Alive alive) {
                boolean dead = alive.isDead();
                if (dead) {
                    gameMap.getGameMapLocatable().remove(coordinates);
                }
            }
        }
    }

    public void fillGameMap(int i) {
        if (i % 5 == 0) {
            if (gameMap.getNumOfGrass() < 10) {
                for (int y = 0; gameMap.getNumOfGrass() < 10; y++) {
                    gameMap.addEntityOnMap(new Grass(randomizer.getRandomCoordinates()));
                }
            }

            if (gameMap.getNumOfHerbivore() < 26) {
                for (int y = 0; gameMap.getNumOfHerbivore() < 26; y++) {
                    gameMap.addEntityOnMap(new Herbivore(randomizer.getRandomCoordinates()));
                }
            }

            if (gameMap.getNumOfPredator() < 13) {
                for (int y = 0; gameMap.getNumOfPredator() < 13; y++) {
                    gameMap.addEntityOnMap(new Predator(randomizer.getRandomCoordinates()));
                }
            }
        }
    }

    public void restoreStamina() {
        for (Locatable moveable : gameMap.getGameMapLocatable().values()) {
            if (moveable instanceof Movable movable2) {
                movable2.restoreStamina();
            }
        }
    }
}
