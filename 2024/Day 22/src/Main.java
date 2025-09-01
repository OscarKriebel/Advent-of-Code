import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        List<Secret> secrets = new ArrayList<>();
        while(scanner.hasNext()) {
            secrets.add(new Secret(Long.parseLong(scanner.nextLine())));
        }
        for(Secret secret : secrets) {
            for(int i = 0; i < 2000; i++) {
                secret.next();
            }
        }
        Map<List<Integer>, Integer> sequences = new HashMap<>();
        long first = 0;
        for(Secret secret : secrets) {
            first += secret.secret;
            for(List<Integer> sequence : secret.sequences.keySet()) {
                if(sequences.containsKey(sequence)) {
                    sequences.put(sequence, sequences.get(sequence) + secret.sequences.get(sequence));
                } else {
                    sequences.put(sequence, secret.sequences.get(sequence));
                }
            }
        }
        System.out.println("First: " + first);
        int second = 0;
        for(Integer bananas : sequences.values()) {
            if(second < bananas) {
                second = bananas;
            }
        }
        System.out.println("Second: " + second);
    }
}