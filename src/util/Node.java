package util;

public class Node {
    private Node parent;
    private final Coordinates coordinates;

    private int gScore;
    private int hScore;
    private int fScore;


    public Node(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.gScore = 0;
        this.hScore = 0;
        this.fScore = 0;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getGScore() {
        return gScore;
    }

    public void setGScore(int gScore) {
        this.gScore = gScore;
        setFScore();
    }

    public int getHScore() {
        return hScore;
    }

    public void setHScore(int hScore) {
        this.hScore = hScore;
        setFScore();
    }

    public int getFScore() {
        return fScore;
    }

    public void setFScore() {
        this.fScore = gScore + hScore;
    }
}
