package gameMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import elements.Locateable;
import elements.Moveable;
import elements.animals.Herbivore;
import elements.animals.Predator;
import elements.Entity;
import elements.staticObjects.Grass;
import util.Coordinates;

public class GameMap {
    private static int width ;
    private static int height;
    private static Map<Coordinates, Locateable> gameMapEntity;

    private GameMap(int width, int height) {
        GameMap.width = width;
        GameMap.height = height;
        GameMap.gameMapEntity = new HashMap<>();
    }

    public static GameMap init(int width, int height) {
        return new GameMap(width, height);
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public Map<Coordinates, Locateable> getGameMapEntity() {
        //return new HashMap<>(gameMapEntity);
        return gameMapEntity;
    }

    //сделать один метод, который будет принимать параметр Grass/Herbivore/Predator
    public int getNumOfGrass() {
        int count = 0;
        List<Locateable> entityList = getEntityList();
        for(Locateable l : entityList) {
            if(l instanceof Grass) {
                count++;
            }
        }
        return count;
    }

    public int getNumOfHerbivore() {
        int count = 0;
        List<Locateable> entityList = getEntityList();
        for(Locateable l : entityList) {
            if(l instanceof Herbivore) {
                count++;
            }
        }
        return count;
    }

    public int getNumOfPredator() {
        int count = 0;
        List<Locateable> entityList = getEntityList();
        for(Locateable l : entityList) {
            if(l instanceof Predator) {
                count++;
            }
        }
        return count;
    }

    public List<Locateable> getEntityList() {
        return new ArrayList<>(gameMapEntity.values());
    }

    public boolean addEntity(Entity entity) {
        if(!gameMapEntity.containsKey(entity.getCoordinates())) {
            gameMapEntity.put(entity.getCoordinates(), entity);
            return true;
        }
        return false;
    }

    public void moveEntity(Moveable moveable, Coordinates newCoordinates) {
        Coordinates oldCoordinates = moveable.getCoordinates();

        if(gameMapEntity.containsKey(oldCoordinates) && gameMapEntity.get(oldCoordinates).equals(moveable)) {
            gameMapEntity.remove(oldCoordinates);

            gameMapEntity.put(newCoordinates, moveable);
        } else {
            System.out.println("Ошибка: Entity не найдена на карте в ожидаемой позиции");
        }
    }

    public static boolean isAccessibleCoordinate(Coordinates coordinates) {
        int x = coordinates.getWidth();
        int y = coordinates.getHeight();
        return x >= 0 && y >= 0 && x < GameMap.getWidth() && y < GameMap.getHeight();
    }

    public static boolean isWalkable(Coordinates coordinates) {
        if(!gameMapEntity.containsKey(coordinates)) {
            return true;
        }
        return (!(gameMapEntity.get(coordinates).isObstacle()));
    }


//    @Override
//    public String toString() {
//        return "GameMap{" +
//                ", gameMapEntity=" + gameMapEntity +
//                '}';
//    }
}
