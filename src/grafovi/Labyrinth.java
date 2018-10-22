package grafovi;

import java.io.File;
import java.util.*;

import javafx.MatrixAction;
import javafx.Playground;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.Util;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Labyrinth extends Application {
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


        Playground p = new Playground(valueMap, valuelegend, new SimpleSolve(1,2));
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

class SimpleSolve implements MatrixAction {
    private int move;
    private int passage;
    public SimpleSolve(int move, int passage){
        this.move = move;
        this.passage = passage;
    }

    @Override
    public void action(IntegerProperty[][] matrix, Map<Integer, String> valuMap) {
        var graph = GarphUtility.parseMatrix(matrix);
        Coordinate firstCor = null, lastCor = null;
        for(var i=0; i<matrix.length; i++){
            for(var j=0; j<matrix[i].length; j++){
                switch (matrix[i][j].get()){
                    case 3: firstCor = new Coordinate(i, j); break;
                    case 4: lastCor = new Coordinate(i, j); break;
                }
            }
        }
        ValueGraphNode<Coordinate> first = null, last = null;
        for(var node : graph){
            if(node.getValue().equals(firstCor))
                first = node;
            if(node.getValue().equals(lastCor))
                last = node;
        }

//        var result = new ArrayList<ValueGraphNode<Coordinate>>();
//        System.out.println( solve(first, last, matrix, result) );

//        result.forEach(System.out::println);
//        for(int k=1; k<result.size()-1; k++){
//            var node = result.get(k);
//            var cor = node.getValue();
//            var i = cor.getI();
//            var j = cor.getJ();
//            matrix[i][j].setValue(2);
//        }

        var allResults = new ArrayList<List<ValueGraphNode<Coordinate>>>();
        solve2(first, last, matrix, allResults, new ArrayList<ValueGraphNode<Coordinate>>());
        System.out.println("Success!!!");
        System.out.println("Number of results: " + allResults.size());

        List<ValueGraphNode<Coordinate>> pickedResult = allResults.get(0);
        for(var el:allResults)
            if(el.size()<pickedResult.size())
                pickedResult = el;

        pickedResult.forEach(System.out::println);
        for(int k=1; k<pickedResult.size()-1; k++){
            var node = pickedResult.get(k);
            var cor = node.getValue();
            var i = cor.getI();
            var j = cor.getJ();
            matrix[i][j].setValue(2);
        }
    }

    private boolean solve(ValueGraphNode<Coordinate> first, ValueGraphNode<Coordinate> last, IntegerProperty[][] matrx, @NotNull List<ValueGraphNode<Coordinate>> result){
        if(result.size()==0)
            result.add(first);

        var node = result.get(result.size()-1);
        node.visit();

        if(node.equals(last))
            return true;

        for(var connection : node){
            if(connection.isVisited())
                continue;
            result.add(connection);

            if( solve(first, last, matrx, result) )
                return true;

//            connection.unvisit();
            result.remove(result.size()-1);
        }
        return false;
    }

    private void solve2(ValueGraphNode<Coordinate> first, ValueGraphNode<Coordinate> last, IntegerProperty[][] matrx,
                           List<List<ValueGraphNode<Coordinate>>> allResults, List<ValueGraphNode<Coordinate>> result){
        if(result.size()==0)
            result.add(first);

        var node = result.get(result.size()-1);
//        node.visit(id);

        if(node.equals(last)) {
            allResults.add(result);
            return;
        }
        System.out.println(node.getValue());
        for(var connection : node){
            var copy = Util.copyList(result);
            copy.add(connection);

            if( Util.containsDuplicates(copy) ){
                continue;
            }
            solve2(first, last, matrx, allResults, copy);
        }
    }

}


