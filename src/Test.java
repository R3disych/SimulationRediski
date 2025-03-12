public class Test {
    public static void main(String[] args) {
        setMap(40, 22);
    }

    public static void setMap(int row, int column) {
        int numOfCells = row * column;
        int numOfFields = numOfCells / 64;
        int numOfHerbivore = numOfFields * 2;
        System.out.println(numOfHerbivore);
        int numOfPredators = numOfFields * 1;
        System.out.println(numOfPredators);
        int numOfGrass = numOfFields * 2;
        System.out.println(numOfGrass);
        int numOfRocks = numOfFields * 2;
        System.out.println(numOfRocks);
        int numOfTrees = numOfFields * 3;
        System.out.println(numOfTrees);
    }
}
