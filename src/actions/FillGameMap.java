package actions;

import elements.animals.Herbivore;
import elements.animals.Predator;
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

    /*
    public void createNewEntity(Entity newEntity) {
        if (newEntity instanceof Grass) {
            gameMap.addEntity(newEntity);
        }
    }

     */

}
