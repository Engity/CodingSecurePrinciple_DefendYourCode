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
        assertTrue(verifier.checkName("MeyVo"));
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
        assertTrue(verifier.checkName("TommyTinhDiepNguyen"));
    }

    @Test
    public void testCheckNameEmpty() {
        assertFalse(verifier.checkName(""));
    }
}
