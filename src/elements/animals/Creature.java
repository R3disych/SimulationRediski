package elements.animals;

import elements.AbstractMovables;
import elements.Alive;
import elements.Entity;
import elements.Movable;
import gameMap.GameMap;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import util.Coordinates;
import util.PathFinder;

public abstract class Creature extends AbstractMovables implements Movable, Alive {
    private int hp;
    private int hunger;
    private int stamina;
    private int initiative;
    private final int MAX_INITIATIVE = 100;
    private final int HUNGER_DECREMENT = 5;
    private final int HP_DECREMENT = 10;


    private Random rand = new Random();

    public Creature(Coordinates coordinates) {
        super(coordinates);
        this.hp = getMaxHp();
        this.hunger = getMaxHunger();
        restoreStamina();
    }

    @Override
    public void reinitInitiative() {
        this.initiative = rand.nextInt(MAX_INITIATIVE);
    }

    @Override
    public int getInitiative() {
        return initiative;
    }

    @Override
    public int getCurrentHp() {
        return hp;
    }

    @Override
    public int getCurrentHunger() {
        return hunger;
    }

    @Override
    public int getStamina() {
        return stamina;
    }

    @Override
    public void restoreStamina() {
        stamina = getMaxStamina();
    }

    @Override
    public void spendStamina() {
        stamina--;
    }

    @Override
    public boolean isObstacle() {
        return true;
    }

    @Override
    public void eat(int nutritionValue) {
        hunger += nutritionValue;
        if (hunger > getMaxHunger()) {
            hunger = getMaxHunger();
        }
        spendStamina();
    }

    @Override
    public boolean decraseHunger() {
        this.hunger -= HUNGER_DECREMENT;
        if (hunger < 0) {
            hunger = 0;
        }
        return hunger == 0;
    }

    @Override
    public boolean decreaseHp(int damage) {
        this.hp -= damage;
        return hp <= 0;
    }

    @Override
    public boolean isDead() {
        return getCurrentHp() <= 0;
    }

    @Override
    public void starving() {
        this.hp -= HP_DECREMENT;
    }

    protected abstract Class<? extends Alive> getVictimType();

    public Optional<Alive> findNearestVictim(PathFinder pathFinder) {
        return pathFinder.getEntitiesNear(this.getCoordinates(), 20)
                .stream()
                .filter(entity -> entity.getClass().equals(getVictimType()))
                .map(locateable -> (Alive) locateable)
                .min(Comparator.comparingDouble(victim ->
                        this.getCoordinates().distanceTo(victim.getCoordinates())));
    }

    protected void makeMoveToVictim(GameMap gameMap, PathFinder pathFinder, Alive victim) {
        List<Coordinates> path = pathFinder.findPath(this.getCoordinates(), victim.getCoordinates());
        if (path == null || path.isEmpty() || path.size() == 1) {
            makeRandomStep(gameMap);
            return;
        }

        Coordinates nextStep = path.get(1);
        if (GameMap.isAccessibleCoordinate(nextStep) && GameMap.isWalkable(nextStep)) {
            step(gameMap, nextStep);
        } else {
            makeRandomStep(gameMap);
        }
    }

    @Override
    public void move(GameMap gameMap, PathFinder pathFinder) {
        Optional<Alive> nearestVictim = findNearestVictim(pathFinder);
        if (nearestVictim.isPresent()) {
            Alive victim = nearestVictim.get();
            if (this.getCoordinates().distanceTo(victim.getCoordinates()) <= 1 && !victim.isDead()) {
                eat(victim.getNutritionalValue());
                victim.decreaseHp(getDamage());
            } else {
                makeMoveToVictim(gameMap, pathFinder, victim);
            }
        } else {
            if (canMakeRandomStep(gameMap)) {
                makeRandomStep(gameMap);
            }
        }
        boolean hungry = decraseHunger();
        if (hungry) {
            starving();
        }
    }
}
