package elements;

import gameMap.GameMap;
import util.Coordinates;
import util.PathFinder;

import java.util.List;
import java.util.Random;

public interface Moveable extends Alive {
    void move(GameMap gameMap, PathFinder pathFinder);
    default void step(GameMap gameMap, Coordinates target) {
        if (canStep()) {
            gameMap.moveEntity(this, target);
            setCoordinates(target);
            spendStamina();
        }
    }
    default void makeMoveToVictim(GameMap gameMap, PathFinder pathFinder, Alive victim) {
        List<Coordinates> path = pathFinder.findPath(this.getCoordinates(), victim.getCoordinates());
        if (path == null) {
            makeRandomStep(gameMap);
            return;
        }
        Coordinates nextStep = path.get(1);
        Locateable nextCoordinates = gameMap.getGameMapEntity().get(nextStep);
        if (gameMap.getGameMapEntity().containsKey(nextStep)
                && nextCoordinates.isObstacle()) {
            return;
        }
        step(gameMap, nextStep);
    }
    default boolean canStep() {
        return getStamina() > 0;
    }
    default Coordinates getRandomNextStep() {
        Random rand = new Random();

        Coordinates currentCoords = this.getCoordinates();
        int currX = currentCoords.getWidth();
        int currY = currentCoords.getHeight();

        int[] dx = {0, 0, -1, 1, -1, -1, 1, 1};
        int[] dy = {-1, 1, 0, 0, -1, 1, -1, 1};
        int newX = currX + dx[rand.nextInt(dx.length)];
        int newY = currY + dy[rand.nextInt(dy.length)];

        Coordinates newCoordinates = new Coordinates(newX, newY);
        return newCoordinates;
    }
    default void makeRandomStep(GameMap gameMap) {
        Coordinates nextStep = this.getRandomNextStep();
        if (GameMap.isAccessibleCoordinate(nextStep) && GameMap.isWalkable(nextStep)) {
            gameMap.moveEntity(this, nextStep);
        } else {

        }
    }
    int getStamina();
    int getMaxStamina();
    void restoreStamina();
    void spendStamina();
    int getDamage();
    void reinitInitiative();
    int getInitiative();
}