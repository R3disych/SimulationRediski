package actions;

import elements.animals.Creature;
import elements.Entity;
import elements.animals.Footprints;
import gameMap.GameMap;
import util.Coordinates;

import java.util.HashMap;
import java.util.Map;

public class ClearMap {
    public static void clearMap(GameMap map) {
        Map<Coordinates, Entity> gameMapCopy = new HashMap<>(map.getGameMapEntity());
        for (Map.Entry<Coordinates, Entity> entry : gameMapCopy.entrySet()) {
            Coordinates coordinates = entry.getKey();
            Entity entity = entry.getValue();
            if (entity instanceof Creature) {
                Creature creature = (Creature) entity;
                if (creature.isDead()) {
                    map.getGameMapEntity().remove(coordinates);
                }
            } else if (entity instanceof Footprints) {
                Footprints footprints = (Footprints) entity;
                map.getGameMapEntity().remove(coordinates);
            }
        }
    }
}
