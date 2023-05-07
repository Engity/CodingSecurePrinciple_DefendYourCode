import org.junit.Test;
import static org.junit.Assert.*;

public class TestFileName {

    Verifier verifier = new Verifier();

    @Test
    public void testCheckValidFileName() {
        assertTrue(verifier.checkFileName("mey.txt"));
        assertTrue(verifier.checkFileName("mey-file.txt"));
        assertTrue(verifier.checkFileName("12345.txt"));
    }

    @Test
    public void testCheckFileNameWithInvalidCharacters() {
        assertFalse(verifier.checkFileName("meyfile!.txt"));
    }

    @Test
    public void testCheckFileNameWithInvalidCharacters2() {
        assertTrue(verifier.checkFileName("example@file.txt"));
    }

    @Test
    public void testCheckFileNameWithInvalidCharacters3() {
        assertFalse(verifier.checkFileName("file*mey.txt"));
    }

    @Test
    public void testCheckFileNameWithoutExtension() {
        assertFalse(verifier.checkFileName("meyfile"));
    }

    @Test
    public void testCheckFileNameWithoutExtension2() {
        assertFalse(verifier.checkFileName("mey-file"));
    }

    @Test
    public void testCheckFileNameWithoutExtension3() {
        assertFalse(verifier.checkFileName("12345"));
    }

    @Test
    public void testCheckFileNameWithInvalidExtension() {
        assertFalse(verifier.checkFileName("mey.tx"));
    }

    @Test
    public void testCheckFileNameWithInvalidExtension2() {
        assertFalse(verifier.checkFileName("mey-file.doc"));
    }

    @Test
    public void testCheckFileNameWithInvalidExtension3() {
        assertFalse(verifier.checkFileName("12345.text"));
    }

}
