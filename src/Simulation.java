import actions.FillGameMap;
import actions.InitSimulation;
import actions.MakeTurn;
import actions.ClearMap;
import gameMap.GameMap;

import javax.swing.*;

import gameMap.GameMapUI;
import util.PathFinder;
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
            GameMap gameMap = new GameMap();
            Randomizer randomizer = new Randomizer(gameMap);
            InitSimulation initSimulation = new InitSimulation();
            FillGameMap fillMap = new FillGameMap(gameMap, randomizer);

            GameMapUI gameMapUI = new GameMapUI(gameMap);

            JFrame frame = new JFrame("Simulation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(gameMapUI);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            randomizer.foo();
            initSimulation.initSim(gameMap, randomizer);

            AtomicInteger i = new AtomicInteger();
            Timer timer = new Timer(10, e -> {
                synchronized (gameMap) {
                    ClearMap.clearMap(gameMap);
                    randomizer.foo();
                    fillMap.fillGameMap(i.get());
                    gameMap.reinitTurns();
                    randomizer.foo();
                    MakeTurn.makeTurn(gameMap, gameMapUI);
                    System.out.println(gameMap);
                    i.getAndIncrement();
                    System.out.println(i.get());
                }
            });
            timer.start();
        });
    }
}
