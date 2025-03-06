import elements.animals.Herbivore;
import elements.staticObjects.Grass;
import gameMap.GameMap;
import util.Coordinates;
import util.PathFinder;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        GameMap gameMap = new GameMap();
        PathFinder pathFinder = new PathFinder(gameMap);

        Herbivore herbivore1 = new Herbivore();
        //herbivore1.setCoordinates(new Coordinates(3, 4));
        System.out.println("Current coords of herbivore1: " + herbivore1.getCoordinates().getWidth() + ", " + herbivore1.getCoordinates().getHeight());

        //Herbivore herbivore2 = new Herbivore();
        //System.out.println("Current coords of herbivore2: " + herbivore2.getCoordinates().getWidth() + ", " + herbivore2.getCoordinates().getHeight());

        Grass grass1 = new Grass();
        System.out.println("Current coords of grass1: " + grass1.getCoordinates().getWidth() + ", " + grass1.getCoordinates().getHeight());




        Grass grass2 = new Grass();
        grass2.setCoordinates(new Coordinates(10, 6));
        System.out.println("Current coords of grass2: " + grass2.getCoordinates().getWidth() + ", " + grass2.getCoordinates().getHeight());

        gameMap.addEntity(herbivore1);
        //gameMap.addEntity(herbivore2);
        gameMap.addEntity(grass1);
        gameMap.addEntity(grass2);



        List<Coordinates> path = pathFinder.findPath(herbivore1.getCoordinates(), grass1.getCoordinates());
        for(Coordinates c : path) {
            System.out.println(c.getWidth() + " " + c.getHeight());
        }

        /*
        Optional<Grass> grasses = herbivore1.findNearestGrass(gameMap);
        if(grasses.isPresent()) {
            System.out.println("Grass2 found " + grasses.get());
        }

         */


        herbivore1.makeMove(gameMap, pathFinder);
    }
}
