import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String input = "input.txt";
        Scanner scanner = new Scanner(new File(input));
        List<List<Character>> garden = new ArrayList<>();
        while(scanner.hasNext()) {
            List<Character> row = new ArrayList<>();
            for(char c : scanner.nextLine().toCharArray()) {
                row.add(c);
            }
            garden.add(row);
        }
        List<Plot> plots = findPlots(makeArray(garden));

        long first = 0L;
        long second = 0L;
        for(Plot p : plots) {
            first += p.getFirstCost();
            second += p.getSecondCost();
        }
        System.out.println("First: " + first);
        System.out.println("Second: " + second);
    }

    public static Character[][] makeArray(List<List<Character>> array) {
        Character[][] output = new Character[array.size()][array.get(0).size()];
        for(int i = 0; i < array.size(); i++) {
            for(int j = 0; j < array.get(i).size(); j++) {
                output[i][j] = array.get(i).get(j);
            }
        }
        return output;
    }

    public static List<Plot> findPlots(Character[][] garden) {
        List<Plot> output = new ArrayList<>();
        for(int y = 0; y < garden.length; y++) {
            for(int x = 0; x < garden[0].length; x++) {
                if(garden[y][x] != '.') {
                    char type = garden[y][x];
                    Set<Coordinate> coords = getPlot(garden, x, y, type);
                    output.add(new Plot(type, coords.stream().toList()));
                }
            }
        }
        return output;
    }

    public static Set<Coordinate> getPlot(Character[][] garden, int x, int y, char type) {
        Set<Coordinate> output = new HashSet<>();
        garden[y][x] = '.';
        output.add(new Coordinate(x, y));
        int height = garden.length;
        int width = garden[0].length;
        //Check up
        if(y-1 >= 0 && garden[y-1][x] == type) {
            output.addAll(getPlot(garden, x, y-1, type));
        }
        //Check down
        if(y+1 < height && garden[y+1][x] == type) {
            output.addAll(getPlot(garden, x, y+1, type));
        }
        //Check left
        if(x-1 >= 0 && garden[y][x-1] == type) {
            output.addAll(getPlot(garden, x-1, y, type));
        }
        //Check right
        if(x+1 < width && garden[y][x+1] == type) {
            output.addAll(getPlot(garden, x+1, y, type));
        }
        return output;
    }
}