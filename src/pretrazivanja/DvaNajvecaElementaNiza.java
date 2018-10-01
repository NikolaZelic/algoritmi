package pretrazivanja;

public class DvaNajvecaElementaNiza
{

    public static void main(String[] args)
    {
        int[] niz = new int[]{5,2,9,3,1,2};
        
        int max2;
        int max;
        if(niz[0]<niz[1]){
            max2 = niz[0];
            max = niz[1];
        }
        else{
            max2 = niz[1];
            max = niz[0];
        }
        
        for(int i=2; i<niz.length; i++){
            if( niz[i]>max ){
                max2 = max;
                max = niz[i];
            }
        }
        
        for(int i:niz)
            System.out.print(i+" ");
        System.out.println("\n"+max);
        System.out.println(max2);
    }
    
}
