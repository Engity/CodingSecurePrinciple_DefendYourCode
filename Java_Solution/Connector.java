/**
 * This class is a connector that use and run
 * prompting input class with main
 *
 * @version 5 May 2023
 */
public class Connector {
    public static void main(String[] args) {
        PromptingInput inputPrompter = new PromptingInput();
        inputPrompter.promptUser(
                "Please input your first name " +
                        "(only alphabetic characters " +
                        "with the maximum length of 50 are allowed): ",
                "First name is invalid, please only enter " +
                        "alphabetic characters with the maximum length of 50."
                        + "\nPlease try again.", "", 1);
        String firstName = inputPrompter.myStoredName;
        inputPrompter.promptUser(
                "Please input your last name " +
                        "(only alphabetic characters " +
                        "with the maximum length of 50 are allowed): ",
                "Last name is invalid, please only enter " +
                        "alphabetic characters with the maximum length of 50."
                        + "\nPlease try again.", "", 1);
        String lastName = inputPrompter.myStoredName;
        inputPrompter.promptUser(
                "Please input your first integer " +
                        "(integer value must not greater then 2,147,483,647 " +
                        "and lower then -2,147,483,648): ",
                "Input is invalid, please enter an integer " +
                        "that is not then greater then 2,147,483,647 " +
                        "and lower then -2,147,483,648"
                        + "\nPlease try again.", "", 2);
        long firstInteger = inputPrompter.myStoredInteger;
        inputPrompter.promptUser(
                "Please input your second integer " +
                        "(integer value must not greater then 2,147,483,647 " +
                        "and lower then -2,147,483,648): ",
                "Input is invalid, please enter an integer " +
                        "that is not then greater then 2,147,483,647 " +
                        "and lower then -2,147,483,648"
                        + "\nPlease try again.", "", 2);
        long secondInteger = inputPrompter.myStoredInteger;
        inputPrompter.promptUser(
                "Please input your input file's name " +
                        "(special characters allowed except _ and -, " +
                        "number and alphabetic characters allowed; " +
                        "Has to end with .txt): ",
                "Input file's name is invalid, please re-enter, " +
                        "alphabetic characters, numbers " +
                        "and special characters allowed except _ and -" +
                        "\n(File name has to end with .txt)"
                        + "\nPlease try again.", "", 3);
        String inputFile = inputPrompter.myStoredFileName;
        inputPrompter.promptUser(
                "Please input your output file's name " +
                        "(special characters allowed except _ and -, " +
                        "number and alphabetic characters allowed; " +
                        "Has to end with .txt): ",
                "Output file's name is invalid, please re-enter, " +
                        "alphabetic characters, numbers " +
                        "and special characters allowed except _ and -" +
                        "\n(File name has to end with .txt)"
                        + "\nPlease try again:", "", 3);
        String outputFile = inputPrompter.myStoredFileName;
        inputPrompter.promptUser(
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
        inputPrompter.writeOutputFile(firstName, lastName, firstInteger,
                secondInteger, inputFile, outputFile);
        inputPrompter.generateErrorLogFile(inputPrompter.myLog);
    }
}
