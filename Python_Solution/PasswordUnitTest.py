#author Mey Vo, Toan Nguyen, Tinh Diep
import unittest
import Verifier

class PasswordTest(unittest.TestCase):
    def test_ValidPassword(self):
        password = "Toan23!@#"
        self.assertTrue(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')

    def test_TooShort(self):
        password = "TN1!"
        self.assertFalse(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')

    def test_NoUppercase(self):
        password = "toan123!@#"
        self.assertFalse(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')

    def test_NoLowercase(self):
        password = "TOAN123!@#"
        self.assertFalse(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')

    def test_NoDigit(self):
        password = "Abcdef!@#"
        self.assertFalse(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')

    def test_NoSpecialChar(self):
        password = "Abc123456"
        self.assertFalse(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')

    def test_Whitespace(self):
        password = "Abc 123!@#"
        self.assertTrue(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')

    def test_TooLong(self):
        password = "Abc123!@#" * 20
        self.assertFalse(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')

# password = "Abc123!@#" * 20
# print(Verifier.Verifier.verifyPassword(password))