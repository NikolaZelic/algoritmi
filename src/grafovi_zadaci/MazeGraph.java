package grafovi_zadaci;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MazeGraph extends Maze {

    public static int NOTACTIVE = 0, ACTIVE = 1, FINISHED = 2;

    private int[][] nodeStates;
    private Coordinate[][] previousNode;

    public MazeGraph(Maze maze){
        super(maze);
        nodeStates = new int[maze.getTopCount()][maze.getLeftCount()];
        previousNode = new Coordinate[maze.getTopCount()][maze.getLeftCount()];
    }

    public Stream<Coordinate> nodesStream(){
        Iterator<Coordinate> sourceIterator = iterator();
        Iterable<Coordinate> iterable = () -> sourceIterator;
        Stream<Coordinate> targetStream = StreamSupport.stream(iterable.spliterator(), false);
        return targetStream.filter(e->e.getValue()==' ');
    }

    protected boolean isCoordinateNode(int top, int left){
        checkCoordinate(top, left);
        return matrix[top][left]!=WALL;
    }

    protected void checkIsCoordinateNode(int top, int left){
        if(!isCoordinateNode(top, left)){
            throw new IllegalArgumentException("Coordinate ("+top+", "+left+") isn't node");
        }
    }

    public List<Coordinate> getNodeNeighbors(int top, int left){
        checkCoordinate(top, left);
        checkIsCoordinateNode(top, left);

        var rez = new ArrayList<Coordinate>();

        if( coordinateExist(top, left+1) && isCoordinateNode(top, left+1) )
            rez.add(getCell(top, left+1));
        if( coordinateExist(top, left-1) && isCoordinateNode(top, left-1) )
            rez.add(getCell(top, left-1));
        if( coordinateExist(top+1, left) && isCoordinateNode(top+1, left) )
            rez.add(getCell(top+1, left));
        if( coordinateExist(top-1, left) && isCoordinateNode(top-1, left) )
            rez.add(getCell(top-1, left));

        return rez;
    }

    public List<Coordinate> getNodeNeighbors(Coordinate node){
        return getNodeNeighbors(node.getTop(), node.getLeft());
    }

    protected void setNodeState(Coordinate node, int value){
        nodeStates[node.getTop()][node.getLeft()] = value;
    }

    protected int getNodeState(Coordinate node){
        return nodeStates[node.getTop()][node.getLeft()];
    }

    protected void setPreviousNode(Coordinate node, Coordinate previoues){
        previousNode[node.getTop()][node.getLeft()] = previoues;
    }

    protected Coordinate getPreviouesNode(Coordinate node){
        return previousNode[node.getTop()][node.getLeft()];
    }

    protected List<Coordinate> getNodePath(Coordinate node){
        var previoues = getPreviouesNode(node);
        var rez = new LinkedList<Coordinate>();
        while(previoues!=null){
            rez.addFirst(previoues);
            previoues = getPreviouesNode(previoues);
        }
        return rez;
    }

    public List<Coordinate> shortestPath(int topFrom, int leftFrom, int topTo, int leftTo){
        checkIsCoordinateNode(topFrom , leftFrom);
        checkIsCoordinateNode(topTo, leftTo);

        var currNode = getCell(topFrom, leftFrom);
        setPreviousNode(currNode, null);

        var queue = new LinkedList<Coordinate>();
        queue.add(currNode);

        while( !queue.isEmpty() ){
            currNode = queue.poll();
            setNodeState(currNode, ACTIVE);

            for (Coordinate neighbor : getNodeNeighbors(currNode)) {
                if (getNodeState(neighbor) != NOTACTIVE)
                    continue;
                setNodeState(neighbor, ACTIVE);
                setPreviousNode(neighbor, currNode);
                queue.add(neighbor);
            }

            setNodeState(currNode, FINISHED);
        }

        var toNode = getCell(topTo, leftTo);
        if( getNodeState(toNode)==NOTACTIVE ){
            return Collections.emptyList();
        }

        return getNodePath(toNode);
    }

    public static void main(String[] args) {
        var graph = new MazeGraph( Maze.defaultMaze1() );
        System.out.println(graph);
        var shortestPath = graph.shortestPath(7, 0, 7, 16);
        System.out.println("\nNumber of steps: " + shortestPath.size() );
        System.out.println("---------(top, left)------------------");
        for(var i=0; i<shortestPath.size(); i++) {
            System.out.printf( "%-3d: %s\n", i+1, shortestPath.get(i).toString() );
        }
    }

}
