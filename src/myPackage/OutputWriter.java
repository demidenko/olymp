package myPackage;

import java.io.*;

public class OutputWriter{
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

    public void flush(){
        out.flush();
    }

    public void close(){
        out.close();
    }
}
