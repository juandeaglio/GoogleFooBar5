import java.math.BigInteger;
import java.util.Arrays;
public class Test
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
        ProbabilitiesToReachEndStatesOf(matrix);
    }
    public static int[] ProbabilitiesToReachEndStatesOf(int[][]matrix)
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

    private static int[] GetProbabilitiesOfTerminalStatesAsIntArr(Fraction[] probabilityRowVector, int[] terminalStates)
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

    public static int[] FindAllTerminatingStates(int[][] matrix)
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

    public static Fraction[][] NormalizeMatrix(int[][] matrix)
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

    public static Fraction[][] GetInverseOf(Fraction[][] normalizedMatrix)
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

    public static Fraction[][] GetIdentityMatrixSizeOf(int size)
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
    public static Fraction[][] Subtract(Fraction[][] from, Fraction[][] with)
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

    public static Fraction[] GetProbabilityRowVectorOf(int[][] matrix)
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
    private static Fraction[][] Multiply(Fraction[][] matrix1, Fraction[][] matrix2)
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
    private static Fraction[][] GetSecondSubMatrixFrom(Fraction[][] standardForm, int howManyTerminating)
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

    private static Fraction[][] GetSubMatrixFrom(Fraction[][] standardForm, int howManyTerminating)
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

    public static Fraction[][] GetStandardFormOf(Fraction[][] normalizedMatrix, int[] terminatingStates)
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
    public static class Matrix
    {
        private Fraction determinant(Fraction[][] matrix)
        {
            if (matrix.length != matrix[0].length)
                throw new IllegalStateException("invalid dimensions");

            if (matrix.length == 2)
                return matrix[0][0].Multiply(matrix[1][1]).Subtract(matrix[0][1].Multiply(matrix[1][0]));

            Fraction det = new Fraction();
            for (int i = 0; i < matrix[0].length; i++)
                det = det.Add(new Fraction((int)Math.pow(-1, i)).Multiply(matrix[0][i]).Multiply(determinant(minor(matrix, 0, i))));
            return det;
        }

        public Fraction[][] inverse(Fraction[][] matrix) {
            Fraction[][] inverse = new Fraction[matrix.length][matrix.length];
            if(matrix[0].length == 2)
            {
                inverse = matrix;
                Fraction result = matrix[0][0].Multiply(matrix[1][1]).Subtract(matrix[0][1].Multiply(matrix[1][0]));
                Fraction det = new Fraction(result.fractionDenominator,result.fractionNumerator);
                inverse[0][0] = matrix[1][1];
                inverse[0][1] = inverse[0][1].Negate();
                inverse[1][0] = inverse[1][0].Negate();
                inverse[1][1] = matrix[0][0];
                for(int i = 0; i < inverse.length;i++)
                {
                    for(int j = 0; j < inverse[0].length; j++)
                    {
                        inverse[i][j] = inverse[i][j].Multiply(det);
                    }
                }
                return inverse;
            }

            // minors and cofactors
            for (int i = 0; i < matrix.length; i++)
                for (int j = 0; j < matrix[i].length; j++)
                {
                    inverse[i][j] = new Fraction((int)Math.pow(-1, i + j)).Multiply(determinant(minor(matrix, i, j)));
                }

            // adjugate and determinant
            Fraction det = new Fraction(1).Divide(determinant(matrix));
            for (int i = 0; i < inverse.length; i++) {
                for (int j = 0; j <= i; j++) {
                    Fraction temp = inverse[i][j];
                    inverse[i][j] = inverse[j][i].Multiply(det);
                    inverse[j][i] = temp.Multiply(det);
                }
            }
            return inverse;
        }

        private Fraction[][] minor(Fraction[][] matrix, int row, int column) {
            Fraction[][] minor = new Fraction[matrix.length - 1][matrix.length - 1];

            for (int i = 0; i < matrix.length; i++)
                for (int j = 0; i != row && j < matrix[i].length; j++)
                    if (j != column)
                        minor[i < row ? i : i - 1][j < column ? j : j - 1] = matrix[i][j];
            return minor;
        }

    }
    public static class Fraction
    {
        BigInteger fractionNumerator;
        BigInteger fractionDenominator;
        public Fraction(int numerator, int denominator)
        {
            SetFractionNumerator(BigInteger.valueOf(numerator));
            SetFractionDenominator(BigInteger.valueOf(denominator));
            SimplifyFraction(this);
        }
        public Fraction(BigInteger numerator, BigInteger denominator)
        {
            SetFractionNumerator(numerator);
            SetFractionDenominator(denominator);
            SimplifyFraction(this);
        }
        public Fraction(int numerator)
        {
            SetFractionNumerator(BigInteger.valueOf(numerator));
            SetFractionDenominator(BigInteger.ONE);
        }
        public Fraction()
        {
            SetFractionNumerator(BigInteger.ZERO);
            SetFractionDenominator(BigInteger.ONE);
        }
        public void SetFractionNumerator(BigInteger numerator)
        {
            if(numerator.compareTo(BigInteger.valueOf(1384573680)) == 0)
            {
                boolean yes = true;
            }
            fractionNumerator = numerator;
        }
        public void SetFractionDenominator(BigInteger denominator)
        {
            if(denominator.compareTo(BigInteger.ZERO) == 0)
            {
                throw new IllegalArgumentException();
            }
            else if(denominator.compareTo(BigInteger.ZERO) < 0 )
            {
                fractionNumerator = fractionNumerator.negate();
                fractionDenominator = denominator.negate();
            }
            else
                fractionDenominator = denominator;
        }
        public Fraction Add(Fraction other)
        {
            formLikeDenominators(this, other);
            return new Fraction(fractionNumerator.add(other.fractionNumerator), fractionDenominator);
        }
        public Fraction Subtract(Fraction other)
        {
            formLikeDenominators(this, other);
            return new Fraction(fractionNumerator.subtract(other.fractionNumerator), fractionDenominator);
        }
        public Fraction Multiply(Fraction other)
        {
            return new Fraction(fractionNumerator.multiply(other.fractionNumerator), fractionDenominator.multiply(other.fractionDenominator));
        }
        public Fraction Divide(Fraction other)
        {
            if(other.fractionNumerator.compareTo(BigInteger.ZERO) == 0)
            {
                throw new IllegalArgumentException();
            }
            return new Fraction(fractionNumerator.multiply(other.fractionDenominator), fractionDenominator.multiply( other.fractionNumerator));
        }
        @Override
        public boolean equals(Object obj)
        {
            Fraction compareTo;
            if(obj.getClass().getTypeName().compareTo("Fraction") == 0)
            {
                compareTo = (Fraction) obj;
                return fractionNumerator.compareTo(compareTo.fractionNumerator) == 0 && fractionDenominator.compareTo(compareTo.fractionDenominator) == 0;
            }
            else
                return false;
        }
        public static BigInteger lcm(BigInteger number1, BigInteger number2)
        {
            if (number1.compareTo(BigInteger.ZERO) == 0 || number2.compareTo(BigInteger.ZERO) == 0)
            {
                return BigInteger.ZERO;
            }
            BigInteger absNumber1 = number1.abs();
            BigInteger absNumber2 = number2.abs();
            BigInteger absHigherNumber = absNumber1.max(absNumber2);
            BigInteger absLowerNumber = absNumber1.min(absNumber2);
            BigInteger lcm = absHigherNumber;
            while (lcm.mod(absLowerNumber).compareTo(BigInteger.ZERO) != 0)
            {
                lcm = lcm.add(absHigherNumber);
            }
            return lcm;
        }
        private static void formLikeDenominators(Fraction current, Fraction other) {
            if(other.fractionDenominator.compareTo(current.fractionDenominator) != 0)
            {
                BigInteger commonDenominator = current.fractionDenominator.gcd(other.fractionDenominator);
                if(commonDenominator.compareTo(BigInteger.ZERO) == 0)
                {
                    BigInteger tempOther = other.fractionDenominator;
                    other.fractionDenominator = other.fractionDenominator.multiply(current.fractionDenominator);
                    other.fractionNumerator = other.fractionNumerator.multiply(current.fractionDenominator);
                    current.fractionDenominator = current.fractionDenominator.multiply(tempOther);
                    current.fractionNumerator = current.fractionNumerator.multiply(tempOther);
                }
                else
                {
                    BigInteger leastCommonMultiple = lcm(current.fractionDenominator, other.fractionDenominator);
                    BigInteger thisFactor = leastCommonMultiple.divide(current.fractionDenominator);
                    BigInteger otherFactor = leastCommonMultiple.divide(other.fractionDenominator);
                    current.fractionDenominator = current.fractionDenominator.multiply(thisFactor);
                    current.fractionNumerator =current.fractionNumerator.multiply(thisFactor);
                    other.fractionDenominator =other.fractionDenominator.multiply(otherFactor);
                    other.fractionNumerator = other.fractionNumerator.multiply(otherFactor);
                }
            }
        }
        public static void SimplifyFraction(Fraction fraction)
        {
            BigInteger factor = fraction.fractionNumerator.gcd(fraction.fractionDenominator);
            fraction.fractionNumerator= fraction.fractionNumerator.divide(factor);
            fraction.fractionDenominator= fraction.fractionDenominator.divide(factor);
            if(fraction.fractionNumerator.compareTo(BigInteger.ZERO) < 0 && fraction.fractionDenominator.compareTo(BigInteger.ZERO) < 0)
            {
                fraction.fractionDenominator = fraction.fractionDenominator.negate();
                fraction.fractionNumerator = fraction.fractionNumerator.negate();
            }
            if(fraction.fractionNumerator.compareTo(BigInteger.valueOf(28419)) == 0)
            {
                boolean yes = true;
            }
        }

        public Fraction Negate()
        {
            fractionNumerator = fractionNumerator.negate();
            return this;
        }
    }
}
