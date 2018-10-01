package recursion;

public class NumberOfSteps
{
    /*
        FROM THIS PAGE: 
        https://www.hackerearth.com/practice/basic-programming/recursion/recursion-and-backtracking/practice-problems/algorithm/simran-and-stairs/
    */

    public static int[] newArray(int[] array){
        if(array==null || array.length==0)
            return new int[1];
        int[] res = new int[array.length+1];
        for(int i=0; i<array.length; i++)
            res[i] = array[i];
        return res;
    }
    public static int sum(int[] array){
        if(array==null)
            return 0;
        int sum = 0;
        for(int i:array)
            sum += i;
        return sum;
    }
    /*
        n - number of stairs
    */
    public static int numberOfSteps(int n, int[] moves){
        int sum = sum(moves);
        if(sum==n)
            return 1;
        else if(sum>n)
            return 0;
        
        int[] newArray = newArray(moves);
        int numOfCombinations = 0;
        
        for(int i=1; i<=3; i++){
            newArray[newArray.length-1] = i;
            numOfCombinations += numberOfSteps(n, newArray);
        }
        
        return numOfCombinations;
    }
    
    public static void main(String[] args){
        System.out.println( numberOfSteps(4, null) );
    }
    
}
