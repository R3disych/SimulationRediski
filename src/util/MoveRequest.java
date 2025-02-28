package util;

import elements.Entity;

public class MoveRequest {
    private final Entity entity;
    private final Coordinates newCoordinates;

    public MoveRequest(Entity entity, Coordinates newCoordinates) {
        this.entity = entity;
        this.newCoordinates = newCoordinates;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public Coordinates getNewCoordinates() {
        return this.newCoordinates;
    }
}
