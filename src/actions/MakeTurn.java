package actions;

import elements.Entity;
import elements.GameMap;
import elements.Animals.Creature;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import util.Coordinates;
import util.MoveRequest;
import util.PathFinder;

public class MakeTurn {
    public MakeTurn() {
    }

    public static void makeTurn(GameMap gameMap, PathFinder pathFinder) {
        List<Entity> toRemove = new ArrayList();
        List<Entity> toAdd = new ArrayList();
        List<MoveRequest> moveRequests = new ArrayList();
        Iterator var5 = gameMap.getGameMap().entrySet().iterator();

        while(var5.hasNext()) {
            Map.Entry<Coordinates, Entity> entry = (Map.Entry)var5.next();
            if (entry.getValue() instanceof Creature) {
                Creature creature = (Creature)entry.getValue();
                creature.makeMove(gameMap, pathFinder, toRemove, toAdd, moveRequests);
            }
        }

        var5 = toRemove.iterator();

        Entity entity;
        while(var5.hasNext()) {
            entity = (Entity)var5.next();
            gameMap.removeEntity(entity);
        }

        var5 = toAdd.iterator();

        while(var5.hasNext()) {
            entity = (Entity)var5.next();
            gameMap.addEntity(entity);
        }

        var5 = moveRequests.iterator();

        while(var5.hasNext()) {
            MoveRequest moveRequest = (MoveRequest)var5.next();
            gameMap.moveEntity(moveRequest.getEntity(), moveRequest.getNewCoordinates());
        }

    }
}
