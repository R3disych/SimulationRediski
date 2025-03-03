package actions;

import elements.Animals.Creature;
import elements.Entity;
import gameMap.GameMap;
import util.Coordinates;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class RemoveDead {
    public static void removeDead(GameMap map) {
        Map<Coordinates, Entity> gameMapCopy = new HashMap<>(map.getGameMapEntity());
        for(Map.Entry<Coordinates, Entity> entry : gameMapCopy.entrySet()) {
            Coordinates coordinates = entry.getKey();
            Entity entity = entry.getValue();
            if(entity instanceof Creature) {
                Creature creature = (Creature) entity;
                if(creature.isDead()) {
                    map.getGameMapEntity().remove(coordinates);
                }
            }
        }
    }
}
