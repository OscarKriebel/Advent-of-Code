import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        long value = 0;
        long test;
        List<Long> vals;
        while(scanner.hasNext()) {
            String[] line = scanner.nextLine().split(": ");
            test = Long.parseLong(line[0]);
            vals = Arrays.stream(line[1].split(" ")).map(Long::parseLong).toList();
            List<Long> left = getVal(vals.get(0), vals.subList(1, vals.size()), Option.ADDITION);
            List<Long> right = getVal(vals.get(0), vals.subList(1, vals.size()), Option.MULTIPLICTION);
            List<Long> middle = getVal(vals.get(0), vals.subList(1, vals.size()), Option.CONCAT);
            List<Long> finals = Stream.concat(Stream.concat(left.stream(), right.stream()), middle.stream()).distinct().toList();
            for(long i : finals) {
                if(test == i) {
                    value += test;
                    break;
                }
            }
        }
        System.out.println("Output: " + value);
    }

    public static List<Long> getVal(long starter, List<Long> remaining, Option operation) {
        if(remaining.size() != 0) {
            long newer = starter;
            if(operation == Option.ADDITION) {
                newer += remaining.get(0);
            } else if(operation == Option.MULTIPLICTION) {
                newer *= remaining.get(0);
            } else {
                newer = Long.parseLong(Long.toString(starter) + Long.toString(remaining.get(0)));
            }
            List<Long> left = getVal(newer, remaining.subList(1, remaining.size()), Option.ADDITION);
            List<Long> right = getVal(newer, remaining.subList(1, remaining.size()), Option.MULTIPLICTION);
            List<Long> middle = getVal(newer, remaining.subList(1, remaining.size()), Option.CONCAT);
            return Stream.concat(Stream.concat(left.stream(), right.stream()), middle.stream()).distinct().toList();
        } else {
            return List.of(starter);
        }
    }
    public enum Option {
        ADDITION,
        MULTIPLICTION,
        CONCAT;
    }
}