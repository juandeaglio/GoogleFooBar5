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
        int[] probabilities = solution.ProbabilitiesToReachGivenTerminalStates(simplestPossible);
        int[] expected = {1,1};
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
        int[] probabilities = solution.ProbabilitiesToReachGivenTerminalStates(matrix1);
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
        int[] probabilities = solution.ProbabilitiesToReachGivenTerminalStates(matrix2);
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
    public void GivenAMatrixWhenStartingAtState0ShouldFindTransitionMatrixAtInitialStep()
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
        int steps = 0;
        int[] transitionMatrix = solution.FindTransitionMatrix(matrix, steps);
        int[] expectedTransitionMatrix = {1,0,0,0,0,0};
        Assertions.assertArrayEquals(expectedTransitionMatrix,transitionMatrix);
    }
    @Test
    public void GivenAMatrixWhenStartingAtState0ShouldFindTransitionMatrixAfterOneStep()
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
        int[] transitionMatrix = solution.FindTransitionMatrix(matrix, steps);
        int[] expectedTransitionMatrix = {1,0,0,0,0,0};
        Assertions.assertArrayEquals(expectedTransitionMatrix,transitionMatrix);
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
    public void GivenTwoFractionsShouldDivideCorrectly()
    {
        Fraction firstFraction = new Fraction(3,2);
        Fraction secondFraction = new Fraction(5,4);
        Fraction expectedResult = new Fraction(6,5);
        Assertions.assertTrue(expectedResult.equals(firstFraction.Divide(secondFraction)));
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
