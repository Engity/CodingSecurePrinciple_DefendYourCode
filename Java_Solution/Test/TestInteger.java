import org.junit.Test;
import static org.junit.Assert.*;

public class TestInteger {

    Verifier verifier = new Verifier();

    @Test
    public void testCheckIntValidPositiveInteger() {
        assertEquals(Integer.valueOf(123), verifier.checkInt("123"));
    }

    @Test
    public void testCheckIntValidNegativeInteger() {
        assertEquals(Integer.valueOf(-456), verifier.checkInt("-456"));
    }

    @Test
    public void testCheckIntValidZeroInteger() {
        assertEquals(Integer.valueOf(0), verifier.checkInt("0"));
    }

    @Test
    public void testCheckIntValidMaxInteger() {
        assertEquals(Integer.valueOf(2147483647), verifier.checkInt("2147483647"));
    }

    @Test
    public void testCheckIntValidMinInteger() {
        assertEquals(Integer.valueOf(-2147483648), verifier.checkInt("-2147483648"));
    }

    @Test
    public void testCheckIntInvalidDecimalNumber() {
        assertNull(verifier.checkInt("123.45"));
    }

    @Test
    public void testCheckIntInvalidAlphanumeric() {
        assertNull(verifier.checkInt("1a2b3c"));
    }

    @Test
    public void testCheckIntInvalidEmptyString() {
        assertNull(verifier.checkInt(""));
    }

    @Test
    public void testCheckIntInvalidOutOfRange1() {
        assertNull(verifier.checkInt("2147483648"));
    }

    @Test
    public void testCheckIntInvalidOutOfRange2() {
        assertNull(verifier.checkInt("-2147483649"));
    }

}

