import java.math.BigInteger;

public class Matrix
{
    private Fraction determinant(Fraction[][] matrix)
    {
        if (matrix.length != matrix[0].length)
            throw new IllegalStateException("invalid dimensions");
        if (matrix.length == 2)
            return matrix[0][0].Multiply(matrix[1][1]).Subtract(matrix[0][1].Multiply(matrix[1][0]));
        if (matrix.length == 1)
        {
            if (matrix[0][0].fractionNumerator.compareTo(BigInteger.ZERO) == 0)
                return new Fraction(matrix[0][0].fractionDenominator, BigInteger.ONE);
            else
                return new Fraction(matrix[0][0].fractionDenominator, matrix[0][0].fractionNumerator);
        }
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