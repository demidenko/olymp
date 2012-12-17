import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
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
		TaskB solver = new TaskB();
		solver.solve(1, in, out);
		out.close();
	}
}

class TaskB {
	public void solve(int testNumber, InputReader in, OutputWriter out) {
        
        long n = in.nextLong();
        long x = in.nextLong();
        long y = in.nextLong();
        long c = in.nextLong();
        
        long tl, tr, tt = -1, t;
        tl = 0;
        tr = (long) 4e9;
        while(tl<tr){
            t = (tl+tr)>>1;
            
            long cc = f(n, t, x, y);
            if(cc>=c){
                tt = t;
                tr = t;
            }else tl = t+1;
        }
        
        out.writeln(tt);
	}
    
    
    public static long f(long n, long t, long x, long y){
        
        long res = (t*t+t)/2 * 4 + 1;
        long s;
        
        if(x-t<1){
            s = t-x+1;
            res -= s*s;
        }
        if(y-t<1){
            s = t-y+1;
            res -= s*s;
        }
        if(x+t>n){
            s = x+t-n;
            res -= s*s;
        }
        if(y+t>n){
            s = y+t-n;
            res -= s*s;
        }

        s = t-(Math.abs(x-1)+Math.abs(y-1))-1;
        if(s>0) res+=(s*s+s)/2;

        s = t-(Math.abs(x-1)+Math.abs(n-y))-1;
        if(s>0) res+=(s*s+s)/2;

        s = t-(Math.abs(n-x)+Math.abs(n-y))-1;
        if(s>0) res+=(s*s+s)/2;

        s = t-(Math.abs(n-x)+Math.abs(y-1))-1;
        if(s>0) res+=(s*s+s)/2;
        
        
        return res;
    }
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
    
    public long nextLong(){
        return Long.parseLong(next());
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

