package nizovi;

public class Utility
{
     public static void write(int[] array){
        for(int i:array)
            System.out.print(i+" ");
        System.out.println();
    }
     
     public static void switchValues(int i, int j, int[] array){
         int tek = array[i];
         array[i] = array[j];
         array[j] = tek;
     }
}
