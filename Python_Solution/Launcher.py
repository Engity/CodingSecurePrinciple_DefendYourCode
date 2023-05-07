import Inputer
import Verifier
import os
import sys


"""
    Open and read everything inside the input file
Args:
    fileName: Name of the file to be read
Returns:
    None: if error(s) occur while reading
    []: if the file is empty
    Normal case: A list of contents inside the input file
"""

def readInput(fileName):
    res = []
    try:
        file = open(fileName, "r")
        lines = file.readlines()
        for line in lines:
            res.append(line)
        file.close()
    except Exception as e:
        Inputer.Inputer.logWriter(getattr(e, 'message', repr(e)))
        return None
    return res

"""
    Open and read everything inside the input file
Args:
    fileName: Name of the file to be read
Returns:
    [] if error(s) occur while reading or the file is empty
    A list of contents inside the input file
"""

def writeResult(firstName, lastname, number1, number2, inputContent, outputFileName = 'output.txt'):
    try:
        file = open(outputFileName, "w")
        file.write("User name: " + firstName + " " + lastname + "\n")
        file.write("\n")
            
        adding = str(number1 + number2)
        multi = str(number1 * number2)
        file.write("Result when adding: ")
        file.write(adding)
        file.write("\n")
        file.write("Result when multiplying: ")
        file.write(multi)
        file.write("\n\n")
            
        file.write("Input file content: \n")
        if (inputContent == None):
            file.write("Error while reading input file. It could be encoding error or the file cannot be accessed.")
        else:
            file.write("\"")
            for content in inputContent:
                file.write(content)
                file.write("\n")
            file.write("\"")
        file.close()
    except Exception as e:
        Inputer.Inputer.logWriter(getattr(e, 'message', repr(e)))
        print(getattr(e, 'message', repr(e)))
    
    return


#Main 
userFirstName = Inputer.Inputer.promptUser(
    "\nPlease input your first name (only alphabetic characters with the maximum length of 50 are allowed): ",
    "First name is invalid, please only enter alphabetic characters with the maximum length of 50."
        + "\nPlease try again.",
    Verifier.Verifier.verifyName,
)

userLastName = Inputer.Inputer.promptUser(
    "\nPlease input your last name (only alphabetic characters with the maximum length of 50 are allowed): ",
    "Last name is invalid, please only enter alphabetic characters with the maximum length of 50."
        + "\nPlease try again.",
    Verifier.Verifier.verifyName,
)

userNumber1 = Inputer.Inputer.promptUser(
    "\nPlease input an integer number (only number from [-2147483648, 2147483647] is allowed): ",
    "Number is invalid, please only enter number from [-2147483648, 2147483647]."
        + "\nPlease try again.",
    Verifier.Verifier.verifyInt,
)

userNumber2 = Inputer.Inputer.promptUser(
    "\nPlease input the other integer number (only number from [-2147483648, 2147483647] is allowed): ",
    "Number is invalid, please only enter number from [-2147483648, 2147483647]."
        + "\nPlease try again.",
    Verifier.Verifier.verifyInt,
)

userPassword = Inputer.Inputer.promptUser(
    "\nPlease input a password. "
    + "Password has to be at least 8 to 30 in length."
    + "\nIt must contain at least a number, Capital letter, non-capital letter."
    + '\nIt must contain at least one of these special characters, !@#$%^&()_+\-={}:;"\|,.<>\/?\n'
    + "\nPlease input your password: ",
    "\nIncorrect format. Please try again: ",
    Verifier.Verifier.verifyPassword,
    True,
    "Please confirm your password: ",
)

inputFile = Inputer.Inputer.promptUser(
    "\nPlease enter name of the input file name, it cannot not contain special character except _ and -; allow number and alphabetic characters and has to end with .txt\n"
    + "File name cannot be 'temp.txt'\n",
    "Incorrect format. Please try again\n",
    Verifier.Verifier.verifyFileName,
)

while(True):
    outputFile = Inputer.Inputer.promptUser(
        "\nPlease enter name of the ouput file name, it cannot not contain special character except _ and -; allow number and alphabetic characters and has to end with .txt\n"
        + "File name cannot be 'temp.txt' and cannot be the same as the input file\n",
        "Incorrect format. Please try again\n",
        Verifier.Verifier.verifyFileName,
    )
    if (inputFile != outputFile):
        break

inputContent = readInput(inputFile)
writeResult(userFirstName, userLastName, userNumber1, userNumber2, inputContent, outputFile)

#remove temporary file
try:
    os.remove("temp.txt")
except Exception as e:
    Inputer.Inputer.logWriter(getattr(e, 'message', repr(e)))
