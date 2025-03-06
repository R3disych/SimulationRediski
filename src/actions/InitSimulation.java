package actions;

import gameMap.GameMap;
import elements.animals.Herbivore;
import elements.animals.Predator;
import elements.staticObjects.Grass;
import elements.staticObjects.Rock;
import elements.staticObjects.Tree;
import util.Randomizer;

public class InitSimulation {

    public void initSim(GameMap gameMap, Randomizer rand) {
        int i;
        for(i = 0; i < 13; ++i) {
            gameMap.addEntity(new Predator(rand.getRandomCoordinates()));
        }
        rand.foo();

        for(i = 0; i < 26; ++i) {
            gameMap.addEntity(new Herbivore(rand.getRandomCoordinates()));
        }
        rand.foo();

        for(i = 0; i < 13; ++i) {
            gameMap.addEntity(new Grass(rand.getRandomCoordinates()));
        }
        rand.foo();

        for(i = 0; i < 13; ++i) {
            gameMap.addEntity(new Rock(rand.getRandomCoordinates()));
        }
        rand.foo();

        for(i = 0; i < 26; ++i) {
            gameMap.addEntity(new Tree(rand.getRandomCoordinates()));
        }
        rand.foo();
    }
}
