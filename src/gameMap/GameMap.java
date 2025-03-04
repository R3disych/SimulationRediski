package gameMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import elements.Animals.Herbivore;
import elements.Animals.Predator;
import elements.Entity;
import elements.staticObjects.Grass;
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

    //сделать один метод, который будет принимать параметр Grass/Herbivore/Predator
    public int getNumOfGrass() {
        int count = 0;
        List<Entity> entityList = getEntityList();
        for(Entity e : entityList) {
            if(e instanceof Grass) {
                count++;
            }
        }
        return count;
    }

    public int getNumOfHerbivore() {
        int count = 0;
        List<Entity> entityList = getEntityList();
        for(Entity e : entityList) {
            if(e instanceof Herbivore) {
                count++;
            }
        }
        return count;
    }

    public int getNumOfPredator() {
        int count = 0;
        List<Entity> entityList = getEntityList();
        for(Entity e : entityList) {
            if(e instanceof Predator) {
                count++;
            }
        }
        return count;
    }

    public List<Entity> getEntityList() {
        List<Entity> entityList = new ArrayList<>();
        for(Map.Entry<Coordinates, Entity> entry : gameMapEntity.entrySet()) {
            Entity entity = entry.getValue();
            entityList.add(entity);
        }
        return entityList;
    }

    public void addEntity(Entity entity) {
        gameMapEntity.put(entity.getCoordinates(), entity);
    }

    public void moveEntity(Entity entity, Coordinates newCoordinates) {
        Coordinates oldCoordinates = entity.getCoordinates();

        if(gameMapEntity.containsKey(oldCoordinates) && gameMapEntity.get(oldCoordinates).equals(entity)) {
            gameMapEntity.remove(oldCoordinates);
            Randomizer.removeCoordinates(oldCoordinates);

            entity.setCoordinates(newCoordinates);
            gameMapEntity.put(entity.getCoordinates(), entity);
            Randomizer.addCoordinates(newCoordinates);
        } else {
            System.out.println("Ошибка: Entity не найдена на карте в ожидаемой позиции");
        }
    }

    public List<Entity> getEntitiesNear(Coordinates coordinates) {
        return getEntitiesNear(coordinates, 15);
    }

    public List<Entity> getEntitiesNear(Coordinates coordinates, int radius) {
        List<Entity> entities = new ArrayList<>();
        for(Entity entity : gameMapEntity.values()) {
            if(coordinates.distanceTo(entity.getCoordinates()) <= radius) {
                entities.add(entity);
            }
        }
        return entities;
    }

    public boolean isValidCoordinate(Coordinates coordinates) {
        int x = coordinates.getWidth();
        int y = coordinates.getHeight();
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
