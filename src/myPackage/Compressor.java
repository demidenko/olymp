package myPackage;

import java.util.Arrays;

public class Compressor {
    private int a[];
    
    public Compressor(int arr[]){
        int n = arr.length;
        int m = 1;
        a = Arrays.copyOf(arr, n+2);
        a[n++] = Integer.MAX_VALUE;
        a[n++] = Integer.MIN_VALUE;
        Arrays.sort(a);
        for(int i=1; i<n; ++i) if(a[m-1]!=a[i]) a[m++] = a[i];
        a = Arrays.copyOf(a, m);
    }
    
    public int get(int val){
        return Arrays.binarySearch(a, val);
    }

    public int getEqualHigher(int val){
        int i = Arrays.binarySearch(a, val);
        return i<0 ? -i-1 : i;
    }

    public int getEqualLower(int val){
        int i = Arrays.binarySearch(a, val);
        return i<0 ? -i-1-1 : i;
    }
    
    public int size(){
        return a.length;
    }
}
