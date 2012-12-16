package myPackage;

import java.util.Arrays;

public class Compresser{
    private int a[];
    
    public Compresser(int arr[]){
        int n = arr.length;
        int m = 1;
        a = arr.clone();
        Arrays.sort(a);
        for(int i=1; i<n; ++i) if(a[m-1]!=a[i]) a[m++] = a[i];
        a = Arrays.copyOf(a, m);
    }
    
    public int get(int val){
        return Arrays.binarySearch(a,val);
    }
}
