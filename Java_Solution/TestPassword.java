import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class centralizes in testing password input validation
 *
 * @author Mey Vo, Toan Nguyen, Tinh Diep
 * @version 5 May 2023
 */public class TestPassword {

    Verifier verifier = new Verifier();

    @Test
    public void testCheckValidPassword() {
        assertTrue(verifier.checkPassword("Mey1234567!"));
    }

    @Test
    public void testCheckPasswordTooShort() {
        assertFalse(verifier.checkPassword("Mey123!"));
    }

    @Test
    public void testCheckPasswordTooLong() {
        assertFalse(verifier.checkPassword("MeyVo1234567890@@@@@@@111111!12"));
    }

    @Test
    public void testCheckPasswordWithoutUppercase() {
        assertFalse(verifier.checkPassword("mey1234567!"));
    }

    @Test
    public void testCheckPasswordWithoutLowercase() {
        assertFalse(verifier.checkPassword("MEY1234567!"));
    }

    @Test
    public void testCheckPasswordWithoutDigit() {
        assertFalse(verifier.checkPassword("MeyVo!"));
    }

    @Test
    public void testCheckPasswordWithoutSpecialChar() {
        assertFalse(verifier.checkPassword("Mey1234567"));
    }

    @Test
    public void testCheckPasswordWithConsecutiveLowercase() {
        assertFalse(verifier.checkPassword("Paassw0rd!"));
    }

    @Test
    public void testCheckPasswordWithForbiddenChars() {
        assertFalse(verifier.checkPassword("Password@1"));
    }
}
