package src;

import static java.lang.Math.sqrt;

public class RednoKorenje
{
/*
    Treba izracunati sqrt(1+sqrt(2+sqer(3+sqer(4....
*/
    public static double rek(int n, int i)
    {
        if(i==n)
            return sqrt(n);
        return sqrt( i + rek(n,i+1) );  
    }
    /*
        Treba izracunati: sqrt(n+sqrt(n-1+sqrt(n-2...
    */
    public static double rek2(int n)
    {
        if(n==1)
            return 1;
        return sqrt( n + rek2(n-1) );
    }
    
    public static double vrtiCifre(int br, int n, int i)
    {
        System.out.println(br);
        if(i==n)
            return sqrt(br);
        int cif = br%10;
        int newBr = cif*100 + br/10;
        return sqrt( br+vrtiCifre(newBr, n, i+1) );
    }
    
    public static void main(String[] args)
    {
//        System.out.println( rek(3,1) );
//        System.out.println(rek2(3));
        System.out.println(vrtiCifre(365, 2, 0));
    }
    
}
