package util;

public class Node {
    private Coordinates coordinates;
    private double fScore;

    public Node(Coordinates coordinates, double fScore) {
        this.coordinates = coordinates;
        this.fScore = fScore;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public double getFScore() {
        return this.fScore;
    }
}
