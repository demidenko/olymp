package myPackage;

import java.util.Arrays;

public class DataStruct {
    public static class SegmentTreeMin{
        int t[];
        int d;

        public SegmentTreeMin(int[] array){
            int n = array.length, i;
            for(d=1; d<n; d<<=1);
            t = new int[d<<1];
            for(i=d; i<n+d; ++i) t[i] = array[i-d];
            for(i=n+d; i<d+d; ++i) t[i] = Integer.MAX_VALUE;
            for(i=d-1; i>0; --i) t[i] = Math.min(t[i*2], t[i*2+1]);
        }

        public void set(int index, int value){
            index+=d;
            t[index] = value;
            for(index>>=1; index>0; index>>=1) t[index] = Math.min(t[index<<1], t[index<<1|1]);
        }

        public int getMin(int left, int right){
            int res = Integer.MAX_VALUE;
            for(left+=d, right+=d; left<=right; left>>=1, right>>=1){
                if((left&1)==1) res = Math.min(res, t[left++]);
                if((right&1)==0) res = Math.min(res, t[right--]);
            }
            return res;
        }

        public int getMin(){
            return t[1];
        }

        public int getIndexWithMin(int left, int right, int min){
            int i, k, q=-1;
            for(i=left+d, right+=d; i<=right;){
                k = Math.min(i&-i, Integer.highestOneBit(right+1-i));
                q = i/k;
                if(min == t[q]) break;
                i+=k;
            }
            for(;q<d;){
                q<<=1;
                if(t[q]>min) ++q;
            }
            return q-d;
        }
    }

    public static class SegmentTreeMax{
        int t[];
        int d;

        public SegmentTreeMax(int[] array){
            int n = array.length, i;
            for(d=1; d<n; d<<=1);
            t = new int[d<<1];
            for(i=d; i<n+d; ++i) t[i] = array[i-d];
            for(i=n+d; i<d+d; ++i) t[i] = Integer.MIN_VALUE;
            for(i=d-1; i>0; --i) t[i] = Math.max(t[i*2], t[i*2+1]);
        }

        public void set(int index, int value){
            index+=d;
            t[index] = value;
            for(index>>=1; index>0; index>>=1) t[index] = Math.max(t[index<<1], t[index<<1|1]);
        }

        public void add(int index, int value){
            index+=d;
            t[index] += value;
            for(index>>=1; index>0; index>>=1) t[index] = Math.max(t[index<<1], t[index<<1|1]);
        }

        public int getMax(int left, int right){
            int res = Integer.MIN_VALUE;
            for(left+=d, right+=d; left<=right; left>>=1, right>>=1){
                if((left&1)==1) res = Math.max(res, t[left++]);
                if((right&1)==0) res = Math.max(res, t[right--]);
            }
            return res;
        }

        public int getMax(){
            return t[1];
        }
    }

    public static class SegmentTreeSum{
        long t[];
        int d;

        public SegmentTreeSum(int[] array){
            int n = array.length, i;
            for(d=1; d<n; d<<=1);
            t = new long[d<<1];
            for(i=d; i<n+d; ++i) t[i] = array[i-d];
            for(i=n+d; i<d+d; ++i) t[i] = 0;
            for(i=d-1; i>0; --i) t[i] = t[i*2]+t[i*2+1];
        }

        public void add(int index, long value){
            for(index+=d; index>0; index>>=1) t[index] += value;
        }

        public long getSum(int left, int right){
            long res = 0;
            for(left+=d, right+=d; left<=right; left>>=1, right>>=1){
                if((left&1)==1) res += t[left++];
                if((right&1)==0) res += t[right--];
            }
            return res;
        }

        public long getSum(){
            return t[1];
        }
    }

    public static class SegmentTreeArray {
        private int t[][];
        private int d;

        public SegmentTreeArray(int array[]){
            int n = array.length, i;
            for(d=1; d<n; d<<=1);
            t = new int[d<<1][];
            for(i=d;i<d+n;++i) t[i] = new int[]{array[i-d]};
            for(i=d+n;i<d+d;++i) t[i] = new int[0];
            for(i=d-1;i>0;--i){
                t[i] = new int[t[i*2].length+t[i*2+1].length];
                ArrayUtils.merge(t[i], t[i*2], t[i*2+1]);
            }
        }

