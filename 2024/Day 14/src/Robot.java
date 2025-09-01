public class Robot {
    private int[] position;
    private int[] velocity;

    public Robot(int[] position, int[] velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public int[] getPosition() {
        return this.position;
    }

    //Iterative
    public void move(int height, int width) {
        this.position[0] += this.velocity[0];
        this.position[1] += this.velocity[1];
        this.position[0] = Math.floorMod(this.position[0], width);
        this.position[1] = Math.floorMod(this.position[1], height);
    }

    //Jumps
    public void move(int height, int width, int iterations) {
        this.position[0] = Math.floorMod((this.position[0] + this.velocity[0] * iterations), width);
        this.position[1] = Math.floorMod((this.position[1] + this.velocity[1] * iterations), height);
    }
}
