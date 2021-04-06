import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SolutionUnitTests
{
    @Test
    public void GivenTheSimplest2x2MatrixShouldReturn1_1()
    {
        int[][] simplestPossible = {{0,1}, {0,0}};
        int[] probabilities = new Solution().ProbabilitiesToReachGivenTerminalStates(simplestPossible);
        int[] expected = {1,1};
        Assertions.assertArrayEquals(expected,probabilities);
    }
    @Test
    public void GivenAMatrixMatrix1ShouldReturn0_3_2_9_14()
    {
        int[][] matrix1 =
                {{0,1,0,0,0,1}
                ,{4,0,0,3,2,0}
                ,{0,0,0,0,0,0}
                ,{0,0,0,0,0,0}
                ,{0,0,0,0,0,0}
                ,{0,0,0,0,0,0}};
        int[] probabilities = new Solution().ProbabilitiesToReachGivenTerminalStates(matrix1);
        Assertions.assertTrue(true);
        int[] expected = {0,3,2,9,14};
        Assertions.assertArrayEquals(expected,probabilities);
    }
    @Test
    public void GivenAMatrixMatrix2ShouldReturn7_6_8_21()
    {
        int[][] matrix2 =
                {{0,2,1,0,0},
                {0,0,0,3,4},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}};
        int[] probabilities = new Solution().ProbabilitiesToReachGivenTerminalStates(matrix2);
        Assertions.assertTrue(true);
        int[] expected = {7,6,8,21};
        Assertions.assertArrayEquals(expected,probabilities);
    }
}
