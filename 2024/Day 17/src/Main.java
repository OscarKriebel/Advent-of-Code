import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        long A = Long.parseLong(scanner.nextLine().split(": ")[1]);
        long B = Long.parseLong(scanner.nextLine().split(": ")[1]);
        long C = Long.parseLong(scanner.nextLine().split(": ")[1]);
        scanner.nextLine();
        String input = scanner.nextLine().replace("Program: ","");
        Program first = new Program(A, B, C, input);
        System.out.println("First: " + first.run());
        List<Long> seeds = List.of(0L);
        for(int i = 0; i < input.replace(",","").length(); i++) {
            List<Long> newSeeds = new ArrayList<>();
            for(Long seed : seeds) {
                for(int a = 0; a < 8; a++) {
                    long newA = seed * 8 + a;
                    Program tester = new Program(newA, B, C, input);
                    String output = tester.run();
                    if (output.equals(input.substring(input.length() - (i * 2) - 1))) {
                        newSeeds.add(newA);
                    }
                }
            }
            seeds = new ArrayList<>(newSeeds);
        }
        long second = Long.MAX_VALUE;
        for(long seed : seeds) {
            if(seed < second) {
                second = seed;
            }
        }
        System.out.println("Second: " + second);
    }
}