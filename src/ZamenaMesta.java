
import java.util.Objects;

public class ZamenaMesta
{
    public static void postaviIndeksPre(Grad[] pre)
    {
        for(int i=0; i<pre.length; i++)
        {
            pre[i].indeksPre = i;
        }
    }
    
    public static void postaviIndeksNovi(Grad[] novi)
    {
        for(int i=0; i<novi.length; i++)
        {
            novi[i].indeksNovi = i;
        }
    }
    
    public static int indeksOd(Grad[] niz, Grad grad)
    {
        for(int i=0; i<niz.length; i++)
            if(niz[i].equals(grad))
                return i;
        return -1;
    }
    
    private static void isvrsiZamenuMesta(Grad ref, Grad upr)
    {
        System.out.println(ref.naziv+" i " + upr.naziv);
    }
    
    public static void zameniMesta(Grad[] pre, Grad[] novi)
    {
        for(int i=0; i<pre.length-1; i++)
        {
            Grad ref = pre[i];  // Referentni
            int refIndeksPrethodni = i;
            int refIndeksNovi = indeksOd(novi, ref);
            
            for(int j=i+1; j<pre.length; j++)
            {
                Grad upr = pre[j];  // Uporedjujuci
                int uprIndeksPre = j;
                int uprIndeksNovi = indeksOd(novi, upr);
                
                // Referentni je presao sa levo na desno u odnosu na uporedjujuci
                boolean refDes = refIndeksPrethodni<uprIndeksPre && refIndeksNovi>uprIndeksNovi;
                // Referentni je presao sa desno na levo u odnosu na uporedjujuci
                boolean refLev = refIndeksPrethodni>uprIndeksPre && refIndeksNovi<uprIndeksNovi;
                if(refDes||refLev)
                    isvrsiZamenuMesta(ref, upr);
            }
        }
    }
    
    public static void main(String[] args)
    {
        Grad g1 = new Grad(175, "Beograd");
        Grad g2 = new Grad(127, "Arilje");
        Grad g3 = new Grad(279, "Nis");
        Grad g4 = new Grad(42, "Podgorica");
        
        Grad[] pre = new Grad[]{g1, g2, g3, g4};
        Grad[] posle = new Grad[]{g2, g3, g1, g4};
        
        zameniMesta(pre, posle);
    }
}

class Grad
{
    int id;
    String naziv;
    
    int indeksPre;
    int indeksNovi;

    public Grad(int id, String naziv)
    {
        this.id = id;
        this.naziv = naziv;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.naziv);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Grad other = (Grad) obj;
        if (this.id != other.id)
        {
            return false;
        }
        if (!Objects.equals(this.naziv, other.naziv))
        {
            return false;
        }
        return true;
    }
    
    
}