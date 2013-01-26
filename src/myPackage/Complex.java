package myPackage;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 25.01.13 22:36
 */
public class Complex {
    static final MathContext mc = new MathContext(100, RoundingMode.HALF_UP);
    
    public BigDecimal a, b;
    
    public Complex(double a, double b){
        this.a = BigDecimal.valueOf(a);
        this.b = BigDecimal.valueOf(b);
    }

    public Complex(BigDecimal a, BigDecimal b){
        this.a = a;
        this.b = b;
    }
    
    public Complex add(Complex c){
        return new Complex(a.add(c.a,mc), b.add(c.b,mc));
    }

    public Complex subtract(Complex c){
        return new Complex(a.subtract(c.a,mc), b.subtract(c.b,mc));
    }
    
    public Complex multiply(Complex c){
        return new Complex(a.multiply(c.a).subtract(b.multiply(c.b),mc), a.multiply(c.b).add(b.multiply(c.a),mc));
    }
}
