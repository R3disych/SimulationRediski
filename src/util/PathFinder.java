package util;

import elements.Entity;
import elements.GameMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class PathFinder {
    private final GameMap map;

    public PathFinder(GameMap map) {
        this.map = map;
    }

    public List<Coordinates> findPath(Coordinates start, Coordinates target) {
        if (this.map.isValidCoordinate(start) && this.map.isValidCoordinate(target)) {
            PriorityQueue<Node> openSet = new PriorityQueue(Comparator.comparingDouble(Node::getFScore));
            Set<Coordinates> closedSet = new HashSet();
            Map<Coordinates, Coordinates> cameFrom = new HashMap();
            Map<Coordinates, Double> gScore = new HashMap();
            Map<Coordinates, Double> fScore = new HashMap();
            gScore.put(start, 0.0);
            fScore.put(start, this.heuristic(start, target));
            openSet.add(new Node(start, (Double)fScore.get(start)));

            while(!openSet.isEmpty()) {
                Node currentNode = (Node)openSet.poll();
                Coordinates currentCoords = currentNode.getCoordinates();
                if (currentCoords.equals(target)) {
                    return reconstructPath(cameFrom, currentCoords);
                }

                closedSet.add(currentCoords);
                Iterator var10 = this.getNeighbors(currentCoords).iterator();

                while(var10.hasNext()) {
                    Coordinates neighbor = (Coordinates)var10.next();
                    if (!closedSet.contains(neighbor) && this.isWalkable(neighbor)) {
                        double tentativeGScore = (Double)gScore.getOrDefault(currentCoords, Double.MAX_VALUE) + 1.0;
                        if (tentativeGScore < (Double)gScore.getOrDefault(currentCoords, Double.MAX_VALUE)) {
                            cameFrom.put(neighbor, currentCoords);
                            gScore.put(neighbor, tentativeGScore);
                            fScore.put(neighbor, tentativeGScore + this.heuristic(neighbor, target));
                            if (openSet.stream().noneMatch((node) -> {
                                return node.getCoordinates().equals(neighbor);
                            })) {
                                openSet.add(new Node(neighbor, (Double)fScore.get(neighbor)));
                            }
                        }
                    }
                }
            }

            return null;
        } else {
            return null;
        }
    }

    private static List<Coordinates> reconstructPath(Map<Coordinates, Coordinates> cameFrom, Coordinates currentCoords) {
        List<Coordinates> path = new ArrayList();
        path.add(currentCoords);

        while(cameFrom.containsKey(currentCoords)) {
            currentCoords = (Coordinates)cameFrom.get(currentCoords);
            path.add(0, currentCoords);
        }

        return path;
    }

    private boolean isWalkable(Coordinates coordinates) {
        if (!this.map.getGameMap().containsKey(coordinates)) {
            return true;
        } else {
            Entity entity = (Entity)this.map.getGameMap().get(coordinates);
            return !entity.isObstacle();
        }
    }

    private List<Coordinates> getNeighbors(Coordinates coordinates) {
        List<Coordinates> neighbors = new ArrayList();
        int x = coordinates.getWidth();
        int y = coordinates.getHeight();
        this.addNeighbor(neighbors, x - 1, y);
        this.addNeighbor(neighbors, x + 1, y);
        this.addNeighbor(neighbors, x, y - 1);
        this.addNeighbor(neighbors, x, y + 1);
        return neighbors;
    }

    private void addNeighbor(List<Coordinates> neighbors, int x, int y) {
        Coordinates neighbor = new Coordinates(x, y);
        if (this.map.isValidCoordinate(neighbor)) {
            neighbors.add(neighbor);
        }

    }

    private double heuristic(Coordinates a, Coordinates b) {
        return (double)(Math.abs(a.getWidth() - b.getWidth()) + Math.abs(a.getHeight() - b.getHeight()));
    }
}
