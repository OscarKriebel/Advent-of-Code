import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        int height = 71;
        int width = 71;
        int bytes = 1024;
        List<Coordinate> corruption = new ArrayList<>();
        while(scanner.hasNext()) {
            int[] line = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
            corruption.add(new Coordinate(line[0], line[1]));
        }
        List<Coordinate> first = solveMaze(corruption, bytes, height, width);
        System.out.println("First: " + getCost(first));
        List<Coordinate> second = first;
//        printGrid(createMaze(corruption, height, width, bytes), first);
        while(getCost(second) != Integer.MAX_VALUE) {
            bytes++;
            if(second.contains(corruption.get(bytes-1))) {
                second = solveMaze(corruption, bytes, height, width);
//                printGrid(createMaze(corruption, height, width, bytes), second);
            }
        }
        System.out.println("Second: " + corruption.get(bytes-1));
    }

    public static List<Coordinate> solveMaze(List<Coordinate> corruption, int bytes, int height, int width) {
        char[][] maze = createMaze(corruption, height, width, bytes);
//        printGrid(maze);
        Map<Coordinate, List<Coordinate>> costs = new HashMap<>();
        Set<Coordinate> unvisited = new HashSet<>();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if(maze[y][x] != '#') {
                    Coordinate position = new Coordinate(x,y);
                    costs.put(position, new ArrayList<>());
                    unvisited.add(position);
                }
            }
        }
        costs.put(new Coordinate(0,0), List.of(new Coordinate(0,0)));
        Coordinate end = new Coordinate(width-1, height-1);
        Coordinate current = findSmallest(costs, unvisited);
        while((current.x != -1 || current.y != -1) && (current.x != end.x || current.y != end.y)) {
            for(Coordinate neigh : List.of(new Coordinate(0,-1), new Coordinate(0,1), new Coordinate(-1, 0), new Coordinate(1, 0))) {
                Coordinate neighbor = current.add(neigh);
                if(unvisited.contains(neighbor) && getCost(costs.get(current)) + 1 < getCost(costs.get(neighbor))) {
                    List<Coordinate> path = new ArrayList<>(costs.get(current));
                    path.add(neighbor);
                    costs.put(neighbor, path);
                }
            }
            unvisited.remove(current);
            current = findSmallest(costs, unvisited);
        }
//        for(Coordinate coord : costs.get(end)) {
//            maze[coord.y][coord.x] = 'O';
//        }
//        printGrid(maze);
        return costs.get(end);
    }

    public static int getCost(List<Coordinate> path) {
        if(path.size() == 0) {
            return Integer.MAX_VALUE;
        } else {
            return path.size() - 1;
        }
    }

    public static Coordinate findSmallest(Map<Coordinate, List<Coordinate>> costs, Set<Coordinate> unvisited) {
        int smallest = Integer.MAX_VALUE;
        Coordinate small = new Coordinate(-1, -1);
        for(Coordinate coord : costs.keySet()) {
            if(unvisited.contains(coord) && getCost(costs.get(coord)) < smallest) {
                smallest = getCost(costs.get(coord));
                small = coord;
            }
        }
        return small;
    }

    public static char[][] createMaze(List<Coordinate> corruption, int height, int width, int bytes) {
        char[][] maze = new char[height][width];
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                maze[y][x] = '.';
            }
        }
        for(int i = 0; i < bytes; i++) {
            Coordinate current = corruption.get(i);
            maze[current.y][current.x] = '#';
        }
        return maze;
    }

    public static void printGrid(char[][] grid) {
        String output = "";
        for(char[] row : grid) {
            for(char c : row) {
                output += c;
            }
            output += "\n";
        }
        System.out.println(output);
    }

    public static void printGrid(char[][] grid, List<Coordinate> path) {
        String output = "";
        for(int y = 0; y < grid.length; y++) {
            for(int x = 0; x < grid[0].length; x++) {
                if(path.contains(new Coordinate(x, y))) {
                    output += "O";
                } else {
                    output += grid[y][x];
                }
            }
            output += "\n";
        }
        System.out.println(output);
    }
}