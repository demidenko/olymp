package myPackage;

import java.util.*;

public class GeomUtils{
    static final double pi = Math.PI;
    static final double fi = (Math.sqrt(5.0)+1);

    public static double vectorProduct(Point a, Point b, Point c){
        return (a.x-c.x)*(b.y-c.y) - (a.y-c.y)*(b.x-c.x);
    }

    public static double areaOfPolygonX2(Point p[]){
        double s = 0;
        Point b = p[p.length-1];
        for(Point a : p){
            s += (a.y+b.y)*(a.x-b.x);
            b = a;
        }
        return Math.abs(s);
    }

    public static boolean pointOnSegment(Point o, Point a, Point b){
        return (a.x-o.x)*(b.y-o.y)==(a.y-o.y)*(b.x-o.x)
                && Math.min(a.x,b.x)<=o.x && o.x<=Math.max(a.x,b.x)
                && Math.min(a.y,b.y)<=o.y && o.y<=Math.max(a.y,b.y);
    }

    public static int pointInPolygon(Point o, Point p[]){
        int f = 1;
        Point b = p[p.length-1];
        for(Point a : p){
            double ax = a.x - o.x;
            double ay = a.y - o.y;
            double bx = b.x - o.x;
            double by = b.y - o.y;
            int s = (int)Math.signum(ax * by - ay * bx);
            if (s == 0 && (ay == 0 || by == 0) && ax * bx <= 0) s = 0; else
            if (ay<0 ^ by<0){
                if (by >= 0) s=-s;
            }else s = 1;
            f*=s;
            b = a;
        }
        return -f;
    }

    public static int pointInPolygon_temp(Point o, Point p[]){
        double s = 0;
        Point b = p[p.length-1];
        for(Point a : p){
            if(pointOnSegment(o, a, b)) return 0;
            s += Math.atan2( (a.x-o.x)*(b.y-o.y)-(a.y-o.y)*(b.x-o.x), (a.x-o.x)*(b.x-o.x)+(a.y-o.y)*(b.y-o.y) );
            b = a;
        }
        return Math.abs(s) > (1e-8) ? 1 : -1;
    }

    public static int pointInConvexPolygon(Point o, Point p[]){
        if(pointOnSegment(o,p[0],p[1]) || pointOnSegment(o,p[0],p[p.length-1])) return 0;
        int l = 1, r = p.length-1, m;
        while(l<r){
            m = (l+r)>>1;
            double v1 = vectorProduct(p[m+1], o, p[0]);
            double v2 = vectorProduct(p[m], o, p[0]);
            if(v1<=0 && v2>=0){
                double v = vectorProduct(p[m], o, p[m+1]);
                return v<0?1:(v>0?-1:0);
            }
            if(v1>=0 && v2<=0) return -1;
            if(v1<0) r = m; else l = m+1;
        }
        return -1;
    }

    public static Point crossLineLine(Point a, Point b, Point c, Point d){
        double u1 = (d.x-c.x)*(a.y-c.y)-(d.y-c.y)*(a.x-c.x);
        double z =  (b.x-a.x)*(d.y-c.y)-(b.y-a.y)*(d.x-c.x);
        if(z!=0){
            return new Point(a.x+(b.x-a.x)*u1/z, a.y+(b.y-a.y)*u1/z);
        }else return null;
    }

    public static Point[] crossSegmentSegmentPoints(Point a, Point b, Point c, Point d){
        double u1 = (d.x-c.x)*(a.y-c.y)-(d.y-c.y)*(a.x-c.x);
        double u2 = (b.x-a.x)*(a.y-c.y)-(b.y-a.y)*(a.x-c.x);
        double z =  (b.x-a.x)*(d.y-c.y)-(b.y-a.y)*(d.x-c.x);
        if(z!=0){
            u1/=z; u2/=z;
            if(0<=u1 && u1<=1 && 0<=u2 && u2<=1)
                return new Point[]{new Point(a.x+(b.x-a.x)*u1, a.y+(b.y-a.y)*u1)};
        }else
        if(u1==0 && u2==0){
            TreeSet<Point> p = new TreeSet<Point>();
            if(pointOnSegment(a,c,d)) p.add(a);
            if(pointOnSegment(b,c,d)) p.add(b);
            if(pointOnSegment(c,a,b)) p.add(c);
            if(pointOnSegment(d,a,b)) p.add(d);
            return p.toArray(new Point[p.size()]);
        }
        return new Point[0];
    }

