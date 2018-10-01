package backtracking;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import library.Util;

public class CtyptihmeticPuzzle2
{
    private String a, b, rez;
    private Character[] allCharacters;
    private Map<Character, Integer> charsValues = new HashMap<>();
    private Integer[] aV, bV, rezV;
    
    
    private void setAllCharacters(String...strs){
        Set<Character> set = new LinkedHashSet<>();
        for(int i=0; i<strs.length; i++){
            for(Character c : strs[i].toCharArray())
                set.add(c);
        }
        allCharacters = set.toArray(new Character[set.size()]);
    }
    private void render(){
        render(a, aV);
        render(b, bV);
        render(rez, rezV);
    }
    private void render(String str, Integer[] values){
        Arrays.fill(values, null);
        char[] chars = str.toCharArray();
        for(int i=0; i<chars.length; i++){
            values[i] = charsValues.get(chars[i]);
        }
    }
    
    public String toString(){
        StringBuilder bld = new StringBuilder();
        int rezLength = rez.length();
        concat(bld, a, aV, rezLength);
        concat(bld, b, bV, rezLength);
        concat(bld, rez, rezV, rezLength);
        return bld.toString();
    }
    private void concat(StringBuilder bld, String str, Integer[] values, int rezLength){
        for(int i=0; i<rezLength-str.length(); i++)
            bld.append("  ");
        char[] chars = str.toCharArray();
        for(int i=chars.length-1; i>=0; i--){
            Integer val = values[i];
            if(val==null)
                bld.append(chars[i]).append(" ");
            else
                bld.append(val).append(" ");
        } 
        bld.append("\n");
    }
    private Integer getVal(Integer[] values, int pos){
        if(pos>=values.length)
            return null;
        return values[pos];
    }
    
    public CtyptihmeticPuzzle2(String a, String b, String rez){
        this.a = new StringBuilder(a).reverse().toString();
        this.b = new StringBuilder(b).reverse().toString();
        this.rez = new StringBuilder(rez).reverse().toString();
        setAllCharacters(a, b, rez);
        aV = new Integer[a.length()];
        bV = new Integer[b.length()];
        rezV = new Integer[rez.length()];
    }
    
    protected boolean set(char c, int val){
        charsValues.put(c, val);
        render();
        return true;
    }
    protected void remove(char c){
        charsValues.remove(c);
        render();
    }
    protected Character nextChar(Character c){
        if(c==null)
            return allCharacters[0];
        for(int i=0; i<allCharacters.length-1; i++)
            if(allCharacters[i].equals(c))
                return allCharacters[i+1];
        return null;
    }
    protected boolean hasContradictions(){
        int previous = 0;
        for(int i=0; i<this.rezV.length; i++){
            Integer a = getVal(aV, i), b = getVal(bV, i), rez = getVal(rezV, i);
            if(a==null || b==null){
                if(rez==null)
                    return false;
                if(i==this.rezV.length-1){
                    if(rez==0 )
                        return true;
                    return rez != previous;
                }
                return false;
            }
            else{
                if(rez==null)
                    return false;
                int sum = a + b;
                if( sum%10+previous != rez )
                    return true;
                previous = sum / 10;
            }
        }
        return false;
    }
    
    public boolean set(char c){
        for(int i=0; i<10; i++){
            if(charsValues.containsValue(i))
                continue;
            set(c, i);
            if( hasContradictions() ){
                remove(c);
            }
            else{
                Character next = nextChar(c);
                if(next==null)
                    return true;
                if( set(next) )
                    return true;
            }
        }
        remove(c);
        return false;
    }
    
    public static void main(String[] args) {
        String 
            a = "BASE",
            b = "BALL",
            rez = "GAMES";
        
        CtyptihmeticPuzzle2 puzzle = new CtyptihmeticPuzzle2(a, b, rez);
        System.out.println( puzzle.set(puzzle.nextChar(null)) );
        System.out.println(puzzle);
                
    }
    
}
