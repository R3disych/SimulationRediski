package gameMap;

import elements.Animals.Herbivore;
import elements.Animals.Predator;
import elements.Entity;
import elements.staticObjects.Grass;
import elements.staticObjects.Rock;
import elements.staticObjects.Tree;
import util.Coordinates;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class GameMapUI extends JPanel {
    private GameMap gameMap;
    private Map<Coordinates, Entity> gameMapEntity;
    private HashMap<String, BufferedImage> images = new HashMap<>();
    private final int cellSize = 36;

    public GameMapUI(GameMap gameMap) {
        this.gameMap = gameMap;
        this.gameMapEntity = gameMap.getGameMapEntity();
        loadImages();
        setPreferredSize(new Dimension(gameMap.getWidth() * cellSize, gameMap.getHeight() * cellSize));
    }

    private void loadImages() {

        try{
            images.put("grass", loadImage("resources/grass.png"));
            images.put("herbivore", loadImage("resources/herbivore.png"));
            images.put("predator", loadImage("resources/predator.png"));
            images.put("tree", loadImage("resources/tree.png"));
            images.put("rock", loadImage("resources/rock.png"));
        } catch (IOException e) {
            System.out.println("Image loading failed" + e.getMessage());
        }
    }

    private BufferedImage loadImage(String path) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new IOException("Could not load image " + path);
        }
        return ImageIO.read(inputStream);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int x = 1; x <= gameMap.getWidth(); x++) {
            for(int y = 1; y <= gameMap.getHeight(); y++) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }

        for(Coordinates coordinates : gameMapEntity.keySet()) {
            Entity entity = gameMapEntity.get(coordinates);
            BufferedImage image = null;
            if(entity instanceof Grass) {
                image = images.get("grass");
            } else if(entity instanceof Herbivore) {
                image = images.get("herbivore");
            } else if(entity instanceof Predator) {
                image = images.get("predator");
            } else if(entity instanceof Tree) {
                image = images.get("tree");
            } else if(entity instanceof Rock) {
                image = images.get("rock");
            }

            if(image != null) {
                g.drawImage(image, coordinates.getWidth() * cellSize, coordinates.getHeight() * cellSize, cellSize, cellSize, this);
            } else {
                g.setColor(Color.RED);
                g.fillOval(coordinates.getWidth() * cellSize, coordinates.getHeight() * cellSize, cellSize, cellSize);
            }
        }
    }

    /*
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 1; i <= gameMap.getWidth(); i++) {
            for(int j = 1; j <= gameMap.getHeight(); j++) {
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

     */
}
