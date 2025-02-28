import actions.InitSimulation;
import actions.MakeTurn;
import gameMap.GameMap;
import gameMap.GameMapUI;
import util.PathFinder;

import javax.swing.*;

public class Test {
    public static void main(String[] args) {
        GameMap map = InitSimulation.initSim();
        PathFinder pathFinder = new PathFinder(map);

        GameMapUI mapUI = new GameMapUI(map);
        JFrame frame = new JFrame("Game Map");
        frame.add(mapUI);
        frame.setSize(650, 650);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        while(true) {
            mapUI.repaint();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            MakeTurn.makeTurn(map, pathFinder);
        }
    }
}
