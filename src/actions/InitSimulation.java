package actions;

import elements.GameMap;
import elements.Animals.Herbivore;
import elements.Animals.Predator;
import elements.staticObjects.Grass;
import elements.staticObjects.Rock;
import elements.staticObjects.Tree;

public class InitSimulation {
    public InitSimulation() {
    }

    public static GameMap initSim() {
        GameMap gameMap = new GameMap();

        int i;
        for(i = 0; i < 5; ++i) {
            gameMap.addEntity(new Predator());
        }

        for(i = 0; i < 6; ++i) {
            gameMap.addEntity(new Herbivore());
        }

        for(i = 0; i < 7; ++i) {
            gameMap.addEntity(new Grass());
        }

        for(i = 0; i < 5; ++i) {
            gameMap.addEntity(new Rock());
        }

        for(i = 0; i < 5; ++i) {
            gameMap.addEntity(new Tree());
        }

        return gameMap;
    }
}
