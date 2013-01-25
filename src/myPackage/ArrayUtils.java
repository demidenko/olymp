package myPackage;

import java.util.*;

public class ArrayUtils{
    private final static Random random = new Random(System.nanoTime());
    static final int SMALL = 50;

    public static void qsortSpecialForRoman(int a[], int l, int r){
        if(l>=r) return;
        int i=l, j=r, m=i+j>>1;
        do{
            while(a[i]<a[m]) ++i;
            while(a[j]>a[m]) --j;
            if(i<=j){
                int t=a[i]; a[i]=a[j]; a[j]=t;
                ++i; --j;
            }
        }while(i<=j);
        if(i<r) qsortSpecialForRoman(a, i, r);
        if(l<j) qsortSpecialForRoman(a, l, j);
    }
    
    public static void sort(int a[], int l, int r){
        if(l>=r) return;
        int i,j,k;
        if(r-l<SMALL){
            for(i=l;i<=r;++i){
                k=i;
                for(j=i+1;j<=r;++j) if(a[j]<a[k]) k=j;
                if(k>i){ int t=a[k]; a[k]=a[i]; a[i]=t; }
            }
            return;
        }
        int m = (l+r)>>1;
        sort(a, l, m);
        sort(a, m + 1, r);
        int b[] = new int[r-l+1];
        for(k=0, i=l, j=m+1; k<b.length; ++k)
            if(i<=m && (j>r || a[j]>a[i])) b[k] = a[i++]; else b[k] = a[j++];
        System.arraycopy(b, 0, a, l, b.length);
    }
    
    public static void sort(int a[]){
        sort(a, 0, a.length-1);
    }

    public static void sort(long a[], int l, int r){
        if(l>=r) return;
        int i,j,k;
        if(r-l<SMALL){
            for(i=l;i<=r;++i){
                k=i;
                for(j=i+1;j<=r;++j) if(a[j]<a[k]) k=j;
                if(k>i){ long t=a[k]; a[k]=a[i]; a[i]=t; }
            }
            return;
        }
        int m = (l+r)>>1;
        sort(a, l, m);
        sort(a, m + 1, r);
        long b[] = new long[r-l+1];
        for(k=0, i=l, j=m+1; k<b.length; ++k)
            if(i<=m && (j>r || a[j]>a[i])) b[k] = a[i++]; else b[k] = a[j++];
        System.arraycopy(b, 0, a, l, b.length);
    }

    public static void sort(double a[], int l, int r){
        if(l>=r) return;
        int i,j,k;
        if(r-l<SMALL){
            for(i=l;i<=r;++i){
                k=i;
                for(j=i+1;j<=r;++j) if(a[j]<a[k]) k=j;
                if(k>i){ double t=a[k]; a[k]=a[i]; a[i]=t; }
            }
            return;
        }
        int m = (l+r)>>1;
        sort(a, l, m);
        sort(a, m + 1, r);
        double b[] = new double[r-l+1];
        for(k=0, i=l, j=m+1; k<b.length; ++k)
            if(i<=m && (j>r || a[j]>a[i])) b[k] = a[i++]; else b[k] = a[j++];
        System.arraycopy(b, 0, a, l, b.length);
    }

    public static void merge(int to[], int fromL[], int fromR[]){
        int i, j, k, n=fromL.length, m=fromR.length;
        for(i=j=k=0; i<n || j<m; ++k)
            if(i<n && (j==m || fromL[j]>fromR[i])) to[k] = fromL[i++];
            else to[k] = fromR[j++];
    }

    public static boolean nextPermutation(int p[]){
        int i, j;
        for(i=p.length-1; i>0 && p[i]<=p[i-1]; --i);
        if(i==0) return false;
        for(--i, j=p.length-1; p[j]<=p[i]; --j);
        int t = p[i]; p[i] = p[j]; p[j] = t;
        for(++i, j=p.length-1; i<j; ++i, --j){
            t = p[i]; p[i] = p[j]; p[j] = t;
        }
        return true;
    }

    public static void randomShuffle(int a[]){
        int n = a.length;
        for(int i=0;i<n;++i){
            int j = random.nextInt(n-i);
            int t = a[i]; a[i] = a[j]; a[j] = t;
        }
    }

    public static int longestIncreasingSubsequence(int a[]){
        int i, n = a.length;
        int t[] = new int[n];
        int d[] = new int[n];
        for(i=0;i<n;++i) t[i] = Integer.MAX_VALUE;
        int ans = 0;
        for(i=0;i<n;++i){
            int j = Arrays.binarySearch(t, a[i]);
            if(j<0) j=-j-1;
            t[j] = a[i];
            d[j] = (j>0?d[j-1]:0)+1;
            ans = Math.max(ans, d[j]);
        }
        return ans;
    }

    public static int[] intArray(Iterable<Integer> from){
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for(int x : from) temp.add(x);
        int res[] = new int[temp.size()], i = 0;
        for(int x : temp) res[i++] = x;
        return res;
    }

    public static int[] intArray(Integer from[]){
        int res[] = new int[from.length], i = 0;
        for(int x : from) res[i++] = x;
        return res;
    }

}
