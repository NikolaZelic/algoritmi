package grafovi;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;

public class PretragaGrafova {

    private int t = 1;
    private List<Character> naziviCvorova;  // Cvor na poziciji nula ima naziv kao nulti element liste
    private List<List<Integer>> susedstvaCvorova;   // Cvor na poziciji nula ima susede na nultom mestu
    private List<Integer> vremeUlaska; // Cvor na poziciji nula je usao u pretraga za vreme na elementu liste nula
    private List<Integer> vremeIzlaska;
    private List<Integer> stanjaCvorova;
    public static final int NEPOSECEN = 0, AKTIVAN = 1, ZAVRSEN = 2;
    private List<Integer> prethodniCvorovi;    // U pretrazi u dubinu, za cvor na poziciji nula prethdoni cvor je cvor element niza na nuli

    public PretragaGrafova(){
        naziviCvorova = new ArrayList<>();
        susedstvaCvorova = new ArrayList<>();
        vremeUlaska = new ArrayList<>();
        vremeIzlaska = new ArrayList<>();
        stanjaCvorova = new ArrayList<>();
        prethodniCvorovi = new ArrayList<>();

        dodajCvor('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k');
        dodajVezu('a', 'b');
        dodajVezu('a', 'c');
        dodajVezu('a', 'd');
        dodajVezu('c', 'e');
        dodajVezu('c', 'f');
        dodajVezu('d', 'f');
        dodajVezu('e', 'h');
        dodajVezu('f', 'g');
        dodajVezu('f', 'j');
        dodajVezu('f', 'i');
        dodajVezu('g', 'j');
        dodajVezu('h', 'i');
        dodajVezu('h', 'k');
        dodajVezu('i', 'k');
    }

    public void dodajCvor(char ...nazivi){
        for(var naziv:nazivi)
            dodajCvor(naziv);
    }

    public void dodajCvor(char naziv){
        if(naziviCvorova.contains(naziv))
            throw new IllegalArgumentException("Vec postoji cvor sa nazivo: " + naziv);
        naziviCvorova.add(naziv);
        susedstvaCvorova.add(new ArrayList<>());
        vremeUlaska.add(null);
        vremeIzlaska.add(null);
        prethodniCvorovi.add(null);
        stanjaCvorova.add(NEPOSECEN);
    }

    private int getPozicija(char nazivCvora){
        for(var i=0; i<naziviCvorova.size(); i++){
            if(naziviCvorova.get(i).equals(nazivCvora))
                return i;
        }
        throw new IllegalArgumentException("Ne postoji cvor sa nazivom: " + nazivCvora);
    }

    public void dodajVezu(char cvor1, char cvor2){
        if(cvor1==cvor2)
            throw new IllegalArgumentException("Cvor ne moze da bude vezan sam za sebe");
        var pos1 = getPozicija(cvor1);
        var pos2 = getPozicija(cvor2);

        var susedi1 = susedstvaCvorova.get(pos1);
        var susedi2 = susedstvaCvorova.get(pos2);

        if(susedi1.contains(cvor2) || susedi2.contains(cvor1))
            throw new IllegalArgumentException("Vec postoji veza izmedju cvorova: " + cvor1 + " i " + cvor2);

        susedi1.add(pos2);
        susedi2.add(pos1);
    }

    private Integer getVremeUlaska(int cvorPos){
        return vremeUlaska.get(cvorPos);
    }
    private Integer getVremeIzlaska(int cvorPos){
        return vremeIzlaska.get(cvorPos);
    }
    private char getNaziv(int cvorPos){
        return naziviCvorova.get(cvorPos);
    }
    private int getBrojCvorova(){
        return naziviCvorova.size();
    }
    private int getStanje(int cvorPos){
        return stanjaCvorova.get(cvorPos);
    }
    private void setStanje(int cvorPos, int stanje){
        exceptionNaNepostojeciCvor(cvorPos);
        stanjaCvorova.set(cvorPos, stanje);
    }
    private void exceptionNaNepostojeciCvor(int cvorPos){
        if( !postojiCvor(cvorPos) )
            throw new IllegalArgumentException("Ne postoji cvor na poziciji: " + cvorPos);
    }
    private boolean postojiCvor(int cvorPos){
        return cvorPos>=0 && cvorPos<naziviCvorova.size();
    }

