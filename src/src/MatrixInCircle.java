package src;

/**
 * 
 * @author Nikola Zelic
 * Treba za uneto n ispisati 
 *  1  2  3  4
 * 12 13 14  5
 * 11 16 15  6
 * 10  9  8  7
 */

public class MatrixInCircle
{
    
    public static void writeMatrix(int[][] matrix)
    {
        for(int i=0; i< matrix.length; i++)
        {
            for( int j=0; j<matrix[i].length; j++ )
                System.out.printf("%5d",matrix[i][j]);
            System.out.println();
        }
    }
    
    public static int[][] matrixInCircle(int n)
    {
        int[][] m = new int[n][n];
        
        int[] direction = {0, 1, 2, 3};
        int[] di = {0, 1, 0, -1};
        int[] dj = {1, 0, -1, 0};
        
        int i=0, j=0, d = 0;
        for( int rb=0; rb<n*n; rb++ )
        {
            // Promena pravca
            int i2 = i + di[d], j2= j + dj[d];
            if( (i==0&&j==n-1) || (i==n-1&&j==n-1) || (i==n-1&&j==0) || ( i2>=0 && i2<n && j2>=0 && j2<n && m[i2][j2]!=0 ) )
            {
                d++;
                if(d>3)
                    d=0;
            }
            
            m[i][j] = rb+1;
            i += di[d];
            j += dj[d];
        }
        
        return m;
    }
    
    public static void main(String[] args)
    {
        int[][] m = matrixInCircle(5);
        writeMatrix(m);
    }
    
}
