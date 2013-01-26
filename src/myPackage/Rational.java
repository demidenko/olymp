package myPackage;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Rational implements Comparable<Rational>{
    static final Rational ZERO = new Rational(0);
    static final Rational ONE = new Rational(1);

    public BigInteger p, q;

    public Rational(){
        p = BigInteger.ZERO; q = BigInteger.ONE;
    }

    public Rational(long a){
        p = BigInteger.valueOf(a); q = BigInteger.ONE;
    }

    public Rational(long a, long b){
        if(b<0){ a=-a; b=-b; }
        p = BigInteger.valueOf(a); q = BigInteger.valueOf(b);
        if(b<0) b=-b;
        while(a!=0 && b!=0)
            if(a>b) a%=b; else b%=a;
        a+=b;
        p=p.divide(BigInteger.valueOf(a));
        q=q.divide(BigInteger.valueOf(a));
    }

    public Rational(BigInteger a){
        p = a; q = BigInteger.ONE;
    }

    public Rational(BigInteger a, BigInteger b){
        if(b.signum()<0){
            b = b.negate();
            a = a.negate();
        }
        BigInteger g = a.gcd(b);
        p = a.divide(g);
        q = b.divide(g);
    }

    public Rational multiply(Rational x){
        return new Rational(p.multiply(x.p), q.multiply(x.q));
    }

    public Rational divide(Rational x){
        return new Rational(p.multiply(x.q), q.multiply(x.p));
    }

    public Rational pow(long b){
        Rational r = ONE;
        Rational a = this.clone();
        while(b>0){
            if((b&1)==1) r = r.multiply(a);
            a = a.multiply(a);
            b>>=1;
        }
        return r;
    }

    public Rational add(Rational x){
        return new Rational(p.multiply(x.q).add(q.multiply(x.p)), q.multiply(x.q));
    }

    public Rational subtract(Rational x){
        return new Rational(p.multiply(x.q).subtract(q.multiply(x.p)), q.multiply(x.q));
    }

    public Rational inverse(){
        return new Rational(q, p);
    }

    public Rational negate(){
        return new Rational(p.negate(), q);
    }

    public Rational clone(){
        return new Rational(p,q);
    }

    public String toString(){
        return p+"/"+q;
    }

    public String toString(int scale){
        return new BigDecimal(p).divide(new BigDecimal(q), scale, 6).toPlainString();
    }

    public int compareTo(Rational x) {
        return p.multiply(x.q).compareTo(q.multiply(x.p));
    }
}
