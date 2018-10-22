package grafovi;

import javafx.beans.property.IntegerProperty;

import java.util.*;

public final class GarphUtility {

    private static void addConnectionToNode(int i, int j, ValueGraphNode<Coordinate> node, int[][] matrix,
                                             Map<Coordinate, ValueGraphNode<Coordinate>> map){
        if(i<0 || i>=matrix.length || j<0 || j>=matrix[0].length || matrix[i][j] == 0)
            return;

        var cor = new Coordinate(i, j);
        var node2 = map.get(cor);
        if(node2==null){
            node2 = new ValueGraphNode<>(cor);
            map.put(cor, node2);
        }
        node.addConnection(node2);
    }
    private static void addConnectionToNode(int i, int j, ValueGraphNode<Coordinate> node, IntegerProperty[][] matrix,
                                            Map<Coordinate, ValueGraphNode<Coordinate>> map){
        if(i<0 || i>=matrix.length || j<0 || j>=matrix[0].length || matrix[i][j].get() == 0)
            return;

        var cor = new Coordinate(i, j);
        var node2 = map.get(cor);
        if(node2==null){
            node2 = new ValueGraphNode<>(cor);
            map.put(cor, node2);
        }
        node.addConnection(node2);
    }
    public static List<ValueGraphNode<Coordinate>> parseMatrix(int[][] matrix){
        var graph = new ArrayList<ValueGraphNode<Coordinate>>();
        var map = new HashMap<Coordinate, ValueGraphNode<Coordinate>>();

        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[i].length; j++){
                if(matrix[i][j]==0)
                    continue;

                var cor = new Coordinate(i, j);

                var node = map.get(cor);
                if(node==null){
                    node = new ValueGraphNode<>(cor);
                    map.put(cor, node);
                }
                graph.add(node);

                addConnectionToNode(i-1, j, node, matrix, map);
                addConnectionToNode(i+1, j, node, matrix, map);
                addConnectionToNode(i, j-1, node, matrix, map);
                addConnectionToNode(i, j+1, node, matrix, map);
            }
        }
        return graph;
    }
    public static List<ValueGraphNode<Coordinate>> parseMatrix(IntegerProperty [][] matrix){
        var graph = new ArrayList<ValueGraphNode<Coordinate>>();
        var map = new HashMap<Coordinate, ValueGraphNode<Coordinate>>();

        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[i].length; j++){
                if(matrix[i][j].get()==0)
                    continue;

                var cor = new Coordinate(i, j);

                var node = map.get(cor);
                if(node==null){
                    node = new ValueGraphNode<>(cor);
                    map.put(cor, node);
                }
                graph.add(node);

                addConnectionToNode(i-1, j, node, matrix, map);
                addConnectionToNode(i+1, j, node, matrix, map);
                addConnectionToNode(i, j-1, node, matrix, map);
                addConnectionToNode(i, j+1, node, matrix, map);
            }
        }
        return graph;
    }

    public static void main(String[] args) {
        var matrix = new int[][]{
                {1,1,1,1},
                {1,0,0,0,},
                {1,0,1,0},
                {1,1,1,1}
        };
        var graph = parseMatrix(matrix);
        graph.forEach( System.out::println );
    }
}

class Coordinate{
    private final int i, j;

    public Coordinate(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return i == that.i && j == that.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

    @Override
    public String toString(){
        return String.format("(%d, %d)", i, j);
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

}