import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        List<char[]> temp = new ArrayList<>();
        while(scanner.hasNext()) {
            temp.add(scanner.nextLine().toCharArray());
        }
        char[][] maze = temp.toArray(char[][]::new);
        Map<Coordinate, List<Direction>> paths = new HashMap<>();
        for(Coordinate coord : findAllEmpty(maze)) {
            paths.put(coord, new ArrayList<>());
        }
        int[] start = findCharacter(maze, 'S');
        paths.put(new Coordinate(start[0], start[1], Direction.EAST), List.of(Direction.EAST));
        Set<Coordinate> unvisited = new HashSet<>(paths.keySet());
        while(!unvisited.isEmpty()) {
            Coordinate smallest = findSmallest(unvisited, paths);
            if(smallest.x == -1 || getCost(paths.get(smallest)) == Integer.MAX_VALUE) {
                break;
            } else {
                for(Direction neigh : List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)) {
                    Coordinate newPos = new Coordinate(smallest.x + neigh.move[0], smallest.y + neigh.move[1], neigh);
                    if(paths.containsKey(newPos)) {
                        if(getCost(paths.get(newPos)) > getCost(paths.get(smallest), neigh)) {
                            paths.put(newPos, new ArrayList<>(paths.get(smallest)));
                            paths.get(newPos).add(neigh);
                        }
                    }
                }
                unvisited.remove(smallest);
            }
        }
        int first = Integer.MAX_VALUE;
        int[] end = findCharacter(maze, 'E');
        Coordinate bestPath = new Coordinate(-1, -1, Direction.EAST);
        for(Coordinate key : paths.keySet()) {
            if(getCost(paths.get(key)) < first && key.x == end[0] && key.y == end[1]) {
                first = getCost(paths.get(key));
                bestPath = key;
            }
        }
        System.out.println("First: " + first);
        Set<Tuple<Integer, Integer>> second = new HashSet<>();
        findAllPaths(second, paths, start, bestPath);
        System.out.println("Second : " + second.size());
    }

    public static void findAllPaths(Set<Tuple<Integer, Integer>> unique, Map<Coordinate, List<Direction>> paths, int[] start, Coordinate current) {
        unique.add(new Tuple<Integer, Integer>(current.x, current.y));
        if(current.x == start[0] && current.y == start[1]) {
            return;
        }
        for(Direction dir : List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)) {
            Coordinate prev = new Coordinate(current.x - current.dir.move[0], current.y - current.dir.move[1], dir);
            if(paths.containsKey(prev) && getCost(paths.get(current)) == getCost(paths.get(prev), current.dir)) {
                findAllPaths(unique, paths, start, prev);
            }
        }
    }

    public static int getCost(List<Direction> path) {
        if(path.size() == 0) {
            return Integer.MAX_VALUE;
        }
        Direction current = path.get(0);
        int total = 0;
        for(int i = 1; i < path.size(); i++) {
            total++;
            if(path.get(i) != current) {
                total += 1000;
                if(path.get(i) == current.getOpposite()) {
                    total += 1000;
                }
                current = path.get(i);
            }
        }
        return total;
    }
    public static int getCost(List<Direction> path, Direction latest) {
        Direction current = path.get(0);
        int total = 0;
        for(int i = 1; i < path.size(); i++) {
            total++;
            if(path.get(i) != current) {
                total += 1000;
                if(path.get(i) == current.getOpposite()) {
                    total += 1000;
                }
                current = path.get(i);
            }
        }
        total++;
        if(latest != current) {
            total += 1000;
        }
        return total;
    }
    public static Coordinate findSmallest(Set<Coordinate> list, Map<Coordinate, List<Direction>> paths) {
        int smallestCost = Integer.MAX_VALUE;
        Coordinate smallest = new Coordinate(-1, -1, Direction.EAST);
        for(Coordinate coord : list) {
            if(getCost(paths.get(coord)) < smallestCost) {
                smallestCost = getCost(paths.get(coord));
                smallest = coord;
            }
        }
        return smallest;
    }

    public static List<Coordinate> findAllEmpty(char[][] grid) {
        List<Coordinate> coords = new ArrayList<>();
        for(int y = 0; y < grid.length; y++) {
            for(int x = 0; x < grid[0].length; x++) {
                if(grid[y][x] != '#') {
                    for(Direction dir : List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)) {
                        if(grid[y + dir.move[1]][x + dir.move[0]] != '#') {
//                            System.out.println(x + ", " + y + " moving from " + (x + dir.move[0]) + ", " + (y + dir.move[1]) + ": " + dir.arrow);
                            coords.add(new Coordinate(x, y, dir.getOpposite()));
                        }
                    }
                }
            }
        }
        return coords;
    }

    public static int[] findCharacter(char[][] grid, char c) {
        for(int y = 0; y < grid.length; y++) {
            for(int x = 0; x < grid[0].length; x++) {
                if(grid[y][x] == c) {
                    return new int[]{x, y};
                }
            }
        }
        return new int[]{-1, -1};
    }

    public static void printGrid(char[][] grid) {
        String output = "";
        for(char[] line : grid) {
            for(char c : line) {
                output += c;
            }
            output += "\n";
        }
        System.out.println(output);
    }
}