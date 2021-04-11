import java.math.BigInteger;
import java.util.Arrays;
public class Solution
{
    public static void main(String[] args)
    {
        int[][]matrix = {
                {0,2,0,0,0,2},
                {4,0,0,3,2,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0}};

        //System.out.println(Arrays.toString(ProbabilitiesToReachEndStatesOf(matrix)));
    }
    public int[] ProbabilitiesToReachEndStatesOf(int[][]matrix)
    {
        if(matrix.length <= 2)
        {
            int[] result = {1,1};
            return result;
        }
        else
        {
            int[] terminalStates = FindAllTerminatingStates(matrix);
            Fraction[] probabilityRowVector = GetProbabilityRowVectorOf(matrix);
            int[] result = GetProbabilitiesOfTerminalStatesAsIntArr(probabilityRowVector, terminalStates);
            return result;
        }
    }

    private int[] GetProbabilitiesOfTerminalStatesAsIntArr(Fraction[] probabilityRowVector, int[] terminalStates)
    {
        int[] result;
        int length = 1;

        for(int i = 0; i < terminalStates.length; i++)
        {
            if(terminalStates[i] == 1)
            {
                length++;
            }
        }
        result = new int[probabilityRowVector.length+1];
        int count = 0;

        BigInteger commonDenominator = BigInteger.ONE;
         for(int i = 0; i < probabilityRowVector.length; i++)
        {
            commonDenominator = Fraction.lcm(commonDenominator, probabilityRowVector[i].fractionDenominator);
        }
        for(int i = 0; i < length-1; i++)
        {
            if(probabilityRowVector[i].fractionDenominator.compareTo(commonDenominator) != 0)
            {
                BigInteger factor = commonDenominator.divide(probabilityRowVector[i].fractionDenominator);
                probabilityRowVector[i].fractionDenominator =probabilityRowVector[i].fractionDenominator.multiply(factor);
                probabilityRowVector[i].fractionNumerator = probabilityRowVector[i].fractionNumerator.multiply(factor);
            }
        }
        for(int i = 0; i < probabilityRowVector.length; i++)
        {
            result[count] = probabilityRowVector[i].fractionNumerator.intValue();
            count++;
        }
        result[count] = commonDenominator.intValue();
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
        if(normalizedMatrix.length == 1 && normalizedMatrix[0].length == 1)
        {
            Fraction[][]inverted = new Fraction[1][1];
            inverted[0] = new Fraction[1];
            inverted[0][0] = new Fraction(0);
            inverted[0][0].fractionNumerator = normalizedMatrix[0][0].fractionDenominator;
            inverted[0][0].fractionDenominator = normalizedMatrix[0][0].fractionNumerator;
            return inverted;
        }
        return new Matrix().inverse(normalizedMatrix);
    }

    public Fraction[][] GetIdentityMatrixSizeOf(int size)
    {
        Fraction[][] identityMatrix = new Fraction[size][];
        for(int i = 0; i < size; i++)
        {
            identityMatrix[i] = new Fraction[size];
            for(int j = 0; j < size ;j++)
            {
                identityMatrix[i][j] = new Fraction();
            }
            identityMatrix[i][i].fractionNumerator = BigInteger.ONE;
            identityMatrix[i][i].fractionNumerator = BigInteger.ONE;
        }
        return identityMatrix;
    }
    public Fraction[][] Subtract(Fraction[][] from, Fraction[][] with)
    {
        if(from.length != with.length && from[0].length != with[0].length)
            throw new IllegalArgumentException("Dimensions must be equal");
        Fraction[][] result = new Fraction[from.length][];
        for(int i = 0; i < with.length; i++)
        {
            result[i] = new Fraction[from.length];
            for(int j = 0; j < with.length; j++)
            {
                result[i][j] = from[i][j].Subtract(with[i][j]);
            }
        }
        return result;
    }

