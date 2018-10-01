package matricniproblemi;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import matrice.library.MatrixAction;
import matrice.library.Playground;

public class SkupljanjeNovcica extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        Map<Integer, String> valuMap = new HashMap<>();
        Map<Integer, String> valueLegend = new HashMap<>();
        
        valuMap.put(0, "gray");
        valueLegend.put(0, "Space");
        valuMap.put(1, "gold");
        valueLegend.put(1, "Coin");
        valuMap.put(2, "green");
        valueLegend.put(2, "Move");
        valuMap.put(3, "darkgreen");
        valueLegend.put(3, "Move over coin");
        
        Playground p = new Playground(valuMap, valueLegend, new Action());
        p.openFile(new File("C:\\Users\\Grupa1\\Desktop\\coins2.txt"));
        
        StackPane root = new StackPane(p);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Skupljanje novcica");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
//        Direction dir = new Direction();
//        do{
//            System.out.println(dir.getDi()+" "+dir.getDj());
//        }
//        while( dir.next() );
    }
    
}

class Action implements MatrixAction
{
    private List<Coordinate> getCoinsCoordinates(IntegerProperty[][] matrix){
        List<Coordinate> result = new ArrayList<>();
        for(int i=0; i<matrix.length; i++)
            for(int j=0; j<matrix[i].length; j++)
                if(matrix[i][j].get()==1)
                    result.add(new Coordinate(i, j));
        return result;
    }
    
    private void reportDirection(int direction){
        switch(direction){
            case 0: System.out.println("UP"); break;
            case 1: System.out.println("RIGHT"); break;
            case 2: System.out.println("DOWN"); break;
            case 3: System.out.println("LEFT"); break;
        }
    }
    
    private boolean move(Coordinate from, Coordinate to, IntegerProperty[][] matrix, List<Coordinate> coinsCoordinates){
        System.out.println(from.getI()+","+from.getJ()+" -> "+to.getI()+","+to.getJ());
        if(from.getI()==4&&from.getJ()==4&&to.getI()==6&&to.getJ()==2){
            System.out.println("Tu sam");
        }
        if(from.equals(to)){
            matrix[from.getI()][from.getJ()].set(3);
            return true;
        }
        // NATURE OF MOVE
        boolean moveVerticly = !from.equealI(to);
        boolean moveUp = from.biggerI(to);
        boolean moveHorizontaly = !from.equealJ(to);
        boolean moveRight = !from.biggerJ(to);
        
        // FIND INITIAL DIRECTION
        Set<Integer> validDirections = Direction.validDirections();
        if(moveVerticly){
            if(moveUp)
                validDirections.remove(2);
            else
                validDirections.remove(0);
        }
        else{
            validDirections.remove(0);
            validDirections.remove(2);
        }
        if(moveHorizontaly){
            if(moveRight)
                validDirections.remove(3);
            else
                validDirections.remove(1);
        }
        else{
            validDirections.remove(3);
            validDirections.remove(1);
        }
        if(validDirections.isEmpty())
            throw new IllegalArgumentException("Faild to find initial direction");
        
        // CREATE DIRECTION
        int initialDirection = (int)validDirections.toArray()[0];
        reportDirection(initialDirection);
        Direction dir = new Direction(initialDirection);
        
        do{
            int i2 = from.getI() + dir.getDi();
            int j2 = from.getJ() + dir.getDj();
            Coordinate step = new Coordinate(i2, j2);
            if( !canMove(step, matrix ))
                continue;
            // Mark move
            if(matrix[i2][j2].get()==1 && (i2!=to.getI() || j2!=to.getJ()) ){ // Coin
                matrix[i2][j2].set(3);
                removeCordinateFromList(coinsCoordinates, new Coordinate(i2, j2));
            }
            else    // Space
                matrix[i2][j2].set(2);
            
            if( move(step, to, matrix, coinsCoordinates) )
                return true;
            else{
                // Delete move
                 if( matrix[i2][j2].get()==3)
                      matrix[i2][j2].setValue(1);
                 else
                      matrix[i2][j2].setValue(0);
            }
        }
        while(dir.next());
        return false;
    }
    
    @Override
    public void action(IntegerProperty[][] matrix, Map<Integer, String> valuMap)
    {
        List<Coordinate> coinsCoordinates = getCoinsCoordinates(matrix);
        if(coinsCoordinates.isEmpty())
            return;
//        coinsCoordinates.removeIf((t) ->{
//            if(t.getI()==4&&t.getJ()==4)
//                return true;
//            return t.getI()>4;
//        });
        for(int i=0; i<coinsCoordinates.size()-1; i++){
            System.out.println("Next coordinate");
            if( !move(coinsCoordinates.get(i), coinsCoordinates.get(i+1), matrix, coinsCoordinates) ){
                System.out.println("Fail");
                return;
            }
        }
    }

    private boolean canMove(Coordinate step, IntegerProperty[][] matrix)
    {
        int i = step.getI(), j = step.getJ();
        if( i<0 || i>=matrix.length || j<0 || j>=matrix[0].length )
            return false;
        if( matrix[i][j].get()==0 || matrix[i][j].get()==1 )
            return true;
        return false;
    }
    
    public static void removeCordinateFromList(List<Coordinate> coinsCoordinates, Coordinate cor){
        Iterator<Coordinate> iterator = coinsCoordinates.iterator();
        while(iterator.hasNext()){
            Coordinate tek = iterator.next();
            if(tek.equals(cor))
                iterator.remove();
        }
    }
}

class Direction 
{
    public static Set<Integer> validDirections(){
        Set<Integer> result = new HashSet<>();
        result.add(0);
        result.add(1);
        result.add(2);
        result.add(3);
        return result;
    }
    
    public static final int[] di = new int[]{-1, 0, 1, 0};
    public static final int[] dj = new int[]{0, 1, 0, -1};
    
    private int currDir;
    private int counter = 0;
    
    public Direction(){
        currDir = 0;
    }
    
    public Direction(int startDirection){
        this.currDir = startDirection;
    }
    
    public int getDi(){
        return di[currDir];
    }
    
    public int getDj(){
        return dj[currDir];
    }
    
    public boolean next(){
        if(counter==3)
            return false;
        currDir++;
        if(currDir>=4)
            currDir = 0;
        counter++;
        return true;
    }
    
    public void reset(){
        currDir = 0;
        counter = 0;
    }
}

class Coordinate{
    private int i,j;

    public Coordinate(int i, int j)
    {
        this.i = i;
        this.j = j;
    }

    public int getI()
    {
        return i;
    }

    public int getJ()
    {
        return j;
    }
    
    public boolean equals(Coordinate oth){
        if(i==oth.getI() && j==oth.getJ())
            return true;
        return false;
    }
    public boolean equealI(Coordinate oth){
        return i==oth.getI();
    }
    public boolean equealJ(Coordinate oth){
        return j==oth.getJ();
    }
    public boolean biggerI(Coordinate oth){
        return i>oth.getI();
    }
    public boolean biggerJ(Coordinate oth){
        return j>oth.getJ();
    }
}





















