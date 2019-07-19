package grafovi;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PretragaUDubinu {
    public static void main(String[] args) {
        LisnatiGrafDubina ob = new LisnatiGrafDubina();
        System.out.println(ob);
//        ob.pretragaUSirinu();
        ob.putOdDo('b', 'k');
    }
}

interface GrafDubina{
    void pretragaUDubinu();
    void pretragaUSirinu();
    int NEAKTIVNO = 1;
    int AKTIVNO = 2;
    int ZAVRSENO = 3;
}

class MatricniGrafDubina implements  GrafDubina{
    private boolean [][] matrica;
    private char [] naziviCvorova;
    private int[] stanja;

    private void resetujStanja(){
        for(int i=0; i<stanja.length; i++){
            stanja[i] = NEAKTIVNO;
        }
    }

    public MatricniGrafDubina(boolean[][] matrica, char[] naziviCvorova){
        if( matrica.length != naziviCvorova.length ){
            throw new IllegalArgumentException("Pogresan argument");
        }
        this.matrica = matrica;
        this.naziviCvorova = naziviCvorova;
        this.stanja = new int[naziviCvorova.length];
        resetujStanja();
    }

    public MatricniGrafDubina(){
        naziviCvorova = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k'};
        matrica = new boolean[naziviCvorova.length][naziviCvorova.length];
        stanja = new int[naziviCvorova.length];
        resetujStanja();
        setVeza('a', 'b');
        setVeza('a', 'c');
        setVeza('a', 'd');
        setVeza('c', 'e');
        setVeza('c', 'f');
        setVeza('d', 'f');
        setVeza('e', 'h');
        setVeza('f', 'g');
        setVeza('f', 'j');
        setVeza('f', 'i');
        setVeza('g', 'j');
        setVeza('h', 'i');
        setVeza('h', 'k');
        setVeza('i', 'k');
    }

    public void setVeza(char a, char b){
        int ap = getPozicija(a);
        int bp = getPozicija(b);
        setVeza(ap, bp);
    }
    public void setVeza(int a, int b){
        if( !validnePozocije(a, b) )
            throw new IllegalArgumentException("Pogresno unesene pozicije za novu vezi: " +a+ " " + b );
        matrica[a][b] = true;
        matrica[b][a] = true;
    }

    private void setStanje(int cvorPos, int stanje){
        if( !validnaPozicija(cvorPos) )
            throw new IllegalArgumentException("Ne postoji cvor na poziciji "+ cvorPos);
        stanja[cvorPos] = stanje;
    }

    private int getPozicija(char a){
        for(int i=0; i<naziviCvorova.length; i++){
            if(naziviCvorova[i] == a)
                 return i;
        }
        throw new IllegalArgumentException("Ne postoji cvor sa nazivom: " + a);
    }

    private char getNaziv(int pos){
        if(pos<naziviCvorova.length)
            return naziviCvorova[pos];
        throw new IllegalArgumentException("Ne postoji cvor na poziciji: " + pos);
    }

    private int getStanje(int cvorPos){
        if( !validnaPozicija(cvorPos) )
            throw new IllegalArgumentException("Ne postoji pozicija: " + cvorPos);
        return stanja[cvorPos];
    }

    private List<Integer> getSusedniCvorovi(int cvorPos){
        List<Integer> susedi = new ArrayList<>();
        for(int i=0; i<matrica.length; i++){
            if( i!=cvorPos && postojiVeza(cvorPos, i) && getStanje(i)==NEAKTIVNO )
                susedi.add(i);
        }
        return susedi;
    }

    public boolean postojiVeza(int a, int b){
        if( !validnePozocije(a, b) )
            throw new IllegalArgumentException("Nepostojece pozicije"+ a+ " " + b);
        return matrica[a][b];
    }

    private boolean validnePozocije(int a, int b){
        if(a<0 || a>=matrica.length || b<0 || b>=matrica.length || a==b)
            return false;
        return true;
    }
    private boolean validnaPozicija(int a){
        if(a<0 || a>=matrica.length)
            return false;
        return true;
    }

