import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.math.BigDecimal;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.FileInputStream;
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
		InputStream inputStream;
		try {
			inputStream = new FileInputStream("pairs.in");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		OutputStream outputStream;
		try {
			outputStream = new FileOutputStream("pairs.out");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		InputReader in = new InputReader(inputStream);
		OutputWriter out = new OutputWriter(outputStream);
		pairs solver = new pairs();
		solver.solve(1, in, out);
		out.close();
	}
}

class pairs {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int i,j;
        boolean g[][] = new boolean[n][m];
        for(i=0;i<n;++i){
            for(;;){
                j = in.nextInt();
                if(j==0) break;
                g[i][j-1] = true;
            }
        }
        
        Pair<Integer,Integer> p[] = GraphUtils.maxMatching(g);
        
        out.writeln(p.length);
        for(i=0;i<p.length;++i) out.writeln(p[i].first+1, ' ',p[i].second+1);
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

class Pair<X extends Comparable, Y extends Comparable> implements Comparable<Pair>{
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
    
    }

class GraphUtils{
    
    public static Pair<Integer, Integer>[] maxMatching(boolean graph[][]){
        int n = graph.length, m = graph[0].length;
        int i;
        boolean u[] = new boolean[n];
        int f[] = new int[m];
        Arrays.fill(f, -1);
        int mm = 0;
        for(i=0;i<n;++i){
            Arrays.fill(u, false);
            if(dfsKhun(graph, i, f, u)) ++mm;
        }
        Pair<Integer, Integer> res[] = new Pair[mm];
        mm = 0;
        for(i=0;i<m;++i) if(f[i]!=-1) res[mm++] = new Pair<Integer, Integer>(f[i], i);
        return res;
    }
    
    private static boolean dfsKhun(boolean graph[][], int i, int f[], boolean u[]){
        if(u[i]) return false;
        u[i] = true;
        for(int j=0; j<graph[i].length; ++j) if(graph[i][j]){
            if(f[j]==-1 || dfsKhun(graph, f[j], f, u)){
                f[j] = i;
                return true;
            }
        }
        return false;
    }
    
    }

