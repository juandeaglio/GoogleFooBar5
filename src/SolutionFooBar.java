import java.math.BigInteger;
import java.util.Arrays;
public class SolutionFooBar
{
    public static void main(String[] args)
    {
        int[][]matrix = {
                {0,1,1,1,1,1,1,1,1,1},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,1,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {1,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0}};/*
        int[][]matrix = {
                {6,10,8,40,5,3,2,4,1,0},
                {50,2,2,2,2,2,2,2,2,0},
                {4,50,0,0,4,4,0,0,0,1},
                {2,2,30,2,4,5,1,2,5,6},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0}};
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
                {0,0,0,0,0,0,0,0,0,0}};*/
        /*int[][] matrix = {
                {0, 1, 1, 1, 1, 2, 0, 0, 0, 1},
                {3, 0, 0, 1, 0, 0, 2, 2, 2, 0},
                {1, 2, 0, 4, 1, 1, 1, 0, 0, 6},
                {2, 2, 0, 0, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {6, 5, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        int[][]matrix = {
                {0,2,0,0,0,2},
                {4,0,0,3,2,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0}};
        int[][]matrix = {
                {5,4,1},
                {0,0,0},
                {0,0,0}};*/

        System.out.println(Arrays.toString(ProbabilitiesToReachEndStatesOf(matrix)));
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
        result = new int[length];
        int count = 0;

        BigInteger commonDenominator = BigInteger.ONE;
        for(int i = 0; i < probabilityRowVector.length; i++)
        {
            if(terminalStates[i] == 1)
            {
                commonDenominator = Fraction.lcm(commonDenominator, probabilityRowVector[i].fractionDenominator);
            }
        }
        for(int i = 0; i < terminalStates.length; i++)
        {
            if(terminalStates[i] == 1 && probabilityRowVector[i].fractionDenominator.compareTo(commonDenominator) != 0)
            {
                BigInteger factor = commonDenominator.divide(probabilityRowVector[i].fractionDenominator);
                probabilityRowVector[i].fractionDenominator =probabilityRowVector[i].fractionDenominator.multiply(factor);
                probabilityRowVector[i].fractionNumerator = probabilityRowVector[i].fractionNumerator.multiply(factor);
            }
        }
        for(int i = 0; i < probabilityRowVector.length; i++)
        {
            if(terminalStates[i] == 1)
            {
                result[count] = probabilityRowVector[i].fractionNumerator.intValue();
                count++;
            }
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
        return new Matrix().inverse(Subtract(GetIdentityMatrixSizeOf(normalizedMatrix.length), normalizedMatrix));
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

    public static Fraction[] GetProbabilityRowVectorOf(int[][] matrix)
    {
        Fraction[][] normalizedMatrix = NormalizeMatrix(matrix);
        Fraction[] probabilityRowVector = GetInverseOf(normalizedMatrix)[0];
        return probabilityRowVector;
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
}