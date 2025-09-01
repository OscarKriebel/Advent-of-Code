public enum Direction {
    UP, DOWN, LEFT, RIGHT;
    public Direction rotation;

    static {
        UP.rotation = RIGHT;
        DOWN.rotation = LEFT;
        LEFT.rotation = UP;
        RIGHT.rotation = DOWN;
    }
}
