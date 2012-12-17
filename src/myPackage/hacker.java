package myPackage;

import java.util.ArrayList;
import java.util.Comparator;

public class hacker {

    static class AntiQuickSort{

        static int[] antiQuickSort(int n){
            int p[] = new int[n];
            p[0] = 1;
            p[1] = 4;
            p[2] = 2;
            p[3] = 3;
            for(int i=4; i<n; i++){
                int j = i/2;
                p[i] = p[j];
                p[j] = i+1;
            }
            return p;
        }


        private static int max;
        private static int[] positions;
        private static ArrayList<Integer> pivots;

        public static int[] antiQuickSortJava6(int size){
            max = size;
            positions = new int[size];
            int[] sorted = new int[size];
            for (int i = 0; i < size; i++)
            {
                positions[i] = i;
                sorted[i] = Integer.MIN_VALUE;
            }
            pivots = new ArrayList<Integer>();
            sort1(sorted, 0, size);
            int[] result = new int[size];
            for (int i = 0; i < size; i++)
            {
                result[positions[i]] = sorted[i];
                if (result[positions[i]] == Integer.MIN_VALUE)
                {
                    result[positions[i]] = max;
                    max--;
                }
            }
            return result;
        }

        private static void sort1(int x[], int off, int len){
            if (len < 7) {
                for (int i=off; i<len+off; i++)
                    for (int j=i; j>off && x[j-1]>x[j]; j--)
                        swap(x, j, j-1);
                return;
            }

            // Choose a partition element, v
            int m = off + (len >> 1);       // Small arrays, middle element
            if (len > 7) {
                int l = off;
                int n = off + len - 1;
                if (len > 40) {        // Big arrays, pseudomedian of 9
                    int s = len/8;

                    // Hack start
                    if (x[l] != Integer.MIN_VALUE ||
                            x[l + s] != Integer.MIN_VALUE ||
                            x[l + 2 * s] != Integer.MIN_VALUE ||
                            x[m - s] != Integer.MIN_VALUE ||
                            x[m] != Integer.MIN_VALUE ||
                            x[m + s] != Integer.MIN_VALUE ||
                            x[n - 2 * s] != Integer.MIN_VALUE ||
                            x[n - s] != Integer.MIN_VALUE ||
                            x[n] != Integer.MIN_VALUE
                            )
                    {
                        // Do nothing
                    }
                    else
                    {
                        set(x, l, max - 4);
                        set(x, l + s, max - 5);
                        set(x, l + 2 * s, max - 3);
                        set(x, m - s, max - 1);
                        set(x, m, max - 2);
                        set(x, m + s, max);
                        set(x, n - 2 * s, max - 7);
                        set(x, n - s, max - 8);
                        set(x, n, max - 6);
                        max -= 9;

                        pivots.add(x[l]);
                    }
                    // Hack end

                    l = med3(x, l,     l+s, l+2*s);
                    m = med3(x, m-s,   m,   m+s);
                    n = med3(x, n-2*s, n-s, n);
                }
                // Hack start
                else {
                    if (x[l] != Integer.MIN_VALUE ||
                            x[m] != Integer.MIN_VALUE ||
                            x[n] != Integer.MIN_VALUE)
                    {
                        // Do nothing
                    }
                    else
                    {
                        set(x, l, max - 1);
                        set(x, m, max - 2);
                        set(x, n, max);
                        max -= 3;

                        pivots.add(x[l]);
                    }
                }
                // Hack end

                m = med3(x, l, m, n); // Mid-size, med of 3
            }
            int v = x[m];

            // Establish Invariant: v* (<v)* (>v)* v*
            int a = off, b = a, c = off + len - 1, d = c;
            while(true) {
                while (b <= c && x[b] <= v) {
                    if (x[b] == v)
                        swap(x, a++, b);
                    b++;
                }
                while (c >= b && x[c] >= v) {
                    if (x[c] == v)
                        swap(x, c, d--);
                    c--;
                }
                if (b > c)
                    break;
                swap(x, b++, c--);
            }

            // Swap partition elements back to middle
            int s, n = off + len;
            s = Math.min(a-off, b-a  );  vecswap(x, off, b-s, s);
            s = Math.min(d-c,   n-d-1);  vecswap(x, b,   n-s, s);

            // Recursively sort non-partition-elements
            if ((s = b-a) > 1)
                sort1(x, off, s);
            if ((s = d-c) > 1)
                sort1(x, n-s, s);
        }

