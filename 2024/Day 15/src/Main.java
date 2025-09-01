import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        boolean com = false;
        Deque<Character> commands = new ArrayDeque<>();
        List<char[]> temp = new ArrayList<>();
        int[] robot = new int[2];
        int height = 0;
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            if(line.equals("")) {
                com ^= true;
            } else if(!com) {
                char[] t = new char[line.length()];
                for(int i = 0; i < line.length(); i++) {
                    t[i] = line.charAt(i);
                    if(line.charAt(i) == '@') {
                        robot[1] = i;
                        robot[0] = height;
                    }
                }
                height++;
                temp.add(t);
            } else {
                commands.addAll(line.chars().mapToObj(x -> (char) x).toList());
            }
        }
        char[][] maze = temp.toArray(char[][]::new);
        char[][] biggerMaze = createLarger(maze);
        int[] secondRobot = findRobot(biggerMaze);
//        printGrid(maze);
//        printGrid(biggerMaze);
        while(!commands.isEmpty()) {
            char command = commands.poll();
            if(command == '<') {
                //Left
                moveRobot(maze, robot, -1, 0);
                moveRobot(biggerMaze, secondRobot, -1, 0);
            } else if(command == '>') {
                //Right
                moveRobot(maze, robot, 1, 0);
                moveRobot(biggerMaze, secondRobot, 1, 0);
            } else if(command == '^') {
                //Up
                moveRobot(maze, robot, 0, -1);
                moveVert(biggerMaze, secondRobot, -1);
            } else {
                //Down
                moveRobot(maze, robot, 0, 1);
                moveVert(biggerMaze, secondRobot, 1);
            }
//            System.out.println("Command: " + command);
//            printGrid(maze);
//            printGrid(biggerMaze);
        }
        System.out.println("First: " + getScore(maze, 'O'));
        System.out.println("Second: " + getScore(biggerMaze, '['));
    }

    public static void moveVert(char[][] grid, int[] robot, int dir) {
        //Store all positions that will be moved
        Set<Coordinate> moving = new HashSet<>();
        //Check if they can be moved
        if(canMove(moving, grid, robot[1], robot[0], dir)) {
            //Set all current positions to nothing
            for(Coordinate coord : moving) {
                grid[coord.y][coord.x] = '.';
            }
            //Set new positions to the previous
            for(Coordinate coord : moving) {
                grid[coord.y+dir][coord.x] = coord.type;
            }
            robot[0] += dir;
        }
    }

    public static boolean canMove(Set<Coordinate> moving, char[][] grid, int x, int y, int dir) {
        moving.add(new Coordinate(x, y, grid[y][x]));
        if(grid[y+dir][x] == '.') {
            return true;
        } else if(grid[y+dir][x] == '#') {
            return false;
        } else if(grid[y+dir][x] == '[') {
            return (canMove(moving, grid, x, y+dir, dir) && canMove(moving, grid, x+1, y+dir, dir));
        } else if(grid[y+dir][x] == ']') {
            return (canMove(moving, grid, x, y+dir, dir) && canMove(moving, grid, x-1, y+dir, dir));
        }
        return false;
    }

    public static void moveRobot(char[][] grid, int[] robot, int diffX, int diffY) {
        int currentX = robot[1];
        int currentY = robot[0];
        while(grid[currentY][currentX] != '#') {
            if(grid[currentY][currentX] == '.') {
                int newX = currentX;
                int newY = currentY;
                while(!(newX == robot[1] && newY == robot[0])) {
                    grid[newY][newX] = grid[newY - diffY][newX - diffX];
                    newY -= diffY;
                    newX -= diffX;
                }
                grid[robot[0]][robot[1]] = '.';
                robot[1] += diffX;
                robot[0] += diffY;
                break;
            } else {
                currentX += diffX;
                currentY += diffY;
            }
        }
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

    public static long getScore(char[][] grid, char type) {
        long total = 0L;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == type) {
                    total += (i * 100L) + j;
                }
            }
        }
        return total;
    }

    public static char[][] createLarger(char[][] grid) {
        char[][] output = new char[grid.length][grid[0].length*2];
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                char one = '.';
                char two = '.';
                if(grid[i][j] == '#') {
                    one = '#';
                    two = '#';
                } else if(grid[i][j] == 'O') {
                    one = '[';
                    two = ']';
                } else if(grid[i][j] == '@') {
                    one = '@';
                }
                output[i][j * 2] = one;
                output[i][j*2+1] = two;
            }
        }
        return output;
    }

    public static int[] findRobot(char[][] grid) {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == '@') {
                    return new int[]{i,j};
                }
            }
        }
        return new int[]{-1,-1};
    }
}