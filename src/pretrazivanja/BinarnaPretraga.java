package pretrazivanja;

public class BinarnaPretraga
{
    private static boolean binSearch(int[] array, int i, int j, int x){
        if(j<i)
            return false;
        int m = (i+j)/2;
        if(array[m]==x)
            return true;
        if( array[m]>x )
            return binSearch(array, i, m-1, x);
        else
            return binSearch(array, m+1, j, x);
    }
    
    public static boolean binSearch(int[] array, int x){
        if(array.length<=0)
            return false;
        return binSearch(array, 0, array.length, x);
    }
    
    public static void main(String[] args)
    {
        int[] array = new int[]{};
        System.out.println( binSearch(array, 3) );
    }
    
}
