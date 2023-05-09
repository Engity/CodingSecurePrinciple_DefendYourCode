import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Scanner;

/**
 * This class centralizes the behavior for reading, validating,
 * storing, and writing-out inputs from user's input
 *
 * The class also includes salt and hashing algorithm to protect
 * password from user's input and also keep track a log file if
 * any error occurs
 *
 * @author Mey Vo, Toan Nguyen, Tinh Diep
 * @version 5 May 2023
 */
public class PromptingInput {
    /**
     * The verifier to verifier user's inputs
     */
    private final Verifier VERIFIER = new Verifier();
    /**
     * The scanner to scan user's inputs
     */
    private final Scanner myInput;
    /**
     * The string builder to keep track of error messages
     */
    final StringBuilder myLog = new StringBuilder();
    /**
     * The string to store name retrieved from user's input
     */
    String myStoredName;
    /**
     * The integer to store integer value retrieved from user's input
     */
    int myStoredInteger;
    /**
     * The string to store file's name retrieved from user's input
     */
    String myStoredFileName;
    /**
     * The byte array contains byte value for salt
     */
    private byte[] mySalt;
    /**
     * The boolean to check if the file is found during password
     * validation process
     */
    private boolean myIsPasswordFileFound = true;

    PromptingInput() {
        myInput = new Scanner(System.in);
    }

    /**
     * Performs validation and storing user input
     * on specific prompts' description
     *
     * @param theDisplayPrompt      The prompt description
     * @param theReEntryPrompt      The prompt description when input is invalid
     * @param theConfirmationPrompt The prompt description for password
     * @param theChoice             The integer choice represent specific prompt
     */
    void promptUser(String theDisplayPrompt, String theReEntryPrompt,
                    String theConfirmationPrompt, int theChoice) {
        String userInput;
        boolean result = false;
        boolean retries = false;
        do {
            //Keep prompting until get a valid result
            //Retries will have different prompt message
            if (retries) {
                System.out.println(theReEntryPrompt);
            } else {
                System.out.println(theDisplayPrompt);
            }
            userInput = myInput.nextLine();

            switch (theChoice) {
                case 1 -> {
                    //Validates name and store it if valid
                    result = VERIFIER.checkName(userInput);
                    if (result) {
                        myStoredName = userInput;
                    } else {
                        retries = true;
                    }
                }

                case 2 -> {
                    //Validates integer and store it if valid
                    if (VERIFIER.checkInt(userInput) != null) {
                        myStoredInteger = VERIFIER.checkInt(userInput);
                        result = true;
                    } else {
                        retries = true;
                    }
                }

                case 3 -> {
                    //Validates file's name and store it if valid
                    result = VERIFIER.checkFileName(userInput);
                    if (result) {
                        myStoredFileName = userInput;
                    } else {
                        retries = true;
                    }
                }

                case 4 -> {
                    //Validates password
                    //If valid, encrypt the password and store to a file
                    //Retrieve salt code and hash code
                    //Validate the password again with encrypted password
                    //Keep prompting if the re-entry password did not match
                    //If file not found, exist the loop
                    result = VERIFIER.checkPassword(userInput);
                    if (result) {
                        String fileName = getPasswordStoringFileName();
                        String hashedPassword = securePassword(userInput);
                        storePassword(hashedPassword, fileName);
                        String theSalt = "";
                        String theHash  = "";

                        try (BufferedReader reader = new BufferedReader(new FileReader(
                                fileName))) {
                            theSalt = reader.readLine(); //The encrypted salt code
                            theHash = reader.readLine(); //The encrypted password hash code
                        } catch (IOException e) {
                            System.out.println(
                                    "There is an error retrieving password process" +
                                    "\nPlease refer to ErrorLogs.txt to see the error"
                            );
                            myIsPasswordFileFound = false; //File not found
                            logErrors(e); //Catch and store error

                        }
                        while (myIsPasswordFileFound) {
                            System.out.println(theConfirmationPrompt);
                            String reTypedPassword = myInput.nextLine();
                            if (validatePassword(reTypedPassword,theSalt , theHash)) {
                                System.out.println("Matched!!");
                                break;
                            } else if (myIsPasswordFileFound) {
                                //Display only in the case file is found
                                System.out.println("Wrong password,try again");
                            }
                        }
                    } else {
                        retries = true;
                    }
                }
            }
        } while (!result);
    }

