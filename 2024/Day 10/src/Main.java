import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        List<List<Integer>> grid = new ArrayList<>();
        List<Coordinate> starts = new ArrayList<>();
        int height = 0;
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            List<Integer> row = new ArrayList<>();
            //Create 2d array of the grid input, noting where the starting positions are
            for(int i = 0; i < line.length(); i++) {
                if(line.charAt(i) == '0') {
                    starts.add(new Coordinate(i, height));
                }
                row.add(Integer.parseInt(Character.toString(line.charAt(i))));
            }
            grid.add(row);
            height++;
        }
        List<Coordinate> first = new ArrayList<>();
        List<Coordinate> second = new ArrayList<>();
        //For each start, walk from them and return all successful walks
        for(Coordinate start : starts) {
            first.addAll(uniqueWalk(grid, start));
            second.addAll(totalWalk(grid, start));
        }
        System.out.println("First: " + first.size());
        System.out.println("Second: " + second.size());
    }

    //Returns all possible walks for a given starting position
    public static List<Coordinate> totalWalk(List<List<Integer>> grid, Coordinate pos) {
        int current = grid.get(pos.y).get(pos.x);
        //If at end of list, return a list of the position of the end of the hike
        if(current == 9) {
            return List.of(pos);
        } else {
            //Else, check in all four directions if there is a number up from the current one
            int height = grid.size();
            int width = grid.get(0).size();
            List<Coordinate> output = new ArrayList<>();
            //Check above
            if(pos.y - 1 >= 0 && grid.get(pos.y-1).get(pos.x) == current + 1) {
                output.addAll(totalWalk(grid, new Coordinate(pos.x, pos.y-1)));
            }
            //Check below
            if(pos.y + 1 < height && grid.get(pos.y+1).get(pos.x) == current + 1) {
                output.addAll(totalWalk(grid, new Coordinate(pos.x, pos.y+1)));
            }
            //Check to the right
            if(pos.x + 1 < width && grid.get(pos.y).get(pos.x+1) == current + 1) {
                output.addAll(totalWalk(grid, new Coordinate(pos.x+1, pos.y)));
            }
            //Check to the left
            if(pos.x - 1 >= 0 && grid.get(pos.y).get(pos.x-1) == current + 1) {
                output.addAll(totalWalk(grid, new Coordinate(pos.x-1, pos.y)));
            }
            return output;
        }
    }

    //Only returns unique end coordinates for each specific walk
    public static Set<Coordinate> uniqueWalk(List<List<Integer>> grid, Coordinate pos) {
        int current = grid.get(pos.y).get(pos.x);
        //If at end of list, return a list of the position of the end of the hike
        if(current == 9) {
            return Set.of(pos);
        } else {
            //Else, check in all four directions if there is a number up from the current one
            int height = grid.size();
            int width = grid.get(0).size();
            Set<Coordinate> output = new HashSet<>();
            //Check above
            if(pos.y - 1 >= 0 && grid.get(pos.y-1).get(pos.x) == current + 1) {
                output.addAll(uniqueWalk(grid, new Coordinate(pos.x, pos.y-1)));
            }
            //Check below
            if(pos.y + 1 < height && grid.get(pos.y+1).get(pos.x) == current + 1) {
                output.addAll(uniqueWalk(grid, new Coordinate(pos.x, pos.y+1)));
            }
            //Check to the right
            if(pos.x + 1 < width && grid.get(pos.y).get(pos.x+1) == current + 1) {
                output.addAll(uniqueWalk(grid, new Coordinate(pos.x+1, pos.y)));
            }
            //Check to the left
            if(pos.x - 1 >= 0 && grid.get(pos.y).get(pos.x-1) == current + 1) {
                output.addAll(uniqueWalk(grid, new Coordinate(pos.x-1, pos.y)));
            }
            return output;
        }
    }
}