import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        List<String> towels = Arrays.stream(scanner.nextLine().split(", ")).toList();
        scanner.nextLine();
        List<String> goals = new ArrayList<>();
        while (scanner.hasNext()) {
            goals.add(scanner.nextLine());
        }
        long first = 0L;
        long second = 0L;
        for (String goal : goals) {
            Map<String, Long> found = new HashMap<>();
            Queue<String> segments = new PriorityQueue<>(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.length() - o2.length();
                }
            });
            segments.add("");
            found.put("", 1L);
            while (!segments.isEmpty()) {
                String current = segments.poll();
                for (String towel : towels) {
                    String prefix = current + towel;
                    if (found.containsKey(prefix)) {
                        found.put(prefix, found.get(prefix) + found.get(current));
                    } else if (goal.startsWith(prefix)) {
                        segments.add(prefix);
                        found.put(prefix, found.get(current));
                    }
                }
            }
            if (found.containsKey(goal)) {
                first++;
                second += found.get(goal);
            }
        }
        System.out.println("First: " + first);
        System.out.println("Second: " + second);
    }
}