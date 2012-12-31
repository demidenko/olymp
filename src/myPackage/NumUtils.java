package myPackage;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class NumUtils {
    static Random random = new Random();

    public static int gcd(int a, int b){
        if(a<0) a=-a; if(b<0) b=-b;
        while(a!=0 && b!=0) if(a>b) a%=b; else b%=a;
        return a+b;
    }

    public static long gcd(long a, long b){
        if(a<0) a=-a; if(b<0) b=-b;
        while(a!=0 && b!=0) if(a>b) a%=b; else b%=a;
        return a+b;
    }

    public static int[] gcdExtended(int a, int b){
        if(a==0) return new int[]{b, 0, 1};
        int call[] = gcdExtended(b%a, a);
        return new int[]{call[0], (call[0]-b*call[1])/a, call[1]};
    }

    public static long[] gcdExtended(long a, long b){
        if(a==0) return new long[]{b, 0, 1};
        long call[] = gcdExtended(b%a, a);
        return new long[]{call[0], (call[0]-b*call[1])/a, call[1]};
    }

    public static long lcm(int a, int b){
        return a/gcd(a,b)*(long)b;
    }

    public static long lcm(long a, long b){
        return a/gcd(a,b)*b;
    }

    public static long[] getDivisors(long n){
        int sqrt = (int)sqrt(n);
        int isFullSquare = (sqrt*sqrt==n ? 1 : 0);
        long[] d = new long[sqrt];
        int m=0, i;
        for(i=1; i<=sqrt; ++i) if(n%i==0) d[m++]=i;
        long res[] = new long[m+m-isFullSquare];
        for(i=0; i<m; ++i) res[i]=d[i];
        for(i=m-1-isFullSquare; i>=0; --i) res[m+m-i-1-isFullSquare]=n/d[i];
        return res;
    }

    public static int pow(int a, int b){
        int res = 1;
        while(b>0){
            if((b&1)==1) res*=a;
            a*=a;
            b>>=1;
        }
        return res;
    }

    public static long pow(long a, long b, long mod){
        long res = 1;
        a=(a%mod+mod)%mod;
        while(b>0){
            if((b&1)==1) res=(res*a)%mod;
            a=(a*a)%mod;
            b>>=1;
        }
        return res%mod;
    }

    public static boolean isPrime(int n){
        if(n<2 || (n>2 && n%2==0) || (n>3 && n%6%4!=1)) return false;
        for(int i=3; 1L*i*i<=n; i+=2) if(n%i==0) return false;
        return true;
    }

    public static boolean[] getPrimeArray(int n){
        int i, j;
        boolean[] p = new boolean[n+1];
        if(n<2) return p;
        p[2] = true;
        for(i=3; i<=n; i+=2) p[i] = true;
        for(i=3; i*i<=n; i+=2) if(p[i]){
            for(j=i*i; j<=n; j+=i*2) p[j] = false;
        }
        return p;
    }
    
    public static int[] getPrimes(int n){
        if(n<2) return new int[0];
        int i, j, m = 0;
        boolean[] p = new boolean[(n+1)/2+1];
        int res[] = new int[countOfPrimes(n)];
        res[m++] = 2;
        for(i=3; i*i<=n; i+=2) if(!p[i>>1]){
            res[m++] = i;
            for(j=i*i; j<=n; j+=i*2) p[j>>1]=true;
        }
        for(; i<=n; i+=2) if(!p[i>>1]) res[m++] = i;
        return Arrays.copyOf(res, m);
    }

    public static int getPrime(int n){	//p(5e7) = 982451653
        int i, j, k, N, m, mt;
        N = 33334;	///N is sqrt(maxprime)+eps, even
        boolean p[] = new boolean[N];
        int pr[] = new int[5000], d[] = new int[5000];
        Arrays.fill(p, true);
        m = 1;
        pr[0] = 2;
        for(i=3; i<N; i+=2) if(p[i]){
            for(j=i*i; j<N; j+=i*2) p[j] = false;
            d[m] = j-N;
            pr[m++] = i;
        }
        if(m>=n) return pr[n-1];
        mt = m;
        for(k=1;;++k){
            Arrays.fill(p, true);
            for(i=1; i<mt; ++i){
                int l = d[i], p2 = pr[i]<<1;
                for(; l<N; l+=p2) p[l] = false;
                d[i] = l-N;
            }
            for(i=1; i<N; i+=2) if(p[i]){
                ++m;
                if(m==n) return N*k + i;
            }
        }
    }

    public static int phi(int n){
        int d, res = n;
        for(d=2; d*d<=n; ++d)
            if(n%d==0){
                while(n%d==0) n/=d;
                res-=res/d;
            }
        if(n>1) res-=res/n;
        return res;
    }

    public static int mu(int n){
        int i, res=1;
        i=2;
        while(n>1){
            if(n%i==0){
                n/=i;
                res*=-1;
                if(n%i==0) return 0;
            }
            ++i;
            if(i*i>n) i=n;
        }
        return res;
    }

    public static int[] getDivisorsArray(int n){
        int d[] = new int[n+1];
        int p[] = new int[countOfPrimes(n)], m = 0;
        for(int i=2;i<=n;++i){
            if(d[i]==0){
                d[i] = i;
                p[m++] = i;
            }
            for(int j=0;j<m && p[j]<=d[i] && p[j]*i<=n;++j) d[p[j]*i] = p[j];
        }
        return d;
    }

    public static long modInverse(long a, long mod){
        a = (a%mod+mod)%mod;
        long[] call = gcdExtended(a,mod);
        return (call[1]%mod+mod)%mod;
    }

    public static long[] factorize(long n){
        long d[] = new long[66];
        int i,m=0; long x;
        for(x=2; n>1;){
            while(n%x==0){
                d[m++]=x;
                n/=x;
            }
            x+=1+(x>2?1:0);
            if(x*x>n) x=n;
        }
        return Arrays.copyOf(d, m);
    }

    public static int[][] factorizeInPowers(int n){
        int d[][] = new int[15][2];
        int m=0, i;
        for(i=2; n>1;){
            d[m][1]=0;
            while(n%i==0){
                d[m][1]++;
                n/=i;
            }
            if(d[m][1]>0) d[m++][0]=i;
            i+=1+(i>2?1:0);
            if(i*i>n) i=n;
        }
        int[][] res = new int[m][2];
        for(i=0;i<m;++i) res[i]=d[i];
        return res;
    }

    static public int generator(int mod){ //mod must be prime!!!
        int phi = mod-1;
        int d[][] = factorizeInPowers(phi);
        int res=0,i;
        for(res=1;res<mod;++res){
            boolean ok=true;
            for(i=0;ok && i<d.length;++i) if( pow(res,phi/d[i][0],mod)==1 ) ok=false;
            if(ok) break;
        }
        return res;
    }

    public static long[] sqrtMod(long a, long mod){ //return all x in[0..p) such x*x=a(mod p) //p must be prime!!!, gcd(a,p)==1!!!
        a = (a%mod+mod)%mod;
        if( mod==2 ) return new long[]{a};
        if( pow(a,(mod-1)>>1,mod)==mod-1 ) return new long[0];
        long b;
        do{
            b=random.nextInt((int)mod);
        }while(pow(b,(mod-1)>>1,mod)!=mod-1);
        long s=0, t=mod-1;
        while((t&1)==0){
            ++s;
            t>>=1;
        }
        long aInv = pow(a,mod-2,mod);
        long c = pow(b,t,mod);
        long r = pow(a,(t+1)>>1,mod);
        for(int i=1;i<s;++i){
            long d = pow(r*r%mod*aInv,1l<<(s-i-1),mod);
            if(d==mod-1) r=(r*c)%mod;
            c=(c*c)%mod;
        }
        return new long[]{r,mod-r};
    }

    public static long sqrt(long n){
        if(n==0) return 0;
        long nx, x = 2;
        boolean d = false;
        for(;;){
            nx=(x + n/x)>>1;
            if(x==nx || nx>x && d) return x;
            d=(nx<x);
            x=nx;
        }
    }
    
    public static int countOfPrimes(int n){
        return 2*n/(32-Integer.numberOfLeadingZeros(n))+1;
    }
    

    public static class CRT{
        private int n;
        private long p[], inv[][];
        private BigInteger max, len;

        public CRT(BigInteger max){
            this.max = max;
            ArrayList<Integer> list = new ArrayList<Integer>();
            BigInteger pr = BigInteger.ONE;
            Random rnd = new Random();
            len = max.shiftLeft(1).add(BigInteger.ONE);
            while(len.compareTo(pr)>0){
                BigInteger prime = BigInteger.probablePrime(31, rnd);
                pr = pr.multiply(prime);
                list.add(prime.intValue());
            }
            len = pr;
            p = new long[list.size()];
            n = 0;
            for(int x : list) p[n++] = x;
            inv = new long[n][n];
            for(int i=0;i<n;++i)
                for(int j=0;j<n;++j) if(i!=j) inv[i][j] = modInverse(p[i], p[j]);
        }
        
        public long[] valueOf(String str){
            long res[] = new long[n];
            BigInteger val = new BigInteger(str);
            for(int i=0;i<n;++i) res[i] = val.mod(BigInteger.valueOf(p[i])).longValue();
            return res;
        }

        public long[] add(long a[], long b[]){
            long res[] = new long[n];
            for(int i=0;i<n;++i) res[i] = (a[i]+b[i])%p[i];
            return res;
        }

        public long[] subtract(long a[], long b[]){
            long res[] = new long[n];
            for(int i=0;i<n;++i) res[i] = (a[i]-b[i]+p[i])%p[i];
            return res;
        }

        public long[] multiply(long a[], long b[]){
            long res[] = new long[n];
            for(int i=0;i<n;++i) res[i] = a[i]*b[i]%p[i];
            return res;
        }
        
        public BigInteger toBigInteger(long c[]){
            BigInteger res = BigInteger.ZERO;
            BigInteger pr = BigInteger.ONE;
            for(int i=0;i<n;++i){
                long x = (c[i] - res.remainder(BigInteger.valueOf(p[i])).longValue() + p[i]) % p[i];
                for(int j=0;j<i;++j) x = x*inv[j][i]%p[i];
                res = res.add(pr.multiply(BigInteger.valueOf(x)));
                pr = pr.multiply(BigInteger.valueOf(p[i]));
            }
            if(res.compareTo(max)>0) res = res.subtract(len);
            return res;
        }

        public long toInteger(long c[], long mod){
            long res = 0;
            long pr = 1;
            for(int i=0;i<n;++i){
                long x = (c[i] - res%p[i] + p[i]) % p[i];
                for(int j=0;j<i;++j) x = x*inv[j][i]%p[i];
                res = (res + pr*x)%mod;
                pr = pr*p[i]%mod;
            }
            return res;
        }
    }
    
    
    public static class Karatsuba{
        private static int MAXN;
        private static int SMALL = 1<<3;
        private int d;
        private int t[];
        
        public Karatsuba(int maxBits){
            for(MAXN = 1; MAXN<maxBits; MAXN<<=1);
            t = new int[8*MAXN];
        }
        
        private void rec(int as, int bs, int rets, int n){
            if(n<=SMALL){
                for(int i=0;i<2*n;++i) t[rets + i] = 0;
                for(int i=0;i<n;++i)
                    for(int j=0;j<n;++j) t[rets + i+j] += t[as + i] * t[bs + j];
                return;
            }
            int ar = as;
            int al = as + n/2;
            int br = bs;
            int bl = bs + n/2;
            int asum = rets + n*5;
            int bsum = rets + n*5 + n/2;
            int x1 = rets;
            int x2 = rets + n;
            int x3 = rets + n*2;
            for(int i=0;i<n/2;++i){
                t[asum + i] = t[al + i] + t[ar + i];
                t[bsum + i] = t[bl + i] + t[br + i];
            }
            rec(ar, br, x1, n / 2);
            rec(al, bl, x2, n / 2);
            rec(asum, bsum, x3, n / 2);
            for(int i=0;i<n;++i) t[x3 + i] -= t[x1 + i] + t[x2 + i];
            for(int i=0;i<n;++i) t[rets + i+n/2] += t[x3 + i];
        }
        
        public int[] multiply(int A[], int B[]){
            int n = Math.max(A.length, B.length);
            for(d=1; d<n; d<<=1);
            int as = d*6, bs = d*7, rets = 0;
            for(int i=0;i<d;++i){
                t[as + i] = i<n ? A[i] : 0;
                t[bs + i] = i<n ? B[i] : 0;
            }
            rec(as, bs, rets, d);
            return Arrays.copyOf(t, d*2);
        }
        
        public BigInteger multiply(BigInteger A, BigInteger B){
            byte Ab[] = A.toByteArray();
            byte Bb[] = B.toByteArray();
            int signA = A.signum()<0 ? 255 : 0;
            int signB = B.signum()<0 ? 255 : 0;
            int n = Math.max(Ab.length, Bb.length);
            for(d=1; d<n; d<<=1);
            int as = d*6, bs = d*7, rets = 0;
            for(int i=0;i<d;++i){
                int x = Ab.length-i-1>=0 ? Ab[Ab.length-i-1] : signA;
                if(x<0) x+=256;
                if(A.signum()<0) x = (~(x-1)) & 255;
                t[as + i] = x;
                int y = Bb.length-i-1>=0 ? Bb[Bb.length-i-1] : signB;
                if(y<0) y+=256;
                if(B.signum()<0) y = (~(y-1)) & 255;
                t[bs + i] = y;
            }
            rec(as, bs, rets, d);
            byte res[] = new byte[d*2];
            int rem = 0;
            for(int i=0;i<d*2;++i){
                rem += t[i];
                res[2*d-i-1] = (byte) (rem & 255);
                rem>>=8;
            }
            return new BigInteger(A.signum()*B.signum(), res);
        }
    }

}
