first = 0
second = 0
problems = []
lines = []
with open("Day 6/input.txt", "r") as file:
    for line in file.read().splitlines():
        #Parse input to get just numbers, and reverse of line for part 2
        items = [x for x in line.split(" ") if x != ""]
        lines.append(line[::-1])
        #Set up array for containing the numbers
        if(len(problems) == 0):
            for i in range(len(items)):
                problems.append([])
        #Either add numbers to problem array or do the addition/multiplication
        for i, item in enumerate(items):
            if(item.isnumeric()):
                problems[i].append(int(item))
            else:
                total = 0
                if(item == "+"):
                    for num in problems[i]:
                        total += num
                else:
                    total = 1
                    for num in problems[i]:
                        total *= num
                first += total 
problem = []
#In part 2, cover each column of the input in reverse
for i in range(len(lines[0])):
    num = ""
    #Put together the number sections
    for line in range(len(lines)-1):
        num += lines[line][i]
    #Add to the numbers for the problem if it is a valid number to skip over empty columns
    if(num.strip().isnumeric()):
        problem.append(int(num.strip()))
    #Final line will contain the operation, so if seen do operation and reset the numbers for the problem
    if(lines[len(lines)-1][i] == "+"):
        total = 0
        for prob in problem:
            total += prob
        second += total
        problem = []
    elif(lines[len(lines)-1][i] == "*"):
        total = 1
        for prob in problem:
            total *= prob
        second += total
        problem = []

print("First: {0}\nSecond: {1}".format(first, second))