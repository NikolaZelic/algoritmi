package zivkovic.dinamicko;

public class NajduzaZajednickaPodniska
{

    public static String niska(String a, String b){
        StringBuilder bld = new StringBuilder();
        for(int i=0; i<a.length(); i++){
            for(int j=0; j<b.length(); j++){
                if( a.charAt(i)==b.charAt(j) )
                    bld.append(a.charAt(i));
            }
        }
        return bld.toString();
    }
    
    public static void main(String[] args) {
        String a = "asdf";
        String b = "sef";
        System.out.println( niska(a,b) );
    }
    
}
