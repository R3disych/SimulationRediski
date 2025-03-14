package elements;

import gameMap.GameMap;
import util.Coordinates;

import java.util.Map;
import java.util.Random;

public abstract class AbstractMovable extends Entity implements Movable {
    public AbstractMovable(Coordinates coordinates) {
        super(coordinates);
    }

    protected void step(GameMap gameMap, Coordinates nextStep) {
        if (canStep() && GameMap.isAccessibleCoordinate(nextStep) && GameMap.isWalkable(nextStep)) {
            gameMap.moveEntity(this, nextStep);
            setCoordinates(nextStep);
        }
    }
    protected boolean canStep() {
        return getStamina() > 0;
    }
    protected boolean canMakeRandomStep(GameMap gameMap) {
        Coordinates currentCoords = getCoordinates();
        int currX = currentCoords.getRow();
        int currY = currentCoords.getColumn();

        Coordinates nextCoords1 = new Coordinates(currX, currY - 1);
        Coordinates nextCoords2 = new Coordinates(currX, currY + 1);
        Coordinates nextCoords3 = new Coordinates(currX - 1, currY);
        Coordinates nextCoords4 = new Coordinates(currX + 1, currY);

        Map<Coordinates, Locatable> gameMapEntity = gameMap.getGameMapLocatable();
        return !gameMapEntity.containsKey(nextCoords1) || !gameMapEntity.containsKey(nextCoords2)
                || !gameMapEntity.containsKey(nextCoords3)
                || !gameMapEntity.containsKey(nextCoords4);
    }
    protected Coordinates getRandomNextStep(GameMap gameMap) {
        Random rand = new Random();

        Coordinates currentCoords = this.getCoordinates();
        int currX = currentCoords.getRow();
        int currY = currentCoords.getColumn();

        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        int maxAttempts = 10;
        for (int i = 0; i < maxAttempts; i++) {
            int newX = currX + dx[rand.nextInt(dx.length)];
            int newY = currY + dy[rand.nextInt(dy.length)];
            Coordinates nextCoords = new Coordinates(newX, newY);

            if (!gameMap.getGameMapLocatable().containsKey(nextCoords) && GameMap.isWalkable(nextCoords)
                    && GameMap.isAccessibleCoordinate(nextCoords)) {
                return nextCoords;
            }
        }

        return currentCoords;
    }
    protected void makeRandomStep(GameMap gameMap) {
        Coordinates nextStep = this.getRandomNextStep(gameMap);
        step(gameMap, nextStep);
    }
}
