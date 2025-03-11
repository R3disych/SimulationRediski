package gameMap;

import elements.Alive;
import elements.Locateable;
import elements.animals.Herbivore;
import elements.animals.Predator;
import elements.staticObjects.Grass;
import elements.staticObjects.Rock;
import elements.staticObjects.Tree;
import util.Coordinates;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class GameMapUI extends JPanel {
    private GameMap gameMap;
    private Map<Coordinates, Locateable> gameMapEntity;
    private HashMap<String, BufferedImage> images = new HashMap<>();
    private final int cellSize = 45;

    public GameMapUI(GameMap gameMap) {
        this.gameMap = gameMap;
        this.gameMapEntity = gameMap.getGameMapEntity();
        loadImages();
        setPreferredSize(new Dimension(1920, 1080));
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

        for(int x = 0; x < GameMap.getWidth(); x++) {
            for(int y = 0; y < GameMap.getHeight(); y++) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }

        for(Coordinates coordinates : gameMapEntity.keySet()) {
            Locateable entity = gameMapEntity.get(coordinates);
            BufferedImage image = null;
            if(entity instanceof Grass) {
                Alive aliveEntity = (Alive) entity;
                if (aliveEntity.isDead()) {
                    image = images.get("bittenGrass");
                } else {
                    image = images.get("grass");
                }
            } else if (entity instanceof Herbivore) {
                Alive aliveEntity = (Alive) entity;
                if (aliveEntity.isDead()) {
                    image = images.get("deadHerbivore");
                } else {
                    image = images.get("herbivore");
                }
            } else if (entity instanceof Predator) {
                Alive aliveEntity = (Alive) entity;
                if (aliveEntity.isDead()) {
                    image = images.get("deadPredator");
                } else {
                    image = images.get("predator");
                }
            } else if (entity instanceof Tree) {
                image = images.get("tree");
            } else if (entity instanceof Rock) {
                image = images.get("rock");
            }

            if(image != null) {
                g.drawImage(image, coordinates.getWidth() * cellSize, coordinates.getHeight() * cellSize, cellSize, cellSize, this);
                g.drawString(String.valueOf(entity.getId()), coordinates.getWidth() * cellSize, coordinates.getHeight() * cellSize);
            } else {
                g.setColor(Color.RED);
                g.fillOval(coordinates.getWidth() * cellSize, coordinates.getHeight() * cellSize, cellSize, cellSize);
            }
        }
    }
}
