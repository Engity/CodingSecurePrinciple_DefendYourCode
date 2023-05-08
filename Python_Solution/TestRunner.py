#author Toan Nguyen
import unittest
def suite():
    suite = unittest.TestSuite()
    #Running all tests with the surfix "test.py"
    for all_test_suite in unittest.defaultTestLoader.discover('', pattern ='*[Tt][eE][sS][Tt].[pP][yY]'):
        for test_suite in all_test_suite:
            suite.addTests(test_suite)
    return suite

runner = unittest.TextTestRunner()
runner.run(suite())
