package src;

public class SetQueens
{
    private int[][] table = new int[8][8];
    
    public void setQueens()
    {
        setRow(0);
    }
    
    // POstavljam kraljicu u n-ti red
    private boolean setRow(int n){
        if(n==8)
            return true;
        for( int i=0; i<8; i++ ){
            if( canSetQueen(n, i) ){
                table[n][i] = 1;
                if( setRow(n+1) )    // Rekurzija
                    return true;
                else{
                    table[n][i] = 0;
                }
            }
        }
        return false;
    }
    
    private boolean canSetQueen(int i, int j){
        // Red
        for(int j2=0; j2<8; j2++)
            if(table[i][j2]==1)
                return false;
        // Kolona
        for(int i2=0; i2<8; i2++)
            if(table[i2][j]==1)
                return false;
        
        for(int i2=0; i2<8; i2++){
            for(int j2=0; j2<8; j2++){
                // Dijagonala rastuca
                if( i2+j2==i+j && table[i2][j2]==1 )
                    return false;
                // Opadajuca dijagonala
                if( i2-j2==i-j && table[i2][j2]==1 )
                    return false;
            }
        }
        return true;
    }
    
    public String toString()
    {
        StringBuilder bld = new StringBuilder();
        for(int i=0; i<table.length; i++)
        {
            for(int j=0; j<table[i].length; j++)
            {
                if(table[i][j]==1)
                    bld.append("1 ");
                else
                    bld.append("0 ");
            }
            bld.append("\n");
        }
        return bld.toString();
    }
    
    public static void main(String[] args)
    {
        SetQueens ob = new SetQueens();
        ob.setQueens();
        System.out.println(ob);
    }
    
}
