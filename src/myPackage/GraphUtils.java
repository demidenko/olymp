package myPackage;


import java.util.Arrays;

public class GraphUtils{
    
    public static void dfs(boolean graph[][], int i, boolean u[]){
        u[i] = true;
        for(int j=0; j<graph[i].length; ++j) if(graph[i][j] && !u[j]) dfs(graph, j, u);
    }

    public static int[][] bfs(char map[][], String good, int sx, int sy, int dx[], int dy[]){
        if(dx==null || dy==null){
            dx = new int[]{1, 0, -1, 0};
            dy = new int[]{0, 1, 0, -1};
        }
        int n = map.length, m = map[0].length;
        int q[][] = new int[2][n*m], qn = 1;
        q[0][0] = sx; q[1][0] = sy;
        int d[][] = new int[n][m];
        for(int r[] : d) Arrays.fill(r, Integer.MAX_VALUE);
        d[sx][sy] = 0;
        for(int k=0;k<qn;++k){
            int x = q[0][k], y = q[1][k];
            for(int i=0;i<dx.length;++i){
                int xt = x+dx[i], yt = y+dy[i];
                if(xt>=0 && xt<n && yt>=0 && yt<m && d[xt][yt]>d[x][y]+1 && good.indexOf(map[xt][yt])>=0){
                    d[xt][yt] = d[x][y]+1;
                    q[0][qn] = xt; q[1][qn] = yt; ++qn;
                }
            }
        }
        return d;
    }
    
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
        for(i=0;i<n;++i) if(f[i]!=-1) res[mm++] = new Pair<Integer, Integer>(f[i], i);
        return res;
    }
    
    private static boolean dfsKhun(boolean graph[][], int i, int f[], boolean u[]){
        if(u[i]) return false;
        u[i] = true;
        for(int j=0; j<graph[i].length; ++j) if(graph[i][j]){
            if(f[j]==-1 || dfsKhun(graph, j, f, u)){
                f[j] = i;
                return true;
            }
        }
        return false;
    }
    
    public static int[] hungarianAlgo(int[][] cost){
        int n = cost.length, i, j, t;
        int w[][] = new int[n][n];
        for(i=0;i<n;++i)
        for(j=0;j<n;++j) w[i][j] = cost[i][j];
        Pair<Integer, Integer> m[];
        boolean g[][] = new boolean[n][n];
        boolean e[][] = new boolean[n*2][n*2];
        boolean u[] = new boolean[n*2];
        boolean em[] = new boolean[n];
        for(;;){
            for(i=0;i<n;++i){
                t = Integer.MAX_VALUE;
                for(j=0;j<n;++j) t = Math.min(t, w[i][j]);
                for(j=0;j<n;++j) w[i][j]-=t;
            }
            for(i=0;i<n;++i){
                t = Integer.MAX_VALUE;
                for(j=0;j<n;++j) t = Math.min(t, w[j][i]);
                for(j=0;j<n;++j) w[j][i]-=t;
            }
            for(i=0;i<n;++i)
            for(j=0;j<n;++j) g[i][j] = (w[i][j]==0);
            m = maxMatching(g);
            if(m.length==n) break;
            for(i=0;i<n;++i)
            for(j=0;j<n;++j) e[i][j] = false;
            for(i=0;i<n;++i)
            for(j=0;j<n;++j) e[i][j+n] = g[i][j];
            Arrays.fill(em, false);
            for(Pair<Integer, Integer> p : m){
                i = p.first;
                j = p.second + n;
                e[i][j] = false;
                e[j][i] = true;
                em[i] = true;
            }
            Arrays.fill(u, false);
            for(i=0;i<n;++i) if(!em[i]) dfs(e, i, u);
            t = Integer.MAX_VALUE;
            for(i=0;i<n;++i)
            for(j=0;j<n;++j) if(u[i] && !u[j+n]) t = Math.min(t, w[i][j]);
            for(i=0;i<n;++i)
            for(j=0;j<n;++j){
                if(!u[i]) w[i][j]+=t;
                if(!u[j+n]) w[i][j]-=t;
            }
        }
        int a[] = new int[n];
        for(Pair<Integer, Integer> p : m) a[p.first] = p.second;
        return a;
    }
    
}
