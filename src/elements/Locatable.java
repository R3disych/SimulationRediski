package elements;

import util.Coordinates;

public interface Locatable extends Identifable {
    Coordinates getCoordinates();
    void setCoordinates(Coordinates coordinates);
    boolean isObstacle();
}