        private static void swap(int x[], int a, int b) {
            int t = x[a];
            x[a] = x[b];
            x[b] = t;
            int tpos = positions[a];
            positions[a] = positions[b];
            positions[b] = tpos;
        }

        private static void vecswap(int x[], int a, int b, int n) {
            for (int i=0; i<n; i++, a++, b++)
                swap(x, a, b);
        }

        private static int med3(int x[], int a, int b, int c) {
            return (x[a] < x[b] ?
                    (x[b] < x[c] ? b : x[a] < x[c] ? c : a) :
                    (x[b] > x[c] ? b : x[a] > x[c] ? c : a));
        }

        private static void set(int x[], int pos, int value){
            if (x[pos] == Integer.MIN_VALUE)
                x[pos] = value;
            else
                throw new RuntimeException();
        }



        private static final int INSERTION_SORT_THRESHOLD = 47;

        static int[] antiQuickSortJava7(int n) {
            maxNum = n;
            p = new int[n];
            int[] t = new int[n];
            for (int i = 0; i < n; i++)	{
                p[i] = i;
                t[i] = Integer.MIN_VALUE;
            }
            hackedSort(t, 0, n-1, true);
            validate(p, n, 0, n-1);
            validate(t, n, 1, n);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[p[i]] = t[i];
            }
            validate(a, n, 1, n);
            return a;
        }

