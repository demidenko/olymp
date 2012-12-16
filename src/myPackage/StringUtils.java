package myPackage;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class StringUtils{

    public static int[] prefixFunction(char s[]){
        int n = s.length, i, j;
        int p[] = new int[n];
        p[0] = 0;
        for(i=1; i<n; ++i){
            for(j=i-1; j>=0 && s[i]!=s[p[j]]; j=p[j]-1);
            p[i] = j<0?0:p[j]+1;
        }
        return p;
    }

    public static int[] zFunction(char s[]){
        int n = s.length, i, j;
        int z[] = new int[n];
        z[0] = j = 0;
        for(i=1; i<n; ++i){
            int k = Math.max(0, Math.min(z[i-j], j+z[j]-i));
            while(i+k<n && s[k]==s[i+k]) ++k;
            if(i+k > j+z[j]) j = i;
            z[i] = k;
        }
        return z;
    }

    public static int[][] palindromesLength(char s[]){
        int i, j, k, n = s.length;
        int d0[] = new int[n], d1[] = new int[n];
        j = 0;
        d1[0] = 1;
        for(i=1; i<n; ++i){
            if(i<=j+d1[j]-1) k = Math.min(d1[j-(i-j)], j+d1[j]-i); else k = 1;
            while(i+k<n && i-k>=0 && s[i+k]==s[i-k]) ++k;
            if(i+k>j+d1[j]) j=i;
            d1[i] = k;
        }
        j = 0;
        d0[0] = 0;
        for(i=1; i<n; ++i){
            if(i<=j+d0[j]-1) k = Math.min(d0[j-(i-j)], j+d0[j]-i); else k = 0;
            while(i+k<n && i-k-1>=0 && s[i+k]==s[i-k-1]) ++k;
            if(i+k>j+d0[j]) j=i;
            d0[i] = k;
        }
        return new int[][]{d0, d1};
    }



    public static int[] suffixArray(char s[]){
        int n = s.length, i, k, m, size = Math.max(256, n);
        int p[] = new int[n], pt[] = new int[n];
        int u[] = new int[n], ut[] = new int[n];
        int c[] = new int[size];
        for(i=0; i<n; ++i) ++c[s[i]];
        for(i=1; i<size; ++i) c[i]+=c[i-1];
        for(i=n-1; i>=0; --i) p[--c[s[i]]]=i;
        u[p[0]] = m = 0;
        for(i=1; i<n; ++i){
            if(s[p[i]]!=s[p[i-1]]) ++m;
            u[p[i]] = m;
        }
        ++m;
        for(k=1; k<n; k<<=1){
            for(i=0; i<n; ++i) pt[i] = (p[i]-k+n)%n;
            Arrays.fill(c, 0, m, 0);
            for(i=0; i<n; ++i) ++c[u[pt[i]]];
            for(i=1; i<m; ++i) c[i]+=c[i-1];
            for(i=n-1; i>=0; --i) p[--c[u[pt[i]]]] = pt[i];
            ut[p[0]] = m = 0;
            for(i=1; i<n; ++i){
                if( u[p[i]]!=u[p[i-1]] || u[(p[i]+k<n?p[i]+k : p[i]+k-n)]!=u[(p[i-1]+k<n?p[i-1]+k : p[i-1]+k-n)] ) ++m;
                ut[p[i]] = m;
            }
            ++m;
            if(m==n) break;
            System.arraycopy(ut, 0, u, 0, n);
        }
        return p;
    }


    public static int[] getLCP(char s[], int[] sufAr){
        int n = s.length, i, j, l = 0;
        int invSufAr[] = new int[n];
        for(i=0; i<n; ++i) invSufAr[sufAr[i]] = i;
        int lcp[] = new int[n];
        for(i=0; i<n; ++i){
            j = invSufAr[i];
            if(j>0) while(sufAr[j]+l<n && sufAr[j-1]+l<n && s[sufAr[j]+l]==s[sufAr[j-1]+l]) ++l;
            lcp[j] = l;
            --l; if(l<0) l = 0;
        }
        return lcp;
    }

    static long distinctSubstrings(char s[]){
        int n = s.length;
        char _s[] = Arrays.copyOf(s, n+1);
        _s[n] = 0;
        int p[] = suffixArray(_s);
        int l[] = getLCP(_s, p);
        long res = 0;
        for(int i=1; i<=n; ++i) res += n-p[i]-l[i];
        return res;
    }

    public void mainLorentz(char s[]){
        mainLorentz(s, 0, s.length-1);
    }

    private void mainLorentz(char s[], int left, int right){
        int n = right-left+1;
        if(n<2) return;
        int m = n>>1;
        mainLorentz(s, left, m);
        mainLorentz(s, left + m, n - m);
        char tt[] = new char[n+1];
        System.arraycopy(s, m+left, tt, 0, n-m);
        tt[n-m] = '$';
        System.arraycopy(s, left, tt, n-m+1, m);
        int z2[] = zFunction(tt);
        for(int i=0,j=n; i<j; ++i,--j){ char t=tt[i]; tt[i]=tt[j]; tt[j]=t; }
        int z3[] = zFunction(tt);
        int k1,k2,l,a,b,i;
        for(i=0;i<n;++i){
            if(i<m){
                k1 = z3[m-i]; k2 = z2[n-m+i+1];
                a = m-k1+left; b = i+k2+left; l = m-i;
            }else{
                k1 = z3[n-i+m]; k2 = z2[i-m+1];
                a = i-k1+1+left; b = m+k2+left; l = i-m+1;
            }
            if(a<=b){
                // upd(a, b, l)
                // for all i=a..b : s[i-l..i-1]==s[i..s+l-1]
            }
        }
    }

    public static class SuffixAutomaton{
        private int N, M;
        public char minChar, maxChar;
        public int length[];
        public int link[];
        public int to[][];
        public int last;
        public int nil;

        public int size[];

        public SuffixAutomaton(char s[], char minChar, char maxChar){
            this.minChar = minChar;
            this.maxChar = maxChar;
            nil = 0;
            last = nil;
            N = 1;
            M = s.length*2+1;
            length = new int[M];
            link = new int[M];
            link[nil] = -1;
            to = new int[maxChar-minChar+1][M];

            size = new int[M];

            for(char c : s) add(c);
        }

        private void add(char c){
            c-=minChar;
            int current = N++;
            length[current] = length[last]+1;
            link[current] = nil;
            for(; last!=-1 && to[c][last]==nil; last = link[last]) to[c][last] = current;
            if(last!=-1){
                int p = to[c][last];
                if(length[p]==length[last]+1) link[current] = p; else{
                    int q = N++;
                    length[q] = length[last]+1;
                    link[q] = link[p];
                    for(char x=minChar; x<=maxChar; ++x) to[x-minChar][q] = to[x-minChar][p];
                    for(; last!=-1 && to[c][last]==p; last = link[last]) to[c][last] = q;
                    link[current] = link[p] = q;
                }
            }
            last = current;
        }
    }


    public static class SuffixAutomata{
        public char smallestChar = 'a';
        public char greatestChar = 'z';

        public static class State{
            int length;
            State link;
            State to[];
            int size;

            public State(){}
        }

        State nil, last;

        public SuffixAutomata(char fromChar, char toChar){
            smallestChar = fromChar;
            greatestChar = toChar;
            nil = new State();
            nil.length = 0;
            nil.link = null;
            nil.to = new State[greatestChar-smallestChar+1];
            Arrays.fill(nil.to, nil);
            last = nil;
        }

        public void addChar(char c){
            c -= smallestChar;
            State current = new State();
            current.length = last.length+1;
            current.link = nil;
            current.to = new State[greatestChar-smallestChar+1];
            Arrays.fill(current.to, nil);
            for(; last!=null && last.to[c]==nil; last = last.link) last.to[c] = current;
            if(last!=null){
                State p = last.to[c];
                if(p.length==last.length+1) current.link = p; else{
                    State q = new State();
                    q.length = last.length+1;
                    q.link = p.link;
                    q.to = p.to.clone();
                    for(; last!=null && last.to[c]==p; last = last.link) last.to[c] = q;
                    p.link = current.link = q;
                }
            }
            last = current;
        }


        public State getState(char s[]){
            State t = nil;
            for(char c : s){
                c-=smallestChar;
                t = t.to[c];
                if(t==nil) return null;
            }
            return t;
        }

        static long distinctSubstrings(char s[]){
            char max = Character.MAX_VALUE;
            char min = Character.MIN_VALUE;
            for(char c : s){
                min = (char) Math.min(min, c);
                max = (char) Math.max(max, c);
            }
            SuffixAutomata a = new SuffixAutomata(min, max);
            long res = 0;
            for(char c : s){
                a.addChar(c);
                res += a.last.length - a.last.link.length;
            }
            return res;
        }
    }



    public static class HashArray{
        int X1, M1, p1[], h1[];
        int X2, M2, p2[], h2[];

        public HashArray(char s[], int X1, int M1, int X2, int M2){
            this.X1 = X1; this.M1 = M1;
            this.X2 = X2; this.M2 = M2;
            int i, n = s.length;
            p1 = new int[n+1];
            p2 = new int[n+1];
            for(p1[0]=p2[0]=1,i=1; i<=n; ++i){
                p1[i] = (int) ((long)p1[i-1]*X1%M1);
                p2[i] = (int) ((long)p2[i-1]*X2%M2);
            }
            h1 = new int[n+1];
            h2 = new int[n+1];
            for(h1[n-1]=h2[n-1]=s[n-1],i=n-2; i>=0; --i){
                h1[i] = (int) ((1L*h1[i+1]*X1 + s[i])%M1);
                h2[i] = (int) ((1L*h2[i+1]*X2 + s[i])%M2);
            }
        }
        
        public HashArray(char s[]){
            this(s,
                    BigInteger.probablePrime(11,new Random()).intValue(),
                    BigInteger.probablePrime(31,new Random()).intValue(),
                    BigInteger.probablePrime(10,new Random()).intValue(),
                    BigInteger.probablePrime(32,new Random()).intValue()
            );
        }

        public long hash(int l, int r){
            return
                    ((h1[l] - 1L*h1[r+1]*p1[r-l+1]%M1 + M1)%M1) << 32L
                    | ((h2[l] - 1L*h2[r+1]*p2[r-l+1]%M2 + M2)%M2);
        }
    }

}
