import java.util.*;
import java.io.*;
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        List<Key> keys = new ArrayList<>();
        List<Key> locks = new ArrayList<>();
        List<String> block = new ArrayList<>();
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            if(line.equals("")) {
                Key key = convertKey(block);
                if(key.isKey) {
                    keys.add(key);
                } else {
                    locks.add(key);
                }
                block = new ArrayList<>();
            } else {
                block.add(line);
            }
        }
        Key last = convertKey(block);
        if(last.isKey) {
            keys.add(last);
        } else {
            locks.add(last);
        }
        int first = 0;
        for(Key key : keys) {
            for(Key lock : locks) {
                if(key.compare(lock)) {
                    first++;
                }
            }
        }
        System.out.println("First: " + first);
    }

    public static Key convertKey(List<String> block) {
        char[][] map = block.stream().map(String::toCharArray).toArray(char[][]::new);
        int[] heights = new int[map[0].length];
        for(int x = 0; x < map[0].length; x++) {
            int height = 0;
            for(int y = 0; y < map.length; y++) {
                if(map[y][x] == '#') {
                    height++;
                }
            }
            heights[x] = height - 1;
        }
        return new Key(heights, (map[0][0] == '.'));
    }
}