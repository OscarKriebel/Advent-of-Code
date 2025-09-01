import java.util.Objects;

public class Coordinate {
    public int x;
    public int y;
    public Direction dir;
    public Coordinate(int x, int y, Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y && dir == that.dir;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, dir);
    }

    public String toString() {
        return this.x + ", " + this.y + ": " + dir.arrow;
    }
}
