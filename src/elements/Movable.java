package elements;

import gameMap.GameMap;
import util.PathFinder;

public interface Movable extends Alive {
    void move(GameMap gameMap, PathFinder pathFinder);
    int getStamina();
    int getMaxStamina();
    void restoreStamina();
    void spendStamina();
    int getDamage();
    void reinitInitiative();
    int getInitiative();
}