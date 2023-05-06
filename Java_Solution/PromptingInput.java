import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class PromptingInput {
    Verifier myVerifier = new Verifier();
    Scanner myInput;
    private String myStoredName;
    private int myStoredInteger;
    private String myPassword;
    private String myStoredFileName;
    private byte[] mySalt;

    PromptingInput() {
        myInput = new Scanner(System.in);
    }

    void promptUser(String displayPrompt, String reEntryPrompt,
                    String confirmationPrompt, int choice) {
        String s;
        boolean result = false;
        boolean retries = false;
        do {
            if (retries) {
                System.out.println(reEntryPrompt);
            } else {
                System.out.println(displayPrompt);
            }
            s = myInput.nextLine();

            switch (choice) {
                case 1 -> {
                    result = myVerifier.checkName(s);
                    if (result) {
                        myStoredName = s;
                    } else {
                        retries = true;
                    }
                }

                case 2 -> {
                    if (myVerifier.checkInt(s) != null) {
                        myStoredInteger = myVerifier.checkInt(s);
                        result = true;
                    } else {
                        retries = true;
                    }
                }

                case 3 -> {
                    result = myVerifier.checkFileName(s);
                    if (result) {
                        myStoredFileName = s;
                    } else {
                        retries = true;
                    }
                }

                case 4 -> {
                    result = myVerifier.checkPassword(s);
                    if (result) {
                        myPassword = securePassword(s);
                        storePassword(myPassword);
                        while (true) {
                            System.out.println(confirmationPrompt);
                            String re = myInput.nextLine();
                            if (validatePassword(re)) {
                                break;
                            } else {
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

    public void storePassword(String encryptedPassword){
        try (FileOutputStream file = new FileOutputStream("password.txt");
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(file))) {
            writer.write(Base64.getEncoder().encodeToString(mySalt)
                    + "\n" + encryptedPassword + "\n");
        } catch (Exception e) {
            System.out.println("There is an error storing password process");
        }
    }

    public boolean validatePassword(String encryptedPassword){
        String readSalt;
        String readHash;
        try (BufferedReader reader = new BufferedReader(new FileReader(
                "password.txt"))) {
            readSalt = reader.readLine();
            readHash = reader.readLine();

            if (readSalt != null && readHash != null) {
                String hashedPassword = securePassword(encryptedPassword);
                return readSalt.equals(Base64.getEncoder()
                        .encodeToString(mySalt))
                        && hashedPassword.equals(readHash);
            }

        } catch (IOException e) {
            System.out.println("Error: file is not in correct format.");
            return false;
        }
        return false;
    }

    public String securePassword(String password) {
        if (mySalt == null) {
            generateSalt();
        }

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(mySalt);
            byte[] hashedPassword = md.digest(
                    password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("There is an error securing password process");
            return "";
        }
    }

    public void generateSalt() {
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            mySalt = new byte[10];
            random.nextBytes(mySalt);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("There is an error generating mySalt process");
        }
    }

    public static void writeOutputFile(String firstName, String lastName,
                                       long firstInteger, long secondInteger,
                                       String inputFile, String outputFile) {
        try (FileOutputStream file = new FileOutputStream(outputFile);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file))) {
            writer.write(firstName);
            writer.newLine();
            writer.write(lastName);
            writer.newLine();
            writer.write(Long.toString(firstInteger));
            writer.newLine();
            writer.write(Long.toString(secondInteger));
            writer.newLine();
            writer.write(Long.toString(firstInteger + secondInteger));
            writer.newLine();
            writer.write(Long.toString(firstInteger * secondInteger));
            writer.newLine();
            writer.write(inputFile);
            writer.newLine();

            String line;
            BufferedReader reader = new BufferedReader(
                    new FileReader(inputFile));
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println(
            "There is an error storing everything input to file");
        }
    }

    public static void main(String[] args) {
        PromptingInput test = new PromptingInput();
        test.promptUser(
                "Please input your first name " +
                "(only alphabetic characters " +
                "with the maximum length of 50 are allowed): ",
                "First name is invalid, please only enter " +
                "alphabetic characters with the maximum length of 50."
                + "\nPlease try again.","", 1);
        String firstName = test.myStoredName;
        test.promptUser(
                "Please input your last name " +
                "(only alphabetic characters " +
                "with the maximum length of 50 are allowed): ",
                "Last name is invalid, please only enter " +
                "alphabetic characters with the maximum length of 50."
                + "\nPlease try again.","", 1);
        String lastName = test.myStoredName;
        test.promptUser(
                "Please input your first integer " +
                 "(integer value must not greater then 2,147,483,647 " +
                 "and lower then -2,147,483,648): ",
                "Input is invalid, please enter an integer " +
                 "that is not then greater then 2,147,483,647 " +
                 "and lower then -2,147,483,648"
                + "\nPlease try again.","", 2);
        long firstInteger = test.myStoredInteger;
        test.promptUser(
                "Please input your second integer " +
                "(integer value must not greater then 2,147,483,647 " +
                "and lower then -2,147,483,648): ",
                "Input is invalid, please enter an integer " +
                "that is not then greater then 2,147,483,647 " +
                "and lower then -2,147,483,648"
                + "\nPlease try again.","", 2);
        long secondInteger = test.myStoredInteger;
        test.promptUser(
                "Please input your input file's name " +
                "(special characters allowed except _ and -, " +
                "number and alphabetic characters allowed; " +
                "Has to end with .txt): ",
                "Input file's name is invalid, please re-enter, " +
                "alphabetic characters, numbers " +
                "and special characters allowed except _ and -"
                + "\nPlease try again.","", 3);
        String inputFile = test.myStoredFileName;
        test.promptUser(
                "Please input your output file's name " +
                "(special characters allowed except _ and -, " +
                "number and alphabetic characters allowed; " +
                "Has to end with .txt): ",
                "Output file's name is invalid, please re-enter, " +
                "alphabetic characters, numbers " +
                "and special characters allowed except _ and -"
                + "\nPlease try again.","", 3);
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
        writeOutputFile(firstName, lastName, firstInteger,
                secondInteger, inputFile, outputFile);
    }
}
