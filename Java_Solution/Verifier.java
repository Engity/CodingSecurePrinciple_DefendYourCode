import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class centralizes the behavior for validating
 * input retrieved from user by using regex.
 *
 *
 * @version 5 May 2023
 */
public class Verifier {
    /**
     * The pattern to compile regex
     */
    private Pattern myPattern;
    /**
     * The matcher to validate the string with given regex
     */
    private Matcher myMatcher;

    /**
     * Check whether a string is an integer
     *
     * @param theInteger, contains the number to check
     * @return a number if s is an int; null if it is not
     */
    public Integer checkInt(String theInteger) {
        int num;
        try {
            num = Integer.parseInt(theInteger);
            //Null if:
            //Exceeds max value allowed for integer
            //Exceeds min value allowed for integer
            //Not an integer
        } catch (NumberFormatException e) {
            return null;
        }
        return num;
    }

    /**
     * Check whether a string can be a name of a person
     *
     * @param theName, contains the name to check
     * @return true if s is a name, false if it is not
     */
    public boolean checkName(String theName) {
        myPattern = Pattern.compile(
                "^[a-zA-Z]{1,50}$");
        //Alphabetic characters only
        //Maximum length is 50 and minimum is 1
        myMatcher = myPattern.matcher(theName);
        return myMatcher.find();
    }

    /**
     * Check whether a string can be a file name
     *
     * @param theFileName, contains the name to check
     * @return true if s is a file name, false if it is not
     */
    public boolean checkFileName(String theFileName) {
        myPattern = Pattern.compile(
                "^[^~)('!*<>:;,?\"|/]+\\.txt$");
        //Characters and numbers allowed
        //Ending must have .txt
        myMatcher = myPattern.matcher(theFileName);
        return myMatcher.find();
    }

    /**
     * Check whether a string can be a valid password
     *
     * @param thePassword, contains the name to check
     * @return true if s is a valid password, false if it is not
     */
    public boolean checkPassword(String thePassword) {
        myPattern = Pattern.compile(
                "^(?!.*[a-z]{4,})" +
                        //Negative look ahead for no more than 3 consecutive
                        //lower-case characters
                        "(?=.*[A-Z])" +
                        //Positive look ahead for at least 1 upper-case character
                        "(?=.*[a-z])" + //At least 1 lower-case
                        "(?=.*\\d)" + //At least 1 digit
                        "(?=.*[!@#$%^&()_+\\-={}:;\"\\\\|,.<>/?])" +
                        //At least 1 punctuation mark.
                        ".{8,30}$"); //At least 8 characters long and at most 30
        myMatcher = myPattern.matcher(thePassword);
        return myMatcher.find();
    }
}