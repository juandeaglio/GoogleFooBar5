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
}