        private static void hackedSort(int[] a, int left, int right, boolean leftmost) {
            int length = right - left + 1;

            // Use insertion sort on tiny arrays
            if (length < INSERTION_SORT_THRESHOLD) {
                if (leftmost) {

                    for (int i = left; i <= right; i++) {
                        if (a[i] == Integer.MIN_VALUE) a[i] = maxNum--;
                    }

                    /*
                    * Traditional (without sentinel) insertion sort,
                    * optimized for server VM, is used in case of
                    * the leftmost part.
                    */
                    for (int i = left, j = i; i < right; j = ++i) {
                        int ai = a[i + 1];
                        int pi = p[i + 1];
                        while (ai < a[j]) {
                            a[j + 1] = a[j];
                            p[j + 1] = p[j];
                            if (j-- == left) {
                                break;
                            }
                        }
                        a[j + 1] = ai;
                        p[j + 1] = pi;
                    }
                } else {

                    for (int i = left+1; i <= right; i++) {
                        if (a[i] == Integer.MIN_VALUE) a[i] = maxNum--;
                    }
                    if (a[left] == Integer.MIN_VALUE) a[left] = maxNum--;

                    /*
                    * Skip the longest ascending sequence.
                    */
                    do {
                        if (left >= right) {
                            return;
                        }
                    } while (a[++left] >= a[left - 1]);

                    /*
                    * Every element from adjoining part plays the role
                    * of sentinel, therefore this allows us to avoid the
                    * left range check on each iteration. Moreover, we use
                    * the more optimized algorithm, so called pair insertion
                    * sort, which is faster (in the context of Quicksort)
                    * than traditional implementation of insertion sort.
                    */
                    for (int k = left; ++left <= right; k = ++left) {
                        int a1 = a[k], a2 = a[left];
                        int p1 = p[k], p2 = p[left];

                        if (a1 < a2) {
                            a2 = a1; a1 = a[left];
                            p2 = p1; p1 = p[left];
                        }
                        while (a1 < a[--k]) {
                            a[k + 2] = a[k];
                            p[k + 2] = p[k];
                        }
                        ++k;
                        a[k + 1] = a1;
                        p[k + 1] = p1;

                        while (a2 < a[--k]) {
                            a[k + 1] = a[k];
                            p[k + 1] = p[k];
                        }
                        a[k + 1] = a2;
                        p[k + 1] = p2;
                    }
                    int last = a[right];
                    int plast = p[right];

                    while (last < a[--right]) {
                        a[right + 1] = a[right];
                        p[right + 1] = p[right];
                    }
                    a[right + 1] = last;
                    p[right + 1] = plast;
                }
                return;
            }

            // Inexpensive approximation of length / 7
            int seventh = (length >> 3) + (length >> 6) + 1;

            /*
            * Sort five evenly spaced elements around (and including) the
            * center element in the range. These elements will be used for
            * pivot selection as described below. The choice for spacing
            * these elements was empirically determined to work well on
            * a wide variety of inputs.
            */
            int e3 = (left + right) >>> 1; // The midpoint
            int e2 = e3 - seventh;
            int e1 = e2 - seventh;
            int e4 = e3 + seventh;
            int e5 = e4 + seventh;

            if (a[e1] == Integer.MIN_VALUE) a[e1] = maxNum--;
            if (a[e2] == Integer.MIN_VALUE) a[e2] = maxNum--;
            if (a[e3] == Integer.MIN_VALUE) a[e3] = maxNum--;
            if (a[e4] == Integer.MIN_VALUE) a[e4] = maxNum--;
            if (a[e5] == Integer.MIN_VALUE) a[e5] = maxNum--;

            // Sort these elements using insertion sort
            if (a[e2] < a[e1]) {
                int t = a[e2]; a[e2] = a[e1]; a[e1] = t;
                int pt= p[e2]; p[e2] = p[e1]; p[e1] = pt;
            }

            if (a[e3] < a[e2]) {
                int t = a[e3]; a[e3] = a[e2]; a[e2] = t;
                int pt= p[e3]; p[e3] = p[e2]; p[e2] = pt;
                if (t < a[e1]) {
                    a[e2] = a[e1]; a[e1] = t;
                    p[e2] = p[e1]; p[e1] = pt;
                }
            }
            if (a[e4] < a[e3]) {
                int t = a[e4]; a[e4] = a[e3]; a[e3] = t;
                int pt= p[e4]; p[e4] = p[e3]; p[e3] = pt;
                if (t < a[e2]) {
                    a[e3] = a[e2]; a[e2] = t;
                    p[e3] = p[e2]; p[e2] = pt;
                    if (t < a[e1]) {
                        a[e2] = a[e1]; a[e1] = t;
                        p[e2] = p[e1]; p[e1] = pt;
                    }
                }
            }
            if (a[e5] < a[e4]) {
                int t = a[e5]; a[e5] = a[e4]; a[e4] = t;
                int pt= p[e5]; p[e5] = p[e4]; p[e4] = pt;
                if (t < a[e3]) {
                    a[e4] = a[e3]; a[e3] = t;
                    p[e4] = p[e3]; p[e3] = pt;
                    if (t < a[e2]) {
                        a[e3] = a[e2]; a[e2] = t;
                        p[e3] = p[e2]; p[e2] = pt;
                        if (t < a[e1]) {
                            a[e2] = a[e1]; a[e1] = t;
                            p[e2] = p[e1]; p[e1] = pt;
                        }
                    }
                }
            }

            // Pointers
            int less  = left;  // The index of the first element of center part
            int great = right; // The index before the first element of right part

            if (a[e1] != a[e2] && a[e2] != a[e3] && a[e3] != a[e4] && a[e4] != a[e5]) {
                /*
                * Use the second and fourth of the five sorted elements as pivots.
                * These values are inexpensive approximations of the first and
                * second terciles of the array. Note that pivot1 <= pivot2.
                */
                int pivot1 = a[e2]; int ppivot1 = p[e2];
                int pivot2 = a[e4]; int ppivot2 = p[e4];

                /*
                * The first and the last elements to be sorted are moved to the
                * locations formerly occupied by the pivots. When partitioning
                * is complete, the pivots are swapped back into their final
                * positions, and excluded from subsequent sorting.
                */
                a[e2] = a[left];  p[e2] = p[left];
                a[e4] = a[right]; p[e4] = p[right];

                /*
                * Skip elements, which are less or greater than pivot values.
                */
                while (a[++less] < pivot1);
                while (a[--great] > pivot2);

                /*
                * Partitioning:
                *
                *   left part           center part                   right part
                * +--------------------------------------------------------------+
                * |  < pivot1  |  pivot1 <= && <= pivot2  |    ?    |  > pivot2  |
                * +--------------------------------------------------------------+
                *               ^                          ^       ^
                *               |                          |       |
                *              less                        k     great
                *
                * Invariants:
                *
                *              all in (left, less)   < pivot1
                *    pivot1 <= all in [less, k)     <= pivot2
                *              all in (great, right) > pivot2
                *
                * Pointer k is the first index of ?-part.
                */
                outer:
                for (int k = less - 1; ++k <= great; ) {
                    int ak = a[k];
                    int pk = p[k];
                    if (ak < pivot1) { // Move a[k] to left part
                        a[k] = a[less];
                        p[k] = p[less];
                        /*
                        * Here and below we use "a[i] = b; i++;" instead
                        * of "a[i++] = b;" due to performance issue.
                        */
                        a[less] = ak;
                        p[less] = pk;
                        ++less;
                    } else if (ak > pivot2) { // Move a[k] to right part
                        while (a[great] > pivot2) {
                            if (great-- == k) {
                                break outer;
                            }
                        }
                        if (a[great] < pivot1) { // a[great] <= pivot2
                            a[k] = a[less];
                            p[k] = p[less];
                            a[less] = a[great];
                            p[less] = p[great];
                            ++less;
                        } else { // pivot1 <= a[great] <= pivot2
                            a[k] = a[great];
                            p[k] = p[great];
                        }
                        /*
                        * Here and below we use "a[i] = b; i--;" instead
                        * of "a[i--] = b;" due to performance issue.
                        */
                        a[great] = ak;
                        p[great] = pk;
                        --great;
                    }
                }

                // Swap pivots into their final positions
                a[left]  = a[less  - 1]; a[less  - 1] = pivot1;
                p[left]  = p[less  - 1]; p[less  - 1] = ppivot1;
                a[right] = a[great + 1]; a[great + 1] = pivot2;
                p[right] = p[great + 1]; p[great + 1] = ppivot2;


                // Sort left and right parts recursively, excluding known pivots
                hackedSort(a, left, less - 2, leftmost);
                hackedSort(a, great + 2, right, false);

                /*
                * If center part is too large (comprises > 4/7 of the array),
                * swap internal pivot values to ends.
                */
                if (less < e1 && e5 < great) {
                    throw new RuntimeException();
                }

                // Sort center part recursively
                hackedSort(a, less, great, false);

            } else { // Partitioning with one pivot
                throw new RuntimeException();
            }
        }

