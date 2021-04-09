import java.math.BigInteger;

public class Fraction
{
    int fractionNumerator;
    int fractionDenominator;
    Fraction(int numerator, int denominator)
    {
        SetFractionNumerator(numerator);
        SetFractionDenominator(denominator);
    }
    public void SetFractionNumerator(int numerator)
    {
        fractionNumerator = numerator;
    }
    public void SetFractionDenominator(int denominator)
    {
        if(denominator == 0)
        {
            throw new IllegalArgumentException();
        }
        fractionDenominator = denominator;
    }
    public Fraction Add(Fraction other)
    {
        if(other.fractionDenominator != fractionDenominator)
        {
            int commonDenominator = gcd(fractionDenominator, other.fractionDenominator);
            if(commonDenominator == 1)
            {
                int tempOther = other.fractionDenominator;
                other.fractionDenominator *= fractionDenominator;
                other.fractionNumerator *= fractionDenominator;
                fractionDenominator *= tempOther;
                fractionNumerator *= tempOther;
            }
            else
            {
                int leastCommonMultiple = lcm(fractionDenominator, other.fractionDenominator);
                int thisFactor = leastCommonMultiple/fractionDenominator;
                int otherFactor = leastCommonMultiple/other.fractionDenominator;
                fractionDenominator *= thisFactor;
                fractionNumerator *= thisFactor;
                other.fractionDenominator *= otherFactor;
                other.fractionNumerator *= otherFactor;
            }
        }
        return new Fraction(Math.abs(fractionNumerator) + Math.abs(other.fractionNumerator),Math.abs(fractionDenominator) + Math.abs(other.fractionDenominator) );
    }
    public Fraction Multiply(Fraction other)
    {
        return new Fraction(Math.abs(fractionNumerator) * Math.abs(other.fractionNumerator), Math.abs(fractionDenominator) * Math.abs(other.fractionDenominator));
    }
    public Fraction Divide(Fraction other)
    {
        if(other.fractionNumerator == 0)
        {
            throw new IllegalArgumentException();
        }
        return new Fraction(Math.abs(fractionNumerator) * Math.abs(other.fractionDenominator), Math.abs(fractionDenominator) * Math.abs(other.fractionNumerator));
    }
    public Fraction Subtract(Fraction other)
    {
        return new Fraction(Math.abs(Math.abs(fractionNumerator) - Math.abs(other.fractionNumerator)), Math.abs((Math.abs(fractionDenominator) - Math.abs(other.fractionDenominator))));
    }
    @Override
    public boolean equals(Object obj)
    {
        Fraction compareTo;
        if(obj.getClass().getTypeName().compareTo("Fraction") == 0)
        {
            compareTo = (Fraction) obj;
            return fractionNumerator == compareTo.fractionNumerator && fractionDenominator == compareTo.fractionDenominator;
        }
        else
            return false;
    }
    public static int gcd(int a, int b)
    {
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
}