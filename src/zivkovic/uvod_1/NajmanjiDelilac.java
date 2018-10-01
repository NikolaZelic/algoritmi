package zivkovic.uvod_1;

public class NajmanjiDelilac
{
    
    static int GCD1(int x, int y)
    {
        int d = Math.min(x, y);
        while( x%d!=0 || y%d!=0 )
        {
            System.out.println("asdf");
            d--;
        }
        return d;
    }
    
    static int GCD2(int x, int y)
    {
        if( x>y )
        {
            int z = x%y;
            if( z==0 )
                return y;
            return GCD2(y, z);
        }
        int z = y%x;
        if( z==0 )
            return x;
        return GCD2(x, z);
    }
    
    static int indeksOd(int[] niz, int x)
    {
        for(int i=0; i<niz.length; i++)
            if(niz[i]==x)
                return i;
        return 0;
    }
    
    /**
     * 
     * @param L - Iznos kredita
     * @param I - Godisnja kamatna stopa
     * @param P - Mesecna rata
     * @param n - Broj meseci
     * @return - Ln za dato n
     */
    static double kreditZaN(double L, double I, double P, int n)
    {
        double Li = L;  // L0 = L;
        double M = 1+I/12;  // Mesecna kamatna stopa
        for(int i=1; i<n; i++)
        {
            Li = M*Li - P;
        }
        return Li;
    }
    
    static double kreditZaNRek(double L, double I, double P, int n)
    {
        if(n==0)
            return L;
        return (1+I/12)*kreditZaNRek(L, I, P, n-1);
    }
    
    static int suma(int n)
    {
        int sum = 0;
        for(int i=0; i<n; i++)
            sum += i;
        return sum;
    }
    
    static void slozenostDvostrukeForPetlje()
    {
        for(int k=1; k<10; k++)
        {
            int br = 0, n=k;
            for(int i=0; i<n; i++)
                for(int j=0; j<i; j++)
                    br++;
            
            System.out.println("Za n="+n+" TS="+br +" suma="+suma(n));
        }
    }
    
    public static void main(String[] args)
    {
        
    }
    
}