    @Override
    public String toString(){
        StringBuilder bld = new StringBuilder();
        for(int i=0; i<naziviCvorova.length; i++){
            bld.append( naziviCvorova[i] ).append(" : ");
            for(int j=0; j<matrica.length; j++){
                if( i!=j && postojiVeza(i, j) )
                    bld.append( naziviCvorova[j] ).append(" ");

            }
            bld.append("\n");
        }
        return bld.toString();
    }

    @Override
    public void pretragaUDubinu(){
        for(int i=0; i<matrica.length; i++){
            if( getStanje(i) == NEAKTIVNO ){
                pretragaSusednihCvorova(i);
            }
        }
    }
    private void pretragaSusednihCvorova(int cvorPos){
        setStanje(cvorPos, AKTIVNO);
        System.out.println(getNaziv(cvorPos));
        List<Integer> susedi = getSusedniCvorovi(cvorPos);
        susedi.forEach( susedPos -> {
            if(getStanje(susedPos)==NEAKTIVNO)
                pretragaSusednihCvorova(susedPos);
        });
        setStanje(cvorPos, ZAVRSENO);
    }

    @Override
    public void pretragaUSirinu() {

    }
}

class LisnatiGrafDubina implements GrafDubina {
    private char[] naziviCvorova;
    private List<Integer> [] susedstva;
    private int[] stanja;

    private void resetujStanja(){
        for(var i=0; i<stanja.length; i++){
            stanja[i] = NEAKTIVNO;
        }
    }
    private void resetujSusedstva(){
        for(var i=0; i<susedstva.length; i++)
            susedstva[i] = new ArrayList<>();
    }

    public LisnatiGrafDubina(){
        naziviCvorova = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k'};
        susedstva = new ArrayList [naziviCvorova.length];
        stanja = new int[naziviCvorova.length];
        resetujStanja();
        resetujSusedstva();
        setVeza('a', 'b');
        setVeza('a', 'c');
        setVeza('a', 'd');
        setVeza('c', 'e');
        setVeza('c', 'f');
        setVeza('d', 'f');
        setVeza('e', 'h');
        setVeza('f', 'g');
        setVeza('f', 'j');
        setVeza('f', 'i');
        setVeza('g', 'j');
        setVeza('h', 'i');
        setVeza('h', 'k');
        setVeza('i', 'k');
    }

    private List<Integer> getSusedi(int cvorPos){
        if( !validnaPozicija(cvorPos) )
            throw new IllegalArgumentException("Ne postoji cvor na poziciji "+ cvorPos);
        return susedstva[cvorPos];
    }

    private int getPozicija(char naziv){
        for(var i=0; i<naziviCvorova.length; i++){
            if(naziviCvorova[i] == naziv)
                return i;
        }
        throw new  IllegalArgumentException("Ne postoji cvor za vazivom: " + naziv);
    }

    private char getNaziv(int pos){
        if( !validnaPozicija(pos) )
            throw new IllegalArgumentException("Ne postoji cvor na poziciji "+pos);
        return naziviCvorova[pos];
    }

    private int getStanje(int pos){
        if( !validnaPozicija(pos) )
            throw new IllegalArgumentException("Ne postoji cvor na poziciji: " + pos);
        return stanja[pos];
    }

    private int setStanje(int cvor, int stanje){
        if( !validnaPozicija(cvor) )
            throw new IllegalArgumentException("Ne postoji cvor na poziciji: " +cvor);
        return stanja[cvor] = stanje;
    }

    public void setVeza(int posA, int posB){
        if( !validnePozicije(posA, posB) )
            throw new IllegalArgumentException("Ne postoje cvorovi na pozicijama "+ posA + " "+ posB );
        var susediA = getSusedi(posA);
        var susediB = getSusedi(posB);
        if( susediA.contains(posB) || susediB.contains(posA) )
            throw new IllegalArgumentException("Vec postoji veza izmedju cvorova " + posA + " " + posB);
        susediA.add(posB);
        susediB.add(posA);
    }
    public void setVeza(char a, char b){
        var posA = getPozicija(a);
        var posB = getPozicija(b);
        setVeza(posA, posB);
    }

