import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.util.Comparator;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.io.Writer;
import java.util.AbstractCollection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.StringTokenizer;
import java.math.BigInteger;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		OutputWriter out = new OutputWriter(outputStream);
		TaskE solver = new TaskE();
		solver.solve(1, in, out);
		out.close();
	}
}

class TaskE {
	public void solve(int testNumber, InputReader in, OutputWriter out) {
        int i,j;
        int n = in.nextInt();
        
        Point p[] = new Point[n];
        for(i=0;i<n;++i) p[i] = new Point(in.nextInt(), in.nextInt());
        
        Point p2[] = p.clone();
        
        Arrays.sort(p);
        Arrays.sort(p2, GeomUtils.comparatorPointYX);
        int allX[] = new int[n];
        int allY[] = new int[n];
        for(i=0;i<n;++i){
            allX[i] = (int) p[i].x;
            allY[i] = (int) p[i].y;
        }
        cprx = new Compresser(allX);
        cpry = new Compresser(allY);
        DataStruct.SegmentTreeSumPersistent s = DataStruct.SegmentTreeSumPersistent.build(0, cprx.size()-1);
        t = new DataStruct.SegmentTreeSumPersistent[cpry.size()];
        
        t[cpry.getEqualLower(-inf)] = s;
        for(Point o : p2){
            s = s.P_add(cprx.get((int) o.x), 1);
            t[cpry.get((int)o.y)] = s;
        }
        
        
        int M = 9;
        int a[] = in.nextIntArray(M);
        Arrays.sort(a);
        do{
            int countLeft = a[0] + a[3] + a[6];
            int countRight = a[2] + a[5] + a[8];
            int countUp = a[0] + a[1] + a[2];
            int countDown = a[8] + a[7] + a[6];
            
            if(countLeft+countRight>=n) continue;
            if(countUp+countDown>=n) continue;
            
            if(countLeft<n && p[countLeft].x==p[countLeft-1].x) continue;
            if(n-countRight-1>=0 && p[n-countRight-1].x==p[n-countRight].x) continue;
            if(countDown<n && p2[countDown].y==p2[countDown-1].y) continue;
            if(n-countUp-1>=0 && p2[n-countUp-1].y==p2[n-countUp].y) continue;
            
            int x0 = cprx.getEqualHigher(-inf);
            int x1 = cprx.getEqualLower((int) p[countLeft-1].x);
            int x2 = cprx.getEqualLower((int) p[n-countRight].x-1);
            int x3 = cprx.getEqualLower(inf);
            int y0 = cpry.getEqualLower((int) (p2[countDown-1].y));
            int y1 = cpry.getEqualLower((int) (p2[n-countUp].y-1));
            int y2 = cpry.getEqualLower(inf);
            
            int c1 = t[y2].getSum(x0, x1);
            int c2 = t[y2].getSum(x0, x2);
            int c3 = t[y2].getSum(x0, x3);
            int c4 = t[y1].getSum(x0, x1);
            int c5 = t[y1].getSum(x0, x2);
            int c6 = t[y1].getSum(x0, x3);
            int c7 = t[y0].getSum(x0, x1);
            int c8 = t[y0].getSum(x0, x2);
            int c9 = t[y0].getSum(x0, x3);


            int b[] = {
                    c1 - c4,
                    c2 - c1 - c5 + c4,
                    c3 - c2 - c6 + c5,
                    c4 - c7,
                    c5 - c4 - c8 + c7,
                    c6 - c5 - c9 + c8,
                    c7,
                    c8 - c7,
                    c9 - c8
            };
            
           //out.writeln(Arrays.toString(a),' ',Arrays.toString(b));
            
            if(Arrays.equals(a, b)){
                out.writeln(String.format("%.10f %.10f\n%.10f %.10f",
                        (p[countLeft-1].x+p[countLeft].x)/2. ,
                        (p[n-countRight-1].x+p[n-countRight].x)/2. ,
                        (p2[countDown-1].y+p2[countDown].y)/2. ,
                        (p2[n-countUp-1].y+p2[n-countUp].y)/2.
                        ));
                return;
            }
            
        }while (ArrayUtils.nextPermutation(a));
        
        out.writeln(-1);
	}
    int inf = (int) 2e9;
    Compresser cprx, cpry;
    DataStruct.SegmentTreeSumPersistent t[];

    }

