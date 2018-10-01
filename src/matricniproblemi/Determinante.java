package matricniproblemi;

import java.util.ArrayList;
import java.util.List;

public class Determinante
{
    public static int[][] listToArray(List<Integer[]> list)
    {
        int[][] result = new int[list.size()][list.get(0).length];
        int rb = 0;
        for(Integer[] array : list)
        {
            for(int j=0; j<array.length; j++)
                result[rb][j] = array[j];
            rb++;
        }
        return result;
    }
    
    public static int[][] izbaci(int[][] mat,int x, int y)
    {
        List<Integer[]> result = new ArrayList<>();
        for(int i=0; i<mat.length; i++){
            if(i==y)
                continue;
            List<Integer> red = new ArrayList<>();
            for(int j=0; j<mat[i].length; j++){
                if(j!=x)
                    red.add(mat[i][j]);
            }
            result.add(red.toArray( new Integer[mat.length-1] ));
        }
        return listToArray(result);
    }
    
    public static double determinanta(int[][] mat)
    {
        if(mat.length==1)
            return mat[0][0];
        double sum = 0;
        int znak = 1;
        for(int i=0; i<mat[0].length; i++)
        {
            sum += znak * determinanta( izbaci(mat, i, 0) );
            znak = -znak;
        }
        return sum;
    }
    
    public static void main(String[] args){
        int[][] mat = new int[][]{
            {1,2,3},
            {4,5,6},
            {7,8,9}
        };
//        mat = izbaci(mat,2,3);
        
//        for(int i=0; i<mat.length; i++){
//            for(int j=0; j<mat[i].length; j++)
//                System.out.print(mat[i][j]+" ");
//            System.out.println();
//        }
        System.out.println(determinanta(mat));
    }
}
