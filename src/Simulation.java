import gameMap.GameMap;
import actions.Action;
import javax.swing.*;
import gameMap.GameMapUI;
import util.Randomizer;

import java.util.concurrent.atomic.AtomicInteger;

public class Simulation {
    public int turnsCount;

    /*
    public void plusTurn() {
        ++this.turnsCount;
    }

     */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameMap gameMap = GameMap.init(40, 22);
            Randomizer randomizer = new Randomizer(gameMap);
            Action action = new Action(gameMap, randomizer);

            GameMapUI gameMapUI = new GameMapUI(gameMap);

            JFrame frame = new JFrame("Simulation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(gameMapUI);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            randomizer.reinitializeFreeCells();
            action.initSim();

            AtomicInteger i = new AtomicInteger();
            Timer timer = new Timer(1000, e -> {
                synchronized (gameMap) {
                    action.clearMap();
                    randomizer.reinitializeFreeCells();
                    action.fillGameMap(i.getAndIncrement());
                    randomizer.reinitializeFreeCells();
                    action.makeTurn();
                    gameMapUI.repaint();
                    i.getAndIncrement();
                    System.out.println(i.get());
                }
            });
            timer.start();
        });
    }
}
