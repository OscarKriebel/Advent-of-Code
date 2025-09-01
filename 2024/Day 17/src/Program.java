import java.util.*;
import java.util.regex.Pattern;

public class Program {
    private long A;
    private long B;
    private long C;
    private List<Integer> commands;
    private int pointer = 0;
    private String output = "";

    public Program(long A, long B, long C, String commands) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.commands = Pattern.compile("\\d").matcher(commands).results().map(x -> Integer.parseInt(x.group())).toList();
    }

    public String run() {
        while(this.pointer < commands.size()) {
            int command = read();
            int oper = read();
            switch (command) {
                case 0 -> adv(oper);
                case 1 -> bxl(oper);
                case 2 -> bst(oper);
                case 3 -> jnz(oper);
                case 4 -> bxc();
                case 5 -> out(oper);
                case 6 -> bdv(oper);
                case 7 -> cdv(oper);
            }
        }
        if(this.output.length() > 0) {
            this.output = this.output.substring(0, this.output.length()-1);
        }
        return this.output;
    }

    private int read() {
        this.pointer++;
        return this.commands.get(this.pointer - 1);
    }

    private long combo(int oper) {
        switch(oper) {
            case 4:
                return this.A;
            case 5:
                return this.B;
            case 6:
                return this.C;
            case 7:
                System.out.println("7 in Operand combo");
                break;
        }
        return oper;
    }

    private void adv(int oper) {
        this.A = (long) Math.floor(this.A / Math.pow(2, combo(oper)));
    }

    private void bxl(int oper) {
        this.B = this.B ^ oper;
    }

    private void bst(int oper) {
        this.B = Math.floorMod(combo(oper), 8);
    }

    private void jnz(int oper) {
        if(this.A != 0) {
            this.pointer = oper;
        }
    }

    private void bxc() {
        this.B = this.B ^ this.C;
    }

    private void out(int oper) {
        this.output += Math.floorMod(combo(oper), 8) + ",";
    }

    private void bdv(int oper) {
        this.B = (long) Math.floor(A / (Math.pow(2, combo(oper))));
    }

    private void cdv(int oper) {
        this.C = (long) Math.floor(A / (Math.pow(2, combo(oper))));
    }

    public void printRegistry() {
        System.out.println("A: " + this.A + ", B: " + this.B + ", C: " + this.C);
    }
}
