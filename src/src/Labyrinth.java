package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;

public class Labyrinth
{
    public static final int PASSAGE = 0, WALL = 1, DOORWAY = 2, EXIT = 3, PATH = 4;
    
    private int[][] m;
    
    public Labyrinth(String text)
    {
        String[] lines = text.split("\n");
        m = new int[lines.length][lines[0].length()];
        
        for(int i=0; i<lines.length; i++ )
        {
            for(int j=0; j<lines[i].length(); j++)
                m[i][j] = Integer.valueOf( lines[i].substring(j, j+1) );
        }
    }
    
    @Override
    public String toString()
    {
        StringBuilder bld = new StringBuilder();
        for(int i=0; i<m.length; i++)
        {
            for(int j=0; j<m[i].length; j++)
            {
                switch( m[i][j] )
                {
                    case PASSAGE: bld.append(" "); break;
                    case WALL: bld.append("*"); break;
                    case DOORWAY: bld.append("D"); break;
                    case EXIT: bld.append("E"); break;
                    case PATH: bld.append("^"); break;
                }
            }
            bld.append("\n");
        }
        return bld.toString();
    }
    
    public void write()
    {
        for(int i=0; i<m.length; i++)
        {
            for(int j=0; j<m[i].length; j++)
                System.out.print(m[i][j]);
            System.out.println();
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException 
    {
        String text = new Scanner(new File("C:\\Users\\Grupa1\\Documents\\NetBeansProjects\\Algoritmi\\src\\lavirint1.txt")).useDelimiter("\\A").next();
        Labyrinth l = new Labyrinth(text);
        System.out.println(l);
    }
    
}