        public int countOfLess(int left, int right, int value){
            int res = 0;
            for(left+=d, right+=d; left<=right; left=(left+1)>>1, right=(right-1)>>1){
                if((left&1)==1){
                    int i = Arrays.binarySearch(t[left], value);
                    if(i<0) i=-i-1;
                    res += i;
                }
                if((right&1)==0){
                    int i = Arrays.binarySearch(t[right], value);
                    if(i<0) i=-i-1;
                    res += i;
                }
            }
            return res;
        }

        public int countOfLess(int value){
            int i = Arrays.binarySearch(t[1], value);
            if(i<0) i=-i-1;
            return i;
        }

        public int countOfMore(int left, int right, int value){
            int res = 0;
            for(left+=d, right+=d; left<=right; left=(left+1)>>1, right=(right-1)>>1){
                if((left&1)==1){
                    int i = Arrays.binarySearch(t[left], value);
                    if(i<0) i=-i-1;
                    res += t[left].length-i;
                }
                if((right&1)==0){
                    int i = Arrays.binarySearch(t[right], value);
                    if(i<0) i=-i-1;
                    res += t[right].length-i;
                }
            }
            return res;
        }

        public int countOfMore(int value){
            int i = Arrays.binarySearch(t[1], value);
            if(i<0) i=-i-1;
            return t[1].length-i;
        }
    }

    public static class SegmentTreeTreap {
        private Treap<Integer> t[];
        private int d;

        public SegmentTreeTreap(int n){
            for(d=1; d<n; d<<=1);
            t = new Treap[d<<1];
            for(int i=1;i<d+d;++i) t[i] = new Treap<Integer>();
        }

        public SegmentTreeTreap(int array[]){
            int n = array.length, i;
            for(d=1; d<n; d<<=1);
            t = new Treap[d<<1];
            for(i=d;i<d+d;++i) t[i] = new Treap<Integer>();
            for(i=0;i<n;++i)
                for(int j=i+d;j>0;j>>=1) t[j].add(array[i]);
        }

        public void addItem(int index, int value){
            for(index+=d; index>0; index>>=1) t[index].add(value);
        }

        public int countOfLess(int left, int right, int value){
            int res = 0;
            for(left+=d, right+=d; left<=right; left=(left+1)>>1, right=(right-1)>>1){
                if((left&1)==1){
                    res += t[left].countOfLess(value);
                }
                if((right&1)==0){
                    res += t[right].countOfLess(value);
                }
            }
            return res;
        }

        public int countOfLess(int value){
            return t[1].countOfLess(value);
        }

        public int countOfMore(int left, int right, int value){
            int res = 0;
            for(left+=d, right+=d; left<=right; left=(left+1)>>1, right=(right-1)>>1){
                if((left&1)==1){
                    res += t[left].countOfMore(value);
                }
                if((right&1)==0){
                    res += t[right].countOfMore(value);
                }
            }
            return res;
        }

        public int countOfMore(int value){
            return t[1].countOfMore(value);
        }
    }

    public static class SegmentTreeSumSegAdd{
        private long t[];
        private long add[];
        int d;

        public SegmentTreeSumSegAdd(int[] array){
            int n = array.length, i;
            for(d=1; d<n; d<<=1);
            t = new long[d<<1];
            add = new long[d<<1];
            for(i=d; i<n+d; ++i) t[i] = array[i-d];
            for(i=n+d; i<d+d; ++i) t[i] = 0;
            for(i=d-1; i>0; --i) t[i] = t[i<<1]+t[i<<1|1];
        }

        private void add(int i, int j, long value, int left, int right, int node){
            if(i==left && j==right){
                add[node] += value;
                t[node] += value*(right-left+1);
            }else{
                int middle = (left+right)>>1;
                if(i<=middle) add(i, Math.min(j,middle), value, left, middle, node<<1);
                if(j>middle) add(Math.max(i, middle+1), j, value, middle+1, right, node<<1|1);
                t[node] = t[node*2] + t[node*2+1] + add[node]*(right-left+1);
            }
        }

