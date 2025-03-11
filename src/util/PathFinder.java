package util;

import elements.Entity;
import elements.Locateable;
import gameMap.GameMap;

import java.util.*;

public class PathFinder {
    private Map<Coordinates, Locateable> gameMapEntity;

    public PathFinder(GameMap map) {
        this.gameMapEntity = map.getGameMapEntity();
    }

    public List<? extends Locateable> getEntitiesNear(Coordinates coordinates) {
        return getEntitiesNear(coordinates, 15);
    }

    public List<? extends Locateable> getEntitiesNear(Coordinates coordinates, int radius) {
        List<Locateable> entities = new ArrayList<>();
        for(Locateable entity : gameMapEntity.values()) {
            if(coordinates.distanceTo(entity.getCoordinates()) <= radius) {
                entities.add(entity);
            }
        }
        return entities;
    }

    public List<Coordinates> findPath(Coordinates start, Coordinates target) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getFScore));
        HashSet<Coordinates> closedSet = new HashSet<>();

        Node startNode = new Node(start);
        startNode.setGScore(0);
        startNode.setHScore(heuristicFunction(start, target));
        startNode.setFScore();
        openSet.add(startNode);

        while(!openSet.isEmpty()) {
            Node currentNode = openSet.poll();
            Coordinates currentCoordinates = currentNode.getCoordinates();

            if(currentCoordinates.equals(target)) {
                return reconstructPath(currentNode);
            }

            closedSet.add(currentCoordinates);

            List<Coordinates> neighbors = getNeighbors(currentCoordinates, target);
            for(Coordinates neighborCoords : neighbors) {
                if(closedSet.contains(neighborCoords)) {
                    continue;
                }
                int tempGScore = currentNode.getGScore() + 1;

                Node neighbor = null;
                for(Node node : openSet) {
                    if(node.getCoordinates().equals(neighborCoords)) {
                        neighbor = node;
                        break;
                    }
                }

                if(neighbor == null || tempGScore < neighbor.getGScore()) {
                    if(neighbor == null) {
                        neighbor = new Node(neighborCoords);
                    }

                    neighbor.setParent(currentNode);
                    neighbor.setGScore(tempGScore);
                    neighbor.setHScore(heuristicFunction(neighborCoords, target));
                    neighbor.setFScore();

                    if(!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }
        return null;
    }

    public List<Coordinates> reconstructPath(Node current) {
        List<Coordinates> path = new LinkedList<>();
        while(current != null) {
            path.addFirst(current.getCoordinates());
            current = current.getParent();
        }
        return new ArrayList<>(path);
    }

    public List<Coordinates> getNeighbors(Coordinates current, Coordinates target) {
        List<Coordinates> neighbors = new ArrayList<>();
        int x = current.getWidth();
        int y = current.getHeight();

        int[] dx = {0, 0, -1, 1, -1, -1, 1, 1};
        int[] dy = {-1, 1, 0, 0, -1, 1, -1, 1};
        for(int i = 0; i < 8; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            Coordinates newCoordinates = new Coordinates(newX, newY);

            if(newCoordinates.equals(target)) {
                neighbors.add(newCoordinates);
            }

            if(GameMap.isAccessibleCoordinate(newCoordinates) && GameMap.isWalkable(newCoordinates)) {
                neighbors.add(newCoordinates);
            }
        }

        return neighbors;
    }

    public int heuristicFunction(Coordinates start, Coordinates target) {
        return Math.abs(target.getWidth() - start.getWidth()) + Math.abs(target.getHeight() - start.getHeight());
    }
}
