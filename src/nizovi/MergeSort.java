package nizovi;
import static library.Util.*;
public class MergeSort
{
    /*
        PO UZORU NA ZIVKOVIC STR. 143
    */
    public static void arrayBreak(int[] a, int[] a1, int[] a2){
        int i1=0, i2=0;
        for(int i=0; i<a.length; i++){
            if(i%2==0)
                a1[i1++] = a[i];
            else
                a2[i2++] = a[i];
        }
    }
    
    public static int[] arrayMerge(int[] a1, int[] a2){
        int[] a = new int[a1.length+a2.length];
        int i1=0, i2=0;
        for(int i=0; i<a.length; i++){
            if(i1>=a1.length)
                a[i] = a2[i2++];
            else if(i2>=a2.length)
                a[i] = a1[i1++];
            else if( a1[i1]<a2[i2] )
                a[i] = a1[i1++];
            else
                a[i] = a2[i2++];
        }
        return a;
    }
    
    public static int[] mergeSort(int[] a){
        if(a.length==1)
            return a;
        int[] a1 = new int[ a.length/2 +(a.length%2==1?1:0) ], a2 = new int[ a.length/2 ];
        arrayBreak(a, a1, a2);
        a1 = mergeSort(a1);
        a2 = mergeSort(a2);
        return arrayMerge(a1, a2);
    }
    
    public static void main(String[] args)
    {
        int[] a = new int[]{8,6,9,2,2,1,1,7,3};
        a = mergeSort(a);
        write(a);
    }
    
}
