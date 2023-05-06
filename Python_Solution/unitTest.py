import unittest
import Verifier

class VerifierTest(unittest.TestCase):
    # def setUp(self):
    #     return
    #     #self.verifier = Verifier.Verifier()

    def test_ValidName(self):
        self.assertEqual(Verifier.Verifier.verifyName("Toan"), "Toan",
                         'Valid Name Test Fails')
    
    def test_InvalidName(self):
        self.assertEqual(Verifier.Verifier.verifyName("Toan123"), False,
                         'Invalid Name Test Fails')
    # def tearDown(self):
    #     return
    #     #self.verifier.dispose()

def suite():
    suite = unittest.TestSuite()
    for all_test_suite in unittest.defaultTestLoader.discover('', pattern='*Test.py'):
        for test_suite in all_test_suite:
            suite.addTests(test_suite)
    return suite

runner = unittest.TextTestRunner()
runner.run(suite())