    /**
     * Stores encrypted password in the given file's name
     * entered by the user
     *
     * @param theEncryptedPassword The string encrypted password
     * @param theFileName          The string file's name
     */
    private void storePassword(String theEncryptedPassword,
                               String theFileName) {
        try (FileOutputStream file = new FileOutputStream(theFileName);
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(file))) {
            //The file will contain encrypted salt code and encrypted password
            writer.write(Base64.getEncoder().encodeToString(mySalt)
                    + "\n" + theEncryptedPassword + "\n");
            System.out.println("Password stored successfully!");
        } catch (Exception e) {
            System.out.println(
                    "There is an error storing password process" +
                    "\nPlease refer to ErrorLogs.txt to see the error"
            );
            logErrors(e); //Catch and store error
        }
    }

    /**
     * Validates password to see if it matches
     * the encrypted password stored in a file
     *
     * @param thePassword The string password entered by the user
     * @param theSalt The salt code for the password
     * @param theHash The hash code of the password
     * @return The boolean true or false if it matched
     */
    private boolean validatePassword(String thePassword, String theSalt, String theHash) {
        if (theSalt != null && theHash != null) {
            String hashedPassword = securePassword(thePassword);
            return theSalt.equals(Base64.getEncoder()
                    .encodeToString(mySalt))
                    && hashedPassword.equals(theHash);
        }
        return false;
    }

    /**
     * Adding salt and perform hash on password
     *
     * @param thePassword The password entered by the user
     * @return The string encrypted password
     */
    private String securePassword(String thePassword) {
        if (mySalt == null) {
            generateSalt();
        }

        try {
            //Process of hashing password
            //Using message digest and SHA-512 hash algorithm
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(mySalt);
            byte[] hashedPassword = md.digest(
                    thePassword.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(
                    "There is an error securing password process" +
                    "\nPlease refer to ErrorLogs.txt to see the error"
            );
            logErrors(e);
            return "";
        }
    }

    /**
     * Generate random bytes for salt
     */
    private void generateSalt() {
        try {
            //Process of generating salt if null using secure random
            SecureRandom random = SecureRandom.getInstanceStrong();
            mySalt = new byte[10];
            random.nextBytes(mySalt);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(
                    "There is an error generating mySalt process" +
                    "\nPlease refer to ErrorLogs.txt to see the error"
            );
            logErrors(e); //Catch and store error
        }
    }

    /**
     * Write the specific inputs entered by the user to a file
     *
     * @param theFirstName     The string first name retrieved from
     *                         user's input
     * @param theLastName      The string last name retrieved from
     *                         user's input
     * @param theFirstInteger  The first integer retrieved value from
     *                         user's input
     * @param theSecondInteger The second integer retrieved value from
     *                         user's input
     * @param theInputFile     The input file's name retrieved from
     *                         user's input
     * @param theOutputFile    The output file's name retrieved from
     *                         user's input
     */
    void writeOutputFile(String theFirstName, String theLastName,
                         long theFirstInteger, long theSecondInteger,
                         String theInputFile, String theOutputFile,
                         boolean theIsInputFileFound) {
        try (FileOutputStream file = new FileOutputStream(theOutputFile);
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(file))) {
            String result = //Formatting result for inputs
                    "First name: " + theFirstName +
                            "\nLast Name: " + theLastName +
                            "\nInteger a: " + theFirstInteger +
                            "\nInteger b: " + theSecondInteger +
                            "\na + b: " + (theFirstInteger + theSecondInteger) +
                            "\na * b: " + (theFirstInteger * theSecondInteger);
            writer.write(result);
            writer.newLine();

            //Copy contents form the input file to output file
            //If file is found then proceed to copy, else report
            if(theIsInputFileFound) {
                String line;
                writer.write(
                        "\nInput file's name: " + theInputFile +
                        "\nContents in the file: ");
                BufferedReader reader = new BufferedReader(
                        new FileReader(theInputFile));
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
                reader.close();
            } else {
                writer.write(
                        "\nInput file's name: " +
                                "File " + theInputFile + " not found!");
            }
        } catch (Exception e) {
            writeOutputFile(theFirstName, theLastName,
                            theFirstInteger, theSecondInteger,
                            theInputFile, theOutputFile, false);
            System.out.println(
                    "There is an error storing all input to file" +
                    "\nPlease refer to ErrorLogs.txt to see the error"
            );
            logErrors(e); //Catch and store error
        }
    }

    /**
     * Prompts for user's input to get the file's name to store user's password
     *
     * @return The string file's name
     */
    private String getPasswordStoringFileName() {
        promptUser(
                "Please input your file's name to store your password" +
                        " (special characters allowed except _ and -, " +
                        "number and alphabetic characters allowed; " +
                        "Has to end with .txt): ",
                "File's name is invalid, please re-enter, " +
                        "alphabetic characters, numbers " +
                        "and special characters allowed except _ and -" +
                        "\n(File name has to end with .txt)"
                        + "\nPlease try again.", "", 3);
        return myStoredFileName;
    }

    /**
     * Formats, generates, and stores error report when an exception is caught
     *
     * @param theError The error type
     */
    void logErrors(Exception theError) {
        myLog.append(LocalDateTime.now().
                format(DateTimeFormatter.
                        ofPattern("MM-dd-yyy, HH:mm:ss\n")));
        myLog.append(theError.getClass())
                .append("\n")
                .append(theError.getMessage()).append("\n");
    }

    /**
     * Writes out errors caught during program running process to a file
     *
     * @param theLog The string builder contains error messages
     */
    void generateErrorLogFile(StringBuilder theLog) {
        try (FileOutputStream file = new FileOutputStream("ErrorLogs.txt");
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(file))) {
            writer.write(theLog.toString());
        } catch (Exception e) {
            System.out.println(
                    "There is an error generating log file process" +
                    "\nPlease refer to ErrorLogs.txt to see the error"
            );
        }
    }
}
