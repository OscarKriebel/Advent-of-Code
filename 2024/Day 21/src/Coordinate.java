import java.util.Objects;

public class Coordinate {
    public int x;
    public int y;
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate add(int x, int y) {
        return new Coordinate(this.x + x, this.y + y);
    }

    public Coordinate add(Coordinate other) {
        return new Coordinate(this.x + other.x, this.y + other.y);
    }

    public Coordinate subtract(int x, int y) {
        return new Coordinate(this.x - x, this.y - y);
    }

    public Coordinate subtract(Coordinate other) {
        return new Coordinate(this.x - other.x, this.y - other.y);
    }

    public Coordinate copy() {
        return new Coordinate(this.x, this.y);
    }

    public int distance(int x, int y) {
        return Math.abs(this.x - x) + Math.abs(this.y - y);
    }

    public int distance(Coordinate other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    public String convertDirection() {
        if(this.x == -1 && this.y == 0) {
            return "<";
        } else if(this.x == 1 && this.y == 0) {
            return ">";
        } else if(this.x == 0 && this.y == -1) {
            return "^";
        } else if(this.x == 0 && this.y == 1) {
            return "v";
        } else if(this.x == 0 && this.y == 0){
            return "A";
        } else {
            return toString();
        }
    }

    public String toString() {
        return this.x + ", " + this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