    private List<Integer> getSusedi(int cvorPoz){
        return susedstvaCvorova.get(cvorPoz);
    }

    private String naziviCvorova(List<Integer> cvorovi){
        var bld = new StringBuilder();
        cvorovi.forEach( e -> {
            bld.append(getNaziv(e)).append(" ");
        });
        return bld.toString();
    }

    private Integer getPrethodnik(int cvorPos){
        exceptionNaNepostojeciCvor(cvorPos);
        return prethodniCvorovi.get(cvorPos);
    }

    private List<Integer> getPrethodnici(int cvorPos){
        var prethodnik = getPrethodnik(cvorPos);
        if(prethodnik==null)
            return List.of();
        var prethodnici = new LinkedList<Integer>();
        while(prethodnik!=null){
            prethodnici.addFirst(prethodnik);
            prethodnik = getPrethodnik(prethodnik);
        }
        return prethodnici;
    }

    @Override
    public String toString(){
        var fmt = new Formatter();
        var lineFormat = "%-4s %-6s %-7s %-10s %s\n";
        fmt.format(lineFormat, "Cvor", "Ulazak", "Izlazak", "Susedi", "Putanja");
        for(var cvorPos=0; cvorPos<getBrojCvorova(); cvorPos++){
            var naziv = getNaziv(cvorPos);
            var ulazak = getVremeUlaska(cvorPos);
            var izlazak = getVremeIzlaska(cvorPos);
            var susedi = getSusedi(cvorPos);
            var prethodnici = getPrethodnici(cvorPos);
            fmt.format(lineFormat, naziv, ulazak, izlazak, naziviCvorova(susedi), naziviCvorova(prethodnici));
        }

        return fmt.toString();
    }

    private void DFS(int cvor){
        // Pretraga u dubinu pocev od cvora: cvor
        var naziv = getNaziv(cvor);
        setStanje(cvor, AKTIVAN);
        setVremeUlaska(cvor, t++);

        var susedi = getSusedi(cvor);
        susedi.forEach( sused -> {
            if(getStanje(sused)==NEPOSECEN) {
                setPrethodni(sused, cvor);
                DFS(sused);
            }
        });

        setStanje(cvor, ZAVRSEN);
        setVremeIzlaska(cvor, t++);
    }

    private void setVremeIzlaska(int cvor, int i) {
        exceptionNaNepostojeciCvor(cvor);
        if(getVremeIzlaska(cvor)!=null)
            throw new IllegalArgumentException("Vec je postavljeno vreme izlaska za cvor: " + cvor + " ("+getNaziv(cvor)+")");
        vremeIzlaska.set(cvor, i);
    }

    private void setVremeUlaska(int cvor, int vreme) {
        exceptionNaNepostojeciCvor(cvor);
        if(getVremeUlaska(cvor)!=null)
            throw new IllegalArgumentException("Vec je postavljeno vreme ulaska za cvor : " + cvor +" ("+getNaziv(cvor)+")");
        vremeUlaska.set(cvor, vreme);
    }

    public void depthFirstSearch(){
        for(var cvorPos=0; cvorPos<getBrojCvorova(); cvorPos++){
            if( getStanje(cvorPos) == NEPOSECEN ){
                setPrethodni(cvorPos, null);
                DFS(cvorPos);
            }
        }
    }

    private void setPrethodni(int cvorPos, Integer prethodni) {
        exceptionNaNepostojeciCvor(cvorPos);
        if(prethodni!=null)
            exceptionNaNepostojeciCvor(prethodni);
        if(prethodniCvorovi.get(cvorPos)!=null)
            throw new IllegalArgumentException("Vec je postavljen prethhodni cvor za "+cvorPos+" ("+getNaziv(cvorPos)+")");
        prethodniCvorovi.set(cvorPos, prethodni);
    }

    public static void main(String[] args) {
        var graf = new PretragaGrafova();
        graf.depthFirstSearch();
        System.out.println(graf);
    }
}
