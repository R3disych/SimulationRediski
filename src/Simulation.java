import actions.FillGameMap;
import actions.InitSimulation;
import actions.MakeTurn;
import actions.RemoveDead;
import gameMap.GameMap;

import javax.swing.*;

import gameMap.GameMapUI;
import util.PathFinder;
import util.Randomizer;

import java.util.Random;
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
            PathFinder pathFinder = new PathFinder(gameMap);
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
            Timer timer = new Timer(1000, e -> {
                synchronized (gameMap) {
                    MakeTurn.makeTurn(gameMap, pathFinder);
                    RemoveDead.removeDead(gameMap);//переставить
                    randomizer.foo();
                    fillMap.fillGameMap(i.get());
                    randomizer.foo();
                    i.getAndIncrement();
                    System.out.println(i.get());
                }
                gameMapUI.repaint();
                //наверное сюда removeDead()
            });
            timer.start();
        });
    }
}
