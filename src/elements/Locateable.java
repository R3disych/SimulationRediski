package elements;

import util.Coordinates;

public interface Locateable extends Identifiable {
    Coordinates getCoordinates();
    void setCoordinates(Coordinates coordinates);
    boolean isObstacle();
}
