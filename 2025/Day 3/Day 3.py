#Take a bank and the number of digits needed from it
#Largest digit that allows for enough digits to be selected is taken
def findLargest(bank, amount):
    #End recursion if all digits have been selected
    if(amount == 0):
        return ""
    largest = "-1"
    index = -1
    #Find largest character in range
    for char in range(len(bank)+1-amount):
        if(int(bank[char]) > int(largest)):
            largest = bank[char]
            index = char
    #Concat with all remaining digits to be found
    return largest + findLargest(bank[index+1:], amount-1)

first = 0
second = 0
with open("Day 3/input.txt", "r") as file:
    for line in file.read().splitlines():
        first += int(findLargest(line, 2))
        second += int(findLargest(line, 12))

print("First: {0}\nSecond: {1}".format(first, second))