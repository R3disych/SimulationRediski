import actions.InitSimulation;
import actions.MakeTurn;
import elements.GameMap;
import javax.swing.JFrame;
import util.PathFinder;

public class Simulation {
    private int turnsCount;

    public Simulation() {
    }

    public void plusTurn() {
        ++this.turnsCount;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game Map");
        GameMap map = InitSimulation.initSim();
        PathFinder pathFinder = new PathFinder(map);
        frame.add(map);
        frame.setSize(650, 650);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3);

        while(true) {
            map.repaint();

            try {
                Thread.sleep(500L);
            } catch (InterruptedException var5) {
                InterruptedException e = var5;
                e.printStackTrace();
            }

            MakeTurn.makeTurn(map, pathFinder);
        }
    }
}