        public void addRange(int left, int right, long value){
            if(left<=right && 0<=left && right<d)
                add(left, right, value, 0, d-1, 1);
        }

        private long getSum(int i, int j, int left, int right, int node){
            if(i==left && j==right){
                return t[node];
            }else{
                int middle = (left+right)>>1;
                long getLeft = 0, getRight = 0;
                if(i<=middle) getLeft = getSum(i, Math.min(j, middle), left, middle, node<<1);
                if(j>middle) getRight = getSum(Math.max(i, middle+1), j, middle+1, right, node<<1|1);
                return getLeft + getRight + add[node]*(j-i+1);
            }
        }

        public long getSum(int left, int right){
            return getSum(left, right, 0, d-1, 1);
        }
    }
    
    public static class SegmentTreeSumSegSet{
        static private int unknown = Integer.MIN_VALUE;
        private long t[];
        private int set[];
        int d;

        public SegmentTreeSumSegSet(int[] array){
            int n = array.length, i;
            for(d=1; d<n; d<<=1);
            t = new long[d<<1];
            set = new int[d<<1];
            Arrays.fill(set,unknown);
            for(i=d; i<n+d; ++i) t[i] = set[i] = array[i-d];
            for(i=n+d; i<d+d; ++i) t[i] = 0;
            for(i=d-1; i>0; --i) t[i] = t[i<<1]+t[i<<1|1];
        }

        private void set(int i, int j, int value, int left, int right, int node){
            if(i==left && j==right){
                set[node] = value;
                t[node] = value*(right-left+1L);
            }else{
                if(set[node]!=unknown){
                    set[node<<1] = set[node<<1|1] = set[node];
                    t[node<<1] = t[node<<1|1] = t[node]>>1;
                    set[node] = unknown;
                }
                int middle = (left+right)>>1;
                if(i<=middle) set(i, Math.min(j,middle), value, left, middle, node<<1);
                if(j>middle) set(Math.max(i, middle+1), j, value, middle+1, right, node<<1|1);
                t[node] = t[node<<1] + t[node<<1|1];
            }
        }

        public void setRange(int left, int right, int value){
            if(left<=right && 0<=left && right<d)
                set(left, right, value, 0, d-1, 1);
        }

        private long getSum(int i, int j, int left, int right, int node){
            if(i==left && j==right){
                return t[node];
            }else{
                if(set[node]!=unknown) return set[node]*(j-i+1L);
                int middle = (left+right)>>1;
                long getLeft = 0, getRight = 0;
                if(i<=middle) getLeft = getSum(i, Math.min(j, middle), left, middle, node<<1);
                if(j>middle) getRight = getSum(Math.max(i, middle+1), j, middle+1, right, node<<1|1);
                return getLeft + getRight;
            }
        }

        public long getSum(int left, int right){
            return getSum(left, right, 0, d-1, 1);
        }
    }

    public static class SegmentTreeMaxSegAdd{
        private long t[];
        private long add[];
        int d;

        public SegmentTreeMaxSegAdd(int[] array){
            int n = array.length, i;
            for(d=1; d<n; d<<=1);
            t = new long[d<<1];
            add = new long[d<<1];
            for(i=d; i<n+d; ++i) t[i] = array[i-d];
            for(i=n+d; i<d+d; ++i) t[i] = Long.MIN_VALUE;
            for(i=d-1; i>0; --i) t[i] = Math.max(t[i*2],t[i*2+1]);
        }

        private void add(int i, int j, long value, int left, int right, int node){
            if(i==left && j==right){
                add[node] += value;
                t[node] += value;
            }else{
                int middle = (left+right)>>1;
                if(i<=middle) add(i, Math.min(j,middle), value, left, middle, node*2);
                if(j>middle) add(Math.max(i, middle + 1), j, value, middle+1, right, node*2+1);
                t[node] = Math.max(t[node*2],t[node*2+1]) + add[node];
            }
        }

        public void addRange(int left, int right, long value){
            if(left<=right && 0<=left && right<d)
                add(left, right, value, 0, d-1, 1);
        }

