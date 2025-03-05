package actions;

import elements.Animals.Herbivore;
import elements.Animals.Predator;
import elements.Entity;
import elements.staticObjects.Grass;
import gameMap.GameMap;
import util.Coordinates;
import util.Randomizer;

import java.util.Map;

public class FillGameMap {
    GameMap gameMap;
    Randomizer randomizer;
    private Map<Coordinates, Entity> gameMapEntity;

    public FillGameMap(GameMap gameMap, Randomizer randomizer) {
        this.gameMap = gameMap;
        this.randomizer = randomizer;
        this.gameMapEntity = gameMap.getGameMapEntity();
    }

    public void fillGameMap(int i) {
        if(i % 5 == 0) {
            if (gameMap.getNumOfGrass() < 7) {
                gameMap.addEntity(new Grass(randomizer.getRandomCoordinates()));
            }

            if (gameMap.getNumOfHerbivore() < 4) {
                gameMap.addEntity(new Herbivore(randomizer.getRandomCoordinates()));
            }

            if (gameMap.getNumOfPredator() < 2) {
                gameMap.addEntity(new Predator(randomizer.getRandomCoordinates()));
            }
        }
    }

    /*
    public void createNewEntity(Entity newEntity) {
        if (newEntity instanceof Grass) {
            gameMap.addEntity(newEntity);
        }
    }

     */

}
