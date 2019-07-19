package grafovi_zadaci;

import java.util.*;

public class AStartGraphSearch extends WeightedGraph {

    class Coordinate {
        private int currNodePos;
        private int top;
        private int left;

        public Coordinate(int currNodePos, int top, int left) {
            this.currNodePos = currNodePos;
            this.top = top;
            this.left = left;
        }

        public Coordinate(char currNode, int top, int left) {
            this.currNodePos = getNodePosition(currNode);
            this.top = top;
            this.left = left;
        }

        public int getTop() {
            return top;
        }

        public int getLeft() {
            return left;
        }

        public int getCurrNodePos() {
            return currNodePos;
        }

        @Override
        public String toString() {
            return getNodeName(currNodePos) + " ("+top+", "+left+")";
        }
    }

    class ResultRow {
        private int currNodePos;
        private double heuristicDistance;
        private Integer distanceFromSatrt, estimation, previousNodePos;

        public ResultRow(int currNodePos, double heuristicDistance, Integer distanceFromSatrt, Integer estimation, Integer previousNodePos) {
            this.currNodePos = currNodePos;
            this.heuristicDistance = heuristicDistance;
            this.distanceFromSatrt = distanceFromSatrt;
            this.estimation = estimation;
            this.previousNodePos = previousNodePos;
        }

        public int getCurrNodePos() {
            return currNodePos;
        }

        public Integer getDistanceFromSatrt() {
            return distanceFromSatrt;
        }

        public double getHeuristicDistance() {
            return heuristicDistance;
        }

        public Integer getEstimation() {
            return estimation;
        }

        public Integer getPreviousNodePos() {
            return previousNodePos;
        }

        public void setDistanceFromSatrt(Integer distanceFromSatrt) {
            this.distanceFromSatrt = distanceFromSatrt;
        }

        public void setEstimation(Integer estimation) {
            this.estimation = estimation;
        }

        public void setPreviousNodePos(Integer previousNodePos) {
            this.previousNodePos = previousNodePos;
        }

        @Override
        public String toString(){
            return String.format("%-4s %-10d %-9.2f %-10s %s", getNodeName(currNodePos), getDistanceFromSatrt(), getHeuristicDistance(), getEstimation(),
                    previousNodePos==null?null:getNodeName(previousNodePos));
        }
    }

    private double calculateHeuristicDistance(int fromPos, int toPos){
        var cor1 = getNodeCoordiante(fromPos);
        var cor2 = getNodeCoordiante(toPos);
        return Math.sqrt( Math.pow(cor1.getTop()-cor2.getTop(), 2) + Math.pow(cor1.getLeft()-cor2.getLeft(), 2) );
    }

    private List<ResultRow> startResultSet(int startNodePos, int endNodePos){
        var rez = new ArrayList<ResultRow>(getNodeNumber());
        for(var i=0; i<getNodeNumber(); i++){
            rez.add( new ResultRow(i, calculateHeuristicDistance(endNodePos, i),  i==startNodePos? 0:null, null, null) );
        }
        return rez;
    }

    private ResultRow getResultRow(List<ResultRow> resultSet, int nodePos){
        for(var r : resultSet){
            if(r.getCurrNodePos()==nodePos)
                return r;
        }
        throw new IllegalArgumentException("There is no node is result set " + nodePos);
    }

    private int lowestEstimationNode(List<ResultRow> resultSet){
        var lowestEstimation = Integer.MAX_VALUE;
        Integer lowesNodePos = null;
        for (ResultRow r : resultSet) {
            if(getNodeState(r.getCurrNodePos())!=OPEN)
                continue;
            if (r.getEstimation() < lowestEstimation) {
                lowestEstimation = r.getEstimation();
                lowesNodePos = r.getCurrNodePos();
            }
        }
        if(lowesNodePos==null){
            resultSet.forEach(System.out::println);
            throw new IllegalArgumentException("result set doesn't have lowest node");
        }
        return lowesNodePos;
    }

    public static final int NOTACTIVE = 0, OPEN = 1, CLOSED = 2;

