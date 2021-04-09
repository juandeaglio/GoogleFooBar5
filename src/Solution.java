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

            }
        }
        return normalizedMatrix;
    }

    public Fraction[][] GetInverseOf(Fraction[][] normalizedMatrix)
    {
        return new Matrix().inverse(Subtract(GetIdentityMatrixSizeOf(normalizedMatrix.length), normalizedMatrix));
    }

    public Fraction[][] GetIdentityMatrixSizeOf(int size)
    {
        Fraction[][] identityMatrix = new Fraction[size][];
        for(int i = 0; i < size; i++)
        {
            identityMatrix[i] = new Fraction[size];
            for(int j = 0; j < size ;j++)
            {
                identityMatrix[i][j] = new Fraction(0,0);
            }
            identityMatrix[i][i].fractionNumerator = 1;
            identityMatrix[i][i].fractionNumerator = 1;
        }
        return identityMatrix;
    }
    public Fraction[][] Subtract(Fraction[][] from, Fraction[][] with)
    {
        if(from.length != with.length && from[0].length != with[0].length)
            throw new IllegalArgumentException("Dimensions must be equal");
        Fraction[][] result = new Fraction[from.length][];
        for(int i = 0; i < from.length; i++)
        {
            result[i] = new Fraction[from.length];
            for(int j = 0; j < from.length; j++)
            {
                result[i][j] = from[i][j].Subtract(with[i][j]);
            }
        }
        return result;
    }
}
