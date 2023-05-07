import datetime
import hashlib
import hmac
import os

class Inputer:

    """
    Helping method for logging errors and time into a file
    Args:
        content: the string array of what content should be printed into the file
        fileName: File name to be save, default as log.txt
    Returns:
    Raises:

    """
    @staticmethod
    def logWriter(content, fileName="log.txt"):
        file = open(fileName, "a")
        file.write(datetime.datetime.now().strftime("%m/%d/%Y, %H:%M:%S") + "\n")
        file.writelines(content)
        file.writelines("\n\n")
        file.close

    """
        Hash the provided password with a randomly-generated salt and return the
        salt and hash to store in the database.
    Args:
        password: the password to be hashed
    Returns:
        Return [the salt, hashed password]
    Raises:
    """
    def hashPassword(password):
        salt = os.urandom(16)
        hashedPW = hashlib.pbkdf2_hmac("sha256", password.encode(), salt, 100000)
        return [hashedPW, salt]

    """
        Check whether a given password is correct and match with the hash and salted password.
    Args:
        salt: The salt used in creating this hashed password
        hashedPW: The hashed password
        password: the password to be checked
    Returns:
        True if the hash matches with the password, False other wise
    Raises:
    """
    def checkPassword(salt, hashedPW, password):
        return hmac.compare_digest(
            hashedPW, hashlib.pbkdf2_hmac("sha256", password.encode(), salt, 100000)
        )
    
    """
        Store the password and the salt into a temporary file
    Args:
        hashedPW: The hashed password
        salt: The salt used in creating this hashed password
    Returns:
        None if error occurs while writing to file
        True if writing to file successfully
    Raises:
    """
    def storePassword(hashedPW, salt):
        try:
            file = open("temp.txt", "wb")
            file.write(salt)
            file.write(hashedPW)
            file.close
            return True
        except Exception as e:
            Inputer.logWriter(getattr(e, 'message', repr(e)))
            return None

    """
        Open the temporary file and read in the hashed password and salt, then delete it
    Args:
    Returns:
        [None, None] if the temporary is not found or has problem openning it
        [The salt, hashed password]
    Raises:
    """
    def retrievePassword():
        try:
            file = open("temp.txt", "rb")
            fileSalt = file.read(16)
            hashed = file.read()
            file.close
            return [hashed, fileSalt]
        except Exception as e:
            Inputer.logWriter(getattr(e, 'message', repr(e)))
            return [None, None]
    
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
    def promptUser(
        displayPrompt="",
        reentryPrompt="",
        checkFunc=lambda: True,
        passwordConfirmation=False,
        confirmationPrompt="",
    ):
        res = None
        while True:
            # Try to read the input
            try:
                inputString = input(displayPrompt)
            except Exception as e:
                # Error reading the input
                print("Error while trying to read the input")
                Inputer.logWriter(getattr(e, 'message', repr(e)))
                return False
            # Checking whether the user input is correct or not
            res = checkFunc(inputString)
            if res:
                if passwordConfirmation:
                    passConfirmed = False
                    while(True):
                        try:
                            # Store the password
                            [pw, saltPW] = Inputer.hashPassword(inputString)
                            if Inputer.storePassword(pw, saltPW):
                                print("Successfully saving password.")
                            else:
                                print("Error while trying to save password.")  
                                return False
                                
                            #Prompt for reconfirmation
                            confirmationInput = input(confirmationPrompt)
                        except Exception as e:
                            Inputer.logWriter(getattr(e, 'message', repr(e)))
                            # Error reading the input
                            print("Error while trying to read the input")
                            return False
                        #Try retrieving the password
                        [hashedPW, salt] = Inputer.retrievePassword()
                        if (salt and hashedPW):
                            if Inputer.checkPassword(salt, hashedPW, confirmationInput):
                                print(
                                    "\nPassword Confirmed."
                                )
                                passConfirmed = True
                                break
                            else:
                                print(
                                    "\nInput does not match with the previous entry. Please enter again."
                                )
                                continue     
                        else:
                            print("Error getting the password")
                            return False
                    if (passConfirmed):
                        break
                else:
                    break
            else:
                print(reentryPrompt)
                continue
        return res