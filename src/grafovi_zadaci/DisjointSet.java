package grafovi_zadaci;

import java.util.*;

public class DisjointSet<T extends Comparable<T>> {

    private T[] elements;
    private int[] parents;

    private static boolean hasDuplicates(Object[] arr){
        for(var i=0; i<arr.length-1; i++){
            for(var j=i+1; j<arr.length; j++){
                if(arr[i].equals(arr[j]))
                    return true;
            }
        }
        return false;
    }

    public DisjointSet(T... elements) {
        if(hasDuplicates(elements))
            throw new IllegalArgumentException("Set can't contains duplicates");

        this.elements = elements;
        Arrays.sort(elements);

        parents = new int[elements.length];
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
        }
    }

    private int findElementPosition(T element){
        var pos = Arrays.binarySearch(elements, element);
        if(pos<0)
            throw new IllegalArgumentException("Set doesn't contains element:" + element);
        return pos;
    }

    private T findPositionElement(int pos){
        return elements[pos];
    }

    public int find(int p){
        int root = p;

        while(root != parents[root])
            root = parents[root];

        while(p != root) {
            int next = parents[p];
            parents[p] = root;
            p = next;
        }

        return root;
    }

    public int find(T elemnt){
        return find( findElementPosition(elemnt) );
    }

    public T findElement(int p){
        return findPositionElement( find(p) );
    }

    public T findElement(T element){
        return findPositionElement( find(element) );
    }

    public boolean connected(T p, T q){
        return find(p) == find(q);
    }

    public void unify(T p, T q){
        var root1 = find(p);
        var root2 = find(q);

        if(root1==root2)
            return;

        parents[root1] = root2;
    }

    public static void main(String[] args) {
        var set = new DisjointSet<Character>('a', 'b', 'c', 'd');
        System.out.println(set.connected('a', 'b'));
        set.unify('a', 'b');
        System.out.println(set.findElement((Character)'a'));
        System.out.println(set.findElement((Character)'b'));
        System.out.println(set.connected('a', 'b'));
    }
}
