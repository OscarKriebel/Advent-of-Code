import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        Map<String, Set<String>> computers = new HashMap<>();
        //Create graph and store nodes by their id
        while (scanner.hasNext()) {
            String[] line = scanner.nextLine().split("-");
            if (!computers.containsKey(line[0])) {
                computers.put(line[0], new HashSet<>());
            }
            if (!computers.containsKey(line[1])) {
                computers.put(line[1], new HashSet<>());
            }
            Set<String> A = computers.get(line[0]);
            Set<String> B = computers.get(line[1]);
            A.add(line[1]);
            B.add(line[0]);
        }
        //Make set of all trios
        Set<Set<String>> groups = new HashSet<>();
        //For each computer in the network
        for (String id : computers.keySet()) {
            Set<String> neighbors = computers.get(id);
            for(String A : neighbors) {
                for(String B : neighbors) {
                    //For each pair of neighbors, check if they have each other as neighbors
                    //If so, add trio to the group of valid trios
                    if(!A.equals(B) && computers.get(A).contains(B)) {
                        groups.add(Set.of(id, A, B));
                    }
                }
            }
        }
        //Check through each valid trio and see if any of their ids being with t
        int first = 0;
        for (Set<String> group : groups) {
            for (String id : group) {
                if (id.startsWith("t")) {
                    first++;
                    break;
                }
            }
        }
        System.out.println("First: " + first);
        //Find all maximal cliques using Bron-Kerbosch algorithm
        Set<Set<String>> secondSet = BronKerbosch(computers, new HashSet<>(), computers.keySet(), new HashSet<>());
        int largest = 0;
        Set<String> large = new HashSet<>();
        //Find largest maximal clique
        for(Set<String> max : secondSet) {
            if(max.size() > largest) {
                largest = max.size();
                large = max;
            }
        }
        //Sort largest maximal clique
        List<String> toSort = new ArrayList<>(large);
        Collections.sort(toSort);
        String second = "";
        for(String s : toSort) {
            second += s + ",";
        }
        System.out.println("Second: " + second.substring(0,second.length()-1));
    }

    //Java implementation of basic Bron-Kerbosch algorithm
    public static Set<Set<String>> BronKerbosch(Map<String, Set<String>> graph, Set<String> R, Set<String> P, Set<String> X) {
        if(P.isEmpty() && X.isEmpty()) {
            return Set.of(R);
        }
        Set<Set<String>> output = new HashSet<>();
        for(String v : P) {
            output.addAll(BronKerbosch(graph, union(R, Set.of(v)), intersection(P, graph.get(v)), intersection(X, graph.get(v))));
            P = minus(P, Set.of(v));
            X = union(X, Set.of(v));
        }
        return output;
    }

    public static <T> Set<T> union(Set<T> A, Set<T> B) {
        Set<T> output = new HashSet<>(A);
        output.addAll(B);
        return output;
    }

    public static <T> Set<T> intersection(Set<T> A, Set<T> B) {
        Set<T> output = new HashSet<>();
        for(T a : A) {
            if(B.contains(a)) {
                output.add(a);
            }
        }
        return output;
    }

    public static <T> Set<T> minus(Set<T> A, Set<T> B) {
        Set<T> output = new HashSet<>();
        for(T a : A) {
            if(!B.contains(a)) {
                output.add(a);
            }
        }
        return output;
    }
}