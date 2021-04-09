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

    public Fraction[][] NormalizeMatrix(int[][] matrix)
    {
        Fraction[][] normalizedMatrix = new Fraction[matrix.length][];
        for(int i = 0; i < matrix.length; i++)
        {
            normalizedMatrix[i] = new Fraction[matrix.length];
            int total = 0;
            for(int j = 0; j < normalizedMatrix[i].length; j++)
            {
                total += matrix[i][j];
            }
            if(total == 0)
            {
                total = 1;
            }
            for(int j = 0; j < normalizedMatrix[i].length; j++)
            {
                if(matrix[i][j] == 0)
                    normalizedMatrix[i][j] = new Fraction(matrix[i][j], 1);
                else
                    normalizedMatrix[i][j] = new Fraction(matrix[i][j], total);

                System.out.println(matrix[i][j] + " " + total + " \t" + normalizedMatrix[i][j].fractionNumerator + " " + normalizedMatrix[i][j].fractionDenominator  );
            }
        }
        return normalizedMatrix;
    }
}
