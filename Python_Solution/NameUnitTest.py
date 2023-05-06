import unittest
import Verifier

class VerifierNameTest(unittest.TestCase):
    def test_ValidName(self):
        self.assertEqual(Verifier.Verifier.verifyName("Toan"), "Toan",
                        'Valid Name Test Fails')
    
    def test_InvalidName(self):
        self.assertEqual(Verifier.Verifier.verifyName("Toan123"), False,
                        'Invalid Name Test Fails')


