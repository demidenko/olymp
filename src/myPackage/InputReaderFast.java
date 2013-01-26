package myPackage;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

/**
 * 30.12.12 18:21
 */
public class InputReaderFast {
    private DataInputStream reader;
    private byte buffer[];
    private int cnt, index;
    
    public InputReaderFast(InputStream stream){
        reader = new DataInputStream(stream);
        buffer = new byte[1<<15];
        cnt = 0;
        index = 0;
    }
    
    public int read(){
        if(index>=cnt){
            try {
                cnt = reader.read(buffer);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            index = 0;
        }
        if(cnt==-1) throw new InputMismatchException();
        return buffer[index++];
    }
    
    public int nextInt(){
        int sign = 1, x = 0, c = read();
        while (c<=32) c=read();
        if(c=='-'){ c=read(); sign=-1; }
        while (c>='0' && c<='9'){
            x = x*10 + c-'0';
            c = read();
        }
        return x*sign;
    }

    public long nextLong(){
        int sign = 1, c = read();
        long x = 0;
        while (c<=32) c=read();
        if(c=='-'){ c=read(); sign=-1; }
        while (c>='0' && c<='9'){
            x = x*10 + c-'0';
            c = read();
        }
        return sign*x;
    }

    public int[] nextIntArray(int n){
        int res[] = new int[n];
        for(int i=0;i<n;++i) res[i] = nextInt();
        return res;
    }
}
