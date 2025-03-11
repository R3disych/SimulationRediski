package elements;

public interface Alive extends Locateable {
    int getCurrentHunger();
    int getMaxHunger();
    int getCurrentHp();
    int getMaxHp();

    /**
     * @param damage
     * @return true if damage was lethal, else false
     */
    boolean decreaseHp(int damage);
    boolean decraseHunger();
    void starving();
    int getNutritionalValue();
    boolean isDead();
    default boolean isStarving() {
        return getCurrentHunger() <= 0;
    }
    void eat(int nutritionValue);
}
