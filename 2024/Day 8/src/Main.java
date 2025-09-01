import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        int height = 0;
        int width = 0;
        Map<Character, List<Coordinate>> antennas = new HashMap<>();
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            width = line.length();
            for(int i = 0; i < line.length(); i++) {
                if(line.charAt(i) != '.') {
                    //Get coordinates of antenna and put it with similar antennas
                    Coordinate coord = new Coordinate(i, height);
                    //Create key and list if needed, otherwise replace key value with new list
                    if(antennas.containsKey(line.charAt(i))) {
                        List<Coordinate> coords = new ArrayList<>(antennas.get(line.charAt(i)));
                        coords.add(coord);
                        antennas.put(line.charAt(i), coords);
                    } else {
                        antennas.put(line.charAt(i),List.of(coord));
                    }
                }
            }
            height++;
        }
        //Make list of antinodes
        Set<Coordinate> antinodes = new HashSet<>();
        for(char key : antennas.keySet()) {
            //Get the list of coordinates per key
            List<Coordinate> coords = antennas.get(key);
            //Loop through the coordinates pairwise
            for(int i = 0; i < coords.size()-1; i++) {
                Coordinate first = coords.get(i);
                for(int j = i+1; j < coords.size(); j++) {
                    Coordinate second = coords.get(j);
                    //Get distance between coordinates and check in front and behind and ensure they would be in the map
                    Coordinate difference = first.to(second);
                    Coordinate front = second.add(difference);
                    Coordinate back = first.subtract(difference);
                    if(front.inBounds(width, height)) {
                        antinodes.add(front);
                    }
                    if(back.inBounds(width, height)) {
                        antinodes.add(back);
                    }
                }
            }
        }
//        visualizeGrid(antennas, antinodes, width, height);
//        for(Coordinate c : antinodes) {
//            System.out.println(c);
//        }
        System.out.println("First: " + antinodes.size());
        //Make new set of coordinates for antinodes
        Set<Coordinate> antis = new HashSet<>();
        //Loop through each key
        for(char key : antennas.keySet()) {
            List<Coordinate> coords = antennas.get(key);
            //Loop through coordinates pairwise
            for(int i = 0; i < coords.size()-1; i++) {
                Coordinate first = coords.get(i);
                for(int j = i+1; j < coords.size(); j++) {
                    Coordinate second = coords.get(j);
                    //Add the nodes themselves to the antinodes so long as there are at least two antennas for a key
                    antis.add(second);
                    antis.add(first);
                    //Find difference in position of the two antennas
                    Coordinate difference = first.to(second);
                    Coordinate front = second.add(difference);
                    //Continue looking forward until reach out of bounds
                    while(front.inBounds(width, height)) {
                        antis.add(front);
                        front = front.add(difference);
                    }
                    //Continue looking backwards until reach out of bounds
                    Coordinate back = first.subtract(difference);
                    while(back.inBounds(width, height)) {
                        antis.add(back);
                        back = back.subtract(difference);
                    }
                }
            }
        }
//        visualizeGrid(antennas, antis, width, height);
        System.out.println("Second: " + antis.size());
    }

    //Function to draw the grid with antennas and antinodes as show in the example
    public static void visualizeGrid(Map<Character, List<Coordinate>> antennas, Set<Coordinate> nodes, int width, int height) {
        //Create grid a character 2-d array
        Character[][] grid = new Character[height][width];
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                grid[i][j] = '.';
            }
        }
        //Add antinodes
        for(Coordinate c : nodes) {
            grid[c.y][c.x] = '#';
        }
        //Add antennas, overriding antinodes from being displayed
        for(char c : antennas.keySet()) {
            for(Coordinate coord : antennas.get(c)) {
                grid[coord.y][coord.x] = c;
            }
        }
        //Create string to print
        String output = "";
        for(Character[] row : grid) {
            for(char c : row) {
                output += c;
            }
            output += "\n";
        }
        System.out.println(output);
    }
}