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
        Coordinate start = findCharacter(maze, 'S');
        Coordinate end = findCharacter(maze, 'E');
        List<Coordinate> solution = Dijkstra(maze, start, end);
        Deque<Coordinate> path = new ArrayDeque<>(solution);
        Map<Coordinate, List<Coordinate>> paths = new HashMap<>();
        while(!path.isEmpty()) {
            Coordinate current = path.poll();
            paths.put(current, new ArrayList<>(path));
        }
        Map<Integer, Integer> first = findDiscounts(paths, solution, 2);
        System.out.println("Done with first");
        Map<Integer, Integer> second = findDiscounts(paths, solution, 20);
//        int i = 0;
//        Map<Integer, Integer> discounts = new HashMap<>();
//        while(!path.isEmpty()) {
//
//        }
//        List<Coordinate> cheats = findCheats(maze);
//        System.out.println(cheats.size() - 1);
//        for(Coordinate cheat : cheats) {
//            System.out.println(i + "/" + cheats.size());
//            i++;
//            maze[cheat.y][cheat.x] = '.';
//            int disc = base - Dijkstra(maze, start, end);
//            if(discounts.containsKey(disc)) {
//                discounts.put(disc, discounts.get(disc) + 1);
//            } else {
//                discounts.put(disc, 1);
//            }
//            maze[cheat.y][cheat.x] = '#';
//        }
        int firstScore = 0;
        for(Integer discount : first.keySet()) {
//            System.out.println("There is " + first.get(discount) + " cheats that save " + discount + " picoseconds.");
            if(discount >= 100) {
                firstScore += first.get(discount);
            }
        }
//        System.out.println("");
        int secondScore = 0;
        for(Integer discount : second.keySet()) {
//            if(discount >= 50) {
//                System.out.println("There is " + second.get(discount) + " cheats that save " + discount + " picoseconds.");
//            }
            if(discount >= 100) {
                secondScore += second.get(discount);
            }
        }
        System.out.println("First: " + firstScore);
        System.out.println("Second: " + secondScore);
    }

    public static Map<Integer, Integer> findDiscounts(Map<Coordinate, List<Coordinate>> paths, List<Coordinate> path, int jump) {
        Map<Integer, Integer> discounts = new HashMap<>();
        int base = paths.get(path.get(0)).size();
        int i = 0;
        for(Coordinate step : path) {
            System.out.println(i + "/" + path.size());
            i++;
            for(Coordinate ahead : paths.get(step)) {
                int distance = step.distance(ahead);
                int newLength = path.indexOf(step) + distance + paths.get(ahead).size();
                if(distance > 1 && distance <= jump && newLength != base) {
                    if(discounts.containsKey(base - newLength)) {
                        discounts.put(base - newLength, discounts.get(base - newLength) + 1);
                    } else {
                        discounts.put(base - newLength, 1);
                    }
                }
            }
        }
        return discounts;
    }

    public static List<Coordinate> findCheats(char[][] grid) {
        List<Coordinate> output = new ArrayList<>();
        for(int y = 1; y < grid.length - 1; y++) {
            for(int x = 1; x < grid[0].length - 1; x++) {
                if(grid[y][x] == '#') {
                    //Vertical or horizontal
                    if((grid[y][x-1] != '#' && grid[y][x+1] != '#') || (grid[y-1][x] != '#' && grid[y+1][x] != '#')) {
                        output.add(new Coordinate(x, y));
                    } else if(grid[y][x-1] != '#' && grid[y-1][x] != '#' && grid[y-1][x-1] == '#') {
                        output.add(new Coordinate(x, y));
                    } else if(grid[y-1][x] != '#' && grid[y][x+1] != '#' && grid[y-1][x+1] == '#') {
                        output.add(new Coordinate(x, y));
                    } else if(grid[y][x+1] != '#' && grid[y+1][x] != '#' && grid[y+1][x+1] == '#') {
                        output.add(new Coordinate(x, y));
                    } else if(grid[y+1][x] != '#' && grid[y][x-1] != '#' && grid[y+1][x-1] == '#') {
                        output.add(new Coordinate(x, y));
                    }
                }
            }
        }
        return output;
    }

    public static List<Coordinate> Dijkstra(char[][] grid, Coordinate start, Coordinate end) {
        //Initialize grid's empty spots with their initial costs
        Map<Coordinate, List<Coordinate>> costs = new HashMap<>();
        costs.put(start, List.of(start));
        costs.put(end, new ArrayList<>());
        Set<Coordinate> unvisited = new HashSet<>();
        for(int y = 0; y < grid.length; y++) {
            for(int x = 0; x < grid[0].length; x++) {
                if(grid[y][x] == '.') {
                    Coordinate position = new Coordinate(x, y);
                    costs.put(position, new ArrayList<>());
                    unvisited.add(position);
                }
            }
        }
        unvisited.add(start);
        unvisited.add(end);
        //Create priority queue based on the coordinate's cost
        Queue<Coordinate> next = new PriorityQueue<>(new Comparator<Coordinate>() {
            @Override
            public int compare(Coordinate o1, Coordinate o2) {
                return getCosts(costs, o1) - getCosts(costs, o2);
            }
        });
        next.add(start);
        Coordinate current = new Coordinate(-1, -1);
        //Dijkstra's algorithm
        while(!next.isEmpty() && !current.equals(end)) {
            current = next.poll();
            unvisited.remove(current);
            for(Coordinate neigh : List.of(new Coordinate(-1, 0), new Coordinate(1, 0), new Coordinate(0,-1),new Coordinate(0, 1))) {
                Coordinate neighbor = current.add(neigh);
                if(unvisited.contains(neighbor) && getCosts(costs, current) + 1 < getCosts(costs, neighbor)) {
                    List<Coordinate> path = new ArrayList<>(costs.get(current));
                    path.add(neighbor);
                    costs.put(neighbor, path);
                    next.add(neighbor);
                }
            }
        }
        return costs.get(end);
    }

    public static int getCosts(Map<Coordinate, List<Coordinate>> costs, Coordinate coord) {
        if(!costs.containsKey(coord) || costs.get(coord).size() == 0) {
            return Integer.MAX_VALUE;
        }
        return costs.get(coord).size() - 1;
    }

    public static Coordinate findCharacter(char[][] grid, char c) {
        for(int y = 0; y < grid.length; y++) {
            for(int x = 0; x < grid[0].length; x++) {
                if(grid[y][x] == c) {
                    return new Coordinate(x, y);
                }
            }
        }

        return new Coordinate(-1, -1);
    }

    public static void printGrid(char[][] grid, List<Coordinate> path) {
        char[][] newGrid = grid.clone();
        for(Coordinate step : path) {
            newGrid[step.y][step.x] = 'O';
        }
        printGrid(newGrid);
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
}