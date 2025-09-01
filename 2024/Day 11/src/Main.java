import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        List<Long> numbers = Arrays.stream(scanner.nextLine().split(" ")).map(Long::parseLong).toList();
        Map<Long, Long> stones = new HashMap<>();
        for(Long num : numbers) {
            if(stones.containsKey(num)) {
                stones.put(num, stones.get(num) + 1L);
            } else {
                stones.put(num, 1L);
            }
        }
        int blinks = 1000;
        for(int i = 0; i < blinks; i++) {
            stones = blink(stones);
            if(i == 24) {
                System.out.println("First: " + getTotal(stones));
            }
        }
        System.out.println("Second: " + getTotal(stones));
    }

    public static Long getTotal(Map<Long, Long> map) {
        Long total = 0L;
        for(Long key : map.keySet()) {
            total += map.get(key);
        }
        return total;
    }

    public static Map<Long, Long> blink(Map<Long, Long> stones) {
        Map<Long, Long> newStones = new HashMap<>();
        for(Long key : stones.keySet()) {
            if(key == 0) {
                if(newStones.containsKey(1L)) {
                    newStones.put(1L, stones.get(key) + newStones.get(1L));
                } else {
                    newStones.put(1L, stones.get(key));
                }
            } else if(Long.toString(key).length() % 2 == 0) {
                Long top = Long.parseLong(Long.toString(key).substring(0, Long.toString(key).length()/2));
                Long bottom = Long.parseLong(Long.toString(key).substring(Long.toString(key).length()/2));
                if(newStones.containsKey(top)) {
                    newStones.put(top, newStones.get(top) + stones.get(key));
                } else {
                    newStones.put(top, stones.get(key));
                }
                if(newStones.containsKey(bottom)) {
                    newStones.put(bottom, newStones.get(bottom) + stones.get(key));
                } else {
                    newStones.put(bottom, stones.get(key));
                }
            } else {
                Long newKey = key * 2024L;
                if(newStones.containsKey(newKey)) {
                    newStones.put(newKey, newStones.get(newKey) + stones.get(key));
                } else {
                    newStones.put(newKey, stones.get(key));
                }
            }
        }
        return newStones;
    }

    //Part 1 solution
    public static Deque<Long> blink(Deque<Long> num) {
        Deque<Long> output = new ArrayDeque<>();
        String number;
        for(long n : num) {
            number = Long.toString(n);
            if(n == 0) {
                output.add(1L);
            } else if(number.length() % 2 == 0) {
                output.add(Long.parseLong(number.substring(0, number.length()/2)));
                output.add(Long.parseLong(number.substring(number.length()/2)));
            } else {
                output.add(n * 2024L);
            }
        }
        return output;
    }
}