import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        int first = 0;
        int second = 0;
        while(scanner.hasNext()) {
            String[] line = scanner.nextLine().split(" ");
            List<Integer> report = new ArrayList<>();
            for(String level : line) {
                report.add(Integer.parseInt(level));
            }
            if(validReport(report)) {
                first++;
            } else {
                Integer[] errors = getErrored(report);
                for(int index : errors) {
                    List<Integer> copy = new ArrayList<>(report);
                    copy.remove(index);
                    if(validReport(copy)) {
                        second++;
                        break;
                    }
                }
            }
        }
        System.out.println("First: " + first);
        System.out.println("Second: " + (first + second));
    }
    public static boolean validReport(List<Integer> report) {
        int prev = -1;
        boolean ascend = (report.get(0) < report.get(1));
        for(int level : report) {
            if (prev != -1) {
                int abs = Math.abs(prev - level);
                if ((ascend && prev > level) || (!ascend && prev < level) || abs < 1 || abs > 3) {
                    return false;
                }
            }
            prev = level;
        }
        return true;
    }

    public static Integer[] getErrored(List<Integer> report) {
        int prev = report.get(0);
        boolean ascend = (report.get(0) < report.get(1));
        for(int i = 1; i < report.size(); i++) {
            if (prev != -1) {
                int abs = Math.abs(prev - report.get(i));
                if ((ascend && prev > report.get(i)) || (!ascend && prev < report.get(i)) || abs < 1 || abs > 3) {
                    return new Integer[]{0, i-1, i};
                }
            }
            prev = report.get(i);
        }
        return new Integer[3];
    }
}