    private boolean validnaPozicija(int pos){
        return !(pos<0 || pos>=susedstva.length);
    }
    private boolean validnePozicije(int a, int b){
        return !(a<0 || a>=susedstva.length || b<0 || b>=susedstva.length || a==b);
    }

    @Override
    public String toString(){
        var bld = new StringBuilder();
        for(var i=0; i<susedstva.length; i++){
            bld.append(getNaziv(i)).append(": ");
            getSusedi(i).forEach( sused -> bld.append(getNaziv(sused)).append(" ") );
            bld.append("\n");
        }
        return bld.toString();
    }

    @Override
    public void pretragaUDubinu(){
        for(var i=0; i<susedstva.length; i++){
            if(getStanje(i)==NEAKTIVNO){
                pretragaSusednihCvorova(i);
            }
        }
    }

    private void pretragaSusednihCvorova(int cvor){
        System.out.println(getNaziv(cvor));
        setStanje(cvor, AKTIVNO);
        var susedi = getSusedi(cvor);
        susedi.forEach( sused -> {
            if( getStanje(sused)==NEAKTIVNO )
                pretragaSusednihCvorova(sused);
        });
        setStanje(cvor, ZAVRSENO);
    }

    @Override
    public void pretragaUSirinu() {
        for(var i=0; i<susedstva.length; i++){
            if(getStanje(i)==NEAKTIVNO){
                pretragaSusednihCvorovaUSirinu(i);
            }
        }
    }

    private void pretragaSusednihCvorovaUSirinu(int cvor){
        System.out.println(getNaziv(cvor));

        setStanje(cvor, AKTIVNO);
        var susedi = getSusedi(cvor);
        var listaZaPosetu = new LinkedList<Integer>();

        susedi.forEach( sused -> {
            if( getStanje(sused) == NEAKTIVNO )
                listaZaPosetu.add(sused);
        });

        while( !listaZaPosetu.isEmpty() ){
            var tek = listaZaPosetu.poll();
            setStanje(tek, AKTIVNO);
            System.out.println(getNaziv(tek));
            var tekSusedi = getSusedi(tek);
            tekSusedi.forEach( sused -> {
                if( getStanje(sused)==NEAKTIVNO && !listaZaPosetu.contains(sused) )
                    listaZaPosetu.add(sused);
            });
            setStanje(tek, ZAVRSENO);
        }

        setStanje(cvor, ZAVRSENO);
    }

    public void putOdDo(char a, char b){
        var pozicijaA = getPozicija(a);
        pretragaSusednihCvorovaUSirinu(pozicijaA, b);
    }
    private void pretragaSusednihCvorovaUSirinu(int cvor, char odrediste){
        var odredistePos = getPozicija(odrediste);

        setStanje(cvor, AKTIVNO);

        var susedi = getSusedi(cvor);

        var listaZaPosetu = new LinkedList<SusedNivo>();

        susedi.forEach( sused -> {
            if( getStanje(sused) == NEAKTIVNO ){
                listaZaPosetu.add(new SusedNivo(sused, 1));
            }
        });

        while( !listaZaPosetu.isEmpty() ){
            var tekSusedNivo = listaZaPosetu.poll();
            var tek = tekSusedNivo.cvor;

            setStanje(tek, AKTIVNO);

            if(tek == odredistePos){
                System.out.println("Pronadjen: " + odrediste + " " + tekSusedNivo.nivo);
            }

            var tekSusedi = getSusedi(tek);
            tekSusedi.forEach( sused -> {
                if( getStanje(sused)==NEAKTIVNO && !listaZaPosetu.contains(sused) ) {
                    listaZaPosetu.add(new SusedNivo(sused, tekSusedNivo.nivo+1));
                }
            });
            setStanje(tek, ZAVRSENO);
        }

        setStanje(cvor, ZAVRSENO);
    }
}

class SusedNivo {
    public int cvor;
    public int nivo;

    public SusedNivo(int cvor, int nivo) {
        this.cvor = cvor;
        this.nivo = nivo;
    }
}