import unittest
from Password import Password

class PasswordTest(unittest.TestCase):
    def test_ValidPassword(self):
        password = Password("Toan23!@#")
        self.assertTrue(password.isValid(), 'Valid password test fails')

    def test_TooShort(self):
        password = Password("TN1!")
        self.assertFalse(password.isValid(), 'Too short password test fails')

    def test_NoUppercase(self):
        password = Password("toan123!@#")
        self.assertFalse(password.isValid(), 'No uppercase letter password test fails')

    def test_NoLowercase(self):
        password = Password("TOAN123!@#")
        self.assertFalse(password.isValid(), 'No lowercase letter password test fails')

    def test_NoDigit(self):
        password = Password("Abcdef!@#")
        self.assertFalse(password.isValid(), 'No digit password test fails')

    def test_NoSpecialChar(self):
        password = Password("Abc123456")
        self.assertFalse(password.isValid(), 'No special character password test fails')

    def test_Whitespace(self):
        password = Password("Abc 123!@#")
        self.assertFalse(password.isValid(), 'Whitespace in password test fails')

    def test_TooLong(self):
        password = Password("Abc123!@#" * 10)
        self.assertFalse(password.isValid(), 'Too long password test fails')
