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
    def promptUser(displayPrompt="", reentryPrompt="", checkFunc=lambda: True):
        while True:
            # Try to read the input
            try:
                inputString = input(displayPrompt)
            except:
                # Error reading the input
                print("Error while trying to read the input")
                return False
            # Checking whether the user input is correct or not
            if checkFunc(inputString):
                break
            else:
                print(reentryPrompt)
                continue
        return inputString

userFirstName = Inputer.promptUser(
    "Please input your first name (only alphabetic characters with the maximum length of 50 are allowed): ",
    "First name is invalid, please only enter alphabetic characters with the maximum length of 50."
        + "\nPlease try again.",
    Verifier.Verifier.verifyName,
)

userLastName = Inputer.promptUser(
    "Please input your last name (only alphabetic characters with the maximum length of 50 are allowed): ",
    "First name is invalid, please only enter alphabetic characters with the maximum length of 50."
        + "\nPlease try again.",
    Verifier.Verifier.verifyName,
)

print("Hello " + userFirstName + " " + userLastName)
