package gameMap;

import elements.Alive;
import elements.Locatable;
import elements.animals.Creature;
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

public class GameMapUI extends JPanel {
    private GameMap gameMap;
    private HashMap<String, BufferedImage> images = new HashMap<>();
    private final int CELL_SIZE = 45;

    public GameMapUI(GameMap gameMap) {
        this.gameMap = gameMap;
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
            images.put("bittenGrass", loadImage("resources/bittenGrass.png"));
            images.put("deadHerbivore", loadImage("resources/deadHerbivore.png"));
            images.put("deadPredator", loadImage("resources/deadPredator.png"));
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

        for(int x = 0; x < GameMap.getRow(); x++) {
            for(int y = 0; y < GameMap.getColumn(); y++) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        for(Coordinates coordinates : gameMap.getGameMapLocatable().keySet()) {
            Locatable entity = gameMap.getGameMapLocatable().get(coordinates);
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
                g.drawImage(image, coordinates.getRow() * CELL_SIZE, coordinates.getColumn() * CELL_SIZE, CELL_SIZE, CELL_SIZE, this);
                g.drawString(String.valueOf(entity.getId()), coordinates.getRow() * CELL_SIZE, coordinates.getColumn() * CELL_SIZE);
            } else {
                g.setColor(Color.RED);
                g.fillOval(coordinates.getRow() * CELL_SIZE, coordinates.getColumn() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }

            if (entity instanceof Creature) {
                Creature creature = (Creature) entity;
                int x = coordinates.getRow() * CELL_SIZE;
                int y = coordinates.getColumn() * CELL_SIZE;

                /*
                g.setColor(Color.BLACK);
                g.drawString("HP: " + creature.getCurrentHp(), x + 5, y + 15);

                g.drawString("Init: " + creature.getInitiative(), x + 5, y + 30);

                g.drawString("Hunger: " + creature.getCurrentHunger(), x + 5, y + 40);
                 */
            }
        }
    }
}