        private long getMaximum(int i, int j, int left, int right, int node){
            if(i>j || left>right || left>i || j>right) return Long.MIN_VALUE;
            if(i==left && j==right){
                return t[node];
            }else{
                int middle = (left+right)>>1;
                long getLeft = Long.MIN_VALUE, getRight = Long.MIN_VALUE;
                if(i<=middle) getLeft = getMaximum(i, Math.min(j, middle), left, middle, node * 2);
                if(j>middle) getRight = getMaximum(Math.max(i, middle + 1), j, middle + 1, right, node * 2 + 1);
                return Math.max(getLeft,getRight) + add[node];
            }
        }

        public long getMaximum(int left, int right){
            return getMaximum(left, right, 0, d-1, 1);
        }
    }

    public static class SegmentTreeMinCntSegAdd{
        private int t[];
        private int cntMin[];
        private int add[];
        private int d;

        public SegmentTreeMinCntSegAdd(int[] array){
            int n = array.length, i;
            for(d=1; d<n; d<<=1);
            t = new int[d<<1];
            add = new int[d<<1];
            cntMin = new int[d<<1];
            for(i=d; i<n+d; ++i){ t[i] = array[i-d]; cntMin[i] = 1; }
            for(i=n+d; i<d+d; ++i){ t[i] = Integer.MAX_VALUE; cntMin[i] = 1; }
            for(i=d-1; i>0; --i){
                if(t[i*2]<t[i*2+1]){
                    t[i] = t[i*2];
                    cntMin[i] = cntMin[i*2] + (t[i*2]==t[i*2+1] ? cntMin[i*2+1] : 0);
                }else{
                    t[i] = t[i*2+1];
                    cntMin[i] = cntMin[i*2+1] + (t[i*2]==t[i*2+1] ? cntMin[i*2] : 0);
                }
            }
        }

        private void add(int i, int j, int value, int left, int right, int node){
            if(i==left && j==right){
                add[node] += value;
                t[node] += value;
            }else{
                int middle = (left+right)>>1;
                if(i<=middle) add(i, Math.min(j,middle), value, left, middle, node*2);
                if(j>middle) add(Math.max(i, middle + 1), j, value, middle+1, right, node*2+1);
                if(t[node*2]<t[node*2+1]){
                    t[node] = t[node*2];
                    cntMin[node] = cntMin[node*2] + (t[node*2]==t[node*2+1] ? cntMin[node*2+1] : 0);
                }else{
                    t[node] = t[node*2+1];
                    cntMin[node] = cntMin[node*2+1] + (t[node*2]==t[node*2+1] ? cntMin[node*2] : 0);
                }
                t[node] += add[node];
            }
        }

        public void addRange(int left, int right, int value){
            if(left<=right && 0<=left && right<d)
                add(left, right, value, 0, d-1, 1);
        }

        private int[] getMinimum(int i, int j, int left, int right, int node){
            if(i>j || left>right || left>i || j>right) return new int[]{Integer.MAX_VALUE, j-i+1};
            if(i==left && j==right){
                return new int[]{t[node], cntMin[node]};
            }else{
                int middle = (left+right)>>1;
                int[] getLeft = {Integer.MAX_VALUE, -1}, getRight = {Integer.MAX_VALUE, -1};
                if(i<=middle) getLeft = getMinimum(i, Math.min(j, middle), left, middle, node * 2);
                if(j>middle) getRight = getMinimum(Math.max(i, middle + 1), j, middle + 1, right, node * 2 + 1);
                int[] res = new int[2];
                if(getLeft[0]<getRight[0]){
                    res[0] = getLeft[0];
                    res[1] = getLeft[1] + (getLeft[0]==getRight[0] ? getRight[1] : 0);
                }else{
                    res[0] = getRight[0];
                    res[1] = getRight[1] + (getLeft[0]==getRight[0] ? getLeft[1] : 0);
                }
                res[0] += add[node];
                return res;
            }
        }

        public int[] getMinimum(int left, int right){
            return getMinimum(left, right, 0, d-1, 1);
        }
    }

