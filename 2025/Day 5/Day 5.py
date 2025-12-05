#Take a list of ranges and a new range, and see if any of the ranges can be merged together
def mergeRanges(newest, valid):
    newValid = valid.copy()
    for fresh in valid:
        #Check for an overlap
        if(newest[0] <= fresh[1] and fresh[0] <= newest[1]):
            #Remove old range and create new range from the two ranges that overlap
            newValid.remove(fresh)
            newFresh = (min(newest[0], fresh[0]), max(newest[1], fresh[1]))
            #Now check if there are any new overlaps from the remaining ranges
            return mergeRanges(newFresh, newValid)
    #If no overlaps, just add new range and return
    newValid.append(newest)
    return newValid

first = 0
second = 0
valid = []
before = True
with open("Day 5/input.txt", "r") as file:
    for line in file.read().splitlines():
        if(before):
            #Get all valid ranges
            if(line == ""):
                before = False
            else:
                #Check if range can be merged with another range
                valid = mergeRanges((int(line.split("-")[0]), int(line.split("-")[1])), valid)
        else:
            #For each id, check if it is in the range of any other id
            for fresh in valid:
                if(int(line) >= fresh[0] and int(line) <= fresh[1]):
                    first += 1
                    break
#Add number of ids in each valid range
for fresh in valid:
    second += fresh[1] - fresh[0] + 1
print("First: {0}\nSecond: {1}".format(first, second))