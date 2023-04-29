import re

class Verifier:
    def __init__(self):

        return

    #verify whether the entry is an int
    #return a number if it is an int
    #return false if entry is not an int
    def verifyInt(self, entry):
        try:
            convertedInt = int(entry)
            #Too big
            if (convertedInt > 2147483647 or convertedInt < -2147483648):
                return False
        except:
            return False
        return convertedInt
    
    #verify whether the entry is a name
    #return the entry if it is a name
    #return false if entry is not a name
    def verifyName(self, entry):
        # Name condition: cannot contains numbers or special characters, can only be from 1 to 50 characters
        x = re.search("^[a-zA-Z]{1,50}$", entry) # Length can only be 50 characters
        if (x == None):
            return False
        return entry

    #verify whether the entry is a file name
    #return the entry if it is a file name
    #return false if entry is not a file name
    def verifyFileName(self, entry):
        # File Name Condition does not contain special character except _ and -, allow number and alphabetic characters; Has to end with .txt
        x = re.search("^[^~)('!*<>:;,?\"*|/]+\.txt$", entry) # Regex to check file name, allow number and character and only file with txt extension
        if (x == None):
            return False
        return entry
    
    #verify whether the entry is a valid password
    #return the entry if it is a valid password
    #return false if entry is not a valid password
    def verifyPassword(self, entry):
        # Password Condition: 
        x = re.search("^[^~)('!*<>:;,?\"*|/]+\.txt$", entry) # Regex to check file name, allow number and character and only file with txt extension
        if (x == None):
            return False
        return entry



verifier = Verifier()
print(verifier.verifyInt("12312312123"))
print(verifier.verifyInt("2147483648"))
print(verifier.verifyInt("29"))

print(verifier.verifyName("TAsd12213123@"))
print(verifier.verifyName("Toan"))
print(verifier.verifyName(""))

print(verifier.verifyFileName("t.txt"))
print(verifier.verifyFileName(".txt"))
print(verifier.verifyFileName("_asdasd.txt"))

# prompts for and reads the user's first name, then last name -- each should be at most 50 characters -- decide what is legitimate for characters for first and last name
    # You must make it clear to the user what is expected for input (describe range, acceptable characters, and anything else you feel is important)
# prompts for and reads in two int values from the user (range of values are those of a 4 byte int)
    # Once again, make clear to user what is expected. Note that an int can be from -2,147,...,... to 2,147,...,...
# prompts for reads the name of an input file from the user
    # Make clear to user what is expected and will be accepted
# prompts for reads the name of an output file from the user
    # Same here
# prompts the user to enter a password, store the password, then ask the user to re-enter the password and verify that it is correct