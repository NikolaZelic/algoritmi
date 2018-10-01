
import java.util.logging.Level;
import java.util.logging.Logger;

public class UspavljivanjeKonekcija
{
    private int brojKonekcija = 25;
    private final int IDEALAN_BROJ_KONEKCIJA = 10;
    private int preskoceno = 0;
    private int pocetnaRazlika;
    
    public UspavljivanjeKonekcija(int brojKonekcija)
    {
        this.brojKonekcija = brojKonekcija;
        pocetnaRazlika = brojKonekcija - IDEALAN_BROJ_KONEKCIJA;
    }
    
    private long poslednjeVreme = System.currentTimeMillis();
    
    public void cleane(){
        long tekuceVreme = System.currentTimeMillis();
        System.out.println("cleane od prethodnog "+(tekuceVreme-poslednjeVreme) + " ms");
        poslednjeVreme = tekuceVreme;
        brojKonekcija--;
        preskoceno = 0;
    }
    
    public void organizujCleane(){
//        System.out.println("organizuj cleane");
        if(brojKonekcija==IDEALAN_BROJ_KONEKCIJA)
            return;
        int razlika = brojKonekcija - IDEALAN_BROJ_KONEKCIJA;
        razlika = pocetnaRazlika - razlika;
        if(preskoceno==razlika)
            cleane();
        else
            preskoceno++;
    }
    
    public static void main(String[] args)
    {
        UspavljivanjeKonekcija ob = new UspavljivanjeKonekcija(30);
        
        Thread t = new Thread( ()->{
            while(true){
                ob.organizujCleane();
                try
                {
                    Thread.sleep(200);
                } catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        t.start();
    }
    
}
