#author Mey Vo, Toan Nguyen, Tinh Diep
import unittest
import Verifier

class VerifierIntTest(unittest.TestCase):
    def test_ValidInt_Positive(self):
        self.assertEqual(Verifier.Verifier.verifyInt("123"), 123,
                         'Valid positive int string test fails')

    def test_ValidInt_Zero(self):
        self.assertEqual(Verifier.Verifier.verifyInt("0"), 0,
                         'Valid zero int string test fails')

    def test_ValidInt_Min(self):
        self.assertEqual(Verifier.Verifier.verifyInt(str(-2**31)), -2147483648,
                         'Valid minimum int value test fails')

    def test_ValidInt_Max(self):
        self.assertEqual(Verifier.Verifier.verifyInt(str(2**31 - 1)), 2147483647,
                         'Valid maximum int value test fails')

    def test_ValidInt_Whitespace(self):
        self.assertEqual(Verifier.Verifier.verifyInt("  123  "), 123,
                         'Valid int string with whitespace test fails')

    def test_InvalidInt_Positive(self):
        self.assertEqual(Verifier.Verifier.verifyInt("+123"), 123,
                         'Invalid positive int string test fails')
        
    def test_InvalidInt_Negative(self):
        self.assertEqual(Verifier.Verifier.verifyInt("-123"), -123,
                         'Invalid negative int string test fails')

    def test_InvalidInt_TooLarge(self):
        self.assertEqual(Verifier.Verifier.verifyInt(str(2**31)), False,
                         'Invalid int value greater than maximum test fails')

    def test_InvalidInt_TooSmall(self):
        self.assertEqual(Verifier.Verifier.verifyInt(str(-2**31 - 1)), False,
                         'Invalid int value less than minimum test fails')

    def test_InvalidInt_Float(self):
        self.assertEqual(Verifier.Verifier.verifyInt("123.45"), False,
                         'Invalid float string test fails')

    def test_InvalidInt_Alpha(self):
        self.assertEqual(Verifier.Verifier.verifyInt("123abc"), False,
                         'Invalid alpha-numeric string test fails')


