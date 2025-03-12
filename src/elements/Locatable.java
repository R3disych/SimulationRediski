package elements;

import util.Coordinates;

public interface Locatable extends Identifiable {
    Coordinates getCoordinates();
    void setCoordinates(Coordinates coordinates);
    boolean isObstacle();
}
