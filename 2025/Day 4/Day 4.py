#Returns a list of coordinates of all spots that contain a valid roll of paper
def checkOpenness(grid):
    #For each iem in the grid
    valid = []
    for i, row in enumerate(grid):
        for j, item in enumerate(row):
            #Only look at toilet paper
            if(item == "@"):
                total = 0
                for x in range(-1, 2):
                    for y in range(-1, 2):
                        #Checking all valid spots around the roll
                        if((not (x == 0 and y == 0)) and (i + y >= 0 and i + y < len(grid)) and (j + x >= 0 and j + x < len(row))):
                            if(grid[i + y][j + x] == "@"):
                                total += 1
                if(total < 4):
                    valid.append([j, i])
    return valid

first = 0
second = 0
grid = []
#Construct grid
with open("Day 4/input.txt", "r") as file:
    for line in file.read().splitlines():
        grid.append(line)

#Loop while changes can still be made
finished = False
while(not finished):
    spots = checkOpenness(grid)
    #If first go around, set the answer to part 1 here
    if(first == 0):
        first = len(spots)
    second += len(spots)
    #If nothing changed then we can stop
    if(len(spots) == 0):
        finished = True
    else:
        #Replace all valid spots on the grid with an empty spot
        for coord in spots:
            grid[coord[1]] = grid[coord[1]][:coord[0]] + "." + grid[coord[1]][coord[0]+1:]

print("First: {0}\nSecond: {1}".format(first, second))