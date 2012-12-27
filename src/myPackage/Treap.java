package myPackage;

import java.util.Random;

public class Treap<T extends Comparable>{
    private class tree<T extends Comparable>{
        T key;
        int prior, size;
        tree left, right;
        tree(T _key, int _prior, tree _left, tree _right){
            key = _key;
            prior = _prior;
            left = _left;
            right = _right;
            size = 1 + size(left) + size(right);
        }
    }

    private int size(tree t){
        return t!=null ? t.size : 0;
    }
    private void update(tree t){
        if(t==null) return;
        t.size = 1 + size(t.left) + size(t.right);
    }
    private void push(tree t){
        if(t==null) return;
    }

    private tree merge(tree left, tree right){
        push(left); push(right);
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

    private tree[] retSplit = new tree[2];
    private void split(tree t, T splitKey){
        push(t);
        if(t==null){
            retSplit[0] = retSplit[1] = null;
        }else
        if(t.key.compareTo(splitKey)<0){
            split(t.right, splitKey);
            t.right = retSplit[0];
            update(t);
            retSplit[0] = t;
        }else{
            split(t.left, splitKey);
            t.left = retSplit[1];
            update(t);
            retSplit[1] = t;
        }
    }

    private tree add(tree t, tree v){
        push(t);
        if(t==null) t = v; else
        if(t.prior < v.prior){
            split(t, (T) v.key);
            v.left = retSplit[0]; v.right = retSplit[1];
            t = v;
        }else{
            int cmp = v.key.compareTo(t.key);
            if(cmp<0) t.left = add(t.left, v); else
            if(cmp>0) t.right = add(t.right, v);
        }
        update(t);
        return t;
    }

    private tree remove(tree t, T key){
        push(t);
        if(t==null) return t;
        int cmp = key.compareTo(t.key);
        if(cmp<0) t.left = remove(t.left, key); else
        if(cmp>0) t.right = remove(t.right, key); else
            t = merge(t.left, t.right);
        update(t);
        return t;
    }

    private tree root;
    private Random rand;

    public Treap(){
        root = null;
        rand = new Random(System.nanoTime());
    }

    public int size(){
        return size(root);
    }

    public void add(T key){
        tree t = root;
        while(t!=null){
            int cmp = t.key.compareTo(key);
            if(cmp>0) t = t.left; else
            if(cmp<0) t = t.right; else return;
        }
        root = add(root, new tree(key, rand.nextInt(), null, null));
    }

    public void remove(T key){
        root = remove(root, key);
    }

    public boolean contains(T key){
        tree t = root;
        while(t!=null){
            int cmp = t.key.compareTo(key);
            if(cmp==0) return true;
            if(cmp>0) t = t.left; else t = t.right;
        }
        return false;
    }

    public T nthElement(int n){
        tree t = root;
        while(t!=null){
            if(n == size(t.left)+1) return (T) t.key;
            if(n <= size(t.left)) t = t.left; else{
                n -= size(t.left)+1;
                t = t.right;
            }
        }
        return null;
    }

    public int countOfLess(T key){
        tree t = root;
        int res = 0;
        while(t!=null){
            push(t);
            if(t.key.compareTo(key)<0){
                res += size(t.left)+1;
                t = t.right;
            }else t = t.left;
        }
        return res;
    }

    public int countOfMore(T key){
        tree t = root;
        int res = 0;
        while(t!=null){
            push(t);
            if(t.key.compareTo(key)>0){
                res += size(t.right)+1;
                t = t.left;
            }else t = t.right;
        }
        return res;
    }

    public T lower(T key){
        tree t = root;
        T res = null;
        while(t!=null){
            int cmp = t.key.compareTo(key);
            if(cmp<0){
                res = (T) t.key;
                t = t.right;
            }else t = t.left;
        }
        return res;
    }

    public T higher(T key){
        tree t = root;
        T res = null;
        while(t!=null){
            int cmp = t.key.compareTo(key);
            if(cmp>0){
                res = (T) t.key;
                t = t.left;
            }else t = t.right;
        }
        return res;
    }

    public T first(){
        if(root==null) return null;
        tree t = root;
        while(t.left!=null) t = t.left;
        return (T) t.key;
    }

    public T last(){
        if(root==null) return null;
        tree t = root;
        while(t.right!=null) t = t.right;
        return (T) t.key;
    }

    private void toArray(tree t, T[] array, int less){
        if(t==null) return;
        toArray(t.left, array, less);
        array[less+size(t.left)] = (T) t.key;
        toArray(t.right, array, less + size(t.left) + 1);
    }

    public T[] toArray(T[] array){
        toArray(root, array, 0);
        return array;
    }
}
