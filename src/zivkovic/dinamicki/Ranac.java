package zivkovic.dinamicki;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import library.Util;

public class Ranac
{
    public List<Item> packageBackpack(List<Item> items, int maxWeight){
        return null;
    }
    public static <T> void allPermutations(List<T> items, List<List<T>> result){
        for(int i=0; i<items.size(); i++){
            List<T> permutation = new ArrayList<>();
            for(int j=0; j<items.size(); j++){
                int pos = i+j;
                if(pos>=items.size())
                    pos -= items.size();
                permutation.add(items.get(pos));
            }
            result.add(permutation);
        }
    }
    public static <T> void subset(List<T> list, int numOfElem, List<List<T>> result){
        if(numOfElem==1){
            for(int i=0; i<list.size(); i++){
                List<T> set = new ArrayList<>();
                set.add( list.get(i) );
                result.add(set);
            }
            return;
        }
        for(int i=0; i<=list.size()-numOfElem; i++){
            for(int j=i+1; j<list.size()-(numOfElem-2); j++){
                List<T> set = new ArrayList();
                set.add(list.get(i));
                for(int k=0; k<numOfElem-1; k++)
                    set.add( list.get(j+k) );
                result.add(set);
            }
        }
    }
    public static <T> List<List<T>> allCombinations(List<T> items){
        // CREATING SUBSETS
        List<List<T>> subsets = new ArrayList<>();
        for(int i=1; i<=items.size(); i++){
            subset(items, i, subsets);
        }
        
        List<List<T>> permutations = new ArrayList<>();
        for(int i=0; i<subsets.size(); i++){
            allPermutations(subsets.get(i), permutations);
        }
        return permutations;
    }
    public static int combinationWeight(List<Item> combination){
        int sum = 0;
        for(Item i:combination)
            sum += i.weight;
        return sum;
    }
    public static int combinationScore(List<Item> combination){
        int sum = 0;
        for(Item i:combination)
            sum += i.priorety;
        return sum;
    }
    public static List<Item> bestCombination(List<List<Item>> combinations, int maxWeight){
        List<Item> best = null;
        int bestScore = 0;
        for(int i=0; i<combinations.size(); i++){
            int combinationWeight = combinationWeight( combinations.get(i) );
            if(combinationWeight>maxWeight)
                continue;
            int score = combinationScore( combinations.get(i) );
            if(score>bestScore){
                best = combinations.get(i);
                bestScore = score;
            }
        }
        return best;
    }
    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add( new Item(60, 1) );
        items.add( new Item(30, 1) );
        items.add( new Item(45, 2) );
        items.add( new Item(90, 2) );
        items.add( new Item(20, 3) );
        
        List<List<Item>> combinations = allCombinations(items);
        List<Item> bestCombination = bestCombination(combinations, 65);
        System.out.println( bestCombination );
        /*
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        List<List<Integer>> allCombinations = allCombinations(list);
        allCombinations.forEach( Util::write );
         */
    }
    
}

class Item {
    public int weight, priorety;
    public Item(int weight, int priorety) {
        this.weight = weight;
        this.priorety = priorety;
    }
    public String toString(){
        return "("+weight+", "+priorety+")";
    }
}