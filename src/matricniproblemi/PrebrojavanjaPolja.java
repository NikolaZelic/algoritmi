package matricniproblemi;

public class PrebrojavanjaPolja
{
    private int[][] mat;
    public PrebrojavanjaPolja(int [][] mat)
    {
        this.mat = mat;
    }
    public String toString()
    {
        StringBuilder bld = new StringBuilder();
        for(int y = 0; y<mat.length; y++)
        {
            for(int x=0; x<mat[y].length; x++)
            {
                bld.append(mat[y][x]).append(" ");
            }
            bld.append("\n");
        }
        return bld.toString();
    }
    
    private int[] dx = new int[]{0, 1, 0, -1};
    private int[] dy = new int[]{1, 0, -1, 0};
    
    public void PopuniPrvoPolje()
    {
        // Pronalazenje prvog pocetnog polja
        for(int y = 0; y<mat.length; y++)
            for(int x=0; x<mat[y].length; x++)
                if( mat[y][x] == 0 )
                {
                    popuniPolje(x, y);
                    return;
                }
    }
    
    private boolean pronadjiPraznoPolje()
    {
       for(int y = 0; y<mat.length; y++)
            for(int x=0; x<mat[y].length; x++)
                if( mat[y][x] == 0 )
                {
                    popuniPolje(x, y);
                    return true;
                } 
        return false;
    }
    
    public int popuniPolja()
    {
        while( true )
        {
            if(!pronadjiPraznoPolje())
                return rbPolja-2;
            rbPolja++;
        }
    }
    
    private int rbPolja = 2;
    
    
    
    private void popuniPolje(int x, int y)
    {
        mat[y][x] = rbPolja;
        
        for(int p=0; p<4; p++)
        {
            int x2 = x + dx[p];
            int y2 = y + dy[p];
            if( x2<0 || y2<0 || y2>=mat.length || x2>=mat[y2].length )
                continue;
            if( mat[y2][x2] == 0 )
                popuniPolje(x2, y2);
        }
    }
    
    public static void main(String[] args)
    {
        int[][] mat = new int[][]{
            {1, 0, 0, 1, 0},
            {0, 0, 0, 1, 0},
            {0, 0, 1, 1, 0},
            {1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {1, 1, 1, 1, 0},
            {0, 0, 0, 1, 0}
        };
        int[][] mat2 = new int[][]{
            {0,0,1,0,0},
            {0,0,1,0,0},
            {0,0,0,0,0},
            {0,0,1,0,0},
            {0,0,1,0,0}
        };
        PrebrojavanjaPolja ob = new PrebrojavanjaPolja(mat2);
        System.out.println(ob);
        System.out.println("---------------------------");
        System.out.println( "Br polja: "+ob.popuniPolja() );
        System.out.println(ob);
    }
    
}
