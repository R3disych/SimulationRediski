package actions;

import gameMap.GameMap;
import elements.Animals.Herbivore;
import elements.Animals.Predator;
import elements.staticObjects.Grass;
import elements.staticObjects.Rock;
import elements.staticObjects.Tree;
import util.Randomizer;

import java.util.Random;

public class InitSimulation {

    public void initSim(GameMap gameMap, Randomizer rand) {
        int i;
        for(i = 0; i < 2; ++i) {
            gameMap.addEntity(new Predator(rand.getRandomCoordinates()));
        }
        rand.foo();

        for(i = 0; i < 4; ++i) {
            gameMap.addEntity(new Herbivore(rand.getRandomCoordinates()));
        }
        rand.foo();

        for(i = 0; i < 7; ++i) {
            gameMap.addEntity(new Grass(rand.getRandomCoordinates()));
        }
        rand.foo();

        for(i = 0; i < 5; ++i) {
            gameMap.addEntity(new Rock(rand.getRandomCoordinates()));
        }
        rand.foo();

        for(i = 0; i < 7; ++i) {
            gameMap.addEntity(new Tree(rand.getRandomCoordinates()));
        }
        rand.foo();
    }
}
