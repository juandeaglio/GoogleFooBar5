import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SolutionUnitTests
{
    Solution solution;
    public void Setup()
    {
        solution = new Solution();
    }
    @Test
    public void GivenTheSimplest2x2MatrixShouldReturn1_1()
    {
        Setup();
        int[][] simplestPossible = {{0,1}, {0,0}};
        int[] probabilities = solution.ProbabilitiesToReachEndStatesOf(simplestPossible);
        int[] expected = {1,1};
        Assertions.assertArrayEquals(expected,probabilities);
    }
    @Test
    public void GivenAMatrixMatrix4ShouldReturn3_3_17_23()
    {
        Setup();
        int[][] matrix4 =
                {{0,2,0,0,0,3}
                ,{4,1,2,2,2,0}
                ,{2,1,0,2,2,0}
                ,{0,0,0,0,0,0}
                ,{0,0,0,0,0,0}
                ,{0,0,0,0,0,0}
                };
        int[] probabilities = solution.ProbabilitiesToReachEndStatesOf(matrix4);
        Assertions.assertTrue(true);

        int[] expected = {3,3,17,23};
        Assertions.assertArrayEquals(expected,probabilities);
    }
    @Test
    public void GivenAMatrixMatrix1ShouldReturn0_3_2_9_14()
    {
        Setup();
        int[][] matrix1 =
                {{0,1,0,0,0,1}
                        ,{4,0,0,3,2,0}
                        ,{0,0,0,0,0,0}
                        ,{0,0,0,0,0,0}
                        ,{0,0,0,0,0,0}
                        ,{0,0,0,0,0,0}
                };
        int[] probabilities = solution.ProbabilitiesToReachEndStatesOf(matrix1);
        Assertions.assertTrue(true);
        int[] expected = {0,3,2,9,14};
        Assertions.assertArrayEquals(expected,probabilities);
    }
    @Test
    public void GivenAMatrixMatrix2ShouldReturn7_6_8_21()
    {
        Setup();
        int[][] matrix2 =
                {{0,2,1,0,0},
                {0,0,0,3,4},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}};
        int[] probabilities = solution.ProbabilitiesToReachEndStatesOf(matrix2);
        Assertions.assertTrue(true);
        int[] expected = {7,6,8,21};
        Assertions.assertArrayEquals(expected,probabilities);
    }
    @Test
    public void GivenAMatrixFindAllStatesThatAreTerminating()
    {
        Setup();
        int[][] matrix =
                {{0,2,1,0,0},
                {0,0,0,3,4},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}};
        int[] statesThatAreTerminating;
        statesThatAreTerminating = solution.FindAllTerminatingStates(matrix);
        int[] statesExpected = {0,0,1,1,1};
        Assertions.assertArrayEquals(statesExpected,statesThatAreTerminating);
    }
    @Test
    public void GivenAMatrixShouldFindProbabilityRowVector()
    {
        Setup();
        int[][] matrix =
                {{0,1,0,0,0,1}
                ,{4,0,0,3,2,0}
                ,{0,0,0,0,0,0}
                ,{0,0,0,0,0,0}
                ,{0,0,0,0,0,0}
                ,{0,0,0,0,0,0}
                };
        Fraction[] probabilityRowMatrix = solution.GetProbabilityRowVectorOf(matrix);
        Fraction[] expectedTransitionMatrix = {new Fraction(9,7),new Fraction(9,14),new Fraction(),new Fraction(3,14),new Fraction(1,7),new Fraction(9,14)};
        Assertions.assertArrayEquals(expectedTransitionMatrix,probabilityRowMatrix);
    }
    @Test
    public void GivenAMatrixNormalizeToOne()
    {
        Setup();
        int[][] matrix =
                {{0,1,0,0,0,1}
                ,{4,0,0,3,2,0}
                ,{0,0,0,0,0,0}
                ,{0,0,0,0,0,0}
                ,{0,0,0,0,0,0}
                ,{0,0,0,0,0,0}
                };
        int steps = 1;
        Fraction[][] normalizedMatrix = solution.NormalizeMatrix(matrix);
        Fraction[][] expectedNormalizedMatrix = {{new Fraction(0, 1),new Fraction(1, 2),new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(1, 2)},
                {new Fraction(4, 9),new Fraction(0, 1),new Fraction(0, 1),new Fraction(3, 9),new Fraction(2, 9),new Fraction(0, 1)},
                {new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1)},
                {new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1)},
                {new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1)},
                {new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1)}};
        Assertions.assertArrayEquals(expectedNormalizedMatrix,normalizedMatrix);
    }
    @Test
    public void GivenTwoFractionsShouldAddCorrectly()
    {
        Fraction firstFraction = new Fraction(3,2);
        Fraction secondFraction = new Fraction(6,4);
        Fraction expectedResult = new Fraction(3,1);
        Fraction resultActual = firstFraction.Add(secondFraction);
        Assertions.assertTrue(expectedResult.equals(resultActual));
    }
    @Test
    public void GivenTwoNegativeFractionsShouldAddCorrectly()
    {
        Fraction firstFraction = new Fraction(-3,2);
        Fraction secondFraction = new Fraction(-6,4);
        Fraction expectedResult = new Fraction(-3,1);
        Fraction resultActual = firstFraction.Add(secondFraction);
        Assertions.assertTrue(expectedResult.equals(resultActual));
    }
    @Test
    public void GivenTwoNegativeFractionsShouldSubtractCorrectly()
    {
        Fraction firstFraction = new Fraction(-3,2);
        Fraction secondFraction = new Fraction(-8,4);
        Fraction expectedResult = new Fraction(1,2);
        Fraction resultActual = firstFraction.Subtract(secondFraction);
        Assertions.assertTrue(expectedResult.equals(resultActual));
    }
    @Test
    public void GivenTwoFractionsShouldSubtractCorrectly()
    {
        Fraction firstFraction = new Fraction(3,2);
        Fraction secondFraction = new Fraction(5,4);
        Fraction expectedResult = new Fraction(1,4);
        Assertions.assertTrue(expectedResult.equals(firstFraction.Subtract(secondFraction)));
    }
    @Test
    public void GivenTwoFractionsShouldMultiplyCorrectly()
    {
        Fraction firstFraction = new Fraction(3,2);
        Fraction secondFraction = new Fraction(5,4);
        Fraction expectedResult = new Fraction(15,8);
        Assertions.assertTrue(expectedResult.equals(firstFraction.Multiply(secondFraction)));
    }
    @Test
    public void GivenTwoNegativeFractionsShouldMultiplyCorrectly()
    {
        Fraction firstFraction = new Fraction(-3,2);
        Fraction secondFraction = new Fraction(-5,4);
        Fraction expectedResult = new Fraction(15,8);
        Assertions.assertTrue(expectedResult.equals(firstFraction.Multiply(secondFraction)));
    }
    @Test
    public void GivenTwoFractionsShouldDivideCorrectly()
    {
        Fraction firstFraction = new Fraction(3,2);
        Fraction secondFraction = new Fraction(5,4);
        Fraction expectedResult = new Fraction(6,5);
        Assertions.assertTrue(expectedResult.equals(firstFraction.Divide(secondFraction)));
    }
    @Test
    public void GivenOneNegativeFractionsShouldDivideCorrectly()
    {
        Fraction firstFraction = new Fraction(3,2);
        Fraction secondFraction = new Fraction(-5,4);
        Fraction expectedResult = new Fraction(-6,5);
        Assertions.assertTrue(expectedResult.equals(firstFraction.Divide(secondFraction)));
    }
    @Test
    public void GivenOneNegativeFractionsShouldProvideCorrectDivision()
    {
        Fraction firstFraction = new Fraction(3,2);
        Fraction secondFraction = new Fraction(-5,4);
        Fraction expectedResult = new Fraction(-6,5);
        Fraction actualResult = firstFraction.Divide(secondFraction);
        System.out.println("wow");
        Assertions.assertTrue(expectedResult.equals(actualResult));
    }
    @Test
    public void GivenALargeMatrixShouldReturn28419_20380_9889_18566_6986_3173_87413()
    {
        Setup();
        int[][]matrix = {
                {6,2,4,2,5,3,2,4,1,0},
                {5,2,2,2,2,2,2,2,2,0},
                {4,2,0,0,4,4,0,0,0,1},
                {2,2,3,2,4,5,1,2,5,6},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0}};
        int[] probabilities = solution.ProbabilitiesToReachEndStatesOf(matrix);
        Assertions.assertTrue(true);
        int[] expected = {28419,20380,9889,18566,6986,3173,87413};
        Assertions.assertArrayEquals(expected,probabilities);
    }
    @Test
    public void GivenALargeEmptyMatrixShouldReturn1_0_0_0_0_0_0_0_0_0_1()
    {
        Setup();
        int[][]matrix = {
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0}};
        int[] probabilities = solution.ProbabilitiesToReachEndStatesOf(matrix);
        Assertions.assertTrue(true);
        int[] expected = {1,0,0,0,0,0,0,0,0,0,1};
        Assertions.assertArrayEquals(expected,probabilities);
    }
    @Test
    public void GivenASmallEmptyMatrixShouldReturn1_0_0_0_1()
    {
        Setup();
        int[][]matrix = {
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0}};
        int[] probabilities = solution.ProbabilitiesToReachEndStatesOf(matrix);
        Assertions.assertTrue(true);
        int[] expected = {1,0,0,0,1};
        Assertions.assertArrayEquals(expected,probabilities);
    }
    @Test
    public void GivenASmallMatrixShouldReturn1_0_0_0_1()
    {
        Setup();
        int[][]matrix = {
                {0,0,0,0},
                {0,0,0,0},
                {1,0,0,0},
                {8,1,1,5}};
        int[] probabilities = solution.ProbabilitiesToReachEndStatesOf(matrix);
        Assertions.assertTrue(true);
        int[] expected = {1,0,1};
        Assertions.assertArrayEquals(expected,probabilities);
    }
    @Test
    public void GivenASmallMatrixWithOnlyOneTerminatingStateShouldReturn1_1()
    {
        Setup();
        int[][]matrix = {
                {4,3,0,0},
                {1,0,4,0},
                {2,0,0,1},
                {0,0,0,0}};
        int[] probabilities = solution.ProbabilitiesToReachEndStatesOf(matrix);
        Assertions.assertTrue(true);
        int[] expected = {1,1};
        Assertions.assertArrayEquals(expected,probabilities);
    }
    @Test
    public void GivenASmallMatrixWithAllButOneTerminatingStateShouldReturnTheFractionsOfInitialState()
    {
        Setup();
        int[][]matrix = {
                {4,3,2,22},
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0}};
        int[] probabilities = solution.ProbabilitiesToReachEndStatesOf(matrix);
        Assertions.assertTrue(true);
        int[] expected = {3,2,22,27};
        Assertions.assertArrayEquals(expected,probabilities);
    }
    @Test
    public void GivenAMediumMatrixShouldReturn1561_2825_625_546_546_1956_8059()
    {
        Setup();
        int[][] matrix = {
                {0, 1, 1, 4, 0, 0, 7},
                {0, 0, 6, 5, 0, 1, 1},
                {2, 0, 0, 3, 1, 1, 0},
                {3, 2, 0, 0, 4, 5, 1},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}};
        int[] probabilities = solution.ProbabilitiesToReachEndStatesOf(matrix);
        Assertions.assertTrue(true);
        int[] expected = {2147,2770,9436,14353};
        Assertions.assertArrayEquals(expected,probabilities);
    }
    @Test
    public void GivenAMatrixShouldNormalizeSubtractFromIdentityThenGetTheInverse()
    {
        Setup();
        int[][] matrix =
                {{0,1,0,0,0,1}
                ,{4,0,0,3,2,0}
                ,{0,0,0,0,0,0}
                ,{0,0,0,0,0,0}
                ,{0,0,0,0,0,0}
                ,{0,0,0,0,0,0}
                };
        Fraction[][] normalizedMatrix = solution.NormalizeMatrix(matrix);
        Fraction[][] inverseMatrix = solution.GetInverseOf(normalizedMatrix);

        Fraction[][] expectedInverseMatrix = {{new Fraction(9, 7),new Fraction(9, 14),new Fraction(0, 1),new Fraction(3, 14),new Fraction(1, 7),new Fraction(9, 14)},
                {new Fraction(4, 7),new Fraction(9, 7),new Fraction(0, 1),new Fraction(3, 7),new Fraction(2, 7),new Fraction(2, 7)},
                {new Fraction(0, 1),new Fraction(0, 1),new Fraction(1, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1)},
                {new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(1, 1),new Fraction(0, 1),new Fraction(0, 1)},
                {new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(1, 1),new Fraction(0, 1)},
                {new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(0, 1),new Fraction(1, 1)}};

        Assertions.assertArrayEquals(expectedInverseMatrix,inverseMatrix);
    }
}
