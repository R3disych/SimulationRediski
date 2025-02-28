package elements;

import java.util.List;
import java.util.Objects;
import util.Coordinates;
import util.Randomizer;

public abstract class Entity {
    private final int id = Randomizer.setRandomId();
    private final String display;
    private Coordinates coordinates;
    private List<Coordinates> path;

    public Entity(String display) {
        this.display = display;
        this.coordinates = Randomizer.setRandomCoordinates();
        this.path = null;
    }

    public int getId() {
        return this.id;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String printCoordinates() {
        int var10000 = this.coordinates.getHeight();
        return "(" + var10000 + "," + this.coordinates.getWidth() + ")";
    }

    public boolean isObstacle() {
        return false;
    }

    public List<Coordinates> getPath() {
        return this.path;
    }

    public void setPath(List<Coordinates> path) {
        this.path = path;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass() == obj.getClass()) {
            Entity that = (Entity)obj;
            return this.id == that.id;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id});
    }

    public String toString() {
        return this.display;
    }
}
