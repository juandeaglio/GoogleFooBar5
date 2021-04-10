import java.math.BigInteger;
import java.util.Arrays;
public class Matrix
{
    public static Fraction[][] transpose(Fraction[][] matrix) {
        Fraction[][] transposedMatrix = new Fraction[matrix.length][matrix.length];
        for (int i=0;i<matrix.length;i++) {
            for (int j=0;j<matrix.length;j++) {
                transposedMatrix[j][i] = matrix[i][j];
            }
        }
        return transposedMatrix;
    }

    public static Fraction determinant(Fraction[][] matrix) throws IllegalArgumentException
    {
        if (matrix.length != matrix[0].length)
            throw new IllegalArgumentException("matrix need to be square.");
        if (matrix.length == 1) {
            return matrix[0][0];
        }
        if (matrix.length == 2) {
            return (matrix[0][0].Multiply(matrix[1][1]).Subtract(matrix[0][1].Multiply(matrix[1][0])));
        }
        Fraction sum = new Fraction();
        for (int i=0; i< matrix.length; i++)
        {
            if(changeSign(i) == -1)
            {
                sum = sum.Add(matrix[0][i].Multiply(determinant(createSubMatrix(matrix, 0, i))).Negate());
            }
            else
                sum = sum.Add(matrix[0][i].Multiply(determinant(createSubMatrix(matrix, 0, i))));
        }
        return sum;
    }

    private static double changeSign(int i) {
        if(i % 2 == 0)
            return 1;
        else
            return -1;
    }

    public static Fraction[][] createSubMatrix(Fraction[][] matrix, int excluding_row, int excluding_col) {
        Fraction[][] mat = new Fraction[matrix.length-1][matrix.length-1];
        int r = -1;
        for (int i=0;i<matrix.length;i++)
        {
            if (i==excluding_row)
                continue;
            r++;
            int c = -1;
            for (int j=0;j<matrix.length;j++)
            {
                if (j==excluding_col)
                    continue;
                //mat[i][j] = new Fraction();
                if(matrix[i][j].fractionNumerator.compareTo(BigInteger.valueOf(9473)) == 0)
                {
                    boolean yes = true;
                }
                mat[r][++c] = matrix[i][j];
            }
        }
        return mat;
    }
    public static Fraction[][] cofactor(Fraction[][] matrix) throws IllegalArgumentException {
        Fraction[][] mat = new Fraction[matrix.length][matrix.length];
        for (int i=0;i<matrix.length;i++)
        {
            mat[i] = new Fraction[matrix.length];
            for (int j=0; j<matrix.length;j++)
            {
                mat[i][j] = new Fraction();
                if(mat[i][j].fractionNumerator.compareTo(BigInteger.valueOf(9473)) == 0)
                {
                    boolean yes = true;
                }
                mat[i][j] = determinant(createSubMatrix(matrix, i, j));
                if(mat[i][j].fractionNumerator.compareTo(BigInteger.valueOf(1384573680)) == 0)
                {
                    boolean yes = true;
                }
                if(changeSign(i) == -1)
                    mat[i][j] =  mat[i][j].Negate();
                if(changeSign(j) == -1)
                    mat[i][j] =  mat[i][j].Negate();
            }
        }

        return mat;
    }
    public static Fraction[][] inverse(Fraction[][] matrix) throws IllegalArgumentException
    {
        if(matrix[0][4].fractionNumerator.compareTo(BigInteger.valueOf(-5)) == 0)
        {
            boolean yes = true;
        }
        Fraction[][] inversedMatrix = transpose(cofactor(matrix));
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix.length; j++)
            {
                if(inversedMatrix[i][j].fractionNumerator.compareTo(BigInteger.valueOf(9473)) == 0)
                {
                    boolean yes = true;
                }
                inversedMatrix[i][j] = inversedMatrix[i][j].Divide(determinant(matrix));
            }
        }
        return inversedMatrix;
    }
}
//87413
