package backtracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/*
    FROM : https://www.geeksforgeeks.org/solving-cryptarithmetic-puzzles-backtracking-8/
*/

public class CryptarithmeticPuzzles
{

    
//    public static void main(String[] args) {
//        String 
//            a = "BASE",
//            b = "BALL",
//            rez = "GAMES";
//        SimplePuzzle puzzle = new SimplePuzzle(a, b, rez);
//        puzzle.write();
//        System.out.println( puzzle.solve() );
//        puzzle.write();
//        puzzle.setValue('B', 3);
//        puzzle.setValue('A', 2);
//        puzzle.setValue('S', 1);
//        puzzle.setValue('L', 9);
//        puzzle.setValue('E', 0);
//        puzzle.setValue('G', 1);
//        puzzle.setValue('M', 9);
//        puzzle.write();
//        System.out.println( puzzle.isCompleted() );
//        System.out.println( puzzle.hasContradiction() );
//           puzzle.removeValue('L', 9);
//           puzzle.write();
//    }
    
}

class SimplePuzzle
{
    private String a, b, rez;
    private Map<Character, Integer> charsValues;
    private Queue<Character> charsQueue;
    private Set<Integer> usedNumbers;
    private Integer[] aVal, bVal, rezVal;
    
    public void write(){
        StringBuilder bld = new StringBuilder();
        write(a, aVal, bld);
        write(b, bVal, bld);
        write(rez, rezVal, bld);
        System.out.println(bld);
    }
    private void write(String a,Integer[] val, StringBuilder bld){
        int dif = length() - val.length;
        for(int i=0; i<dif; i++)
            bld.append("  ");
        for(int i=val.length-1; i>=0; i--){
            bld.append(val[i]==null? ""+a.charAt(i): val[i]).append(" ");
        }
        bld.append("\n");
    }
    private Character getChar(String str, int pos){
        if(pos>=str.length())
            return null;
        return str.charAt(pos);
    }
    private Integer getValue(Character c){
        if(c==null)
            return null;
        return charsValues.get(c);
    }
    private int length(){
        return rez.length();
    }
    private PositionValues getValues(int pos){
        if(pos>=length())
            throw new IllegalArgumentException("Position " + pos +" is out of range");
        Character aChar = getChar(a, pos);
        Character bChar = getChar(b, pos);
        Character rezChar = getChar(rez, pos);
        Integer aVal = getValue(aChar);
        Integer bVal = getValue(bChar);
        Integer rezVal = getValue(rezChar);
        return new PositionValues(aChar, bChar, rezChar, aVal, bVal, rezVal );
    }
    private void addCharsToQueue(String str){
        for(char c:str.toCharArray()){
            if(charsQueue.contains(c))
                continue;
            charsQueue.add(c);
        }
    }
    
    public SimplePuzzle(String a, String b, String rez) {
        this.a = new StringBuilder(a).reverse().toString();
        this.b = new StringBuilder(b).reverse().toString();
        this.rez = new StringBuilder(rez).reverse().toString();
        charsValues = new HashMap<>();
        insertChars(a);
        insertChars(b);
        insertChars(rez);
        usedNumbers = new HashSet<>();
        aVal = new Integer[a.length()];
        bVal = new Integer[b.length()];
        rezVal = new Integer[rez.length()];
        charsQueue = new UniqueQueue<>();
        addCharsToQueue(this.a);
        addCharsToQueue(this.b);
        addCharsToQueue(this.rez);
    }
    private void insertChars(String str){
        for(char c:str.toCharArray())
            charsValues.put(c, null);
    }

