import actions.FillGameMap;
import actions.InitSimulation;
import actions.MakeTurn;
import actions.RemoveDead;
import gameMap.GameMap;

import javax.swing.*;

import gameMap.GameMapUI;
import util.PathFinder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Simulation {
    private int turnsCount;

    public void plusTurn() {
        ++this.turnsCount;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameMap gameMap = InitSimulation.initSim();
            PathFinder pathFinder = new PathFinder(gameMap);
            FillGameMap fillMap = new FillGameMap(gameMap);

            GameMapUI gameMapUI = new GameMapUI(gameMap);

            JFrame frame = new JFrame("Simulation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(gameMapUI);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            Timer timer = new Timer(1000, new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   synchronized (gameMap) {
                       MakeTurn.makeTurn(gameMap, pathFinder);
                       RemoveDead.removeDead(gameMap);
                       fillMap.fillGameMap();
                   }
                   gameMapUI.repaint();
               }
            });
            timer.start();
        });
        /*
        GameMap map = InitSimulation.initSim();
        PathFinder pathFinder = new PathFinder(map);
        FillGameMap fillMap = new FillGameMap(map);

        GameMapUI mapUI = new GameMapUI(map);
        JFrame frame = new JFrame("Game Map");
        frame.add(mapUI);
        frame.setSize(800, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

         while(true) {
                gameMapUI.repaint();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                MakeTurn.makeTurn(gameMap, pathFinder);
                RemoveDead.removeDead(gameMap);
                fillMap.fillGameMap();
            }

         */


    }
}
