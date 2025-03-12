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
    private final int INITIAL_PREDATOR_NUMBER = 13;
    private final int INITIAL_HERBIVORE_NUMBER = 26;
    private final int INITIAL_GRASS_NUMBER = 18;
    private final int INITIAL_ROCK_NUMBER = 13;
    private final int INITIAL_TREE = 26;
    private final int REFILL_INTERVAL = 5;

    public Action(GameMap gameMap, Randomizer randomizer, GameMapUI gameMapUI) {
        this.gameMap = gameMap;
        this.gameMapLocateable = gameMap.getGameMapLocatable();
        this.randomizer = randomizer;
        this.gameMapUI = gameMapUI;
    }

    public void initSimulation() {
        int i;
        for (i = 0; i < INITIAL_PREDATOR_NUMBER; ++i) {
            gameMap.addEntityOnMap(new Predator(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();

        for (i = 0; i < INITIAL_HERBIVORE_NUMBER; ++i) {
            gameMap.addEntityOnMap(new Herbivore(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();

        for (i = 0; i < INITIAL_GRASS_NUMBER; ++i) {
            gameMap.addEntityOnMap(new Grass(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();

        for (i = 0; i < INITIAL_ROCK_NUMBER; ++i) {
            gameMap.addEntityOnMap(new Rock(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();

        for (i = 0; i < INITIAL_TREE; ++i) {
            gameMap.addEntityOnMap(new Tree(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();
    }

    public void makeTurn() {
        restoreMovablesStamina();
        PathFinder pathFinder = new PathFinder(gameMap);
        List<Movable> entitiesToMove = getMovablesToMove(gameMap.getGameMapLocatable());

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

    public List<Movable> getMovablesToMove(Map<Coordinates, Locatable> gameMapLocateable) {
        List<Movable> movablesToMove = new ArrayList<>();
        for (Locatable entity : gameMapLocateable.values()) {
            if (entity instanceof Movable movable && !movable.isDead()) {
                movable.reinitInitiative();
                movablesToMove.add(movable);
            }
        }
        return movablesToMove;
    }

    public void clearMapOfDead() {
        Map<Coordinates, Locatable> gameMapCopy = new HashMap<>(gameMap.getGameMapLocatable());
        for (Map.Entry<Coordinates, Locatable> entry : gameMapCopy.entrySet()) {
            Coordinates coordinates = entry.getKey();
            Locatable entity = entry.getValue();
            if (entity instanceof Alive alive) {
                if (alive.isDead()) {
                    gameMap.getGameMapLocatable().remove(coordinates);
                }
            }
        }
    }

    public void fillGameMap(int i) {
        if (i % REFILL_INTERVAL == 0) {
            if (gameMap.getNumOfGrass() < INITIAL_GRASS_NUMBER) {
                for (int y = 0; gameMap.getNumOfGrass() < INITIAL_GRASS_NUMBER; y++) {
                    gameMap.addEntityOnMap(new Grass(randomizer.getRandomCoordinates()));
                }
            }

            if (gameMap.getNumOfHerbivore() < INITIAL_HERBIVORE_NUMBER) {
                for (int y = 0; gameMap.getNumOfHerbivore() < INITIAL_HERBIVORE_NUMBER; y++) {
                    gameMap.addEntityOnMap(new Herbivore(randomizer.getRandomCoordinates()));
                }
            }

            if (gameMap.getNumOfPredator() < INITIAL_PREDATOR_NUMBER) {
                for (int y = 0; gameMap.getNumOfPredator() < INITIAL_PREDATOR_NUMBER; y++) {
                    gameMap.addEntityOnMap(new Predator(randomizer.getRandomCoordinates()));
                }
            }
        }
    }

    public void restoreMovablesStamina() {
        for (Locatable moveable : gameMap.getGameMapLocatable().values()) {
            if (moveable instanceof Movable movable2) {
                movable2.restoreStamina();
            }
        }
    }
}
