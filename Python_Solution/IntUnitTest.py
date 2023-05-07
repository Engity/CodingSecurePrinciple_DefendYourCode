import unittest
import Verifier

class VerifierIntTest(unittest.TestCase):
    def test_ValidInt_Number(self):
        self.assertEqual(Verifier.Verifier.verifyInt(1312313), 1312313,
                        'Valid Int Number Fails')
    
    def test_ValidInt_String(self):
        self.assertEqual(Verifier.Verifier.verifyInt("123"), 123,
                        'Valid Int String Test Fails')
    
    def test_ValidInt_Bignum(self):
        self.assertEqual(Verifier.Verifier.verifyInt("2147483647"), 2147483647,
                        'Valid Int Big Num Fails')
        
    def test_InvalidInt_Bignum(self):
        self.assertEqual(Verifier.Verifier.verifyInt("2147483648"), False,
                        'Invalid Int Big Num Fails')


