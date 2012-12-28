package myPackage;

public class Pair<X extends Comparable, Y extends Comparable> implements Comparable<Pair>{
    public X first;
    public Y second;
    
    public Pair(X x, Y y){
        first = x;
        second = y;
    }
    
    public int compareTo(Pair o) {
        int cmp = first.compareTo(o.first);
        if(cmp!=0) return cmp;
        return second.compareTo(o.second);
    }
    
    public Pair<Y, X> swap(){
        return new Pair<Y, X>(second, first);
    }
}
