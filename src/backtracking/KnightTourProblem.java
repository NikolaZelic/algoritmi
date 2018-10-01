package backtracking;
/*
    FROM PAGE:
    https://www.geeksforgeeks.org/the-knights-tour-problem-backtracking-1/
*/

public class KnightTourProblem
{
    public static void print(Integer[][] mat){
        for(Integer[] i:mat){
            for(Integer j:i)
                System.out.printf("%5d", j);
            System.out.println();
        }
    }   
    
    // PRAVAC: dugacak-gore-levo, dugacak-gore-desno, kratak-gore-levo, kratak-gore-desno,
    // kratak-dole-levo, kratak-dole-desno, dugacak-dole-levo, dugacak-dole-desno
    public static int[] 
        di = new int[]{-2,-2,-1,-1,1,1,2,2},
        dj = new int[]{-1,1,-2,2,-2,2,-1,1};
    
    public static boolean canMove(int i, int j, Integer[][] table){
        if(i<0||j<0||i>7||j>7)
            return false;
        return table[i][j] == null;
    }
    
    public static boolean move(int i, int j, Integer[][] table, int rb){
        table[i][j] = rb;
        rb++;
        if(rb==64)
            return true;
//        System.out.println("---------------"+rb+"---------------------");
//        print(table);
        for(int pos=0; pos<8; pos++){
            int i2=i+di[pos], j2=j+dj[pos];
            if( !canMove(i2, j2, table) )
                continue;
            if( move(i2, j2, table, rb) )
                return true;
            else{
                table[i2][j2] = null;
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        Integer[][] table = new Integer[8][8];
        boolean move = move(0, 0, table,0);
        System.out.println(move);
        print(table);
    }
    
}
