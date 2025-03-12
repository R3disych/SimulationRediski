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
    private static final int DEFAULT_FIELD = 64;
    private static final int REFILL_INTERVAL = 5;

    private static final int DEFAULT_PREDATOR_RATE = 1;
    private static final int DEFAULT_HERBIVORE_RATE = 2;
    private static final int DEFAULT_GRASS_RATE = 2;
    private static final int DEFAULT_TREE_RATE = 3;
    private static final int DEFAULT_ROCK_RATE = 2;

    private int initialPredatorNumber;
    private int initialHerbivoreNumber;
    private int initialGrassNumber;
    private int initialRockNumber;
    private int initialTreeNumber;

    public Action(GameMap gameMap, Randomizer randomizer, GameMapUI gameMapUI) {
        this.gameMap = gameMap;
        this.gameMapLocateable = gameMap.getGameMapLocatable();
        this.randomizer = randomizer;
        this.gameMapUI = gameMapUI;
        setNumberOfEntities();
    }

    private void setNumberOfEntities() {
        int numOfCells = GameMap.getRow() * GameMap.getColumn();
        int numOfFields = numOfCells / DEFAULT_FIELD;
        initialPredatorNumber = numOfFields * DEFAULT_PREDATOR_RATE;
        initialHerbivoreNumber = numOfFields * DEFAULT_HERBIVORE_RATE;
        initialGrassNumber = numOfFields * DEFAULT_GRASS_RATE;
        initialRockNumber = numOfFields * DEFAULT_ROCK_RATE;
        initialTreeNumber = numOfFields * DEFAULT_TREE_RATE;
    }

    public void initSimulation() {
        int i;
        for (i = 0; i < initialPredatorNumber; ++i) {
            gameMap.addEntityOnMap(new Predator(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();

        for (i = 0; i < initialHerbivoreNumber; ++i) {
            gameMap.addEntityOnMap(new Herbivore(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();

        for (i = 0; i < initialGrassNumber; ++i) {
            gameMap.addEntityOnMap(new Grass(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();

        for (i = 0; i < initialRockNumber; ++i) {
            gameMap.addEntityOnMap(new Rock(randomizer.getRandomCoordinates()));
        }
        randomizer.reinitializeFreeCells();

        for (i = 0; i < initialTreeNumber; ++i) {
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
            if (gameMap.getNumOfGrass() < initialGrassNumber) {
                for (int y = 0; gameMap.getNumOfGrass() < initialGrassNumber; y++) {
                    gameMap.addEntityOnMap(new Grass(randomizer.getRandomCoordinates()));
                }
            }

            if (gameMap.getNumOfHerbivore() < initialHerbivoreNumber) {
                for (int y = 0; gameMap.getNumOfHerbivore() < initialHerbivoreNumber; y++) {
                    gameMap.addEntityOnMap(new Herbivore(randomizer.getRandomCoordinates()));
                }
            }

            if (gameMap.getNumOfPredator() < initialPredatorNumber) {
                for (int y = 0; gameMap.getNumOfPredator() < initialPredatorNumber; y++) {
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
