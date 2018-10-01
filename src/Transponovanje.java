/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Grupa1
 */
public class Transponovanje
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[][] mat = new int[][]{
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,16}
        };
        
        int[][] mat2 = new int[mat.length][mat[0].length];
        
        for(int i=0; i<mat[0].length; i++)
            mat2[i] = mat[i];
        
        for(int i=1; i<mat.length; i++){
            for(int j=1; j<mat[i].length; j++){
                mat2[i][j] = mat[j][i];
            }
        }
        
        for(int[] i:mat2){
            for(int j:i)
                System.out.printf("%4d", j);
            System.out.println();
        }
    }
    
}
