import gameMap.GameMap;
import actions.Action;
import javax.swing.*;
import gameMap.GameMapUI;
import util.Randomizer;

import java.util.concurrent.atomic.AtomicInteger;

public class Simulation {
    private static final int LOW_SPEED = 2000;
    private static final int MEDIUM_SPEED = 1000;
    private static final int HIGH_SPEED = 100;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameMap gameMap = GameMap.init(40, 22);
            Randomizer randomizer = new Randomizer(gameMap);
            GameMapUI gameMapUI = new GameMapUI(gameMap);
            Action action = new Action(gameMap, randomizer, gameMapUI);

            JFrame frame = new JFrame("Simulation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(gameMapUI);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            randomizer.reinitializeFreeCells();
            action.initSimulation();

            AtomicInteger turn = new AtomicInteger();
            Timer timer = new Timer(HIGH_SPEED, e -> {
                synchronized (gameMap) {
                    action.clearMapOfDead();
                    randomizer.reinitializeFreeCells();
                    action.fillGameMap(turn.get());
                    randomizer.reinitializeFreeCells();
                    action.makeTurn();
                    gameMapUI.repaint();
                    turn.getAndIncrement();
                    System.out.println(turn.get());
                }
            });
            timer.start();
        });
    }
}