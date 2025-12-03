first = 0
second = 0
#Get all factors for length of number
def getFactors(num):
    return [n for n in range(1, int(num/2) + 1) if num % n == 0]

#Check if the string is a match for the length of the split
def checkSplit(string, splitter):
    amount = int(len(string) / splitter)
    slice = string[0:splitter]
    for i in range(amount):
        #If it does not match, then we can return false for this instance
        if(slice != string[(i * splitter):(i * splitter + splitter)]):
            return False
    return True

with open("Day 2/input.txt", "r") as file:
    ids = file.readline().split(",")
    for space in ids:
        #For each range of ids
        for i in range(int(space.split("-")[0]), int(space.split("-")[1])+1):
            num = str(i)
            #Find out in what ways it can be split
            splits = getFactors(len(num))
            #Go in reverse to check the largest split first for part 1
            for split in splits[::-1]:
                if(checkSplit(num, split)):
                    second += i
                    #If split matches part 1
                    if(len(num) % 2 == 0 and split == int(len(num) / 2)):
                        first += i
                    break

print("First: {0}\nSecond: {1}".format(first, second))