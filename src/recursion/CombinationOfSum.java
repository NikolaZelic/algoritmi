package recursion;

import java.util.LinkedList;
import java.util.List;

/*
    For inputed n shuld find all combinations of sum
*/
public class CombinationOfSum
{
    public static Integer[] newArray(Integer[] array){
        if(array==null || array.length==0)
            return new Integer[1];
        Integer[] res = new Integer[array.length+1];
        for(int i=0; i<array.length; i++)
            res[i] = array[i];
        return res;
    }
    public static int sum(Integer[] array){
        if(array==null)
            return 0;
        int sum = 0;
        for(int i:array)
            sum += i;
        return sum;
    }
    public static void print(Integer[] array){
        for(int i:array)
            System.out.print(i+" ");
        System.out.println();
    }
    public static void solve(int n, Integer[] arr, List<Integer[]> results){
        int sum = sum(arr);
        if(sum==n){
//            print(arr);
            results.add(arr);
            return;
        }
        if(sum>n)
            return;
        Integer[] newArray = newArray(arr);
        for(int i=1; i<=n-sum; i++){
            newArray[newArray.length-1] = i;
            solve(n, newArray, results);
        }
    }

    public static void main(String[] args){
        List<Integer[]> result = new LinkedList<>();
        solve(5, null, result);
        System.out.println("Number of combinations: "+result.size());
        result.forEach( (e)->print(e) );
    }
    
}
