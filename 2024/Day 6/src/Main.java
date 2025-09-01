import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        List<Integer[]> positions = new ArrayList<>();
        Integer[] start = new Integer[2];
        List<Character[]> tempGrid = new ArrayList<>();
        int height = 0;
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            height++;
            Character[] row = new Character[line.length()];
            for(int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if(c == '^') {
                    start = new Integer[]{height-1, i};
                }
                row[i] = c;
            }
            tempGrid.add(row);
        }
        Integer[] position = start;
        int width = tempGrid.get(0).length;
        Character[][] grid = new Character[height][width];
        for(int i = 0; i < tempGrid.size(); i++) {
            grid[i] = tempGrid.get(i);
        }
        Direction dir = Direction.UP;
        Integer[] next;
        Character[][] gridCopy;
        List<Position> seen;
        List<Integer[]> obstructions = new ArrayList<>();
        Direction newDir;
        Integer[] newNext;
        int second = 0;
        while(inRange(position, height, width)) {
            //Show that you have been here before
            if(!contains(positions, position)) {
                positions.add(position);
            }
            //Get next position
            next = getNext(position, dir);
            //And check if you need to rotate or not
            while (inRange(next, height, width) && grid[next[0]][next[1]] == '#') {
                dir = dir.rotation;
                next = getNext(position, dir);
            }
            //Check for loop
            if(inRange(next, height, width)) {
                gridCopy = deepCopy(grid);
                gridCopy[next[0]][next[1]] = '#';
                seen = new ArrayList<>();
                newDir = Direction.UP;
                position = start;
                while(inRange(position, height, width)) {
                    seen.add(new Position(position, newDir));
                    newNext = getNext(position, newDir);
                    while(inRange(newNext, height, width) && gridCopy[newNext[0]][newNext[1]] == '#') {
                        newDir = newDir.rotation;
                        newNext = getNext(position, newDir);
                    }
                    if(seen.contains(new Position(newNext, newDir))) {
                        if(!contains(obstructions, next)) {
                            second++;
                            obstructions.add(next);
                        }
                        break;
                    } else {
                        position = newNext.clone();
                    }
                }
            }
            //Move guard
            position = next.clone();
        }
        if(!contains(positions, position)) {
            positions.add(position);
        }
        //printGrid(grid);
        System.out.println("First: " + positions.size());
        System.out.println("Second: " + second);
    }
    public static Character[][] deepCopy(Character[][] original) {
        if (original == null) {
            return null;
        }

        final Character[][] result = new Character[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }
    public static boolean contains(List<Integer[]> obs, Integer[] pos) {
        for(Integer[] ob : obs) {
            if(ob[0].equals(pos[0]) && ob[1].equals(pos[1])) {
                return true;
            }
        }
        return false;
    }
    public static boolean inRange(Integer[] pos, int height, int width) {
        return (pos[0] >= 0 && pos[0] < height && pos[1] >= 0 && pos[1] < width);
    }
    public static void printGrid(Character[][] grid) {
        String output = "";
        for(Character[] row : grid) {
            for(char c : row) {
                output += c;
            }
            output += "\n";
        }
        System.out.println(output);
    }

    public static Integer[] getNext(Integer[] position, Direction dir) {
        if (dir == Direction.UP) {
            return new Integer[]{position[0] - 1, position[1]};
        } else if (dir == Direction.DOWN) {
            return new Integer[]{position[0] + 1, position[1]};
        } else if (dir == Direction.LEFT) {
            return new Integer[]{position[0], position[1] - 1};
        } else {
            return new Integer[]{position[0], position[1] + 1};
        }
    }
}

