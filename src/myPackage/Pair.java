package myPackage;

public class Pair<X,Y> implements Comparable<Pair>{
    public X first;
    public Y second;
    
    public Pair(X x, Y y){
        first = x;
        second = y;
    }
    
    public int compareTo(Pair o) {
        int cmp = ((Comparable)first).compareTo(o.first);
        if(cmp!=0) return cmp;
        return ((Comparable)second).compareTo(o.second);
    }
}
