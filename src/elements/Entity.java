package elements;

import java.util.Objects;
import util.Coordinates;
import util.Randomizer;

public abstract class Entity implements Identifable, Locatable {
    private final int id;
    private Coordinates coordinates;

    public Entity(Coordinates coordinates) {
        this.id = Randomizer.setRandomId();
        this.coordinates = coordinates;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
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
}