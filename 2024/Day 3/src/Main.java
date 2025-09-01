import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        String corrupt = "";
        while(scanner.hasNext()) {
            corrupt += scanner.nextLine();
        }
        Pattern firstRegex = Pattern.compile("mul\\(\\d{1,3}+,\\d{1,3}+\\)");
        List<String> matches = firstRegex.matcher(corrupt).results().map(MatchResult::group).toList();
        int first = 0;
        Pattern numbers = Pattern.compile("\\d+");
        for(String match : matches) {
            first += numbers.matcher(match).results().map(x -> Integer.parseInt(x.group())).reduce(1, (x,y) -> x*y);
        }
        System.out.println("First: " + first);

        System.out.println((new Scanner(new File("input.txt"))).findAll(Pattern.compile("mul\\(\\d{1,3}+,\\d{1,3}+\\)")).map(MatchResult::group).map(x -> x.replace("mul(","").replace(")","").split(",")).map(x -> Integer.parseInt(x[0]) * Integer.parseInt(x[1])).reduce(0, Integer::sum));
        int second = 0;
        Pattern secondRegex = Pattern.compile("(mul\\(\\d{1,3}+,\\d{1,3}+\\))|(do\\(\\))|(don't\\(\\))");
        matches = secondRegex.matcher(corrupt).results().map(MatchResult::group).toList();
        boolean on = true;
        for(String match : matches) {
            if(match.equals("don't()")){
                on = false;
            } else if(match.equals("do()")) {
                on = true;
            } else if(on) {
                second += numbers.matcher(match).results().map(x -> Integer.parseInt(x.group())).reduce(1, (x,y) -> x*y);
            }
        }
        System.out.println("Second: " + second);
    }
}