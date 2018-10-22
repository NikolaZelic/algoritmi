package javafx;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Test extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        Map<Integer, String> valueMap = new HashMap<>();
        Map<Integer, String> valuelegend = new HashMap<>();
        valueMap.put(0, "blue");
        valuelegend.put(0, "zid");
        valueMap.put(1, "gray");
        valuelegend.put(1, "prolaz");
        valueMap.put(2, "green");
        valuelegend.put(2, "move");
        valueMap.put(3, "red");
        valuelegend.put(3, "start");
        valueMap.put(4, "black");
        valuelegend.put(4, "end");
        
        MatrixAction solveLabirinth = new MatrixAction()
        {
            // up, right, down, left
            int[] di = new int[]{-1, 0, 1, 0};
            int[] dj = new int[]{0, 1, 0, -1};
            
            boolean canMove(int i, int j, IntegerProperty[][] matrix){
                int rowNum = matrix.length, colNum = matrix[0].length;
                if(i<0 || i>=rowNum || j<0 || j>=colNum)
                    return false;
                int val = matrix[i][j].getValue();
                if(val==1 || val==4)
                    return true;
                return false;
            }
            
            boolean moveFrom(int i, int j, IntegerProperty[][] matrix){
                for(int pos=0; pos<4; pos++){
                    int i2 = i + di[pos], j2 = j + dj[pos];
                    if( canMove(i2, j2, matrix) ){
                        if(matrix[i2][j2].get()==4)
                            return true;
                        matrix[i2][j2].setValue(2);
//                        System.out.println("napred "+i2+" "+j2 + " potez "+ pos);
                        if( moveFrom(i2, j2, matrix) )
                            return true;
                        else{
//                            System.out.println("nazad "+i2+" "+j2);
                            matrix[i2][j2].setValue(1);
                        }
                           
                    }
                }
                return false;
            }
            
            public void action(IntegerProperty[][] matrix, Map<Integer, String> valuMap)
            {
                int i=0, j=0;
                // Find start point
                big: for(; i<matrix.length; i++){
                    for(; j<matrix[0].length; j++){
                        if(matrix[i][j].getValue()==3)
                            break big;
                    }
                    j=0;
                }
                moveFrom(i, j, matrix);
            }
        };
        
        Playground p = new Playground(valueMap, valuelegend,solveLabirinth);
        File f = new File("C:\\Users\\Grupa1\\Desktop\\test matrica\\lavirint4.txt");
        p.openFile(f);

        Scene scene = new Scene(p, 800, 650);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
    
}
