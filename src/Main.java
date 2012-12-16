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
		F solver = new F();
		int testCount = Integer.parseInt(in.next());
		for (int i = 1; i <= testCount; i++)
			solver.solve(i, in, out);
		out.close();
	}
}

class F {
	public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int b[] = in.nextIntArray(n);
        int a[] = b.clone();
        for(int i=0;i<n;++i){
            a[i] = Math.min(a[i], i>0?a[i-1]+1:1);
        }
        
        for(int i=n-1;i>=0;--i){
            a[i] = Math.min(a[i], i<n-1?a[i+1]+1:1);
        }
        
        int res = 0;
        for(int i=0;i<n;++i) res+=b[i]-a[i];
        
        out.writeln(res);
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

