public class Key {
    public int[] heights;
    public boolean isKey;

    public Key(int[] heights, boolean type) {
        this.heights = heights;
        this.isKey = type;
    }

    public Key(int first, int second, int third, int fourth, int fifth, boolean type) {
        this.heights = new int[]{first, second, third, fourth, fifth};
        this.isKey = type;
    }

    public boolean compare(Key other) {
        for(int i = 0; i < this.heights.length; i++) {
            if(this.heights[i] + other.heights[i] > 5) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        String output;
        if(this.isKey) {
            output = "Key: ";
        } else {
            output = "Lock: ";
        }
        for(int height : this.heights) {
            output += height + ",";
        }
        return output.substring(0,output.length()-1);
    }
}
