import java.math.BigInteger;
import java.util.Arrays;
public class SolutionFooBar
{
    public static void main(String[] args)
    {
        /*int[][]matrix = {
                {6,1345,845,5345345,5,3,2,4,1,0},
                {56345,2,2,2,2,2,2,2,2,0},
                {4,57345,0,0,4,4,0,0,0,1},
                {2,2,533445,2,4,5,1,2,5,6},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0}};*/
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
        /*int[][]matrix = {
                {0,2,0,0,0,2},
                {4,0,0,3,2,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0}};*/
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

        int commonDenominator = 1;
        for(int i = 0; i < probabilityRowVector.length; i++)
        {
            if(terminalStates[i] == 1)
            {
                commonDenominator = Fraction.lcm(commonDenominator, probabilityRowVector[i].fractionDenominator);
            }
        }
        for(int i = 0; i < result.length; i++)
        {
            if(terminalStates[i] == 1 && probabilityRowVector[i].fractionDenominator != commonDenominator)
            {
                int factor = commonDenominator / probabilityRowVector[i].fractionDenominator;
                probabilityRowVector[i].fractionDenominator *= factor;
                probabilityRowVector[i].fractionNumerator *= factor;
            }
        }
        for(int i = 0; i < probabilityRowVector.length; i++)
        {
            if(terminalStates[i] == 1)
            {
                result[count] = probabilityRowVector[i].fractionNumerator;
                count++;
            }
        }
        result[count] = commonDenominator;
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
            identityMatrix[i][i].fractionNumerator = 1;
            identityMatrix[i][i].fractionNumerator = 1;
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
    public static class Fraction {
        int fractionNumerator;
        int fractionDenominator;

        public Fraction(int numerator, int denominator) {
            SetFractionNumerator(numerator);
            SetFractionDenominator(denominator);
            SimplifyFraction(this);
        }

        public Fraction(int numerator) {
            SetFractionNumerator(numerator);
            SetFractionDenominator(1);
        }

        public Fraction() {
            SetFractionNumerator(0);
            SetFractionDenominator(1);
        }

        public void SetFractionNumerator(int numerator) {
            fractionNumerator = numerator;
        }

        public void SetFractionDenominator(int denominator) {
            if (denominator == 0) {
                throw new IllegalArgumentException();
            }
            fractionDenominator = denominator;
        }

        public Fraction Add(Fraction other) {
            formLikeDenominators(this, other);
            return new Fraction(fractionNumerator + other.fractionNumerator, fractionDenominator);
        }

        public Fraction Subtract(Fraction other) {
            formLikeDenominators(this, other);
            return new Fraction(fractionNumerator - other.fractionNumerator, Math.abs(Math.abs(fractionDenominator)));
        }

        public Fraction Multiply(Fraction other) {
            return new Fraction(fractionNumerator * other.fractionNumerator, fractionDenominator * other.fractionDenominator);
        }

        public Fraction Divide(Fraction other) {
            if (other.fractionNumerator == 0) {
                throw new IllegalArgumentException();
            }
            return new Fraction(fractionNumerator * other.fractionDenominator, fractionDenominator * other.fractionNumerator);
        }

        @Override
        public boolean equals(Object obj) {
            Fraction compareTo;
            if (obj.getClass().getTypeName().compareTo("Fraction") == 0) {
                compareTo = (Fraction) obj;
                return fractionNumerator == compareTo.fractionNumerator && fractionDenominator == compareTo.fractionDenominator;
            } else
                return false;
        }

        public static int gcd(int a, int b) {
            return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
        }

        public static int lcm(int number1, int number2) {
            if (number1 == 0 || number2 == 0) {
                return 0;
            }
            int absNumber1 = Math.abs(number1);
            int absNumber2 = Math.abs(number2);
            int absHigherNumber = Math.max(absNumber1, absNumber2);
            int absLowerNumber = Math.min(absNumber1, absNumber2);
            int lcm = absHigherNumber;
            while (lcm % absLowerNumber != 0) {
                lcm += absHigherNumber;
            }
            return lcm;
        }

        private static void formLikeDenominators(Fraction current, Fraction other) {
            if (other.fractionDenominator != current.fractionDenominator) {
                int commonDenominator = gcd(current.fractionDenominator, other.fractionDenominator);
                if (commonDenominator == 1) {
                    int tempOther = other.fractionDenominator;
                    other.fractionDenominator *= current.fractionDenominator;
                    other.fractionNumerator *= current.fractionDenominator;
                    current.fractionDenominator *= tempOther;
                    current.fractionNumerator *= tempOther;
                } else {
                    int leastCommonMultiple = lcm(current.fractionDenominator, other.fractionDenominator);
                    int thisFactor = leastCommonMultiple / current.fractionDenominator;
                    int otherFactor = leastCommonMultiple / other.fractionDenominator;
                    current.fractionDenominator *= thisFactor;
                    current.fractionNumerator *= thisFactor;
                    other.fractionDenominator *= otherFactor;
                    other.fractionNumerator *= otherFactor;
                }
            }
        }

        public static void SimplifyFraction(Fraction fraction) {
            int factor = gcd(fraction.fractionNumerator, fraction.fractionDenominator);
            fraction.fractionNumerator /= factor;
            fraction.fractionDenominator /= factor;
            if (fraction.fractionNumerator < 0 && fraction.fractionDenominator < 0) {
                fraction.fractionDenominator = -fraction.fractionDenominator;
                fraction.fractionNumerator = -fraction.fractionNumerator;
            }
        }
    }
}
