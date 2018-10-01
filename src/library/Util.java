package library;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Util
{
    public static <T> void write(Iterable<T> list){
        for(T i:list)
            System.out.print(i.toString()+" ");
        System.out.println();
    }
    public static void write(int[] array){
        for(int i:array)
            System.out.print(i+", ");
        System.out.println();
    }
    public static void write(Object[] array){
        for(Object i:array)
            System.out.print(i+", ");
        System.out.println(); 
   }
    
    public static void switchValues(int i, int j, int[] array){
        int tek = array[i];
        array[i] = array[j];
        array[j] = tek;
    }
    public static void switchValues(int i, int j, Object[] array){
        Object tek = array[i];
        array[i] = array[j];
        array[j] = tek;
    }
    
    public static <T> List<T> copyList(List<T> list){
        List<T> result;
        try{
            result = list.getClass().newInstance();
        }
        catch(Exception e){
            result = new ArrayList<>( list.size() );
        }
        for(T i:list)
            result.add(i);
        return result;
    }
    public static <T> Set<T> copySet(Set<T> set){
        Set<T> result = new HashSet<>(set.size());
        result.addAll(set);
        return result;
    }

}
