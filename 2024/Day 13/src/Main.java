import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        long first = 0L;
        long second = 0L;
        while(scanner.hasNext()) {
            String one = scanner.nextLine();
            if(!one.equals("")) {
                Pattern p = Pattern.compile("\\d+");
                long[] a = p.matcher(one).results().map(MatchResult::group).mapToLong(Long::parseLong).toArray();
                long[] b = p.matcher(scanner.nextLine()).results().map(MatchResult::group).mapToLong(Long::parseLong).toArray();
                long[] prizes = p.matcher(scanner.nextLine()).results().map(MatchResult::group).mapToLong(Long::parseLong).toArray();
                double[] firstPresses = findPresses(a, b, prizes);
                //System.out.println(arrayToString(presses));
                if(closeEnough(firstPresses) && firstPresses[0] <= 100 && firstPresses[1] <= 100) {
                    first += (Math.round(firstPresses[0]) * 3L) + Math.round(firstPresses[1]);
                }
                double[] secondPresses = findPresses(a, b, new long[]{prizes[0] + 10000000000000L, prizes[1] + 10000000000000L});
                if(closeEnough(secondPresses)) {
                    second += (Math.round(secondPresses[0]) * 3L) + Math.round(secondPresses[1]);
                }
            }
        }
        System.out.println("First: " + first);
        System.out.println("Second: " + second);
    }

    public static double[] findPresses(long[] a, long[] b, long[] p) {
        double[] A = Arrays.stream(a).asDoubleStream().toArray();
        double[] B = Arrays.stream(b).asDoubleStream().toArray();
        double[] prizes = Arrays.stream(p).asDoubleStream().toArray();
        double[] output = new double[2];
        output[1] = (prizes[1] - ((A[1] / A[0])  * prizes[0])) / (B[1] - ((A[1] / A[0]) * B[0]));
        output[0] = (prizes[0] - (B[0] * output[1])) / A[0];
        return output;
    }

    public static boolean closeEnough(double[] nums) {
        for(double n : nums) {
            if(Math.abs(Math.round(n) - n) >= 0.001) {
                return false;
            }
        }
        return true;
    }

    public static String arrayToString(double[] array) {
        String output = "";
        for(double item : array) {
            output += item + " ";
        }
        return output;
    }
}