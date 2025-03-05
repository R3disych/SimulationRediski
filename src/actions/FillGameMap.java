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
    private Map<Coordinates, Entity> gameMapEntity;

    public FillGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
        this.gameMapEntity = gameMap.getGameMapEntity();
    }

    public void fillGameMap() {
        if(gameMap.getNumOfGrass() < 7) {
            gameMap.addEntity(new Grass(Randomizer.setRandomCoordinates()));
        }

        if(gameMap.getNumOfHerbivore() < 4) {
            gameMap.addEntity(new Herbivore(Randomizer.setRandomCoordinates()));
        }

        if(gameMap.getNumOfPredator() < 2) {
            gameMap.addEntity(new Predator(Randomizer.setRandomCoordinates()));
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
