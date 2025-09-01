import java.util.*;

public class Plot {
    private List<Coordinate> plants;
    private char type;
    private long area = 0L;
    private long perimeter = 0L;
    private long sides = 0L;

    public Plot(char type, List<Coordinate> plants) {
        this.type = type;
        this.plants = plants;
    }

    public long getFirstCost() {
        this.calcArea();
        this.calcPerimeter();
        return this.area * this.perimeter;
    }

    public long getSecondCost() {
        this.calcArea();
        this.calcSides();
        return this.area * this.sides;
    }

    public void calcArea() {
        this.area = this.plants.size();
    }

    public void calcPerimeter() {
        long perim = 0L;
        for(Coordinate plant : plants) {
            //Check up
            if(!plants.contains(plant.add(0,-1))) {
                perim++;
            }
            //Check down
            if(!plants.contains(plant.add(0,1))) {
                perim++;
            }
            //Check left
            if(!plants.contains(plant.add(-1,0))) {
                perim++;
            }
            //Check down
            if(!plants.contains(plant.add(1,0))) {
                perim++;
            }
        }
        this.perimeter = perim;
    }

    public void calcSides() {
        //find corners
        long sides = 0;
        for(Coordinate plant : this.plants) {
            //Inner:      Outer:
            //    OX          OO
            //    XX          OX

            //Top left
            if(this.plants.contains(plant.add(-1, 0)) && this.plants.contains(plant.add(0,-1))  && !this.plants.contains(plant.add(-1, -1))) {
                sides++;
            } else if(!this.plants.contains(plant.add(-1, 0)) && !this.plants.contains(plant.add(0,-1))) {
                sides++;
            }
            //Top right
            if(this.plants.contains(plant.add(1, 0)) && this.plants.contains(plant.add(0,-1))  && !this.plants.contains(plant.add(1, -1))) {
                sides++;
            } else if(!this.plants.contains(plant.add(1, 0)) && !this.plants.contains(plant.add(0,-1))) {
                sides++;
            }
            //Bottom left
            if(this.plants.contains(plant.add(-1, 0)) && this.plants.contains(plant.add(0,1))  && !this.plants.contains(plant.add(-1, 1))) {
                sides++;
            } else if(!this.plants.contains(plant.add(-1, 0)) && !this.plants.contains(plant.add(0,1))) {
                sides++;
            }
            //Bottom right
            if(this.plants.contains(plant.add(1, 0)) && this.plants.contains(plant.add(0,1))  && !this.plants.contains(plant.add(1, 1))) {
                sides++;
            } else if(!this.plants.contains(plant.add(1, 0)) && !this.plants.contains(plant.add(0,1))) {
                sides++;
            }
        }
        this.sides = sides;
    }

    public String getString() {
        return this.type + ": "  + this.plants.toString();
    }
}
