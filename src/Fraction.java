import java.math.BigInteger;

public class Fraction
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
        if(other.fractionDenominator != current.fractionDenominator)
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
    }
}