import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        LinkedList<Integer> full = new LinkedList<>();
        //Get input as single line
        String input = "";
        while(scanner.hasNext()) {
            input += scanner.nextLine();
        }
        //Swap back and forth between adding the id or not
        boolean active = true;
        int id = 0;
        //For each number in the sequence
        for(char c : input.toCharArray()) {
            //Go through it that number of times, adding either the id or -1 to represent empty space
            for(int i = 0; i < Integer.parseInt(Character.toString(c)); i++) {
                if(active) {
                    full.add(id);
                } else {
                    full.add(-1);
                }
            }
            //Increase the id if need be
            if(active) {
                id++;
            }
            //Swap being active or not
            active = !active;
        }
        Integer[] fully = full.toArray(new Integer[0]);
        //List to be used in part one
        List<Integer> file = new ArrayList<>();
        //While there are parts of the file still not looked at
        while(full.size() > 0) {
            //Get the front of the list
            int current = full.pop();
            //If empty, then check that you can get values from the back and loop until you run into an actual value to add to the front
            if(current == -1) {
                int back;
                if(full.size() > 0) {
                    back = full.removeLast();
                } else {
                    back = -1;
                }
                while(back == -1 && full.size() > 0) {
                    back = full.removeLast();
                }
                if(back != -1) {
                    file.add(back);
                }
                //Otherwise just add the number to the list
            } else {
                file.add(current);
            }
        }
        //Checksum
        System.out.println("First: " + checksum(file));
        //Go back to the final id of the sequence and loop through them in reverse
        id--;
        while(id > 0) {
            moveBlock(fully, id);
            id--;
        }
        System.out.println("Second: " + checksum(fully));
    }

    public static <T> void printArray(T[] list) {
        String output = "";
        for(int i = 0; i < list.length; i++) {
            output += list[i].toString();
            if (i < list.length - 1) {
                output += ", ";
            }
        }
        System.out.println(output);
    }

    public static long checksum(List<Integer> list) {
        long output = 0;
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i) >= 0) {
                output += list.get(i) * i;
            }
        }
        return output;
    }

    public static long checksum(Integer[] list) {
        return checksum(List.of(list));
    }

    public static Integer[] moveBlock(Integer[] list, int id) {
        //Find the block of the id given
        int idTo = -1;
        int idFrom = -1;
        for(int i = list.length-1; i >= 0; i--) {
            if(list[i] == id && idTo == -1) {
                idTo = i;
                idFrom = i;
            } else if(list[i] == id) {
                idFrom = i;
            } else if(idTo != -1 && idFrom != -1) {
                break;
            }
        }
        int length = idTo - idFrom + 1;
        //Search forwards to see if there is a new spot for the block
        int to = -1;
        int from = -1;
        for(int i = 0; i <= idFrom; i++) {
            //If ended looking at empty spaces
            if(list[i] != -1) {
                //Check if there is enough space for the current block of ids
                if(to - from + 1 >= length && to != -1 && from != -1) {
                    //If so, swap their positions
                    for(int j = from; j <= from + length-1; j++) {
                        list[j] = id;
                    }
                    for(int j = idFrom; j <= idTo; j++) {
                        list[j] = -1;
                    }
                    break;
                } else {
                    //Otherwise reset the known position of the empty section
                    to = -1;
                    from = -1;
                }
            } else {
                //Update the size of the empty section
                if(from == -1) {
                    from = i;
                }
                to = i;
            }
        }
        return list;
    }
}