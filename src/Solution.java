import java.util.Arrays;

public class Solution
{
    public static void main(String[] args)
    {
    }
    public int[] ProbabilitiesToReachGivenTerminalStates(int[][]matrix)
    {
        int[] result = new int[matrix.length];
        return result;
    }

    public int[] FindAllTerminatingStates(int[][] matrix)
    {
        int[] terminatingStates = new int[matrix.length];
        Arrays.fill(terminatingStates,1);
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix.length; j++)
            {
                if(matrix[i][j] > 0)
                {
                    terminatingStates[i] = 0;
                }
            }
        }
        return terminatingStates;
    }

    public int[] FindTransitionMatrix(int[][] matrix, int steps)
    {
        int[] transitionMatrix = new int[matrix.length];
        transitionMatrix[0] = 1; //initial state will always be state0
        for(int i = 0; i < steps; i++)
        {

        }
        return transitionMatrix;
    }
}
