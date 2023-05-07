import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class PromptingInput {
    /**
     * The verifier to verifier user's inputs
     */
    private final Verifier myVerifier = new Verifier();
    /**
     * The scanner to scan user's inputs
     */
    private final Scanner myInput;
    /**
     * The string builder to keep track of error messages
     */
    private final StringBuilder log = new StringBuilder();
    /**
     * The string to store name retrieved from user's input
     */
    private String myStoredName;
    /**
     * The integer to store integer value retrieved from user's input
     */
    private int myStoredInteger;
    /**
     * The string to store password retrieved from user's input
     */
    private String myPassword;
    /**
     * The string to store file's name retrieved from user's input
     */
    private String myStoredFileName;
    /**
     * The byte array contains byte value for salt
     */
    private byte[] mySalt;
    /**
     * The boolean to check if the file is found during password
     * validation process
     */
    private boolean fileFound = true;

    PromptingInput() {
        myInput = new Scanner(System.in);
    }

    public static void main(String[] args) {
        PromptingInput test = new PromptingInput();
        test.promptUser(
                "Please input your first name " +
                "(only alphabetic characters " +
                "with the maximum length of 50 are allowed): ",
                "First name is invalid, please only enter " +
                "alphabetic characters with the maximum length of 50."
                + "\nPlease try again.", "", 1);
        String firstName = test.myStoredName;
        test.promptUser(
                "Please input your last name " +
                "(only alphabetic characters " +
                "with the maximum length of 50 are allowed): ",
                "Last name is invalid, please only enter " +
                "alphabetic characters with the maximum length of 50."
                + "\nPlease try again.", "", 1);
        String lastName = test.myStoredName;
        test.promptUser(
                "Please input your first integer " +
                "(integer value must not greater then 2,147,483,647 " +
                "and lower then -2,147,483,648): ",
                "Input is invalid, please enter an integer " +
                "that is not then greater then 2,147,483,647 " +
                "and lower then -2,147,483,648"
                + "\nPlease try again.", "", 2);
        long firstInteger = test.myStoredInteger;
        test.promptUser(
                "Please input your second integer " +
                "(integer value must not greater then 2,147,483,647 " +
                "and lower then -2,147,483,648): ",
                "Input is invalid, please enter an integer " +
                "that is not then greater then 2,147,483,647 " +
                "and lower then -2,147,483,648"
                + "\nPlease try again.", "", 2);
        long secondInteger = test.myStoredInteger;
        test.promptUser(
                "Please input your input file's name " +
                "(special characters allowed except _ and -, " +
                "number and alphabetic characters allowed; " +
                "Has to end with .txt): ",
                "Input file's name is invalid, please re-enter, " +
                "alphabetic characters, numbers " +
                "and special characters allowed except _ and -" +
                "\n(File name has to end with .txt)"
                + "\nPlease try again.", "", 3);
        String inputFile = test.myStoredFileName;
        test.promptUser(
                "Please input your output file's name " +
                "(special characters allowed except _ and -, " +
                "number and alphabetic characters allowed; " +
                "Has to end with .txt): ",
                "Output file's name is invalid, please re-enter, " +
                        "alphabetic characters, numbers " +
                        "and special characters allowed except _ and -" +
                        "\n(File name has to end with .txt)"
                        + "\nPlease try again:", "", 3);
        String outputFile = test.myStoredFileName;
        test.promptUser(
                "Password has to be at least 8 to 30 in length." +
                "\nIt must contain at least a number, a capital letter," +
                " and a non-capital letter." +
                "\nIt must contain at least one of these punctuation marks" +
                "\n!@#$%^&()_+\\-={}:;\"|,.<>/?" +
                "\nNo three consecutive lower-case characters" +
                "\nPlease input your password:\s",
                "Password has to be at least 8 to 30 in length." +
                "\nIt must contain at least a number, a capital letter," +
                " and a non-capital letter." +
                "\nIt must contain at least one of these punctuation marks" +
                "\n!@#$%^&()_+\\-={}:;\"|,.<>/?" +
                "\nNo three consecutive lower-case characters" +
                "\nPlease input your password:\s",
                "Please re-enter your password for confirmation: ", 4);
        test.writeOutputFile(firstName, lastName, firstInteger,
                secondInteger, inputFile, outputFile);
        test.generateErrorLogFile(test.log);
    }

    /**
     * Performs validation and storing user input
     * on specific prompts' description
     *
     * @param displayPrompt      The prompt description
     * @param reEntryPrompt      The prompt description when input is invalid
     * @param confirmationPrompt The prompt description for password
     * @param choice             The integer choice represent specific prompt
     */
    private void promptUser(String displayPrompt, String reEntryPrompt,
                            String confirmationPrompt, int choice) {
        String userInput;
        boolean result = false;
        boolean retries = false;
        do {
            //Keep prompting until get a valid result
            //Retries will have different prompt message
            if (retries) {
                System.out.println(reEntryPrompt);
            } else {
                System.out.println(displayPrompt);
            }
            userInput = myInput.nextLine();

            switch (choice) {
                case 1 -> {
                    //Validates name and store it if valid
                    result = myVerifier.checkName(userInput);
                    if (result) {
                        myStoredName = userInput;
                    } else {
                        retries = true;
                    }
                }

                case 2 -> {
                    //Validates integer and store it if valid
                    if (myVerifier.checkInt(userInput) != null) {
                        myStoredInteger = myVerifier.checkInt(userInput);
                        result = true;
                    } else {
                        retries = true;
                    }
                }

                case 3 -> {
                    //Validates file's name and store it if valid
                    result = myVerifier.checkFileName(userInput);
                    if (result) {
                        myStoredFileName = userInput;
                    } else {
                        retries = true;
                    }
                }

                case 4 -> {
                    //Validates password
                    //If valid, encrypt the password and store to a file
                    //Validate the password again with encrypted password
                    //Keep prompting if the re-entry password did not match
                    //If file not found, exist the loop
                    result = myVerifier.checkPassword(userInput);
                    if (result) {
                        String file = getPasswordStoringFileName();
                        myPassword = securePassword(userInput);
                        storePassword(myPassword, file);
                        while (fileFound) {
                            System.out.println(confirmationPrompt);
                            String re = myInput.nextLine();
                            if (validatePassword(re, file)) {
                                break;
                            } else if (fileFound) {
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
     * @param encryptedPassword The string encrypted password
     * @param fileName          The string file's name
     */
    private void storePassword(String encryptedPassword, String fileName) {
        try (FileOutputStream file = new FileOutputStream(fileName);
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(file))) {
            //The file will contain encrypted salt code and encrypted password
            writer.write(Base64.getEncoder().encodeToString(mySalt)
                    + "\n" + encryptedPassword + "\n");
        } catch (Exception e) {
            System.out.println("There is an error storing password process");
            log.append(e.getMessage());
        }
    }

    /**
     * Validates password to see if it matches
     * the encrypted password stored in a file
     *
     * @param password The string password entered by the user
     * @param fileName The string file's name entered by the user
     * @return The boolean true or false if it matched
     */
    private boolean validatePassword(String password, String fileName) {
        String readSalt;
        String readHash;
        try (BufferedReader reader = new BufferedReader(new FileReader(
                fileName))) {
            readSalt = reader.readLine(); //The encrypted salt code
            readHash = reader.readLine(); //The encrypted password hash code

            //Encrypt the password to compare
            // with the previous password (encrypted)
            if (readSalt != null && readHash != null) {
                String hashedPassword = securePassword(password);
                return readSalt.equals(Base64.getEncoder()
                        .encodeToString(mySalt))
                        && hashedPassword.equals(readHash);
            }

        } catch (IOException e) {
            System.out.println(
                    "There is an error validating password process");
            fileFound = false; //File not found
            log.append(e.getMessage());
            return false;
        }
        return false;
    }

    /**
     * Adding salt and perform hash on password
     *
     * @param password The password entered by the user
     * @return The string encrypted password
     */
    private String securePassword(String password) {
        if (mySalt == null) {
            generateSalt();
        }

        try {
            //Process of hashing password
            //Using message digest and SHA-512 hash algorithm
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(mySalt);
            byte[] hashedPassword = md.digest(
                    password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("There is an error securing password process");
            log.append(e.getMessage());
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
            System.out.println("There is an error generating mySalt process");
            log.append(e.getMessage());
        }
    }

    /**
     * Write the specific inputs entered by the user to a file
     *
     * @param firstName     The string first name retrieved from user's input
     * @param lastName      The string last name retrieved from user's input
     * @param firstInteger  The first integer retrieved value from user's input
     * @param secondInteger The second integer retrieved value from user's input
     * @param inputFile     The input file's name retrieved from user's input
     * @param outputFile    The output file's name retrieved from user's input
     */
    private void writeOutputFile(String firstName, String lastName,
                                 long firstInteger, long secondInteger,
                                 String inputFile, String outputFile) {
        try (FileOutputStream file = new FileOutputStream(outputFile);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file))) {
            String result = //Formatting result for inputs
                    "First name: " + firstName +
                    "\nLast Name: " + lastName +
                    "\nInteger a: " + firstInteger +
                    "\nInteger b: " + secondInteger +
                    "\na + b: " + (firstInteger + secondInteger) +
                    "\na * b: " + (firstInteger * secondInteger) +
                    "\n\nInput file's name: " + inputFile +
                    "\nContents in the file: ";
            writer.write(result);
            writer.newLine();

            //Copy contents form the input file to output file
            String line;
            BufferedReader reader = new BufferedReader(
                    new FileReader(inputFile));
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(
                    "There is an error storing all input to file");
            log.append(e.getMessage());
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
                "(special characters allowed except _ and -, " +
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
     * Writes out errors caught during program running process to a file
     *
     * @param log The string builder contains error messages
     */
    private void generateErrorLogFile(StringBuilder log) {
        try (FileOutputStream file = new FileOutputStream("ErrorLogs.txt");
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(file))) {
            writer.write(log.toString());
        } catch (Exception e) {
            System.out.println("There is an error generating log file process");
        }
    }
}
