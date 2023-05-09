import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class centralizes in testing name input validation
 *
 * @author Mey Vo, Toan Nguyen, Tinh Diep
 * @version 5 May 2023
 */
public class TestName {
    Verifier verifier = new Verifier();

    @Test
    public void testCheckValidName() {
        assertTrue(verifier.checkName("Mey"));
        assertTrue(verifier.checkName("Tommy"));
        assertTrue(verifier.checkName("Toan"));
    }

    @Test
    public void testCheckNameAtMaxLength() {
        assertTrue(verifier.checkName("MeyVoMeyVoMeyVoMeyVoMeyVoMeyVoMeyVoMeyVoMeyVoMeyVo"));
    }

    @Test
    public void testCheckNameLowerCase() {
        assertTrue(verifier.checkName("yes"));
    }

    @Test
    public void testCheckNameAllUpperCase() {
        assertTrue(verifier.checkName("YES"));
    }

    @Test
    public void testCheckNameWithNumbers() {
        assertFalse(verifier.checkName("Mey123"));
    }

    @Test
    public void testCheckNameWithSpecialCharacters() {
        assertFalse(verifier.checkName("Toan!"));
    }

    @Test
    public void testCheckNameTooLong() {
        assertFalse(verifier.checkName("TommyTinhDiepNguyenABMNIOPAMSJLKSMANSDJSQOOOOOOOOOO"));
    }

    @Test
    public void testCheckNameEmpty() {
        assertFalse(verifier.checkName(""));
    }

    @Test
    public void testCheckNameWithWhiteSpace() {
        assertFalse(verifier.checkName("Toan "));
    }
}