    public static class SegmentTreeSegSet{
        static private int unknown = Integer.MIN_VALUE;
        private int t[];
        int d;

        public SegmentTreeSegSet(int[] array){
            int n = array.length, i;
            for(d=1; d<n; d<<=1);
            t = new int[d<<1];
            for(i=d; i<n+d; ++i) t[i] = array[i-d];
            for(i=n+d; i<d+d; ++i) t[i] = unknown;
            for(i=d-1; i>0; --i) t[i] = unknown;
        }

        private void set(int i, int j, int value, int left, int right, int node){
            if(t[node]==value) return;
            if(i==left && j==right){
                t[node] = value;
            }else{
                if(t[node]!=unknown){
                    t[node*2] = t[node*2+1] = t[node];
                    t[node] = unknown;
                }
                int middle = (left+right)>>1;
                if(i<=middle) set(i, Math.min(j,middle), value, left, middle, node*2);
                if(j>middle) set(Math.max(i, middle + 1), j, value, middle+1, right, node*2+1);
            }
        }

        public void setRange(int left, int right, int value){
            if(left<=right && 0<=left && right<d)
                set(left, right, value, 0, d-1, 1);
        }

        public int getItem(int index){
            int i, res = unknown;
            for(i=index+d; i>0; i>>=1) if(t[i]!=unknown) res = t[i];
            return res;
        }
    }

    public static class SegmentTreeSegAdd{
        private long t[];
        int d;

        public SegmentTreeSegAdd(int[] array){
            int n = array.length, i;
            for(d=1; d<n; d<<=1);
            t = new long[d<<1];
            for(i=d; i<n+d; ++i) t[i] = array[i-d];
        }

        public void addRange(int left, int right, int value){
            for(int i=left+d, j=right+d; i<=j;){
                int k = Math.min(i&-i, Integer.highestOneBit(j-i+1));
                t[i/k] += value;
                i+=k;
            }
        }

        public int getItem(int index){
            int i, res = 0;
            for(i=index+d; i>0; i>>=1) res += t[i];
            return res;
        }
    }

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

        public int getSum(){
            return sum;
        }

        public int getIndexWithSum(int s){
            SegmentTreeSumPersistent t = this;
            int i = 0;
            while(t.size>1){
                if(t.sum<s){
                    s -= t.sum;
                    i += t.left.size;
                    t = t.right;
                }else t = t.left;
            }
            return i;
        }

