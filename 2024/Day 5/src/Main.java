import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        int first = 0;
        boolean diff = false;
        List<Integer[]> updates = new ArrayList<>();
        List<Integer[]> rules = new ArrayList<>();
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            if(line.equals("")) {
                diff = true;
            } else if(diff){
                Integer[] update = Stream.of(line.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
                if(isOrdered(update, rules)) {
                    first += update[(update.length-1)/2];
                } else {
                    updates.add(update);
                }
            } else {
                String[] split = line.split("\\|");
                rules.add(new Integer[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])});
            }
        }
        System.out.println("First: " + first);
        int second = 0;
        for(Integer[] update : updates) {
            while(!isOrdered(update, rules)) {
                for(Integer[] rule : rules) {
                    Integer[] ind = checkRule(update, rule);
                    if(ind.length != 0) {
                        int temp = update[ind[0]];
                        update[ind[0]] = update[ind[1]];
                        update[ind[1]] = temp;
                        break;
                    }
                }
            }
            second += update[(update.length-1)/2];
        }
        System.out.println("Second: " + second);
    }
    public static boolean isOrdered(Integer[] update, List<Integer[]> rules) {
        for(Integer[] rule : rules) {
            if(checkRule(update, rule).length != 0) {
                return false;
            }
        }
        return true;
    }
    public static Integer[] checkRule(Integer[] update, Integer[] rule) {
        if(contains(update, rule[0]) && contains(update, rule[1]) && indexOf(update, rule[0]) > indexOf(update, rule[1])) {
            return new Integer[]{indexOf(update, rule[0]), indexOf(update, rule[1])};
        } else {
            return new Integer[0];
        }
    }
    public static boolean contains(Integer[] arr, int item) {
        for(int i : arr) {
            if(i == item) {
                return true;
            }
        }
        return false;
    }
    public static int indexOf(Integer[] arr, int item) {
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == item) {
                return i;
            }
        }
        return -1;
    }
}