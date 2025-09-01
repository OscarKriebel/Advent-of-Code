import java.util.Objects;

public class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "X: " + this.x + ", Y: " + this.y;
    }
    //Difference between two coordinates
    public Coordinate to(Coordinate other) {
        return new Coordinate(other.x - this.x, other.y - this.y);
    }
    //Add coordinate from another
    public Coordinate add(Coordinate other) {
        return new Coordinate(this.x + other.x, this.y + other.y);
    }
    //Subtract coordinate from another
    public Coordinate subtract(Coordinate other) {
        return new Coordinate(this.x - other.x, this.y - other.y);
    }
    //Hash for HashSet and HashMap
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    //Check if node is inside a given width and height
    public boolean inBounds(int width, int height) {
        return (this.x >= 0 && this.y >= 0 && this.x < width && this.y < height);
    }
    //Equals for HashSet and HashMap
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }
}
