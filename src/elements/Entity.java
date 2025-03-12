package elements;

import java.util.List;
import java.util.Objects;
import util.Coordinates;
import util.Randomizer;

public abstract class Entity implements Identifiable, Locatable {
    private final int id;
    private final String display;
    private Coordinates coordinates;
    private List<Coordinates> path;

    public Entity(String display) {
        this.id = Randomizer.setRandomId();
        this.display = display;
        this.path = null;
    }

    public Entity(String display, Coordinates coordinates) {
        this.id = Randomizer.setRandomId();
        this.display = display;
        this.coordinates = coordinates;
        this.path = null;
    }

    @Override
    public int getId() {
        return id;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isObstacle() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass() == obj.getClass()) {
            Entity that = (Entity) obj;
            return this.id == that.id;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return this.display;
    }
}