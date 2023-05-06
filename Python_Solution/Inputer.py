import Verifier


class Inputer:
    """
    Helping method for prompting user, will repeat the prompt again if the answer is in invalid form

    Args:
        displayPrompt: Prompt to display to ask for your user input at first
        reentryPrompt: Prompt to display after the user entered in incorrect format.
        checkFunc: Function to check for validness. Default: All input is valid.

    Returns:
        Valid string input from the user

    Raises:
    """

    @staticmethod
    def promptUser(displayPrompt="", reentryPrompt="", checkFunc=lambda: True, confirmation = False, confirmationPrompt = ""):
        res = None
        while True:
            # Try to read the input
            try:
                inputString = input(displayPrompt)
            except:
                # Error reading the input
                print("Error while trying to read the input")
                return False
            # Checking whether the user input is correct or not
            res = checkFunc(inputString)
            if res:
                if (confirmation):
                    try:
                        confirmationInput = input(confirmationPrompt)
                    except:
                        # Error reading the input
                        print("Error while trying to read the input")
                        return False
                    if (confirmationInput != inputString):
                        print("Input does not match with the previous entry. Please enter again.")
                        continue
                    else:
                        break

                else:
                    break
            else:
                print(reentryPrompt)
                continue
        return res

# userFirstName = Inputer.promptUser(
#     "\nPlease input your first name (only alphabetic characters with the maximum length of 50 are allowed): ",
#     "First name is invalid, please only enter alphabetic characters with the maximum length of 50."
#         + "\nPlease try again.",
#     Verifier.Verifier.verifyName,
# )

# userLastName = Inputer.promptUser(
#     "\nPlease input your last name (only alphabetic characters with the maximum length of 50 are allowed): ",
#     "Last name is invalid, please only enter alphabetic characters with the maximum length of 50."
#         + "\nPlease try again.",
#     Verifier.Verifier.verifyName,
# )

# userNumber = Inputer.promptUser(
#     "\nPlease input an integer number (only number from [-2147483648, 2147483647] is allowed): ",
#     "Number is invalid, please only enter number from [-2147483648, 2147483647]."
#         + "\nPlease try again.",
#     Verifier.Verifier.verifyInt,
# )

# print(userNumber ** 2)
# print ((1 << 62) - 1)

userPassword = Inputer.promptUser(
    "\nPlease input a password. "
    + "Password has to be at least 8 to 30 in length."
        + "\nIt must contain at least a number, Capital letter, non-capital letter."
        + "\nIt must contain at least one of these special characters, !@#$%^&()_+\-={}:;\"\|,.<>\/?\n"
        + "\nPlease input your password: ",
    "Password has to be at least 8 to 30 in length."
        + "\nIt must contain at least a number, Capital letter, non-capital letter."
        + "\nIt must contain at least one of these special characters, !@#$%^&()_+\-={}:;\"\|,.<>\/?\n"
        + "\nPlease input your password: "
        + "\nPlease try again: ",
    Verifier.Verifier.verifyPassword,
    True,
    "Please confirm your password: "
)

print("Password is", userPassword)