        public void add(int index, int value){
            SegmentTreeSumPersistent t = this;
            for(;;){
                t.sum += value;
                if(t.size==1) break;
                t = index<t.left.size ? t.left : t.right;
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
    

    public static class FenwickTreeSum{
        private long t[];
        int size;

        public FenwickTreeSum(int n){
            t = new long[n];
            size = n;
        }

        public void add(int index, int value){
            for(;index<size; index|=index+1) t[index]+=value;
        }

        private long sum(int index){
            long s = 0;
            for(;index>=0;index=(index&(index+1))-1) s+=t[index];
            return s;
        }

        public long getSum(int left, int right){
            return sum(right) - sum(left);
        }
    }

    public static class FenwickTree2DSum{
        private long t[][];
        int N, M;

        public FenwickTree2DSum(int n, int m){
            t = new long[n][m];
            N = n;
            M = m;
        }

        public void add(int indexR, int indexC, int value){
            for(int i=indexR; i<N; i|=i+1)
                for(int j=indexC; j<M; j|=j+1) t[i][j]+=value;
        }

        private long sum(int indexR, int indexC){
            long s = 0;
            for(int i=indexR; i>=0; i=(i&(i+1))-1)
                for(int j=indexC; j>=0; j=(j&(j+1))-1) s+=t[i][j];
            return s;
        }

        public long getSum(int indexRA, int indexCA, int indexRB, int indexCB){
            return sum(indexRB, indexCB) - sum(indexRA-1, indexCB) - sum(indexRB, indexCA-1) + sum(indexRA-1, indexCA-1);
        }
    }



    public static class SparseTableMin{
        private int t[][], b[];
        int size, log;

        public SparseTableMin(int[] array){
            size = array.length;
            for(log=0; (1<<log)<=size; ++log);
            t = new int[log][size];
            int i, j, k;
            for(i=0;i<size;++i) t[0][i] = array[i];
            for(j=1, k=1; j<log; ++j, k<<=1)
                for(i=0; i+k<size; ++i) t[j][i] = Math.min(t[j-1][i], t[j-1][i+k]);
            b = new int[size+1];
            for(i=2;i<=size;++i) b[i] = b[i-1]+((i-1&i)==0?1:0);
        }

        public int getMin(int left, int right){
            int len = right-left+1;
            if(len<=0) return Integer.MAX_VALUE;
            return Math.min(t[b[len]][left], t[b[len]][right-(1<<b[len])+1]);
        }
    }

    public static class SparseTableMax{
        private int t[][], b[];
        int size, log;

        public SparseTableMax(int[] array){
            size = array.length;
            for(log=0; (1<<log)<=size; ++log);
            t = new int[log][size];
            int i, j, k;
            for(i=0;i<size;++i) t[0][i] = array[i];
            for(j=1, k=1; j<log; ++j, k<<=1)
                for(i=0; i+k<size; ++i) t[j][i] = Math.max(t[j-1][i], t[j-1][i+k]);
            b = new int[size+1];
            for(i=2;i<=size;++i) b[i] = b[i-1]+((i-1&i)==0?1:0);
        }

        public int getMax(int left, int right){
            int len = right-left+1;
            if(len<=0) return Integer.MIN_VALUE;
            return Math.max(t[b[len]][left], t[b[len]][right-(1<<b[len])+1]);
        }
    }



    public static class SqrtDecompositionSum{
        private int a[][], s[];
        int n, K, N;

        public SqrtDecompositionSum(int array[]){
            n = array.length;
            for(K=1;(K+1)*(K+1)<n;++K);
            N = (n-1)/K+1;
            a = new int[N][K];
            s = new int[N];
            int i=0, j=0;
            for(int k=0;k<n;++k){
                a[i][j] = array[k];
                s[i] += array[k];
                ++j; if(j==K){ j=0; ++i; }
            }
        }

        public int getSum(int left, int right){
            int sum = 0, i;
            int L = left/K, R = right/K;
            if(L==R){
                for(i=left%K, R=right%K; i<=R; ++i) sum+=a[L][i];
            }else{
                for(i=left%K; i<K; ++i) sum+=a[L][i];
                for(i=L+1; i<R; ++i) sum+=s[i];
                for(i=right%K; i>=0; --i) sum+=a[R][i];
            }
            return sum;
        }

        public void add(int index, int value){
            int T = index/K;
            a[T][index%K] += value;
            s[T] += value;
        }
    }

    public static class SqrtDecompositionMax{
        private int a[][], s[];
        int n, K, N;

        public SqrtDecompositionMax(int array[]){
            n = array.length;
            for(K=1;(K+1)*(K+1)<n;++K);
            N = (n-1)/K+1;
            a = new int[N][K];
            s = new int[N];
            int i=0, j=0;
            for(int k=0;k<n;++k){
                a[i][j] = array[k];
                s[i] = Math.max(s[i], array[k]);
                ++j; if(j==K){ j=0; ++i; }
            }
        }

        public int getMax(int left, int right){
            int max = 0, i;
            int L = left/K, R = right/K;
            if(L==R){
                for(i=left%K, R=right%K; i<=R; ++i) max = Math.max(max, a[L][i]);
            }else{
                for(i=left%K; i<K; ++i) max = Math.max(max, a[L][i]);
                for(i=L+1; i<R; ++i) max = Math.max(max, s[i]);
                for(i=right%K; i>=0; --i) max = Math.max(max, a[R][i]);
            }
            return max;
        }

        public void add(int index, int value){
            if(value==0) return;
            int i = index/K, j = index%K;
            if( (a[i][j]==s[i]) == (value>0) ){
                a[i][j] += value;
                s[i] = Math.max(s[i], a[i][j]);
            }else{
                a[i][j] += value;
                s[i] = a[i][j];
                for(j=0;j<K;++j) s[i] = Math.max(s[i], a[i][j]);
            }
        }
    }


}
