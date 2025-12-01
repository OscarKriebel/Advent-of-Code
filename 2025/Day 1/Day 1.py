position = 50
first = 0
second = 0
with open("Day 1/input.txt", "r") as file:
    for line in file.readlines():
        #If left we are decreasing, if right we are increasing
        #Increment by 1 for each click
        clicks = []
        if(line[0] == "L"):
            clicks = [-1] * int(line[1:])
        else:
            clicks = [1] * int(line[1:])
        #Check if we pass over 0 during rotation for part 2
        for move in clicks:
            position += move
            if(position % 100 == 0):
                second += 1
        position %= 100
        #At 0 for part 1
        if(position == 0):
            first += 1         
print("First: {0}\nSecond: {1}".format(first, second))
