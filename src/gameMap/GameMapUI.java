package gameMap;

import elements.Entity;
import util.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class GameMapUI extends JPanel {
    private GameMap gameMap;
    private Map<Coordinates, Entity> gameMapEntity;

    public GameMapUI(GameMap gameMap) {
        this.gameMap = gameMap;
        this.gameMapEntity = gameMap.getGameMapEntity();
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 0; i <= gameMap.getWidth(); i++) {
            for(int j = 0; j <= gameMap.getHeight(); j++) {
                Coordinates currentCoords = new Coordinates(j, i);
                if(gameMapEntity.containsKey(currentCoords)) {
                    Entity currentEntity = gameMapEntity.get(currentCoords);
                    g.drawString(currentEntity.toString(), j * 25, i * 25);
                } else {
                    g.drawString(" .", j * 25, i * 25);
                }
            }
        }
    }
}
