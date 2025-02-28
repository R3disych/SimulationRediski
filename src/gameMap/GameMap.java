package gameMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import elements.Entity;
import util.Coordinates;
import util.Randomizer;

public class GameMap {
    private int width;
    private int height;
    private Map<Coordinates, Entity> gameMapEntity;

    public GameMap() {
        this.width = 30;
        this.height = 30;
        gameMapEntity = new HashMap<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Map<Coordinates, Entity> getGameMapEntity() {
        return gameMapEntity;
    }

    public void addEntity(Entity entity) {
        gameMapEntity.put(entity.getCoordinates(), entity);
    }

    public void removeEntity(Entity entity) {
        gameMapEntity.remove(entity.getCoordinates());
        Randomizer.removeCoordinates(entity.getCoordinates());
    }

    public void moveEntity(Entity entity, Coordinates newCoordinates) {
        Coordinates oldCoordinates = entity.getCoordinates();

        if(gameMapEntity.containsKey(oldCoordinates) && gameMapEntity.get(oldCoordinates).equals(entity)) {
            gameMapEntity.remove(oldCoordinates);
            entity.setCoordinates(newCoordinates);
            gameMapEntity.put(entity.getCoordinates(), entity);
        } else {
            System.out.println("Ошибка: Entity не найдена на карте в ожидаемой позиции");
        }
    }

    public List<Entity> getEntitiesNear(Coordinates coordinates) {
        return getEntitiesNear(coordinates, 15);
    }

    public List<Entity> getEntitiesNear(Coordinates coordinates, int radius) {
    }

    public boolean isValidCoordinate(Coordinates coordinates) {
        int x = coordinates.getWidth();
        int y = coordinates.getHeight();
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
