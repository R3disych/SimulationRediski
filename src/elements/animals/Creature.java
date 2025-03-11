package elements.animals;

import elements.Alive;
import elements.Entity;
import elements.Moveable;
import gameMap.GameMap;

import java.util.Comparator;
import java.util.Optional;
import java.util.Random;

import util.Coordinates;
import util.PathFinder;

public abstract class Creature extends Entity implements Moveable, Alive {
    private int Hp;
    private int hunger;
    private int stamina;
    private int initiative;

    private Random rand = new Random();

    public Creature(String display) {
        super(display);
        this.Hp = getMaxHp();
        this.hunger = getMaxHunger();
        restoreStamina();
    }

    @Override
    public void reinitInitiative() {
        this.initiative = rand.nextInt(100);
    }

    @Override
    public int getInitiative() {
        return initiative;
    }

    @Override
    public int getCurrentHp() {
        return Hp;
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
    public void step(GameMap gameMap, Coordinates target) {
        Moveable.super.step(gameMap, target);
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
    }

    @Override
    public boolean decraseHunger() {
        this.hunger -= 15;
        return hunger <= 0;
    }

    @Override
    public boolean decreaseHp(int damage) {
        this.Hp -= damage;
        return Hp <= 0;
    }

    @Override
    public boolean isDead() {
        return getCurrentHp() <= 0;
    }

    @Override
    public void starving() {
        this.Hp -= 15;
    }

    public abstract Class<? extends Alive> getVictimType();

    public Optional<Alive> findNearestVictim(PathFinder pathFinder) {
        return pathFinder.getEntitiesNear(this.getCoordinates(), 20)
                .stream()
                .filter(entity -> entity.getClass().equals(getVictimType()))
                .map(locateable -> (Alive) locateable)
                .min(Comparator.comparingDouble(victim ->
                        this.getCoordinates().distanceTo(victim.getCoordinates())));
    }

    @Override
    public void move(GameMap gameMap, PathFinder pathFinder) {
        Optional<Alive> nearestVictim = findNearestVictim(pathFinder);
        if (nearestVictim.isPresent()) {
            Alive victim = nearestVictim.get();
            if (this.getCoordinates().distanceTo(victim.getCoordinates()) <= 1 && !victim.isDead()) {
                this.eat(victim.getNutritionalValue());
                boolean killed = victim.decreaseHp(getDamage());
                if (killed) {
                    step(gameMap, victim.getCoordinates());
                }
                spendStamina();
                System.out.println("Я скушал кого-то");
            } else {
                makeMoveToVictim(gameMap, pathFinder, victim);
                spendStamina();
                System.out.println("Я походил к цели");
            }
        } else {
            makeRandomStep(gameMap);
            spendStamina();
            System.out.println("Я случайно походил");
        }
        boolean hungry = decraseHunger();
        if (hungry) {
            starving();
            System.out.println("Я получил урон от голодания");
        }
    }
}
