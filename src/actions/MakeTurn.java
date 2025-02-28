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
        List<Entity> toRemove = new ArrayList();
        List<Entity> toAdd = new ArrayList();
        List<MoveRequest> moveRequests = new ArrayList<>();

        Iterator<Map.Entry<Coordinates, Entity>> iterator = gameMap.getGameMapEntity().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Coordinates, Entity> entry = iterator.next();
            Entity entity = entry.getValue();
            if (entity instanceof Creature) {
                Creature creature = (Creature) entity;
                creature.makeMove(gameMap, pathFinder, toRemove, toAdd, moveRequests);
            }
        }
    }
}
