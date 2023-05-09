#author Mey Vo, Toan Nguyen, Tinh Diep
import unittest
import Verifier

class FileNameTest(unittest.TestCase):
    def test_ValidFileName00(self):
        file = "am-.txt"
        self.assertTrue(Verifier.Verifier.verifyFileName(file), 'Valid file\'s name test fails')

    def test_ValidFileName01(self):
        file = "a_b.txt"
        self.assertTrue(Verifier.Verifier.verifyFileName(file), 'Valid file\'s name test fails')

    def test_ValidFileName02(self):
        file = "a_b.yoyo.txt"
        self.assertTrue(Verifier.Verifier.verifyFileName(file), 'Valid file\'s name test fails')    

    def test_InvalidFileName00(self):
        file = "file*mey.txt"
        self.assertFalse(Verifier.Verifier.verifyFileName(file), 'Valid file\'s name test fails')

    def test_InvalidFileName01(self):
        file = "example@file.txt"
        self.assertFalse(Verifier.Verifier.verifyFileName(file), 'Valid file\'s name test fails')

    def test_InvalidFileName02(self):
        file = "123ab?.txt"
        self.assertFalse(Verifier.Verifier.verifyFileName(file), 'Valid file\'s name test fails')    

    def test_FileWithNoExtension(self):
        file = "toannguyen"
        self.assertFalse(Verifier.Verifier.verifyFileName(file), 'Valid file\'s name test fails')

    def test_NoFileNameInputButHasEnding(self):
        file = ".txt"
        self.assertFalse(Verifier.Verifier.verifyFileName(file), 'Valid file\'s name test fails')

    def test_InvalidFileType00(self):
        file = "file.text"
        self.assertFalse(Verifier.Verifier.verifyFileName(file), 'Valid file\'s name test fails')

    def test_InvalidFileType01(self):
        file = "file.docs"
        self.assertFalse(Verifier.Verifier.verifyFileName(file), 'Valid file\'s name test fails')

    def test_InvalidFileType02(self):
        file = "file.tx" * 20
        self.assertFalse(Verifier.Verifier.verifyFileName(file), 'Valid file\'s name test fails')