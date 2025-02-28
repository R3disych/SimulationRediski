package util;

import java.util.Objects;

public class Coordinates {
    private int width;
    private int height;

    public Coordinates(int x, int y) {
        this.width = x;
        this.height = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setCoordinates(int x, int y) {
        this.width = x;
        this.height = y;
    }

    public double distanceTo(Coordinates other) {
        return Math.abs(this.getWidth() - other.getWidth()) + Math.abs(this.getHeight() - other.getHeight());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(obj != null && this.getClass() == obj.getClass()) {
            Coordinates c = (Coordinates)obj;
            return this.width == c.width && this.height == c.height;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[]{this.width, this.height});
    }
}
