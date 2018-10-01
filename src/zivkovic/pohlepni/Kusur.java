package zivkovic.pohlepni;

import java.util.ArrayList;
import java.util.List;
import library.Util;

public class Kusur
{
    public static void kusur(int[] novcanice, int iznos, List<Integer> rezultat){
        if(iznos<=0)
            return;
        for(int i=novcanice.length-1; i>=0; i--){
            int nov = novcanice[i];
            if( nov <= iznos ){
                rezultat.add( nov );
                iznos -= nov;
                kusur(novcanice, iznos, rezultat);
                return;
            }
        }
    }
    
    public static void main(String[] args) {
        int[] novcanice = new int[]{1,2,5,10,50,100,200,500,1000};
        List<Integer> rezultat = new ArrayList<>();
        kusur(novcanice, 1767, rezultat);
        Util.write(rezultat);
    }
    
}
