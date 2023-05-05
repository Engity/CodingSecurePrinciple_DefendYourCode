import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verifier{
    private static Pattern myPattern;
    private static Matcher myMatcher;

    /**
     * Check whether a string is an integer
     * @param s, contains the number to check
     * @return a number if s is an int; null if it is not
     */
    public static Integer checkInt(String s){
        int num;
        try {
            num = Integer.parseInt(s);
            if ((num & 0x80000000) != 0 && (num & 0x7FFFFFFF) != 0) {
                return null;
            }
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
    public static boolean checkName(String s){
        myPattern = Pattern.compile("^[a-zA-Z]{1,50}$");
        myMatcher = myPattern.matcher(s);
        return myMatcher.find();
    }

    /**
     * Check whether a string can be a file name
     * @param s, contains the name to check
     * @return true if s is a file name, false if it is not
     */
    public static boolean checkFileName(String s) {
        myPattern = Pattern.compile("^[^~)('!*<>:;,?\"|/]+\\.txt$");
        myMatcher = myPattern.matcher(s);
        return myMatcher.find();
    }

    /**
     * Check whether a string can be a valid password
     * @param s, contains the name to check
     * @return true if s is a valid password, false if it is not
     */
    public static boolean checkPassword(String s) {
        myPattern = Pattern.compile("^[A-Za-z0-9!@#$%^&()_+\\-={}:;\"\\\\|,.<>/?]*$");
        myMatcher = myPattern.matcher(s);
        return myMatcher.find();
    }


}