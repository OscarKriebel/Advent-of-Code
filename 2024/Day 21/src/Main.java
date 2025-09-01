import java.util.*;
import java.io.*;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        List<String> pins = new ArrayList<>();
        while(scanner.hasNext()) {
            pins.add(scanner.nextLine());
        }
        char[][] numpad = makeGrid("numpad");
        char[][] directional = makeGrid("stick");
        Map<String, String> numbers = findAllShortest(numpad);
        Map<String, String> directions = findAllShortest(directional);
        long first = 0L;
        long second = 0L;
        for(String pin : pins) {
            first += calcRobots(pin, numbers, directions, 3);
            second += calcRobots(pin, numbers, directions, 26);
        }
        System.out.println("First: " + first);
        System.out.println("Second: " + second);
    }

    public static long calcRobots(String input, Map<String, String> numberMap, Map<String, String> directionsMap, int robots) {
        Map<String, Long> occurences = convert(numberMap, input);
        for(int i = 0; i < robots-1; i++) {
            occurences = convert(directionsMap, occurences);
        }
        long length = 0;
        for(String key : occurences.keySet()) {
            length += (long) key.length() * occurences.get(key);
        }
        return Long.parseLong(input.substring(0,3)) * length;
    }

    public static Map<String, Long> convert(Map<String, String> mapping, Map<String, Long> occurences) {
        Map<String, Long> output = new HashMap<>();
        for(String key : occurences.keySet()) {
            String input = "A" + key;
            for(int i = 0; i < input.length()-1; i++) {
                String current = mapping.get(input.substring(i, i+2));
                if(output.containsKey(current)) {
                    output.put(current, output.get(current) + occurences.get(key));
                } else {
                    output.put(current, occurences.get(key));
                }
            }
        }
        return output;
    }

    public static Map<String, Long> convert(Map<String, String> mapping, String input) {
        Map<String, Long> output = new HashMap<>();
        input = "A" + input;
        for(int i = 0; i < input.length()-1; i++) {
            String current = mapping.get(input.substring(i, i+2));
            if(output.containsKey(current)) {
                output.put(current, output.get(current) + 1L);
            } else {
                output.put(current, 1L);
            }
        }
        return output;
    }

    public static Map<String, String> findAllShortest(char[][] grid) {
        Map<String, String> output = new HashMap<>();
        Map<Coordinate, Character> positions = new HashMap<>();
        List<Character> unique = new ArrayList<>();
        for(int y = 0; y < grid.length; y++) {
            for(int x = 0; x < grid[0].length; x++) {
                if(grid[y][x] != '#') {
                    unique.add(grid[y][x]);
                    positions.put(new Coordinate(x, y), grid[y][x]);
                }
            }
        }
        for(int i = 0; i < unique.size(); i++) {
            for(int j = i; j < unique.size(); j++) {
                String combo = Character.toString(unique.get(i)) + unique.get(j);
                output.put(combo, findShortest(positions, unique.get(i), unique.get(j)));
                if(!output.containsKey(reverse(combo))) {
                    output.put(reverse(combo), findShortest(positions, unique.get(j), unique.get(i)));
                }
            }
        }
        return output;
    }

    public static String findShortest(Map<Coordinate, Character> positions, char from, char to) {
        if(from == to) {
            return "A";
        }
        Coordinate start = findCharacter(positions, from);
        Coordinate end = findCharacter(positions, to);
        Deque<List<Coordinate>> allPaths = new ArrayDeque<>();
        allPaths.add(List.of(start));
        List<Coordinate> best = new ArrayList<>();
        int smallest = Integer.MAX_VALUE;
        while(!allPaths.isEmpty()) {
            List<Coordinate> current = allPaths.poll();
            Coordinate last = current.get(current.size() - 1);
            for (Coordinate direction : List.of(new Coordinate(-1, 0), new Coordinate(1, 0), new Coordinate(0, 1), new Coordinate(0, -1))) {
                Coordinate next = last.add(direction);
                if (!current.contains(next) && positions.containsKey(next)) {
                    List<Coordinate> newbie = new ArrayList<>(current);
                    newbie.add(next);
                    int distance = getDistance(convertToMovements(newbie));
                    if(smallest > distance) {
                        if (next.equals(end)) {
                            smallest = distance;
                            best = newbie;
                        } else {
                            allPaths.add(newbie);
                        }
                    }
                }
            }
        }
        best = convertToMovements(best);
        best.add(new Coordinate(0, 0));
        return convertDirections(best);
    }

    public static String convertDirections(List<Coordinate> path) {
        String output = "";
        for(Coordinate direction : path) {
            output += direction.convertDirection();
        }
        return output;
    }

    public static int getDistance(List<Coordinate> path) {
        //If not items in path then cannot be a path
        if(path.size() == 0) {
            return Integer.MAX_VALUE-1;
        } else if(path.size() == 1) {
            //If only one long then its just one step and return as such
            return 1;
        }
        //At least one step
        int total = 1;
        Coordinate current = path.get(0);
        //Compare one step to the next one
        for(int i = 1; i < path.size(); i++) {
            //Prioritize to keep going in a straight line
            if(current.equals(path.get(i))) {
                total++;
            } else {
                total += 5;
            }
            //Prioritize going as far away as possible on the keybpard < then ^ or v then > or A
            if(current.equals(new Coordinate(-1, 0))) {
                total++;
            } else if(current.equals(new Coordinate(0, -1)) || current.equals(new Coordinate(0, 1))) {
                total += 2;
            } else {
                total += 3;
            }
            current = path.get(i);
        }
        return total;
    }

    public static List<Coordinate> convertToMovements(List<Coordinate> path) {
        List<Coordinate> output = new ArrayList<>();
        Coordinate current = path.get(0);
        for(int i = 1; i < path.size(); i++) {
            output.add(path.get(i).subtract(current));
            current = path.get(i);
        }
        return output;
    }

    public static String reverse(String input) {
        String output = "";
        for(char c : input.toCharArray()) {
            output = c + output;
        }
        return output;
    }

    public static Coordinate findCharacter(Map<Coordinate, Character> positions, char target) {
        for(Coordinate coord : positions.keySet()) {
            if(positions.get(coord) == target) {
                return coord;
            }
        }
        return new Coordinate(-1, -1);
    }

    public static Coordinate findCharacter(char[][] grid, char target) {
        for(int y = 0; y < grid.length; y++) {
            for(int x = 0; x < grid[0].length; x++) {
                if(grid[y][x] == target) {
                    return new Coordinate(x, y);
                }
            }
        }
        return new Coordinate(-1, -1);
    }

    public static char[][] makeGrid(String fileName) throws FileNotFoundException {
        List<char[]> temp = new ArrayList<>();
        Scanner scanner = new Scanner(new File(fileName + ".txt"));
        while(scanner.hasNext()) {
            temp.add(scanner.nextLine().toCharArray());
        }
        return temp.toArray(char[][]::new);
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