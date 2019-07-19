package grafovi_zadaci;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DijkstrasShortestPath extends WeightedGraph {

    class ResultRow {
        private int currNodePos;
        private int shortestDistance;
        private Integer previousNodePos;

        public ResultRow(int currNodePos, int shortestDistance, Integer previousNodePos) {
            this.currNodePos = currNodePos;
            this.shortestDistance = shortestDistance;
            this.previousNodePos = previousNodePos;
        }

        public int getCurrNodePos() {
            return currNodePos;
        }

        public char getCurrNodeName(){
            return getNodeName(currNodePos);
        }

        public int getShortestDistance() {
            return shortestDistance;
        }

        public void setShortestDistance(int shortestDistance) {
            this.shortestDistance = shortestDistance;
        }

        public Integer getPreviousNodePos() {
            return previousNodePos;
        }

        public Character getPreciousNodeName(){
            if(previousNodePos==null)
                return null;
            return getNodeName(previousNodePos);
        }

        public void setPreviousNodePos(Integer previousNodePos) {
            this.previousNodePos = previousNodePos;
        }

        @Override
        public String toString() {
            return String.format("%-5s %-8d %-2s", getNodeName(currNodePos), shortestDistance, previousNodePos==null? null:getNodeName(previousNodePos));
        }
    }

    protected List<ResultRow> startRezalt(int startNodePos){
        var rez = new ArrayList<ResultRow>(getNodeNumber());
        for(var i=0; i<getNodeNumber(); i++){
            rez.add( new ResultRow(i, i==startNodePos? 0:Integer.MAX_VALUE, null) );
        }
        return rez;
    }

    protected static ResultRow getResultRow(List<ResultRow> resultSet, int currNodePos){
        for(var r : resultSet){
            if(r.getCurrNodePos()==currNodePos)
                return r;
        }
        throw new IllegalArgumentException("Result set doesn't contains "+ currNodePos);
    }

    protected String getPath(List<ResultRow> resultSet, char node){
        var position = getNodePosition(node);
        var r = getResultRow(resultSet, position);
        var previous = r.getPreviousNodePos();
        var rez = new LinkedList<Character>();
        while(previous!=null){
            rez.addFirst(getNodeName(previous));
            previous = getResultRow(resultSet, previous).getPreviousNodePos();
        }
        rez.add(node);
        var bld = new StringBuilder();
        rez.forEach( e-> bld.append(e).append(" "));
        return bld.toString();
    }

    public static final int UNVISITED = 0, VISITED = 1;

    public List<ResultRow> getShortestPathsFrom(char fromNode){
        for(var i=0; i<getNodeNumber(); i++)
            setNodeState(i, UNVISITED);

        var fromNodePos = getNodePosition(fromNode);
        var resultSet = startRezalt(fromNodePos);

        var queue = new LinkedList<Integer>();
        queue.add(fromNodePos);

        while( !queue.isEmpty() ){
            var currNode = queue.poll();
            var curResultRow = getResultRow(resultSet, currNode);
            var currDistance = curResultRow.getShortestDistance();
            setNodeState(currNode, VISITED);

            getNodeNeighbors(currNode).forEach( neighbor -> {
                var distance = currDistance + getDistance(currNode, neighbor);
                var neighborResultRow = getResultRow(resultSet, neighbor);
                if(neighborResultRow.getShortestDistance()>distance){
                    neighborResultRow.setShortestDistance(distance);
                    neighborResultRow.setPreviousNodePos(currNode);
                }
                if(getNodeState(neighbor)!=VISITED){
                    queue.add(neighbor);
                }
            });
        }

        return resultSet;
    }

    public static void main(String[] args) {
        var graph = new DijkstrasShortestPath();
        graph.defaultGraph3();
        System.out.println(graph);

        var node = 'n';
        System.out.println("DISTANCE FROM " + node);
        System.out.printf("%-5s %-8s %-2s\n", "Node", "Distance", "Previous");
        var rezultSet = graph.getShortestPathsFrom(node);
        rezultSet.forEach( System.out::println );

        var node2 = 'e';
        System.out.println("Path from " + node + " to " + node2);
        System.out.println( graph.getPath(rezultSet, node2) );
    }
}
