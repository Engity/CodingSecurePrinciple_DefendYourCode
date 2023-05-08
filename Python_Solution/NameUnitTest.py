import unittest
import Verifier

class VerifierNameTest(unittest.TestCase):
    def test_ValidName(self):
        self.assertEqual(Verifier.Verifier.verifyName("Toan"), "Toan",
                        'Valid Name Test Fails')
    
    def test_InvalidName(self):
        self.assertEqual(Verifier.Verifier.verifyName("Toan123"), False,
                        'Invalid Name Test Fails')
    
    def test_EmptyName(self):
        self.assertEqual(Verifier.Verifier.verifyName(""), False,
                        'Empty Name Test Fails')
    
    def test_ShortName(self):
        self.assertEqual(Verifier.Verifier.verifyName("T"), "T",
                        'Short Name Test Fails')
    
    def test_LongName(self):
        self.assertEqual(Verifier.Verifier.verifyName("ToanNguyenTran"), "ToanNguyenTran",
                        'Long Name Test Fails')
    
    def test_NameWithSpaces(self):
        self.assertEqual(Verifier.Verifier.verifyName("Toan Nguyen"), False,
                        'Name With Spaces Test Fails')
    
    def test_NameWithSpecialChars(self):
        self.assertEqual(Verifier.Verifier.verifyName("J@ne"), False,
                        'Name With Special Chars Test Fails')
                        
                        
    def test_NameWithLowercase(self):
        self.assertEqual(Verifier.Verifier.verifyName("toan"), "toan",
                        'Lowercase Name Test Fails')
                        
    def test_NameWithMixedCase(self):
        self.assertEqual(Verifier.Verifier.verifyName("ToAn"), "ToAn",
                        'Mixed Case Name Test Fails')
                                                
    def test_NameWithNumbers(self):
        self.assertEqual(Verifier.Verifier.verifyName("Toan123"), False,
                        'Name With Numbers Test Fails')
                        
    def test_NameWithSpecialCharsAndNumbers(self):
        self.assertEqual(Verifier.Verifier.verifyName("Toan#123"), False,
                        'Name With Special Chars and Numbers Test Fails')