    public static boolean crossSegmentSegment(Point a, Point b, Point c, Point d){
        double u1 = (d.x-c.x)*(a.y-c.y)-(d.y-c.y)*(a.x-c.x);
        double u2 = (b.x-a.x)*(a.y-c.y)-(b.y-a.y)*(a.x-c.x);
        double z =  (b.x-a.x)*(d.y-c.y)-(b.y-a.y)*(d.x-c.x);
        if(z!=0){
            u1/=z; u2/=z;
            return (0<=u1 && u1<=1 && 0<=u2 && u2<=1);
        }else
        if(u1==0 && u2==0){
            if(pointOnSegment(a,c,d)) return true;
            if(pointOnSegment(b,c,d)) return true;
            if(pointOnSegment(c,a,b)) return true;
            if(pointOnSegment(d,a,b)) return true;
        }
        return false;
    }

    public static Point[] crossCircleCircle(Point o1, double r1, Point o2, double r2){
        if(o1.x==o2.x && o1.y==o2.y && r1==r2) return new Point[3];
        Point o = o2.subtract(o1);
        double d2 = o.x*o.x+o.y*o.y;
        if(d2>sqr(r1+r2)) return new Point[0];
        if(d2<sqr(r1-r2)) return new Point[0];
        double d = Math.sqrt(d2);
        if(d2==sqr(r1+r2) || d2==sqr(r1-r2)) return new Point[]{ new Point(o.x/d*r1+o1.x, o.y/d*r1+o1.y) };
        double l = (sqr(r1)-sqr(r2)+d2)/(d*2);
        double h = Math.sqrt(r1*r1-l*l);
        return new Point[]{
                new Point(o.x/d*l + o.y/d*h + o1.x, o.y/d*l - o.x/d*h + o1.y),
                new Point(o.x/d*l - o.y/d*h + o1.x, o.y/d*l + o.x/d*h + o1.y)
        };
    }

    public static Point[] crossLineCircle(Point a, Point b, Point o, double r){
        double s = (b.x-a.x)*(b.y-o.y)-(b.y-a.y)*(b.x-o.x);
        double d = Math.sqrt(sqr(a.x - b.x) + sqr(a.y - b.y));
        double h = Math.abs(s)/d;
        if(h>r) return new Point[0];
        double l = Math.sqrt(r*r-h*h);
        Point v = new Point(b.x-a.x, b.y-a.y);
        double sign = s<0 ? 1 : -1;
        Point c = new Point(o.x + sign*v.y/d*h, o.y - sign*v.x/d*h);
        if(h==r) return new Point[]{c};
        return new Point[]{
                new Point(c.x + v.x/d*l, c.y + v.y/d*l),
                new Point(c.x - v.x/d*l, c.y - v.y/d*l)
        };
    }

    public static Point[] crossLineCircle_2(Point a, Point b, Point o, double r){
        double A = sqr(b.x-a.x)+sqr(b.y-a.y);
        double B = (b.x-a.x)*(a.x-o.x)*2 + (b.y-a.y)*(a.y-o.y)*2;
        double C = sqr(a.x-o.x) + sqr(a.y-o.y) - r*r;
        double D = B*B-4*A*C;
        if(D<0) return new Point[0];
        double k1 = (-B+Math.sqrt(D))/A/2, k2 = (-B-Math.sqrt(D))/A/2;
        if(D<1e-9) return new Point[]{ new Point((b.x-a.x)*k1+a.x, (b.y-a.y)*k1+a.y) };
        else return new Point[]{
                new Point((b.x-a.x)*k1+a.x, (b.y-a.y)*k1+a.y),
                new Point((b.x-a.x)*k2+a.x, (b.y-a.y)*k2+a.y)
        };
    }
    
    public static Point[] convexHull(Point p[]){
        int i,j,n=p.length,m;
        final Point c[] = p.clone();
        for(i=j=0;i<n;++i) if(c[i].y<c[j].y || c[i].y==c[j].y && c[i].x<c[j].x) j=i;
        Point t = c[0]; c[0] = c[j]; c[j] = t;
        Arrays.sort(c, 1, n, new Comparator<Point>(){
            public int compare(Point o1, Point o2) {
                double v = vectorProduct(o1, o2, c[0]);
                if(v!=0) return v>0?-1:1;
                v = o1.distanceSq(c[0]) - o2.distanceSq(c[0]);
                return v==0 ? 0 : (v<0?-1:1);
            }
        });
        m = n;
        for(i=n=1;i<m;++i) if(c[i].compareTo(c[n-1])!=0) c[n++]=c[i];
        m = Math.min(2,n);
        for(i=2;i<n;++i){
            while(m>1 && vectorProduct(c[i],c[m-2],c[m-1])<=0) --m;
            c[m++] = c[i];
        }
        return Arrays.copyOf(c,m);
    }

