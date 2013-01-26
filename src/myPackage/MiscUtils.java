package myPackage;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Stack;

public class MiscUtils{
    


    public static String toFibonacci(long x){
        if(x==0) return "0";
        long f1=1, f2=0;
        while(f1<=x){
            f1 = f1+f2;
            f2 = f1-f2;
        }
        StringBuilder res = new StringBuilder();
        while(f1>f2){
            f2 = f1-f2;
            f1 = f1-f2;
            res.append(x>=f1 ? '1' : '0');
            if(f1<=x) x-=f1;
        }
        return res.toString();
    }

    public static String arabToRome(int x){
        String q = "IVXLCDM", res = "", r;
        String t[] = {"1","11","111","12","2","21","211","2111"};
        int p = 0, i;
        while(x>0){
            r = t[x%10];
            for(i=0; i<r.length(); ++i) res += q.charAt(r.charAt(i)-'0'+p*2-1);
            x/=10;
            ++p;
        }
        return res;
    }

    public static int romeToArab(String s){
        String q = "IVXLCDM";
        int res = 0, i, k, p[] = {1,5,10,50,100,500,1000};
        char c = 0;
        for(i=s.length()-1; i>=0; --i){
            if( (k=q.indexOf(s.charAt(i)))<0 || (c>0 && q.indexOf(c)<0) ) return -1;
            if( k>=q.indexOf(c) ) res+=p[k]; else res-=p[k];
            c = s.charAt(i);
        }
        return res;
    }
    
    public static int[][] magicSquare(int n){
        if(n==2) return null;
        int p[][] = new int[n][n];
        int i, j, k, l, c=n/2;
        if(n%4==0){
            int ii;
            for(l=0;l<n/4;++l){
                i=0; j=4*l; k=1+(l*4*n);
                for(ii=0;ii<c;++ii){
                    p[(i+n)%n][(j+n)%n] = k++;
                    ++i; --j;
                }
                i=c; j=4*l-c+1;
                for(ii=0;ii<c;++ii){
                    p[(i+n)%n][(j+n)%n] = k++;
                    ++i; ++j;
                }
                i=n-1; j=4*l+1;
                for(ii=0;ii<c;++ii){
                    p[(i+n)%n][(j+n)%n] = k++;
                    --i; ++j;
                }
                i=c-1; j=4*l+c;
                for(ii=0;ii<c;++ii){
                    p[(i+n)%n][(j+n)%n] = k++;
                    --i; --j;
                }
            }
            for(l=0;l<n/4;++l){
                i=n-1; j=4*l+2; k=1+(l*4*n)+2*n;
                for(ii=0;ii<c;++ii){
                    p[(i+n)%n][(j+n)%n] = k++;
                    --i; --j;
                }
                i=c-1; j=4*l-c+1+2;
                for(ii=0;ii<c;++ii){
                    p[(i+n)%n][(j+n)%n] = k++;
                    --i; ++j;
                }
                i=0; j=4*l+1+2;
                for(ii=0;ii<c;++ii){
                    p[(i+n)%n][(j+n)%n] = k++;
                    ++i; ++j;
                }
                i=c; j=4*l+c+2;
                for(ii=0;ii<c;++ii){
                    p[(i+n)%n][(j+n)%n] = k++;
                    ++i; --j;
                }
            }
        }else{
            i=-c; j=c;
            for(k=1;k<=n*n;++k){
                p[(i+n)%n][(j+n)%n] = k;
                ++i; ++j;
                if(k%n==0){
                    i=i-n+1;
                    j=j-n-1;
                }
            }
            if(n%2==0){
                int t;
                int[][] q = new int[n][n];
                for(i=0;i<c;++i)
                    for(j=0;j<c;++j){
                        q[j][i]=p[j][c-i-1];
                        q[j+c][i]=p[j][c-i-1]+c*c*2;
                        q[j][i+c]=p[j][c-i-1]+c*c*3;
                        q[j+c][i+c]=p[j][c-i-1]+c*c;
                    }
                for(i=1;i<=(c-3)/2;++i){
                    for(j=0;j<c;++j){
                        t=q[c-i][j]; q[c-i][j]=q[c-i][j+c]; q[c-i][j+c]=t;
                        t=q[c+i-1][j]; q[c+i-1][j]=q[c+i-1][j+c]; q[c+i-1][j+c]=t;
                    }
                }
                t=q[0][0]; q[0][0]=q[0][c]; q[0][c]=t;
                t=q[0][c-1]; q[0][c-1]=q[0][n-1]; q[0][n-1]=t;
                for(i=1;i<c-1;++i){
                    t=q[1][i]; q[1][i]=q[1][i+c]; q[1][i+c]=t;
                }
                p=q;
            }
        }
        return p;
    }
    
    
    