        private static int maxNum;
        private static int[] p;

        private static void validate(int[] a, int n, int L, int R) {
            boolean[] used = new boolean[n];
            for (int x : a) {
                if (x < L || R < x) {
                    throw new RuntimeException();
                }
                if (used[x - L]) {
                    throw new RuntimeException();
                }
                used[x - L] = true;
            }
        }

    }

    static class AntiHash{

        static long hash(String s, long X){
            long h = 0;
            for(char c : s.toCharArray()) h = h*X+c;
            return h;
        }

        static long hash(String s, long X, long M){
            long h = 0;
            for(char c : s.toCharArray()) h = (h*X+c)%M;
            return h;
        }

        static Pair<String, String> antiHashBase64(int n){
            StringBuilder s1 = new StringBuilder();
            StringBuilder s2 = new StringBuilder();
            char c[] = new char[]{'a','b'};
            for(int i=0;i<n;++i){
                s1.append(c[Integer.bitCount(i)&1]);
                s2.append(c[Integer.bitCount(i)&1^1]);
            }
            return new Pair<String,String>(s1.toString(), s2.toString());
        }
        
        static Pair<String, String> antiHash(long X, long M){
            int n = (1<<16)+1, i, j;
            final long p[] = new long[n];
            for(p[0]=i=1; i<n; ++i){
                p[i] = p[i-1]*X%M;
            }
            Comparator<Integer> idcmp = new Comparator<Integer>() {
                public int compare(Integer o1, Integer o2) {
                    return Long.signum(p[o1]-p[o2]);
                }
            };
            
            ArrayList<long[]> ss = new ArrayList<long[]>();
            ArrayList<Integer[]> ii = new ArrayList<Integer[]>();
            
            int m = n, k = 1;
            while(true){
                
            }
            
            
 //           return null;
        }

    }



}
