import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class PromptingInput {
    Verifier verifier = new Verifier();
    Scanner input;
    private String myStoredName;
    private int myStoredInteger;
    private String myPassword;
    private String myStoredFileName;
    private byte[] salt;

    /**
     * 
1. prompts for and reads the user's first name, then last name -- each should be at most 50 characters -- decide what is legitimate for characters for first and last name
You must make it clear to the user what is expected for input (describe range, acceptable characters, and anything else you feel is important)

2. prompts for and reads in two int values from the user (range of values are those of a 4 byte int)
Once again, make clear to user what is expected. Note that an int can be from -2,147,...,... to 2,147,...,...

3. prompts for reads the name of an input file from the user
Make clear to user what is expected and will be accepted

4. prompts for reads the name of an output file from the user
Same here

5. prompts the user to enter a password, store the password, then ask the user to re-enter the password and verify that it is correct
password should be hashed using a salt and written to a file
to validate, grab hash from file and compare it to hash from second user entry for password
as long as passwords don't match/follow your password specifications, re-prompt the user
6. opens the output file and
writes the user's name
writes the result of adding the two integer values (no overflow should occur)
writes the result of multiplying the two integer values (no overflow should occur),
writes the contents of the input file
Each thing written should be clearly labeled (e.g. First name, Last name, First Integer, Second Integer, Sum, Product, Input File Name, Input file contents)
NOTE: it is ok to echo output to the screen as you wish
     */
    PromptingInput() throws NoSuchAlgorithmException {
        input = new Scanner(System.in);
    }
    void promptUser(String displayPrompt, String reEntryPrompt, String confirmationPrompt, int choice, boolean confirmation) throws  IOException {
        String s;
        boolean result = false;
        boolean retries = false;
        do {
            if (retries) {
                System.out.println(reEntryPrompt);
            } else {
                System.out.println(displayPrompt);
            }
            s = input.nextLine();

            switch (choice) {
                case 1 -> {
                    result = verifier.checkName(s);
                    if (result) {
                        myStoredName = s;
                    } else {
                        retries = true;
                    }
                }

                case 2 -> {
                    if (verifier.checkInt(s) != null) {
                        myStoredInteger = verifier.checkInt(s);
                        result = true;
                    } else {
                        retries = true;
                    }
                }

                case 3 -> {
                    result = verifier.checkPassword(s);
                    if (result) {
                        myPassword = securePassword(s);
                        storePassword(myPassword);
                        while (true) {
                            System.out.println("Re-enter your password for confirmation:");
                            String re = input.nextLine();
                            if (validatePassword(re)) {
                                break;
                            }
                        }
                    } else {
                        retries = true;
                    }
                }
                case 4 -> {
                    result = verifier.checkFileName(s);
                    if (result) {
                        myStoredFileName = s;
                    } else {
                        retries = true;
                    }
                }
            }
        } while (!result);
    }
    public void storePassword(String encryptedPassword) throws IOException {
        FileOutputStream file = null;
        BufferedWriter writer = null;

        try {
            file = new FileOutputStream("password.txt");
            writer = new BufferedWriter(new OutputStreamWriter(file));
            writer.write(Base64.getEncoder().encodeToString(salt) + "\n" + encryptedPassword + "\n");
        } catch (Exception e) {
            System.out.println("There is an error storing password process");
        } finally {
            if (writer != null) writer.close();
            if (file != null) file.close();
        }
    }
    public boolean validatePassword(String encryptedPassword) throws IOException {
        String readSalt = null;
        String readHash = null;
        try (BufferedReader reader = new BufferedReader(new FileReader("password.txt"))) {
            readSalt = reader.readLine();
            readHash = reader.readLine();

            if (readSalt != null && readHash != null) {
                String hashedPassword = securePassword(encryptedPassword);
                return readSalt.equals(Base64.getEncoder().encodeToString(salt)) && hashedPassword.equals(readHash);
            }

        } catch (IOException e) {
            System.out.println("Error: file is not in correct format.");
            return false;
        }
        return false;
    }

    public String securePassword(String password) {
        if (salt == null) {
            generateSalt();
        }

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("There is an error securing password process");
            return "";
        }
    }

    public void generateSalt() {
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            salt = new byte[10];
            random.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("There is an error generating salt process");
        }
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        PromptingInput test = new PromptingInput();
        test.promptUser("Enter your name: ", "Please re-enter: ","",3,false);
        String name = test.myPassword;
        System.out.println(name+"\s");
    }
}
