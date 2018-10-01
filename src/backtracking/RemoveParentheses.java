package backtracking;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class RemoveParentheses
{

    public static void solve(String str, Set<String> result){
        for(int i=0; i<str.length(); i++){
            if( !isParenthes(str, i) )
                continue;
            String str2 = remove(str, i);
            if( correct(str2) ){
                result.add(str2);
            }
        }
    }
    
    public static void allCombinations(String str, Set<String> result){
        if(str.length()==1)
            return;
        for(int i=0; i<str.length(); i++){
            String str2 = remove(str, i);
            result.add(str2);
            allCombinations(str2, result);
        }
    }
    
    public static void solve2(String str, Set<String> result){
        if(str.length()==1)
            return;
        List<String> nextCall = new ArrayList<>();
        boolean haveCorrect = false;
        for(int i=0; i<str.length(); i++){
            if( !isParenthes(str, i) )
                continue;
            String str2 = remove(str, i);
            if( correct(str2) ){
                haveCorrect = true;
                result.add(str2);
            }
            else{
                nextCall.add(str2);
            }
        }
        if( !haveCorrect )
            nextCall.forEach( e -> solve2(e, result) );
    }
    
    public static void main(String[] args) {
//        String str = "(v)())()";
//        String str = "asdf";
        String str = ")((()";
        Set<String> result = new LinkedHashSet<>();
        solve2(str, result);
//        allCombinations(str, result);
        result.forEach( System.out::println );
    }

    private static boolean isParenthes(String str, int i) {
        char c = str.charAt(i);
        return c=='('||c==')';
    }
    private static String remove(String str, int i) {
        char[] chars = new char[str.length()-1];
        int pos = 0;
        char[] toCharArray = str.toCharArray();
        for(int j=0; j<toCharArray.length; j++)
            if(j!=i)
                chars[pos++] = toCharArray[j];
        return new String(chars);
    }
    private static boolean correct(String str) {
        int count = 0;
        for(char c:str.toCharArray()){
            switch(c){
                case '(': count++; break;
                case ')': count--; break;
                default: continue;
            }
            if(count<0)
                return false;
        }
        return count == 0;
    }
    
}