class InputReader{
    private BufferedReader reader;
    private StringTokenizer tokenizer;

    public InputReader(InputStream stream){
        reader = new BufferedReader(new InputStreamReader(stream));
        tokenizer = null;
    }
    
    public String next(){
        while (tokenizer == null || !tokenizer.hasMoreTokens()){
            try{
                tokenizer = new StringTokenizer(reader.readLine());
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
        return tokenizer.nextToken();
    }
    
    public int nextInt(){
        return Integer.parseInt(next());
    }

    public int[] nextIntArray(int n){
        int res[] = new int[n];
        for(int i=0;i<n;++i) res[i] = nextInt();
        return res;
    }
}

class OutputWriter{
    private PrintWriter out;

    public OutputWriter(Writer out){
        this.out = new PrintWriter(out);
    }

    public OutputWriter(OutputStream out){
        this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)));
    }

    public void write(Object ... o){
        for(Object x : o) out.print(x);
    }

    public void writeln(Object ... o){
        write(o);
        out.println();
    }

    public void close(){
        out.close();
    }
}

class Point implements Comparable<Point>{
    public double x, y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Point(){
        x = y = 0;
    }

    public int compareTo(Point p){
        return x==p.x?(y==p.y?0:(y<p.y?-1:1)):(x<p.x?-1:1);
    }

    public String toString(){
        return x+" "+y;
    }

    }

class GeomUtils{


    
    public static final Comparator comparatorPointYX = new Comparator<Point>(){
        public int compare(Point p1, Point p2) {
            return p1.y==p2.y?(p1.x==p2.x?0:(p1.x<p2.x?-1:1)):(p1.y<p2.y?-1:1);
        }
    };


    }

class Compresser{
    private int a[];
    
    public Compresser(int arr[]){
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

class DataStruct {

    public static class SegmentTreeSumPersistent{
        public SegmentTreeSumPersistent left, right;
        int size;

        private int sum;

        private void calc(){
            sum = (left==null ? 0 : left.sum + right.sum);
        }

        public static SegmentTreeSumPersistent build(int left, int right){
            if(left==right) return new SegmentTreeSumPersistent(null, null);
            int middle = (left + right)>>1;
            return new SegmentTreeSumPersistent(build(left,middle), build(middle+1,right));
        }

        private SegmentTreeSumPersistent(SegmentTreeSumPersistent _left, SegmentTreeSumPersistent _right){
            left = _left;
            right = _right;
            sum = 0;
            size = (left==null? 1 : left.size+right.size);
            calc();
        }

        public int getSum(int i, int j){
            if(i>j) return 0;
            if(i==0 && j==size-1){
                return sum;
            }else{
                int m = left.size-1;
                return
                        (i<=m ? left.getSum(i, Math.min(m, j)) : 0)
                                + (j>m ? right.getSum(Math.max(0, i - m - 1), j-m-1) : 0);
            }
        }

        public SegmentTreeSumPersistent P_add(int index, int value){
            if(index<0 || index>=size) throw new RuntimeException();
            if(size==1){
                SegmentTreeSumPersistent t = new SegmentTreeSumPersistent(left, right);
                t.sum = sum + value;
                return t;
            }else{
                SegmentTreeSumPersistent t = new SegmentTreeSumPersistent(left, right);
                if(index<left.size) t.left = left.P_add(index, value);
                else t.right = right.P_add(index-left.size, value);
                t.calc();
                return t;
            }
        }
    }



    }

class ArrayUtils{

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

    }

