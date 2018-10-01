
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static library.Math.*;
import library.Util;
import java.util.stream.Collectors;

public class Test
{
    public static void zameniMesta(int[] niz, int i, int j){
        int a = niz[i];
        niz[i] = niz[j];
        niz[j] = a;
    }
    
    public static void main(String[] args) {
        int[] niz = new int[]{4,1,2,5,64,23,3,8,43,2,7,4,11};
        
//        for(int i=0; i<niz.length-1; i++){
//            for(int j=niz.length-1; j>i; j--){
//                if( niz[i] > niz[j] )
//                    zameniMesta(niz, i, j);
//            }
//        }

        for(int j=niz.length-1; j>0; j--){
            if( niz[j-1] > niz[j] )
                zameniMesta(niz, j-1, j);
        }
        
        for(int i:niz)
            System.out.print(i+" ");
                    
        
    }
    
}
