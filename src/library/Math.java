package library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Math
{
    private Math(){
        
    }
    
    private static <T> List<List<T>> permutationsWithoutRepeating(List<T> set, int i, List<T> permutation, Set<T> used, List<List<T>> result){
        int n = set.size();
        if( permutation==null ){
            if( i<n-1 )
                permutationsWithoutRepeating(set, i+1, null, null, result);
            
            permutation = new ArrayList<>(n);
            used = new HashSet<>(n, 1);
            T element = set.get(i);
            permutation.add(element);
            used.add(element);
            
            permutationsWithoutRepeating(set, i, Util.copyList(permutation), Util.copySet(used), result);
        }
        else{
            if( permutation.size()==n ){
                result.add(permutation);
                return result;
            }
            for(int j=0; j<n; j++){
                T element = set.get(j);
                if( used.contains(element) )
                    continue;
                Set<T> usedCopy = Util.copySet(used);
                List<T> permutationCopy = Util.copyList(permutation);
                usedCopy.add(element);
                permutationCopy.add(element);
                permutationsWithoutRepeating(set, i, permutationCopy, usedCopy, result);
            }
        }
        return result;
    }
    public static <T> List<List<T>> permutationsWithoutRepeating(List<T> set, List<List<T>> result){
        return permutationsWithoutRepeating(set, 0, null, null, result);
    }
    public static <T> List<List<T>> permutationsWithoutRepeating(List<T> set){
        List<List<T>> permutations = new ArrayList<>(set.size());
        return permutationsWithoutRepeating(set, permutations);
    }
    
    private static <T> void combinationsWithoutRepeating(List<T> set, int i, int k, int tek, List<T> permutation, List<List<T>> result){
        if( permutation==null && i<set.size()-k ){
            combinationsWithoutRepeating(set, i+1, k, -1, null, result);
        }
        if( permutation==null ){
            permutation = new ArrayList<>(k);
            permutation.add( set.get(i) );
            if(k>1)
                for(; i<set.size()-1; i++)
                    combinationsWithoutRepeating(set, i, k, i+1, Util.copyList(permutation), result);
            else{
                result.add(permutation);
            }
        }
        else{
            permutation.add( set.get(tek) );
            if( permutation.size() >= k ){  // Exit condition
                result.add(permutation);
                return;
            }
            for(tek=tek+1; tek<set.size(); tek++)
                combinationsWithoutRepeating(set, i, k, tek, Util.copyList(permutation), result);
        }
    }
    public static <T> List<List<T>> combinationsWithoutRepeating(List<T> set,  int k, List<List<T>> result){
        combinationsWithoutRepeating(set, 0, k, -1, null, result);
        return result;
    }
    public static <T> List<List<T>> combinationsWithoutRepeating(List<T> set,  int k){
        List<List<T>> result = new ArrayList<>(set.size());
        combinationsWithoutRepeating(set, 0, k, -1, null, result);
        return result;
    }

    public static <T> List<List<T>> variationsWithoutRepeating(List<T> set, int k, List<List<T>> result){
        List<List<T>> combinations = combinationsWithoutRepeating(set, k);
        for(List<T> combination:combinations){
            permutationsWithoutRepeating(combination, result);
        }
        return result;
    }
    public static <T> List<List<T>> variationsWithoutRepeating(List<T> set, int k){
        return variationsWithoutRepeating(set, k, new ArrayList<List<T>>() );
    }
    
    public static long numberOfPermutationsWithoutRepeating(int n){
        return factorial(n);
    }
    public static long numberOfVariationsWithoutRepeating(int n, int k){
        return factorial(n) / factorial(n-k);
    }
    public static long numberOfCombinationsWithoutRepeating(int n, int k){
        return factorial(n) / (factorial(n-k)*factorial(k));
    }
    public static long factorial(int n){
        long rez = 1;
        for(int i=2; i<=n; i++)
            rez *= i;
        return rez;
    }
}
