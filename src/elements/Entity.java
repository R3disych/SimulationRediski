package elements;

import java.util.List;
import java.util.Objects;
import util.Coordinates;
import util.Randomizer;

public abstract class Entity {
    private final int id;
    private final String display;
    private Coordinates coordinates;
    private List<Coordinates> path;

    public Entity(String display) {
        this.id = Randomizer.setRandomId();
        this.display = display;
        this.coordinates = Randomizer.setRandomCoordinates();
        this.path = null;
    }

    public int getId() {
        return id;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String printCoordinates() {
        return "(" + coordinates.getHeight() + "," + coordinates.getWidth() + ")";
    }

    public boolean isObstacle() {
        return false;
    }

    public List<Coordinates> getPath() {
        return path;
    }

    public void setPath(List<Coordinates> path) {
        this.path = path;
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