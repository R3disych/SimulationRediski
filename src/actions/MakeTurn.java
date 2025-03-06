package actions;

import elements.Entity;
import gameMap.GameMap;
import elements.animals.Creature;

import java.util.*;

import gameMap.GameMapUI;
import util.Coordinates;
import util.PathFinder;

public class MakeTurn {
    public static void makeTurn(GameMap gameMap, GameMapUI gameMapUI) {
        while (true) {

            PathFinder pathFinder = new PathFinder(gameMap);
            Map<Coordinates, Entity> gameMapEntity = gameMap.getGameMapEntity();

            List<Creature> entitiesToMove = new ArrayList<>();
            for (Map.Entry<Coordinates, Entity> entry : gameMapEntity.entrySet()) {
                Entity entity = entry.getValue();
                if (entity instanceof Creature creature) {
                    if (creature.isTurnable()) {
                        creature.reinitInitiative();
                        entitiesToMove.add(creature);
                    }
                }
            }

            if (entitiesToMove.isEmpty()) {
                return;
            }

            entitiesToMove.sort(Comparator.comparingInt(Creature::getInitiative).reversed());
            for (Creature creature : entitiesToMove) {
                gameMapUI.repaint();
                creature.makeMove(gameMap, pathFinder);
            }
        }

//        Iterator<Map.Entry<Coordinates, Entity>> iterator = gameMapEntity.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<Coordinates, Entity> entry = iterator.next();
//            Coordinates coordinates = entry.getKey();
//            Entity entity = entry.getValue();
//            if (entity instanceof Creature creature) {
//                if (!creature.isDead()) {
//                    creature.makeMove(gameMap, pathFinder);
//                }
//            }
//        }


//        for (Map.Entry<Coordinates, Entity> entry : gameMapEntity.entrySet()) {
//            Coordinates coordinates = entry.getKey();
//            Entity entity = entry.getValue();
//            if (entity instanceof Creature) {
//                Creature creature = (Creature) gameMapEntity.get(coordinates);
//                if (!creature.isDead()) {
//                    creaturesToMove.add(creature);
//                }
//            }
//        }
//
//        for (Creature creature : creaturesToMove) {
//            creature.makeMove(gameMap, pathFinder);
//            creature.restoreTurns();
//        }
    }
}
