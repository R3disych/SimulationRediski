package actions;

import elements.Entity;
import gameMap.GameMap;
import elements.Animals.Creature;

import java.util.*;

import util.Coordinates;
import util.MoveRequest;
import util.PathFinder;

public class MakeTurn {

    public static void makeTurn(GameMap gameMap, PathFinder pathFinder) {
        /*
        Iterator<Map.Entry<Coordinates, Entity>> iterator = gameMap.getGameMapEntity().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Coordinates, Entity> entry = iterator.next();
            Entity entity = entry.getValue();
            if (entity instanceof Creature) {
                Creature creature = (Creature) entity;
                creature.makeMove(gameMap, pathFinder);
            }
        }

         */
        Map<Coordinates, Entity> gameMapCopy = new HashMap<>(gameMap.getGameMapEntity());
        Map<Coordinates, Entity> gameMapOriginal = gameMap.getGameMapEntity();
        for(Map.Entry<Coordinates, Entity> entry : gameMapCopy.entrySet()) {
            Coordinates coordinates = entry.getKey();
            Entity entity = entry.getValue();
            if (entity instanceof Creature) {
                Creature creature = (Creature) gameMapOriginal.get(coordinates);
                if(creature != null && !creature.isDead()) {
                    creature.makeMove(gameMap, pathFinder);
                    creature.restoreTurns();
                }
            }
        }
    }
}
