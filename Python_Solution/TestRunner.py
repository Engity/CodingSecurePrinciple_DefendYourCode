import unittest
def suite():
    suite = unittest.TestSuite()
    for all_test_suite in unittest.defaultTestLoader.discover('', pattern ='*test.py'):
        for test_suite in all_test_suite:
            suite.addTests(test_suite)
    return suite

runner = unittest.TextTestRunner()
runner.run(suite())
