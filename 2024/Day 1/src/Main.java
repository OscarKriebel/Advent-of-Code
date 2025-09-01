import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        PriorityQueue<Integer> first = new PriorityQueue<>();
        PriorityQueue<Integer> second = new PriorityQueue<>();
        while(scanner.hasNext()) {
            String[] line = scanner.nextLine().split("   ");
            first.add(Integer.parseInt(line[0]));
            second.add(Integer.parseInt(line[1]));
        }
        int total = 0;
        while(!first.isEmpty()) {
            total += Math.abs(first.poll() - second.poll());
        }
        System.out.println("First: " + Integer.toString(total));

        scanner = new Scanner(new File("input.txt"));
        Map<String, Integer> amounts = new HashMap<>();
        List<Integer> locations = new ArrayList<>();
        while(scanner.hasNext()) {
            String[] line = scanner.nextLine().split("   ");
            locations.add(Integer.parseInt(line[0]));
            if(!amounts.containsKey(line[1])) {
                amounts.put(line[1], 1);
            } else {
                amounts.put(line[1], amounts.get(line[1]) + 1);
            }
        }
        total = 0;
        for(int location : locations) {
            if(amounts.containsKey(Integer.toString(location))) {
                total += location * amounts.get(Integer.toString(location));
            }
        }
        System.out.println("Second: " + total);
    }
}