    private void renderValues(){
        renderValues(a, aVal);
        renderValues(b, bVal);
        renderValues(rez, rezVal);
    }
    private void renderValues(String a, Integer[] aVal){
        for(int i=0; i<a.length(); i++){
            char c = a.charAt(i);
            Integer val = charsValues.get(c);
            aVal[i] = val;
        }
    }
    public void setValue(char c, int value){
        usedNumbers.add(value);
        charsValues.put(c, value);
        renderValues();
    }
    public void removeValue(char c, int value){
        System.out.println("remove "+c+" "+value);
        usedNumbers.remove(value);
        charsValues.put(c, null);
        renderValues();
    }
    public boolean hasContradiction(){
        int previous = 0;
        for(int i=0; i<length(); i++){
            PositionValues values = getValues(i);
            Integer a = values.getaVal();
            Integer b = values.getbVal();
            Integer rez = values.getcRezVal();
            if(a==null||b==null||rez==null)
                return false;
            int sum = a+b+previous;
            if(sum%10!=rez)
                return true;
            if(sum>=10)
                previous = (a+b)/10;
        }
        return false;
    }
    public boolean isCompleted() {
        for(Integer i:aVal)
            if(i==null)
                return false;
        for(Integer i:bVal)
            if(i==null)
                return false;
        for(Integer i:rezVal)
            if(i==null)
                return false;
        return true;
    }
    public boolean assumeNumber(char c, int number){
        System.out.println(c + " " + number);
//        if(c=='B' && number == 9)
//            System.out.println("cekaj");
        setValue(c, number);
        write();
        if( hasContradiction() ){
            removeValue(c, number);
            charsQueue.add(c);
            return false;
        }
        if( isCompleted() )
            return true;
        
        int n = charsQueue.size();
        NumberGenerator generator = new NumberGenerator(this);
        for(int i=0; i<n; i++){
            Character c2 = charsQueue.poll();
            while(true){
                Integer number2 = generator.nextInt();
                if(number2==-1)
                    break;
                if( assumeNumber(c2, number2) )
                    return true;
            }
            charsQueue.add(c);
        }
//        removeValue(c, number);
//        charsQueue.add(c);
        return false;
    }

    public boolean solve(){
        int n = charsQueue.size();
        for(int i=0; i<n; i++){
            Character c2 = charsQueue.poll();
            if(c2==null)
                break;
            NumberGenerator generator = new NumberGenerator(this);
            while(true){
                Integer number2 = generator.nextInt();
                if(number2==-1)
                    break;
                if( assumeNumber(c2, number2) )
                    return true;
            }
        }
        return false;
    }
    
    class PositionValues
    {
        private Character a, b, rez;
        private Integer aVal, bVal, rezVal;
        public PositionValues(Character a, Character b, Character rez, Integer aVal, Integer bVal, Integer rezVal) {
            this.a = a;
            this.b = b;
            this.rez = rez;
            this.aVal = aVal;
            this.bVal = bVal;
            this.rezVal = rezVal;
        }
        public Character getA() {
            return a;
        }
        public Character getB() {
            return b;
        }
        public Character getRez() {
            return rez;
        }
        public Integer getaVal() {
            return aVal;
        }
        public Integer getbVal() {
            return bVal;
        }
        public Integer getcRezVal() {
            return rezVal;
        }
        public String toString(){
            return a+"="+aVal+" "+b+"="+bVal+" "+rez+"="+rezVal;
        }
    }

    class NumberGenerator{
        private SimplePuzzle parent;
        int i=0;
        public int nextInt(){
            if(i==10)
                return -1;
            for(;i<10;i++){
                if( !parent.usedNumbers.contains(i) ){
                    parent.usedNumbers.add(i);
                    return i++;
                }
            }
            return -1;
        }
        public NumberGenerator(SimplePuzzle parent){
            this.parent = parent;
        }
    }
}

class UniqueQueue<T> extends LinkedList<T> {
  private final Set<T> set = new HashSet<T>();

  @Override
  public boolean add(T t) {
    // Only add element to queue if the set does not contain the specified element.
    if (set.add(t)) {
      super.add(t);
    }

    return true; // Must always return true as per API def.
  }

    @Override
    public T poll() {
        T e =  super.poll(); 
        set.remove(e);
        return e;
    }

  
}

class TestAllCombinations
{
    private String a, b, rez;
    private Integer[] aVal, bVal, rezVal;
    private Character[] allChars;
    
