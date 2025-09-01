import java.util.*;
import java.io.*;
public class Main {
    public static void main(String[] args) throws FileNotFoundException  {
        Scanner scanner = new Scanner(new File("input.txt"));
        Map<String, Integer> values = new HashMap<>();
        boolean initial = true;
        List<String> equations = new ArrayList<>();
        Set<String> wrong = new HashSet<>();
        String x = "";
        String y = "";
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            if(line.equals("")) {
                initial = false;
            } else if(initial) {
                String[] parts = line.split(": ");
                values.put(parts[0], Integer.parseInt(parts[1]));
                if(parts[0].startsWith("x")) {
                    x = parts[1] + x;
                } else {
                    y = parts[1] + y;
                }
            } else {
                equations.add(line);
                String[] parts = line.split(" ");
                //Parts 0, 2, 4
                for(String part : List.of(parts[0], parts[2], parts[4])) {
                    if(!values.containsKey(part)) {
                        values.put(part, null);
                    }
                }
            }
        }
        //Loop through the equations and do what is possible
        //Then try again with equations that were not done until no equations remain
        while(!equations.isEmpty()) {
            List<String> remaining = new ArrayList<>();
            for(String equation : equations) {
                String[] parts = equation.split(" ");
                //0 and 2 are operands, 1 is the operator, 4 is the output
                if(values.get(parts[0]) != null && values.get(parts[2]) != null) {
                    switch (parts[1]) {
                        case "AND" -> values.put(parts[4], values.get(parts[0]) & values.get(parts[2]));
                        case "OR" -> values.put(parts[4], values.get(parts[0]) | values.get(parts[2]));
                        case "XOR" -> values.put(parts[4], values.get(parts[0]) ^ values.get(parts[2]));
                    }
                } else {
                    remaining.add(equation);
                }
            }
            equations = remaining;
        }
        long first = 0;
        int zTotal = 0;
        //Convert z value to integer, keeping track of how many z values there are
        for(String key : values.keySet()) {
            if(key.startsWith("z")) {
                first += Math.pow(2, Integer.parseInt(key.substring(1))) * values.get(key);
                zTotal++;
            }
        }
        System.out.println("First: " + first);
        //Adder checker for part 2
        for(String equation : equations) {
            String[] parts = equation.split(" ");
            if(parts[4].startsWith("z") && !parts[1].equals("XOR")) {
                wrong.add(parts[4]);
            } else if(!(parts[0].startsWith("x") || parts[0].startsWith("y")) && !(parts[2].startsWith("x") || parts[2].startsWith("y")) && !parts[4].startsWith("z") && parts[1].equals("XOR")) {
                wrong.add(parts[4]);
            } else if(parts[1].equals("XOR")) {
                for(String other : equations) {
                    String[] others = other.split(" ");
                    if(others[1].equals("OR") && (others[0].equals(parts[4]) || others[2].equals(parts[4]))) {
                        wrong.add(parts[4]);
                    }
                }
            } else if(parts[1].equals("AND") && !(parts[0].equals("x00") || parts[2].equals("x00"))) {
                for(String other : equations) {
                    String[] others = other.split(" ");
                    if(!others[1].equals("OR") && (others[0].equals(parts[4]) || others[2].equals(parts[4]))) {
                        wrong.add(parts[4]);
                    }
                }
            }
        }
        wrong.remove("z" + (zTotal - 1));
        String second = wrong.stream().sorted().reduce("", (going, other) -> going + "," + other).substring(1);
        System.out.println("Second: " + second);
    }
}