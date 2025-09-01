import java.util.Objects;

public class Coordinate {
    public int x;
    public int y;
    public char type;

    public Coordinate(int x, int y, char type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, type);
    }
}
