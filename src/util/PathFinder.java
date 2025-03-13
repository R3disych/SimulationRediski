package util;

import elements.Locatable;
import gameMap.GameMap;

import java.util.*;

public class PathFinder {
    private Map<Coordinates, Locatable> gameMapEntity;
    private final int RADIUS = 15;

    public PathFinder(GameMap map) {
        this.gameMapEntity = map.getGameMapLocatable();
    }

    public List<Locatable> getEntitiesNear(Coordinates coordinates) {
        return getEntitiesNear(coordinates, RADIUS);
    }

    public List<Locatable> getEntitiesNear(Coordinates coordinates, int radius) {
        List<Locatable> entities = new ArrayList<>();
        for(Locatable entity : gameMapEntity.values()) {
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
        int x = current.getRow();
        int y = current.getColumn();

        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};
        for(int i = 0; i < 4; i++) {
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
        return Math.abs(target.getRow() - start.getRow()) + Math.abs(target.getColumn() - start.getColumn());
    }

    private static class Node {
        private Node parent;
        private final Coordinates coordinates;

        private int gScore;
        private int hScore;
        private int fScore;


        private Node(Coordinates coordinates) {
            this.coordinates = coordinates;
            this.gScore = 0;
            this.hScore = 0;
            this.fScore = 0;
        }

        private Coordinates getCoordinates() {
            return coordinates;
        }

        private Node getParent() {
            return parent;
        }

        private void setParent(Node parent) {
            this.parent = parent;
        }

        private int getGScore() {
            return gScore;
        }

        private void setGScore(int gScore) {
            this.gScore = gScore;
            setFScore();
        }

        private void setHScore(int hScore) {
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
}