    public Fraction[] GetProbabilityRowVectorOf(int[][] matrix)
    {
        Fraction[][] normalizedMatrix = NormalizeMatrix(matrix);
        int[] terminatingStates = FindAllTerminatingStates(matrix);
        int howManyTerminating = 0;
        for(int i = 0; i < terminatingStates.length; i++)
        {
            if(terminatingStates[i] == 1)
                howManyTerminating++;
        }
        if(terminatingStates[0] == 1)
        {
            Fraction[] emptyArr = new Fraction[howManyTerminating];
            emptyArr[0] = new Fraction(1,1);
            for(int i = 1; i<howManyTerminating;i++)
            {
                emptyArr[i] = new Fraction();
            }
            return emptyArr;
        }
        Fraction[][] standardForm = GetStandardFormOf(normalizedMatrix, terminatingStates);
        Fraction[][] subMatrix = GetSubMatrixFrom(standardForm, howManyTerminating);
        Fraction[][] identityMatrix = GetIdentityMatrixSizeOf(subMatrix.length);
        Fraction[][] result = Subtract(identityMatrix,subMatrix);
        Fraction[][] fundamentalMatrix = GetInverseOf(result);
        Fraction[][] secondSubMatrix = GetSecondSubMatrixFrom(standardForm, howManyTerminating);
        Fraction[][] fRMatrix = Multiply(fundamentalMatrix, secondSubMatrix);

        return fRMatrix[0];
    }
    private Fraction[][] Multiply(Fraction[][] matrix1, Fraction[][] matrix2)
    {
        Fraction[][] product = new Fraction[matrix1.length][matrix2[0].length];
            for(int i = 0; i < matrix1.length; i++)
            {
                for(int j = 0; j < matrix2[0].length;j++)
                {
                    product[i][j] = new Fraction(0);
                    for(int k = 0; k < matrix2.length; k++)
                    {
                        product[i][j] = product[i][j].Add(matrix1[i][k].Multiply(matrix2[k][j]));
                    }
                }
            }

        return product;
    }
    private Fraction[][] GetSecondSubMatrixFrom(Fraction[][] standardForm, int howManyTerminating)
    {
        Fraction[][] secondSubMatrix = new Fraction[standardForm.length-howManyTerminating][];
        for(int i = howManyTerminating; i < standardForm.length; i++)
        {
            secondSubMatrix[i-howManyTerminating] = new Fraction[howManyTerminating];
            for(int j = 0; j < howManyTerminating; j++)
            {
                secondSubMatrix[i-howManyTerminating][j] = standardForm[i][j];
            }
        }
        return secondSubMatrix;
    }

    private Fraction[][] GetSubMatrixFrom(Fraction[][] standardForm, int howManyTerminating)
    {
        Fraction[][] subMatrix = new Fraction[standardForm.length-howManyTerminating][];
        for(int i = howManyTerminating; i < standardForm.length; i++)
        {
            subMatrix[i-howManyTerminating] = new Fraction[standardForm.length-howManyTerminating];
            for(int j = howManyTerminating; j < standardForm.length; j++)
            {
                subMatrix[i-howManyTerminating][j-howManyTerminating] = standardForm[i][j];
            }
        }
        return subMatrix;
    }

    public Fraction[][] GetStandardFormOf(Fraction[][] normalizedMatrix, int[] terminatingStates)
    {
        Fraction[][] standardFormMatrix = new Fraction[normalizedMatrix.length][];
        int count = 0;
        int count2;
        for(int i = 0; i < terminatingStates.length; i++)
        {
            count2 = 0;
            if(terminatingStates[i] == 1)
            {
                standardFormMatrix[count] = new Fraction[normalizedMatrix.length];
                for(int j = 0; j < terminatingStates.length; j++)
                {
                    if(terminatingStates[j] == 1)
                    {
                        standardFormMatrix[count][count2] = normalizedMatrix[i][j];
                        count2++;
                    }

                }
                for(int j = 0; j < terminatingStates.length; j++)
                {
                    if(terminatingStates[j] == 0)
                    {
                        standardFormMatrix[count][count2] = normalizedMatrix[i][j];
                        count2++;
                    }
                }
                count++;
            }
        }
        for(int i = 0; i < terminatingStates.length; i++)
        {
            count2 = 0;
            if(terminatingStates[i] == 0)
            {
                standardFormMatrix[count] = new Fraction[normalizedMatrix.length];
                for(int j = 0; j < terminatingStates.length; j++)
                {
                    if(terminatingStates[j] == 1)
                    {
                        standardFormMatrix[count][count2] = normalizedMatrix[i][j];
                        count2++;
                    }

                }
                for(int j = 0; j < terminatingStates.length; j++)
                {
                    if(terminatingStates[j] == 0)
                    {
                        standardFormMatrix[count][count2] = normalizedMatrix[i][j];
                        count2++;
                    }
                }
                count++;
            }
        }
        return standardFormMatrix;
    }
}
