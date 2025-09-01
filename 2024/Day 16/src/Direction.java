public enum Direction {
    NORTH('^', new int[]{0, -1}),
    SOUTH('V', new int[]{0, 1}),
    EAST('>', new int[]{1,0}),
    WEST('<', new int[]{-1,0});

    public final char arrow;
    public final int[] move;
    private Direction(char arrow, int[] move) {
        this.arrow = arrow;
        this.move = move;
    }

    public Direction getOpposite() {
        if(this == NORTH) {
            return SOUTH;
        } else if(this == SOUTH) {
            return NORTH;
        } else if(this == EAST) {
            return WEST;
        } else {
            return EAST;
        }
    }
}
