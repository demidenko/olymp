package myPackage;

/**
 * 02.01.13 13:24
 */
public class Point3D implements Comparable<Point3D>{
    public double x, y, z;
    
    public Point3D(double X, double Y, double Z){
        x = X;
        y = Y;
        z = Z;
    }
    
    public double distance(Point3D p){
        return Math.sqrt((x-p.x)*(x-p.x)+(y-p.y)*(y-p.y)+(z-p.z)*(z-p.z));
    }

    public int compareTo(Point3D p){
        if(p.x!=x) return x<p.x?-1:1;
        if(p.y!=y) return y<p.y?-1:1;
        if(p.z!=z) return z<p.z?-1:1;
        return 0;
    }

    public String toString(){
        return x+" "+y+" "+z;
    }
}
