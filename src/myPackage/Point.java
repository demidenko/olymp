package myPackage;

public class Point implements Comparable<Point>{
    public double x, y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Point(){
        x = y = 0;
    }

    public Point add(Point p){
        return new Point(x+p.x, y+p.y);
    }

    public Point subtract(Point p){
        return new Point(x-p.x, y-p.y);
    }

    public double distance(Point p){
        return Math.sqrt((x-p.x)*(x-p.x) + (y-p.y)*(y-p.y));
    }

    public double distanceSq(Point p){
        return (x-p.x)*(x-p.x) + (y-p.y)*(y-p.y);
    }

    public long distanceSqLong(Point p){
        return ((long)x-(long)p.x)*((long)x-(long)p.x) + ((long)y-(long)p.y)*((long)y-(long)p.y);
    }

    public double distanceToLine(Point p, Point q){
        double c = p.distanceSq(q);
        if(c==0) return distance(p);
        double s = (p.x-x)*(q.y-y) - (p.y-y)*(q.x-x);
        return Math.abs(s/Math.sqrt(c));
    }

    public double distanceToSegment(Point p, Point q){
        double a = this.distanceSq(p);
        double b = this.distanceSq(q);
        double c = p.distanceSq(q);
        if(c==0) return Math.sqrt(a);
        if(a >= c+b) return Math.sqrt(b); else
        if(b >= c+a) return Math.sqrt(a);
        double s = (p.x-x)*(q.y-y) - (p.y-y)*(q.x-x);
        return Math.abs(s/Math.sqrt(c));
    }
    
    public double projectionToSegment(Point p, Point q){
        return ((x-p.x)*(x-p.x)+(y-p.y)*(y-p.y)) / ((q.x-p.x)*(q.x-p.x)+(q.y-p.y)*(q.y-p.y)); 
    }

    public Point rotate(double angle){
        double s = Math.sin(angle), c = Math.cos(angle);
        return new Point(x*c-y*s, x*s+y*c);
    }

    public int compareTo(Point p){
        return x==p.x?(y==p.y?0:(y<p.y?-1:1)):(x<p.x?-1:1);
    }

    public String toString(){
        return x+" "+y;
    }

    public String toString(int scale){
        return String.format("%."+scale+"f %."+scale+"f",x,y);
    }
}
