package myPackage;

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
}
