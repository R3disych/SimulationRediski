package gameMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import elements.Locatable;
import elements.Movable;
import elements.animals.Herbivore;
import elements.animals.Predator;
import elements.Entity;
import elements.staticObjects.Grass;
import util.Coordinates;

public class GameMap {
    private static int row;
    private static int column;
    private static Map<Coordinates, Locatable> gameMapLocatable;

    private GameMap(int row, int column) {
        GameMap.row = row;
        GameMap.column = column;
        GameMap.gameMapLocatable = new HashMap<>();
    }

    public static GameMap init(int row, int column) {
        return new GameMap(row, column);
    }

    public static int getRow() {
        return row;
    }

    public static int getColumn() {
        return column;
    }

    public Map<Coordinates, Locatable> getGameMapLocatable() {
        return new HashMap<>(gameMapLocatable);
    }

    public int getNumOf(Class<? extends Locatable> locatableClass) {
        int count = 0;
        List<Locatable> locatables = getEntityList();
        for (Locatable locatable : locatables) {
            if (locatableClass.isInstance(locatable)) {
                count++;
            }
        }
        return count;
    }

    public List<Locatable> getEntityList() {
        return new ArrayList<>(gameMapLocatable.values());
    }

    public boolean addEntityOnMap(Entity entity) {
        if(!gameMapLocatable.containsKey(entity.getCoordinates())) {
            gameMapLocatable.put(entity.getCoordinates(), entity);
            return true;
        }
        return false;
    }

    public void moveEntity(Movable movable, Coordinates newCoordinates) {
        Coordinates oldCoordinates = movable.getCoordinates();

        if(gameMapLocatable.containsKey(oldCoordinates) && gameMapLocatable.get(oldCoordinates).equals(movable)) {
            gameMapLocatable.remove(oldCoordinates);

            gameMapLocatable.put(newCoordinates, movable);
        } else {
            System.out.println("Ошибка: Entity не найдена на карте в ожидаемой позиции");
        }
    }

    public void removeEntity(Coordinates coordinates) {
        gameMapLocatable.remove(coordinates);
    }

    public static boolean isAccessibleCoordinate(Coordinates coordinates) {
        int x = coordinates.getRow();
        int y = coordinates.getColumn();
        return x >= 0 && y >= 0 && x < GameMap.getRow() && y < GameMap.getColumn();
    }

    public static boolean isWalkable(Coordinates coordinates) {
        if(!gameMapLocatable.containsKey(coordinates)) {
            return true;
        }
        return (!(gameMapLocatable.get(coordinates).isObstacle()));
    }
}
