package nizovi;

import static nizovi.Utility.switchValues;
import static nizovi.Utility.write;

public class SortiranjeTriRazliciteVrednosti
{
    public static int[] sort(int[] array){
        int first = 0;
        int last = array.length-1;
        int tek = 0;
        while(tek<=last){
            switch(array[tek]){
                case 0: switchValues(first++, tek++, array); break;
                case 1: tek++; break;
                case 2: switchValues(tek, last--, array); break;
            }
        }
        return array;
    }

    public static void main(String[] args)
    {
        int[] array = new int[]{0,2,0,1,0,0,2};
        write(array);
        sort(array);
        write(array);
    }
    
}
