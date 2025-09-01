import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        List<List<Character>> grid = new ArrayList<>();
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            List<Character> row = new ArrayList<>();
            for(char l : line.toCharArray()) {
                row.add(l);
            }
            grid.add(row);
        }
        int height = grid.size();
        int width = grid.get(0).size();
        int first = 0;
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                //Check horizontal
                if(j < width-3) {
                    if((grid.get(i).get(j) == 'X' && grid.get(i).get(j+1) == 'M' && grid.get(i).get(j+2) == 'A' && grid.get(i).get(j+3) == 'S') || (grid.get(i).get(j) == 'S' && grid.get(i).get(j+1) == 'A' && grid.get(i).get(j+2) == 'M' && grid.get(i).get(j+3) == 'X')) {
                        first++;
                    }
                }
                //Check Vertical
                if(i < height-3) {
                    if((grid.get(i).get(j) == 'X' && grid.get(i+1).get(j) == 'M' && grid.get(i+2).get(j) == 'A' && grid.get(i+3).get(j) == 'S') || (grid.get(i).get(j) == 'S' && grid.get(i+1).get(j) == 'A' && grid.get(i+2).get(j) == 'M' && grid.get(i+3).get(j) == 'X')) {
                        first++;
                    }
                }
                //Check Forward Diagonal
                if(j < width-3 && i < height-3) {
                    if((grid.get(i).get(j) == 'X' && grid.get(i+1).get(j+1) == 'M' && grid.get(i+2).get(j+2) == 'A' && grid.get(i+3).get(j+3) == 'S') || (grid.get(i).get(j) == 'S' && grid.get(i+1).get(j+1) == 'A' && grid.get(i+2).get(j+2) == 'M' && grid.get(i+3).get(j+3) == 'X')) {
                        first++;
                    }
                }
                //Check Backwards Diagonal
                if(i > 2 && j < width-3) {
                    if((grid.get(i).get(j) == 'X' && grid.get(i-1).get(j+1) == 'M' && grid.get(i-2).get(j+2) == 'A' && grid.get(i-3).get(j+3) == 'S') || (grid.get(i).get(j) == 'S' && grid.get(i-1).get(j+1) == 'A' && grid.get(i-2).get(j+2) == 'M' && grid.get(i-3).get(j+3) == 'X')) {
                        first++;
                    }
                }
            }
        }
        System.out.println("First: " + first);
        int second = 0;
        for(int i = 0; i < height - 2; i++) {
            for(int j = 0; j < width - 2; j++) {
                char nw = grid.get(i).get(j);
                char ne = grid.get(i).get(j+2);
                char center = grid.get(i+1).get(j+1);
                char sw = grid.get(i+2).get(j);
                char se = grid.get(i+2).get(j+2);
                if(center == 'A') {
                    //Forwards MAS then SAM
                    if(nw == 'M' && se == 'S') {
                        //Backwards MAS then SAM
                        if(sw == 'M' && ne == 'S') {
                            second++;
                        } else if(sw == 'S' && ne == 'M') {
                            second++;
                        }
                    } else if (nw == 'S' && se == 'M') {
                        //Backwards MAS then SAM
                        if(sw == 'M' && ne == 'S') {
                            second++;
                        } else if(sw == 'S' && ne == 'M') {
                            second++;
                        }
                    }
                }
            }
        }
        System.out.println("Second: " + second);
    }
}