    public List<ResultRow> resultSet(char from, char to) {
        for (var i = 0; i < getNodeNumber(); i++)
            setNodeState(i, NOTACTIVE);

        var fromPos = getNodePosition(from);
        var toPos = getNodePosition(to);

        var resultSet = startResultSet(fromPos, toPos);

        var currNode = fromPos;
        var currRow = getResultRow(resultSet, currNode);
        currRow.setEstimation((int) currRow.getHeuristicDistance());

        while (currNode != toPos) {
            System.out.println(getNodeName(currNode));
            currRow = getResultRow(resultSet, currNode);
            for (var neighbor : getNodeNeighbors(currNode)) {
                if (getNodeState(neighbor) == CLOSED)
                    continue;
                setNodeState(neighbor, OPEN);
                var distanceFromStart = currRow.getDistanceFromSatrt() + getDistance(currNode, neighbor);
                var neighborRow = getResultRow(resultSet, neighbor);
                var estimation = distanceFromStart + neighborRow.getHeuristicDistance();
                if (neighborRow.getEstimation() == null || estimation < neighborRow.getEstimation()) {
                    neighborRow.setEstimation((int) estimation);
                    neighborRow.setPreviousNodePos(currNode);
                    neighborRow.setDistanceFromSatrt(distanceFromStart);
                }

            }
            setNodeState(currNode, CLOSED);
            currNode = lowestEstimationNode(resultSet);
            setNodeState(currNode, NOTACTIVE);
        }
        return resultSet;
    }

    private List<Coordinate> nodesCoordinate;

    public AStartGraphSearch(){
        super();
        nodesCoordinate = new ArrayList<>();
    }

    @Override
    public void addNode(char nodeName) {
        super.addNode(nodeName);
        nodesCoordinate.add(null);
    }

    public void setNodeCoordinate(int nodePos, Coordinate coordinate){
        checkNodePosition(nodePos);
        nodesCoordinate.set(nodePos, coordinate);
    }

    public void setNodeCoordinate(char node, Coordinate coordinate){
        setNodeCoordinate(getNodePosition(node), coordinate);
    }

    public void setNodeCoordinate(char node, int top, int left){
        setNodeCoordinate(node, new Coordinate(node, top, left));
    }

    public void defaultCoordinates3(){
        setNodeCoordinate('a', 8, 0 );
        setNodeCoordinate('b', 4, 2 );
        setNodeCoordinate('d', 3, 4 );
        setNodeCoordinate('c', 8, 3 );

        setNodeCoordinate('m', 1, 13 );
        setNodeCoordinate('l', 4, 13 );
        setNodeCoordinate('o', 3, 16 );
        setNodeCoordinate('k', 7, 13 );

        setNodeCoordinate('p', 7, 18 );
        setNodeCoordinate('j', 12, 13 );
        setNodeCoordinate('n', 13, 15 );

        setNodeCoordinate('h', 10, 7 );
        setNodeCoordinate('i', 11, 9 );
        setNodeCoordinate('e', 12, 4 );

        setNodeCoordinate('f', 12, 1 );
        setNodeCoordinate('g', 14, 6 );
    }

    public Coordinate getNodeCoordiante(int nodePos){
        checkNodePosition(nodePos);
        return nodesCoordinate.get(nodePos);
    }

    @Override
    public String toString() {
        var bld = new Formatter();
        var lineFormat = "%-17s %s\n";
        bld.format(lineFormat, "Name (coordinate)", "Neighbors");
        for(var i=0; i<getNodeNumber(); i++){
            bld.format(lineFormat, getNodeCoordiante(i),  getNodeNeighborsDistance(i) );
        }
        return bld.toString();
    }


    public static void main(String[] args) {
        var graph = new AStartGraphSearch();
        graph.defaultGraph3();
        graph.defaultCoordinates3();
        System.out.println("---------- GRAPH ---------------");
        System.out.println(graph);

        var from = 'n';
        var to = 'e';

        var resultSet = graph.resultSet(from , to);
        System.out.println("Search from "+from +" to "+to );
        System.out.printf("%-4s %-10s %-9s %-10s %s\n", "Node", "From start", "Heuristic", "Estimation", "Previous");
        resultSet.forEach( System.out::println );
    }
}
