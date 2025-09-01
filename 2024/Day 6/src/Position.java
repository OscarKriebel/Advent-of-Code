import java.util.Arrays;
import java.util.Objects;

public class Position {
    public Integer[] pos;
    public Direction dir;

    public Position(Integer[] pos, Direction dir) {
        this.pos = pos;
        this.dir = dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Arrays.equals(pos, position.pos) && dir == position.dir;
    }
}
