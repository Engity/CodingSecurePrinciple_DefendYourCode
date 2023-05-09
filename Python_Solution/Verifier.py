#author Mey Vo, Toan Nguyen, Tinh Diep
import re

class Verifier:
    @staticmethod
    def verifyInt(entry):
        try:
            if (entry[0] == '+'):
                convertedInt = int(entry[1:])
            else:
                convertedInt = int(entry)
            # Too big
            if convertedInt > 2147483647 or convertedInt < -2147483648:
                return False
        except:
            return False
        return convertedInt

    # verify whether the entry is a name
    # return the entry if it is a name
    # return false if entry is not a name
    @staticmethod
    def verifyName(entry):
        # Name condition: cannot contains numbers or special characters, can only be from 1 to 50 characters
        x = re.search("^[a-zA-Z]{1,50}$", entry)  # Length can only be 50 characters
        if x == None:
            return False
        return entry

    # verify whether the entry is a file name
    # return the entry if it is a file name
    # return false if entry is not a file name
    @staticmethod
    def verifyFileName(entry):
        # File Name Condition does not contain special character except _ and -, allow to have number and alphabetic characters; Has to end with .txt
        # File name cannot also be temp.txt
        x = re.search(
            "(?!^temp\.txt$)^([\w\-_]+)\.txt$", entry
        )  # Regex to check file name, allow number and character and only file with txt extension
        if x == None:
            return False
        return entry

    # verify whether the entry is a valid password
    # return the entry if it is a valid password
    # return false if entry is not a valid password
    @staticmethod
    def verifyPassword(entry):
        # Password Condition:
        x = re.search(
            "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])"
            + "(?=.*[!@#$%^&()_+\-={}:;\"\|,.<>\/?])"
            + "(^[A-Za-z0-9!@#$%^&()_+\-={}:;\"\|,.<>\/? ]{8,30}$)"
            , entry
        )  # Regex to check file name, allow number and character and only file with txt extension
        if x == None:
            return False
        return entry


#
# print(Verifier.verifyInt("12312312123"))
# print(Verifier.verifyInt("2147483648"))
# print(Verifier.verifyInt("29"))

# print(Verifier.verifyName("TAsd12213123@"))
# print(Verifier.verifyName("Toan"))
# print(Verifier.verifyName(""))

# print(Verifier.verifyFileName("t.txt"))
# print(Verifier.verifyFileName(".txt"))
# print(Verifier.verifyFileName("_asdasd.txt"))

# Inputer.promptUser("Please input your name: ", "Name is invalid",)

# prompts for and reads the user's first name, then last name -- each should be at most 50 characters -- decide what is legitimate for characters for first and last name
# You must make it clear to the user what is expected for input (describe range, acceptable characters, and anything else you feel is important)
# prompts for and reads in two int values from the user (range of values are those of a 4 byte int)
# Once again, make clear to user what is expected. Note that an int can be from -2,147,...,... to 2,147,...,...
# prompts for reads the name of an input file from the user
# Make clear to user what is expected and will be accepted
# prompts for reads the name of an output file from the user
# Same here
# prompts the user to enter a password, store the password, then ask the user to re-enter the password and verify that it is correct

