package javafx;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.StringTokenizer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import static java.nio.file.StandardOpenOption.*;

public class Util
{
    public static IntegerProperty[][] parseFileToMatrix(File file) throws IOException{
        if(file==null || !file.exists())
            return null;
        List<String> lines = Files.readAllLines(file.toPath());
        if(lines==null || lines.isEmpty())
            return null;
        
        int rowsNum = lines.size();
        int colNum = new StringTokenizer(lines.get(0), ",").countTokens();
        IntegerProperty[][] result = new IntegerProperty[rowsNum][colNum];
        
        int i=0;
        for(String line : lines){
            StringTokenizer tokenzier = new StringTokenizer(line, ",");
            int a = tokenzier.countTokens();
            if(a>colNum)
                throw new IllegalArgumentException("File doesn't have a proper format. Line "+ i+" has more numbers then it should.");
                
            int j=0;
            while(tokenzier.hasMoreTokens()){
                String token = tokenzier.nextToken();
                result[i][j] = new SimpleIntegerProperty(Integer.valueOf(token));
                j++;
            }
            i++;
        }
        
        return result;
    }

    public static void writeMatrix(IntegerProperty[][] matrix, File file) throws IOException{
        if(matrix==null || file==null || matrix.length==0 || matrix[0].length == 0)
            throw new IllegalArgumentException("Invalid input parameters");
        
        StringBuilder bld = new StringBuilder();
        
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[i].length; j++){
                bld.append(matrix[i][j].getValue());
                if(j<matrix[i].length-1)
                    bld.append(",");
            }
            bld.append("\n");
        }
        
        Files.write(file.toPath(), bld.toString().getBytes("UTF-8"), CREATE, TRUNCATE_EXISTING);
    }
    
    public static void writeMatrix(IntegerProperty[][] matrix){
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[i].length; j++)
                System.out.print(matrix[i][j].getValue()+" ");
            System.out.println();
        }
    }
}