    public static Point[] ABCToPoints(double A, double B, double C){
        Point res[] = new Point[2];
        if(A==0){ res[0] = new Point(0, -C/B); res[1] = new Point(1, -C/B); } else
        if(B==0){ res[0] = new Point(-C/A, 0); res[1] = new Point(-C/A, 1); } else
        if(C==0){ res[0] = new Point(0 ,0); res[1] = new Point(-B, A); } else{
            res[0] = new Point(0, -C/B);
            res[1] = new Point(-C/A, 0);
        }
        return res;
    }

    public static double[] pointsToABC(Point p, Point q){
        double A, B, C;
        if(p.y==q.y){
            A = 0;
            B = 1;
            C = -p.y;
        }else
        if(p.x==q.x){
            A = 1;
            B = 0;
            C = -p.x;
        }else{
            A = p.y-q.y;
            B = p.x-q.x;
            C = B*p.y - A*p.x;
            if(A<0){
                A=-A; B=-B; C=-C;
            }
        }
        return new double[]{A,B,C};
    }


    public static Point[] get2DTree(Point p[]){
        Point t[] = new Point[p.length];
        for(int i=0; i<p.length; ++i) t[i] = new Point(p[i].x, p[i].y);
        built2DTree(t, 0, t.length-1, comparatorPointXY);
        return t;
    }

    private static void built2DTree(Point p[], int left, int right, Comparator<Point> comparator){
        if(left>=right) return;
        int middle = (left + right)>>1;
        Arrays.sort(p, left, right+1, comparator);
        Comparator<Point> anotherComparator = comparator==comparatorPointXY ? comparatorPointYX : comparatorPointXY;
        built2DTree(p, left, middle-1, anotherComparator);
        built2DTree(p, middle+1, right, anotherComparator);
    }

    public static Point getNearestPoint(Point p, Point _2DTree[]){
        return getNearestPoint(p, _2DTree, 0, _2DTree.length-1, comparatorPointXY);
    }

    private static Point getNearestPoint(Point p, Point t[], int left, int right, Comparator<Point> comparator){
        if(left>right) return null;
        int middle = (left + right)>>1;
        Point p3 = (p.compareTo(t[middle])==0 ? null : t[middle]);
        if(left==right) return p3;
        Point p1, p2;
        Comparator<Point> anotherComparator = comparator==comparatorPointXY ? comparatorPointYX : comparatorPointXY;
        p2 = null;
        if( comparator.compare(p, t[middle])<0 ){
            p1 = getNearestPoint(p, t, left, middle-1, anotherComparator);
            double dist = comparator==comparatorPointXY ? (t[middle].x-p.x)*(t[middle].x-p.x) : (t[middle].y-p.y)*(t[middle].y-p.y);
            if( p1==null || p.distanceSq(p1)>dist ) p2 = getNearestPoint(p, t, middle+1, right, anotherComparator);
        }else{
            p1 = getNearestPoint(p, t, middle+1, right, anotherComparator);
            double dist = comparator==comparatorPointXY ? (t[middle].x-p.x)*(t[middle].x-p.x) : (t[middle].y-p.y)*(t[middle].y-p.y);
            if( p1==null || p.distanceSq(p1)>dist ) p2 = getNearestPoint(p, t, left, middle-1, anotherComparator);
        }
        if(p1==null || p2!=null&&p2.distanceSq(p)<p1.distanceSq(p)) p1 = p2;
        if(p1==null || p3!=null&&p3.distanceSq(p)<p1.distanceSq(p)) p1 = p3;
        return p1;
    }

