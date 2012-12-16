package myPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class InputReader{
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
    
    public String nextLine(){
        tokenizer = null;
        try{
            return reader.readLine();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    
    public int nextInt(){
        return Integer.parseInt(next());
    }

    public long nextLong(){
        return Long.parseLong(next());
    }
    
    public double nextDouble(){
        return Double.parseDouble(next());
    }

    public BigInteger nextBigInteger() {
        return new BigInteger(next());
    }

    public BigDecimal nextBigDecimal() {
        return new BigDecimal(next());
    }
    
    public int[] nextIntArray(int n){
        int res[] = new int[n];
        for(int i=0;i<n;++i) res[i] = nextInt();
        return res;
    }
}
