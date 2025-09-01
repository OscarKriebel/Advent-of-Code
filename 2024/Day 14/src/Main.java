import javax.imageio.ImageIO;
import java.util.*;
import java.io.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner scanner = new Scanner(new File("input.txt"));
        List<Robot> robots = new ArrayList<>();
        while(scanner.hasNext()) {
            String[] line = scanner.nextLine().substring(2).split(" v=");
            int[] pos = Arrays.stream(line[0].split(",")).mapToInt(Integer::parseInt).toArray();
            int[] vel = Arrays.stream(line[1].split(",")).mapToInt(Integer::parseInt).toArray();
            robots.add(new Robot(pos, vel));
        }
        int height = 103;
        int width = 101;
        int seconds = 100;
        //Part 1
        for(Robot r : robots) {
            r.move(height, width, seconds);
        }
        System.out.println("First: " + getQuadrants(robots, height, width));
        //Part 2 - Manual search through the images to find second for tree
        for(Robot r : robots) {
            r.move(height, width, 7051 - seconds);
        }
        System.out.println(printRobots(robots, height, width));
//        for(int i = 0; i < seconds; i++) {
//            for(Robot r : robots) {
//                r.move(height, width);
//            }
//            createImage(convertRobots(robots, height, width), i);
//        }

    }

    public static int[][] convertRobots(List<Robot> robots, int height, int width) {
        int[][] output = new int[height][width];
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                output[i][j] = 0;
            }
        }
        for(Robot r : robots) {
            int[] pos = r.getPosition();
            output[pos[1]][pos[0]] = Color.WHITE.getRGB();
        }
        return output;
    }

    //[top left, top right, bottom left, bottom right]
    public static int getQuadrants(List<Robot> robots, int height, int width) {
        int[] quads = new int[]{0,0,0,0};
        int wid = (int) Math.floor((double) width / 2);
        int hei = (int) Math.floor((double) height / 2);
        for(Robot r : robots) {
            int[] pos = r.getPosition();
            if(pos[0] < wid && pos[1] < hei) {
                //Top left quadrant
                quads[0]++;
            } else if(pos[0] >= width - wid && pos[1] < hei){
                //Top right quadrant
                quads[1]++;
            } else if(pos[0] < wid && pos[1] >= height - hei) {
                //Bottom left quadrant
                quads[2]++;
            } else if(pos[0] >= width - wid && pos[1] >= height - hei) {
                //Bottom right quadrant
                quads[3]++;
            }
        }
        int out = 1;
        for(int num : quads) {
            out *= num;
        }
        return out;
    }

    public static String arrayToString(int[] nums) {
        String output = "";
        for(int n : nums) {
            if(n == 0) {
                output += ".";
            } else {
                output += n;
            }
        }
        return output;
    }

    public static String printRobots(List<Robot> robots, int height, int width) {
        int[][] grid = new int[height][width];
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                grid[i][j] = 0;
            }
        }
        for(Robot r : robots) {
            int[] pos = r.getPosition();
            grid[pos[1]][pos[0]]++;
        }
        String out = "";
        for(int[] row : grid) {
            out += arrayToString(row) + "\n";
        }
        return out;
    }

    public static void createImage(int[][] locations, int iter) {
        BufferedImage image = new BufferedImage(locations[0].length, locations.length, BufferedImage.TYPE_BYTE_BINARY);
        for (int y = 0; y < locations.length; y++) {
            for (int x = 0; x < locations[0].length; x++) {
                image.setRGB(x, y, locations[y][x]);
            }
        }

        try {
            ImageIO.write(image, "png", new File("img/" + (iter + 1) + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}