package util;

import java.util.Objects;

public class Coordinates {
    private int row;
    private int column;

    public Coordinates(int x, int y) {
        this.row = x;
        this.column = y;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public double distanceTo(Coordinates other) {
        return Math.abs(this.getRow() - other.getRow()) + Math.abs(this.getColumn() - other.getColumn());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(obj != null && this.getClass() == obj.getClass()) {
            Coordinates c = (Coordinates)obj;
            return this.row == c.row && this.column == c.column;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[]{this.row, this.column});
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
