package elements;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import util.Coordinates;
import util.Randomizer;

public class GameMap extends JPanel {
    private int width = 30;
    private int height = 30;
    private Map<Coordinates, Entity> gameMap = new HashMap();

    public GameMap() {
    }

    public Map<Coordinates, Entity> getGameMap() {
        return this.gameMap;
    }

    public void addEntity(Entity entity) {
        this.gameMap.put(entity.getCoordinates(), entity);
    }

    public void removeEntity(Entity entity) {
        this.gameMap.remove(entity.getCoordinates());
        Randomizer.removeCoordinates(entity.getCoordinates());
    }

    public void moveEntity(Entity entity, Coordinates newCoordinates) {
        Coordinates oldCoordinates = entity.getCoordinates();
        if (this.gameMap.containsKey(oldCoordinates) && ((Entity)this.gameMap.get(oldCoordinates)).equals(entity)) {
            this.gameMap.remove(oldCoordinates);
            entity.setCoordinates(newCoordinates);
            this.gameMap.put(entity.getCoordinates(), entity);
        } else {
            System.out.println("Ошибка: Entity не найдена на карте в ожидаемой позиции");
        }

    }

    public List<Entity> getEntitiesNear(Coordinates coordinates) {
        return this.getEntitiesNear(coordinates, 15);
    }

    public List<Entity> getEntitiesNear(Coordinates coordinates, int radius) {
        List<Entity> nearbyEntities = new ArrayList();
        Iterator var4 = (new HashMap(this.gameMap)).entrySet().iterator();

        while(var4.hasNext()) {
            Map.Entry<Coordinates, Entity> entry = (Map.Entry)var4.next();
            Coordinates currentCoords = (Coordinates)entry.getKey();
            Entity entity = (Entity)entry.getValue();
            if (this.isValidCoordinate(currentCoords) && coordinates.distanceTo(currentCoords) <= (double)radius) {
                nearbyEntities.add(entity);
            }
        }

        return nearbyEntities;
    }

    public boolean isValidCoordinate(Coordinates coordinates) {
        int x = coordinates.getWidth();
        int y = coordinates.getHeight();
        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i = 0; i < this.height; ++i) {
            for(int j = 0; j < this.width; ++j) {
                Coordinates currentCoords = new Coordinates(i, j);
                if (this.gameMap.containsKey(currentCoords)) {
                    Entity currentEntity = (Entity)this.gameMap.get(currentCoords);
                    g.drawString(currentEntity.toString(), j * 20, i * 20);
                } else {
                    g.drawString(".", j * 20, i * 20);
                }
            }
        }

    }
}
