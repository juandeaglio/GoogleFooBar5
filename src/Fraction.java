import java.math.BigInteger;

public class Fraction
{
    int fractionNumerator;
    int fractionDenominator;
    public Fraction(int numerator, int denominator)
    {
        SetFractionNumerator(numerator);
        SetFractionDenominator(denominator);
        SimplifyFraction(this);
    }
    public Fraction(int numerator)
    {
        SetFractionNumerator(numerator);
        SetFractionDenominator(1);
    }
    public Fraction()
    {
        SetFractionNumerator(0);
        SetFractionDenominator(1);
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
        formLikeDenominators(this, other);
        return new Fraction(fractionNumerator + other.fractionNumerator, fractionDenominator);
    }
    public Fraction Subtract(Fraction other)
    {
        formLikeDenominators(this, other);
        return new Fraction(fractionNumerator - other.fractionNumerator, Math.abs(Math.abs(fractionDenominator)));
    }
    public Fraction Multiply(Fraction other)
    {
        return new Fraction(fractionNumerator * other.fractionNumerator, fractionDenominator * other.fractionDenominator);
    }
    public Fraction Divide(Fraction other)
    {
        if(other.fractionNumerator == 0)
        {
            throw new IllegalArgumentException();
        }
        return new Fraction(fractionNumerator * other.fractionDenominator, fractionDenominator * other.fractionNumerator);
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
    private static void formLikeDenominators(Fraction current, Fraction other) {
        if(other.fractionDenominator != current.fractionDenominator)
        {
            int commonDenominator = gcd(current.fractionDenominator, other.fractionDenominator);
            if(commonDenominator == 1)
            {
                int tempOther = other.fractionDenominator;
                other.fractionDenominator *= current.fractionDenominator;
                other.fractionNumerator *= current.fractionDenominator;
                current.fractionDenominator *= tempOther;
                current.fractionNumerator *= tempOther;
            }
            else
            {
                int leastCommonMultiple = lcm(current.fractionDenominator, other.fractionDenominator);
                int thisFactor = leastCommonMultiple/current.fractionDenominator;
                int otherFactor = leastCommonMultiple/ other.fractionDenominator;
                current.fractionDenominator *= thisFactor;
                current.fractionNumerator *= thisFactor;
                other.fractionDenominator *= otherFactor;
                other.fractionNumerator *= otherFactor;
            }
        }
    }
    public static void SimplifyFraction(Fraction fraction)
    {
        int factor = gcd(fraction.fractionNumerator,fraction.fractionDenominator);
        fraction.fractionNumerator/=factor;
        fraction.fractionDenominator/=factor;
        if(fraction.fractionNumerator < 0 && fraction.fractionDenominator < 0)
        {
            fraction.fractionDenominator = -fraction.fractionDenominator;
            fraction.fractionNumerator = -fraction.fractionNumerator;
        }
    }
}