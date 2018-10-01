package nizovi;
import static nizovi.Utility.write;

public class SortiranjeDveRazliciteVrednosti
{
    public static int[] sort(int[] array){
        int last = -1;
        for(int i=0; i<array.length; i++){
            if(array[i]==0){
                if(last!=-1){
                    array[last] = 0;
                    array[i] = 1;
                    last = i;
                }
            }
            else{
                if(last==-1)
                    last = i;
            }
        }
        return array;
    }
    
    public static void main(String[] args)
    {
        int[] array = new int[]{0,0,0,1,0,1,1};
        write(array);
        sort(array);
        write(array);
    }
    
}