    public static Point[] MinkowskiSum(Point a[], Point b[]){
        ArrayList<Point> res = new ArrayList<Point>();
        int i, j, k;
        int n = a.length, m = b.length;
        for(i=k=0; k<n; ++k) if(a[k].compareTo(a[i])<0) i = k;
        for(j=k=0; k<m; ++k) if(b[k].compareTo(b[j])<0) j = k;
        res.add(a[i].add(b[j]));
        boolean ua[] = new boolean[n], ub[] = new boolean[m];
        while(true){
            if(i==n) i = 0;
            if(j==m) j = 0;
            if(ua[i] && ub[j]) break;
            if(ua[i] || !ub[j]&& comparatorVectorProduct.compare(a[(i+1)%n].subtract(a[i]), b[(j+1)%m].subtract(b[j]))>0){
                res.add(res.get(res.size()-1).add(b[(j+1)%m]).subtract(b[j]));
                ub[j] = true;
                ++j;
            }else{
                res.add(res.get(res.size() - 1).add(a[(i + 1) % n]).subtract(a[i]));
                ua[i] = true;
                ++i;
            }
        }
        res.remove(res.size()-1);
        return res.toArray(new Point[res.size()-1]);
    }


    
    public Pair<Point, Point> getNearestPoints(Point points[]){
        Point p[] = points.clone();
        Arrays.sort(p);
        for(int i=0;i<p.length-1;++i) if(p[i].compareTo(p[i+1])==0) return new Pair<Point, Point>(p[i], p[i+1]);
        Point q[] = p.clone();
        Pair<Point, Point> res = new Pair<Point, Point>(p[0], p[1]);
        return null;
    }
    
    private void getNearestPoints(Point p[], Point q[], int left, int right, Pair<Point,Point> best){
        if(left>right) return;
        if(right-left<5){
            for(int i=left; i<=right; ++i)
            for(int j=i+1; j<=right; ++j){
                if(best.first.distanceSq(best.second) > p[i].distanceSq(p[j])){
                    best.first = p[i];
                    best.second = p[j];
                }
                if(comparatorPointYX.compare(q[i], q[j])>0){
                    Point t = p[i]; p[i] = p[j]; p[j] = t;
                }
                return;
            }
        }
        int middle = (left+right)>>1;
        getNearestPoints(p, q, left, middle, best);
        getNearestPoints(p, q, middle+1, right, best);
        double D = best.first.distance(best.second);
        
        
    }
    
    
    
    public static double orientedVolume(Point3D p1, Point3D p2, Point3D p3, Point3D p4){
        double a1=p1.x-p4.x, a2=p1.y-p4.y, a3=p1.z-p4.z;
        double b1=p2.x-p4.x, b2=p2.y-p4.y, b3=p2.z-p4.z;
        double c1=p3.x-p4.x, c2=p3.y-p4.y, c3=p3.z-p4.z;
        return a1*(b2*c3-b3*c2)
               - a2*(b1*c3-b3*c1)
               + a3*(b1*c2-b2*c1);
    }
    
    public static double areaOfTriangle3D(Point3D p1, Point3D p2, Point3D p3){
        double a1=p1.x-p3.x, a2=p1.y-p3.y, a3=p1.z-p3.z;
        double b1=p2.x-p3.x, b2=p2.y-p3.y, b3=p2.z-p3.z;
        double x=a2*b3-a3*b2;
        double y=-(a1*b3-a3*b1);
        double z=a1*b2-b1*a2;
        return Math.sqrt(x*x+y*y+z*z);
    }


    public static final Comparator comparatorPointXY = new Comparator<Point>(){
        public int compare(Point p1, Point p2) {
            return p1.x==p2.x?(p1.y==p2.y?0:(p1.y<p2.y?-1:1)):(p1.x<p2.x?-1:1);
        }
    };
    public static final Comparator comparatorPointYX = new Comparator<Point>(){
        public int compare(Point p1, Point p2) {
            return p1.y==p2.y?(p1.x==p2.x?0:(p1.x<p2.x?-1:1)):(p1.y<p2.y?-1:1);
        }
    };
    public static final Comparator comparatorVectorProduct = new Comparator<Point>(){
        public int compare(Point p1, Point p2) {
            double vectorProduct = p1.x*p2.y - p1.y*p2.x;
            return vectorProduct==0?0:(vectorProduct<0?1:-1);
        }
    };
    public static final Comparator comparatorClockWise = new Comparator<Point>(){
        public int compare(Point p1, Point p2){
            if(p1.y==0 && p2.y==0){
                if(p1.x>0) return p2.x>0 ? 0 : -1;
                else return p2.x<0 ? 0 : 1;
            }
            if(p1.y==0 && p1.x>0) return -1;
            if(p1.y==0 && p1.x<0) return p2.y>0 ? 1 : -1;
            if(p2.y==0 && p2.x>0) return 1;
            if(p2.y==0 && p2.x<0) return p1.y>0 ? -1 : 1;
            if(p1.y>0 && p2.y<0) return -1;
            if(p1.y<0 && p2.y>0) return 1;
            double vectorProduct = p1.x*p2.y - p1.y*p2.x;
            return vectorProduct==0?0:(vectorProduct<0?1:-1);
        }
    };


    static double sqr(double x){ return x*x;}
}