    public TestAllCombinations(String a, String b, String rez) {
        this.a = new StringBuilder(a).reverse().toString();
        this.b = new StringBuilder(b).reverse().toString();
        this.rez = new StringBuilder(rez).reverse().toString();
        allChars = allChars(a,b,rez);
        int n = allChars.length;
        aVal = new Integer[a.length()];
        bVal = new Integer[b.length()];
        rezVal = new Integer[rez.length()];
    }
    
    
    private static Character[] allChars(String...strs){
        Set<Character> set = new HashSet<>();
        for(String str:strs)
            for(Character c:str.toCharArray())
                set.add(c);
        return set.toArray(new Character[set.size()]);
    }
    public Character[] allChars(){
        return allChars(a,b,rez);
    }

    public List<List<Integer>> allCombinations(){
        List<Integer> digs = new ArrayList<>(10);
        Collections.addAll(digs, 0,1,2,3,4,5,6,7,8,9);
        Character[] allChars = allChars();
        List<List<Integer>> variations = library.Math.variationsWithoutRepeating(digs, allChars.length);
        return variations;
    }
    
    public void writeCombination(List<Integer> combination){
        Map<Character, Integer> charsValues = new HashMap<>();
        for(int i=0; i<allChars.length; i++)
            charsValues.put(allChars[i], combination.get(i));
        Integer[] av = new Integer[a.length()], bv = new Integer[b.length()], rezv = new Integer[rez.length()];
        setValues(aVal, a, charsValues);
        setValues(bVal, b, charsValues);
        setValues(rezVal, rez, charsValues);
        StringBuilder bld = new StringBuilder();
        write(a, aVal, bld);
        write(b, bVal, bld);
        write(rez, rezVal, bld);
        System.out.println(bld);
    }
    private void write(String a, Integer[] val, StringBuilder bld){
        int dif = rez.length() - val.length;
        for(int i=0; i<dif; i++)
            bld.append("  ");
        for(int i=val.length-1; i>=0; i--){
            bld.append(val[i]==null? ""+a.charAt(i): val[i]).append(" ");
        }
        bld.append("\n");
    }
    public boolean testCombination(List<Integer> combination){
        Map<Character, Integer> charsValues = new HashMap<>();
        for(int i=0; i<allChars.length; i++)
            charsValues.put(allChars[i], combination.get(i));
        Integer[] av = new Integer[a.length()], bv = new Integer[b.length()], rezv = new Integer[rez.length()];
        setValues(aVal, a, charsValues);
        setValues(bVal, b, charsValues);
        setValues(rezVal, rez, charsValues);
        
        int previous = 0;
        for(int i=0; i<rezVal.length; i++){
            Integer a = getVal(aVal, i);
            Integer b = getVal(bVal, i);
            Integer rez = getVal(rezVal, i);
            if( a==null || b==null ){
                if( rez==previous )
                    return true;
            }
            else{
                int sum = a+b+previous;
                if( sum%10 != rez )
                    return false;
                previous = (a+b) / 10;
            }
        }
        return false;
    }
    private static void setValues(Integer[] values, String str, Map<Character, Integer> map){
        for(int i=0; i<values.length; i++){
            Character c = str.charAt(i);
            values[i] = map.get(c);
        }
    }
    private static Integer getVal(Integer[] arr, int pos){
        if(pos>=arr.length)
            return null;
        return arr[pos];
    }
    private void findVariation(List<List<Integer>> variations){
        int count = 0;
        for(List<Integer> variation:variations){
            if(testCombination(variation)){
                writeCombination(variation);
                count++;
            }
        }
        System.out.println("Number of combinations: " + count);
    }
    
    public static void main(String[] args) {
        String 
            a = "BASE",
            b = "BALL",
            rez = "GAMES";
        TestAllCombinations test = new TestAllCombinations(a, b, rez);
        List<List<Integer>> variations = test.allCombinations();
        test.findVariation(variations);
    }
    
}