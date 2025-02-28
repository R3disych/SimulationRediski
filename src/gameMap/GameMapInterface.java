package gameMap;

import elements.Entity;
import util.Coordinates;

import java.util.HashMap;
import java.util.Map;

public class GameMapInterface {
    private GameMap gameMap;
    private Map<Coordinates, Entity> entities;

    public GameMapInterface(GameMap gameMap) {
        this.gameMap = gameMap;
        this.entities = gameMap.getGameMapEntity();
    }

    public Map<Coordinates, Entity> getEntities() {
        return entities;
    }
}
