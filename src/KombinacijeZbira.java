public class KombinacijeZbira
{
    public static void ispisNiz(int[] niz){
        for(int i=0; i<niz.length; i++)
            System.out.print(niz[i] + ( i!=niz.length-1? " +":"" ) );
        System.out.println("");
    }
    
    public static int[] dodaj(int[] niz, int br)
    {
        int[] newniz = new int[niz.length+1];
        for(int i=0; i<niz.length; i++)
            newniz[i] = niz[i];
        newniz[niz.length] = br;
        return newniz;
    }
    
    public static int zbirNiza(int[] niz){
        int sum = 0;
        for(int i : niz)
            sum += i;
        return sum;
    }
    
    public static void broj(int n, int tek, int[] brojevi)
    {
        if(brojevi==null){  // Prvi
            
            return;
        }
        
        int sumaNiza = zbirNiza(brojevi);
    }
    
    public static void main(String[] args)
    {
        broj(5, 5, null );
    }
    
}
