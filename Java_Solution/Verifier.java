import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verifier{
      Pattern myPattern;
      Matcher myMatcher;

    /**
     * Check whether a string is an integer
     * @param s, contains the number to check
     * @return a number if s is an int; null if it is not
     */
    public Integer checkInt(String s){
        int num;
        try {
            num = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
        return num;
    }

    /**
     * Check whether a string can be a name of a person
     * @param s, contains the name to check
     * @return true if s is a name, false if it is not
     */
    public boolean checkName(String s){
        myPattern = Pattern.compile("^[a-zA-Z]{1,50}$");
        myMatcher = myPattern.matcher(s);
        return myMatcher.find();
    }

    /**
     * Check whether a string can be a file name
     * @param s, contains the name to check
     * @return true if s is a file name, false if it is not
     */
    public boolean checkFileName(String s) {
        myPattern = Pattern.compile("^[^~)('!*<>:;,?\"|/]+\\.txt$");
        myMatcher = myPattern.matcher(s);
        return myMatcher.find();
    }

    /**
     * Check whether a string can be a valid password
     * @param s, contains the name to check
     * @return true if s is a valid password, false if it is not
     */
    public boolean checkPassword(String s) {
        myPattern = Pattern.compile("^(?!.*[a-z]{4,})" +
                        //Negative look ahead for no more than 3 consecutive
                        //lower-case characters.
                        "(?=.*[A-Z])" +
                        //Positive look ahead for at least 1 upper-case character.
                        "(?=.*[a-z])" + //At least 1 lower-case.
                        "(?=.*\\d)" + //At least 1 digit.
                        "(?=.*[!@#$%^&()_+\\-={}:;\"\\\\|,.<>/?])" +
                        //At least 1 punctuation mark.
                        ".{8,30}$"); //At least 10 characters long and at most 30.
        myMatcher = myPattern.matcher(s);
        return myMatcher.find();
    }
}