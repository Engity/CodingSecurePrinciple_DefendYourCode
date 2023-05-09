#author Mey Vo, Toan Nguyen, Tinh Diep
import unittest
import Verifier

class FileNameTest(unittest.TestCase):
    def test_ValidFileName00(self):
        password = "am-.txt"
        self.assertTrue(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')

    def test_ValidFileName01(self):
        password = "a_b.txt"
        self.assertTrue(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')

    def test_ValidFileName02(self):
        password = "a_b.yoyoyo.txt"
        self.assertTrue(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')    

    def test_InvalidFileName00(self):
        password = "file*mey.txt"
        self.assertFalse(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')

    def test_InvalidFileName01(self):
        password = "example@file.txt"
        self.assertFalse(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')

    def test_InvalidFileName02(self):
        password = "123ab?.txt"
        self.assertFalse(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')    

    def test_FileWithNoExtension(self):
        password = "toannguyen"
        self.assertFalse(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')

    def test_NoFileNameInputButHasEnding(self):
        password = ".txt"
        self.assertFalse(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')

    def test_InvalidFileType00(self):
        password = "file.text"
        self.assertFalse(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')

    def test_InvalidFileType01(self):
        password = "file.docs"
        self.assertTrue(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')

    def test_InvalidFileType02(self):
        password = "file.tx" * 20
        self.assertFalse(Verifier.Verifier.verifyPassword(password), 'Valid password test fails')