    public static long calculate(String s){
        int n = s.length(), i, j;
        int br[] = new int[n];
        Arrays.fill(br, -1);
        Stack<Integer> st = new Stack<Integer>();
        for(i=0;i<n;++i){
            if(s.charAt(i)=='(') st.add(i); else
            if(s.charAt(i)==')'){
                j = st.pop();
                br[i] = j;
                br[j] = i;
            }
        }
        for(i=j=0;i<n;++i){
            char ch = s.charAt(i);
            if('0'<=ch && ch<='9'); else{
                if(i-1>=j){
                    br[j] = i-1;
                    br[i-1] = j;
                }
                j = i+1;
            }
        }
        if(i-1>=j){
            br[j] = i-1;
            br[i-1] = j;
        }
        //for(i=0;i<n;++i) System.out.print(br[i]+" "); System.out.println();
        return calculate(s.toCharArray(), br, 0, n - 1);
    }
    
    private static long calculate(char s[], int br[], int l, int r){
        if(br[l] == r){
            if(s[l]=='(') return calculate(s, br, l + 1, r - 1);
            if('0'<=s[l] && s[l]<='9') return Long.parseLong(new String(s, l, r-l+1));
        }
        char tp = 's';
        int sign = 1;
        int i,j;
        Stack<Long> st = new Stack<Long>(), _st = new Stack<Long>();
        Stack<Character> op = new Stack<Character>(), _op = new Stack<Character>();
        for(i=l; i<=r;){
            if(tp=='s'){
                if(s[i]=='-'){
                    sign *= -1;
                    ++i;
                }else{
                    tp = 'x';
                }
            }else
            if(tp=='x'){
                long call = calculate(s, br, i, br[i]);
                st.push(call*sign);
                tp = 'o';
                i = br[i] + 1;
            }else
            if(tp=='o'){
                if(s[i]!='+' && s[i]!='-' && s[i]!='*') throw new RuntimeException("wtf#2 "+s[i]);
                op.push(s[i]);
                tp = 's';
                sign = 1;
                ++i;
            }
        }
        _st.clear(); _st.addAll(st);
        _op.clear(); _op.addAll(op);
        st.clear();
        op.clear();
        st.push(_st.get(0));
        for(i=0;i<_op.size();++i){
            char o = _op.get(i);
            if(o=='*'){
                long x = st.pop();
                long y = _st.get(i+1);
                long res = x*y;
                st.add(res);
            }else{
                st.push(_st.get(i+1));
                op.push(o);
            }
        }
        _st.clear(); _st.addAll(st);
        _op.clear(); _op.addAll(op);
        st.clear();
        op.clear();
        st.push(_st.get(0));
        for(i=0;i<_op.size();++i){
            char o = _op.get(i);
            if(o=='+'){
                long x = st.pop();
                long y = _st.get(i+1);
                long res = x+y;
                st.add(res);
            }else
            if(o=='-'){
                long x = st.pop();
                long y = _st.get(i+1);
                long res = x-y;
                st.add(res);
            }else{
                st.push(_st.get(i+1));
                op.push(o);
            }
        }
        return st.pop();
    }
    
    
    static BigDecimal _2 = BigDecimal.valueOf(2);
    static MathContext mc = new MathContext(50, RoundingMode.HALF_UP);
    static BigDecimal eps = new BigDecimal("1e-15");
    //
    public static BigDecimal[] realRoots(BigDecimal p[]){
        int n = p.length-1;
        if(n==1){
            return new BigDecimal[]{
                    p[1].negate().divide(p[0], mc)
            };
        }
        /*if(n==2){
            BigDecimal D = p[1].multiply(p[1],mc).subtract(p[0].multiply(p[2]).multiply(BigDecimal.valueOf(4)),mc);
            if(D.signum()<0) return new BigDecimal[0];
            D = sqrt(D);
            return new BigDecimal[]{
                    p[1].negate().subtract(D).divide(p[0].multiply(_2),mc),
                    p[1].negate().add(D).divide(p[0].multiply(_2),mc)
            };
        }*/
        BigDecimal d[] = new BigDecimal[n];
        for(int i=0;i<n;++i) d[i] = p[i].multiply(BigDecimal.valueOf(n - i));
        BigDecimal ext[] = realRoots(d);
        BigDecimal res[] = new BigDecimal[n];
        int m = 0;
        for(int i=0;i<=ext.length;++i){
            BigDecimal l = (i==0 ? new BigDecimal("-1e10") : ext[i-1]);
            BigDecimal r = (i==ext.length ? new BigDecimal("1e10") : ext[i]);
            boolean increases = valueAt(p, l).compareTo(valueAt(p, r))<0;
            for(int it=0; it<200; ++it){
                BigDecimal x = l.add(r).divide(_2, mc);
                if(valueAt(p, x).signum()>0 == increases) r = x; else l = x;
            }
            if(valueAt(p, l).abs().compareTo(eps)<0) res[m++] = l;
        }
        return Arrays.copyOf(res, m);
    }
    
    public static BigDecimal valueAt(BigDecimal p[], BigDecimal x){
        BigDecimal xp = x;
        BigDecimal s = p[p.length-1];
        for(int i=p.length-2;i>=0;--i){
            s = s.add(p[i].multiply(xp), mc);
            if(i>0) xp = xp.multiply(x, mc);
        }
        return s;
    }
    
    public static BigDecimal sqrt(BigDecimal x){
        if(x.signum()<0) throw new ArithmeticException();
        BigDecimal r = _2;
        for(int it=0; it<50; ++it)
            r = r.add(x.divide(r,mc)).divide(_2,mc);
        return r;
    }
}
