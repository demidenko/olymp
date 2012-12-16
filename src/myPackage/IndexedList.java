package myPackage;

import java.util.Random;

public class IndexedList<T>{
    private class tree<T>{
        T value;
        int prior, size;
        tree left, right;
        boolean reverse;
        tree(T _value, int _prior, tree _left, tree _right){
            value = _value;
            prior = _prior;
            left = _left;
            right = _right;
            size = 1 + size(left) + size(right);
            reverse = false;
        }
    }

    private int size(tree t){ return t==null ? 0 : t.size; }
    private void update(tree t){
        if(t==null) return;
        t.size = 1 + size(t.left) + size(t.right);
    }
    private void push(tree t){
        if(t==null) return;
        if(t.reverse){
            if(t.left!=null) t.left.reverse^=true;
            if(t.right!=null) t.right.reverse^=true;
            t.reverse = false;
            tree temp = t.left;
            t.left = t.right;
            t.right = temp;
        }
    }

    private tree merge(tree left, tree right){
        push(left);
        push(right);
        tree t;
        if(left==null) t = right; else
        if(right==null) t = left; else
        if(left.prior > right.prior){
            left.right = merge(left.right, right);
            t = left;
        }else{
            right.left = merge(left, right.left);
            t = right;
        }
        update(t);
        return t;
    }

    private tree[] split(tree t, int splitKey){
        tree left, right;
        if(t==null) left = right = null; else{
            push(t);
            if( splitKey <= size(t.left) ){
                tree res[] = split(t.left, splitKey);
                left = res[0]; t.left = res[1];
                right = t;
            }else{
                tree[] res = split(t.right, splitKey-size(t.left)-1);
                t.right = res[0]; right = res[1];
                left = t;
            }
            update(t);
        }
        return new tree[]{left, right};
    }

    private tree root;
    private Random rand;

    public IndexedList(){
        root = null;
        rand = new Random(System.nanoTime());
    }
    //public IndexedList(int N){root = null; n = 0; for(int i=0;i<N;++i) add(i,new T());}

    public void add(int index, T value){
        if(index<0 || index>size(root)) return;
        tree q = new tree(value, rand.nextInt(), null, null);
        tree res[] = split(root, index);
        res[0] = merge(res[0], q);
        root = merge(res[0], res[1]);
    }

    public void add(T value){
        add(size(root), value);
    }

    public T get(int index){
        if(index<0 || index>=size(root)) return null;
        tree t = root;
        while(t!=null){
            int key = size(t.left);
            if( index==key ) break;
            if( index < key ) t = t.right;
            else{
                index-=key+1;
                t = t.right;
            }
        }
        return (T) t.value;
    }

    public void moveRange(int left, int right, int dist){
        if(dist==0) return;
        if(left>right || left+dist<0 || right+dist>=size(root)) throw new ArrayIndexOutOfBoundsException(left+" "+right+" to "+dist+" size = "+size(root));
        tree leftPart, middle, rightPart, call[];
        call = split(root, left);
        leftPart = call[0]; rightPart = call[1];
        call = split(rightPart, right-left+1);
        middle = call[0]; rightPart = call[1];
        root = merge(leftPart, rightPart);
        call = split(root, left+dist);
        root = merge(call[0], middle);
        root = merge(root, call[1]);
    }

    public void reverse(int left, int right){
        tree leftPart, middle, rightPart, call[];
        call = split(root, left);
        leftPart = call[0]; rightPart = call[1];
        call = split(rightPart, right-left+1);
        middle = call[0]; rightPart = call[1];
        if(middle!=null) middle.reverse ^= true;
        root = merge(leftPart, middle);
        root = merge(root, rightPart);
    }

    public IndexedList<T> cut(int start, int end){
        start = Math.max(start, 0);
        end = Math.min(end, size(root));
        if(start>=end) return new IndexedList<T>();
        tree leftPart, rightPart, middlePart, call[];
        call = split(root, start);
        leftPart = call[0]; rightPart = call[1];
        call = split(rightPart, end-start+1);
        middlePart = call[0]; rightPart = call[1];
        root = merge(leftPart, rightPart);
        IndexedList<T> res = new IndexedList<T>();
        res.root = middlePart;
        return res;
    }

    public int size(){
        return size(root);
    }

    private void toArray(tree t, T[] array, int less){
        if(t==null) return;
        push(t);
        toArray(t.left, array, less);
        array[less+size(t.left)] = (T) t.value;
        toArray(t.right, array, less + size(t.left) + 1);
    }

    public T[] toArray(T[] array){
        toArray(root, array, 0);
        return array;
    }
}
