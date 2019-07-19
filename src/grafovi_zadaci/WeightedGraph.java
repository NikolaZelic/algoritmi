package grafovi_zadaci;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class WeightedGraph extends Graph {

    private List<List<Integer>> nodeNeighborsDistance;

    public WeightedGraph(){
        super();
        nodeNeighborsDistance = new ArrayList<>();
    }

    public int getDistance(int nodePos, int neighborPos){
        checkNodePosition(nodePos);
        checkNodePosition(neighborPos);
        var neighbors = getNodeNeighbors(nodePos);
        var distances = nodeNeighborsDistance.get(nodePos);
        for(var i=0; i<neighbors.size(); i++){
            if(neighbors.get(i)==neighborPos){
                return distances.get(i);
            }
        }
        throw new IllegalArgumentException("There is no connetion between " + getNodeName(nodePos) + " and " + getNodeName(neighborPos));
    }

    public int getDistance(char node, char neighbor){
        return getDistance(getNodePosition(node), getNodePosition(neighbor));
    }

    public List<NodeNeighbor> getNodeNeighborsDistance(int nodePos){
        checkNodePosition(nodePos);
        var neighbors = getNodeNeighbors(nodePos);
        var rez = new ArrayList<NodeNeighbor>();
        neighbors.forEach( neighbor -> rez.add(new NodeNeighbor(nodePos, neighbor, getDistance(nodePos, neighbor))) );
        return rez;
    }

    @Override
    public void clean() {
        super.clean();
        nodeNeighborsDistance = new ArrayList<>();
    }

    @Override
    public void refresh() {
        super.refresh();
        for(var i=0; i<getNodeNumber(); i++){
            nodeNeighborsDistance = new ArrayList<>();
        }
    }

    @Override
    public void addNode(char nodeName) {
        super.addNode(nodeName);
        nodeNeighborsDistance.add(new ArrayList<>());
    }

    @Override
    @Deprecated
    public void addConnection(char node1, char node2) {
        throw new UnsupportedOperationException("Weight graph has to have distance between nodes");
    }

    public void addConnection(char node1, char node2, int distance){
        var pos1 = getNodePosition(node1);
        var pos2 = getNodePosition(node2);
        super.addConnection(node1, node2);
        nodeNeighborsDistance.get(pos1).add(distance);
        nodeNeighborsDistance.get(pos2).add(distance);
    }

    @Override
    public String toString() {
        var bld = new Formatter();
        var lineFormat = "%-4s %s\n";
        bld.format(lineFormat, "Name", "Neighbors");
        for(var i=0; i<getNodeNumber(); i++){
            bld.format(lineFormat, getNodeName(i),  getNodeNeighborsDistance(i) );
        }
        return bld.toString();
    }

    @Override
    protected void defaultGraph1() {
        addNodes('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k');
        addConnection('a', 'b', 2);
        addConnection('a', 'c', 3);
        addConnection('a', 'd', 1);
        addConnection('c', 'e', 4);
        addConnection('c', 'f', 6);
        addConnection('d', 'f', 3);
        addConnection('e', 'h', 9);
        addConnection('f', 'g', 7);
        addConnection('f', 'j', 2);
        addConnection('f', 'i', 8);
        addConnection('g', 'j', 3);
        addConnection('h', 'i', 6);
        addConnection('h', 'k', 4);
        addConnection('i', 'k', 3);
    }

    class NodeNeighbor {
        private int startNode;
        private int distanceNode;
        private int distance;

        public NodeNeighbor(int startNode, int distanceNode, int distance) {
            this.startNode = startNode;
            this.distanceNode = distanceNode;
            this.distance = distance;
        }

        public int getStartNode() {
            return startNode;
        }

        public int getDistanceNode() {
            return distanceNode;
        }

        public int getDistance() {
            return distance;
        }

        public char getStartNodeName(){
            return getNodeName(startNode);
        }

        public char getDistanceNodeName(){
            return getNodeName(distanceNode);
        }

        @Override
        public String toString() {
            return String.format("%-1s-%-1s:%-1d", getStartNodeName(), getDistanceNodeName(), getDistance());
        }
    }

    public void defaultGraph2(){
        addNodes('a', 'b', 'c', 'd', 'e');
        addConnection('a', 'b', 6);
        addConnection('a', 'd', 1);
        addConnection('d', 'b', 2);
        addConnection('d', 'e', 1);
        addConnection('b', 'e', 2);
        addConnection('b', 'c', 5);
        addConnection('e', 'c', 5);
    }

    public void defaultGraph3(){
        addNodes('a', 'b', 'c', 'd', 'm', 'l', 'o', 'k', 'p', 'j', 'n', 'f', 'e', 'g', 'h', 'i');
        addConnection('a', 'b', 5);
        addConnection('a', 'c', 5);
        addConnection('b', 'c', 4);
        addConnection('b', 'd', 3);
        addConnection('d', 'c', 7);
        addConnection('d', 'l', 13);
        addConnection('d', 'm', 14);
        addConnection('d', 'k', 16);
        addConnection('d', 'h', 11);
        addConnection('m', 'l', 9);
        addConnection('m', 'o', 5);
        addConnection('l', 'o', 4);
        addConnection('l', 'k', 5);
        addConnection('k', 'p', 4);
        addConnection('k', 'n', 7);
        addConnection('p', 'j', 9);
        addConnection('p', 'n', 7);
        addConnection('j', 'i', 4);
        addConnection('i', 'h', 3);
        addConnection('n', 'g', 12);
        addConnection('n', 'j', 3);
        addConnection('h', 'c', 8);
        addConnection('h', 'e', 5);
        addConnection('e', 'c', 7);
        addConnection('e', 'f', 4);
        addConnection('f', 'g', 9);
    }

    public void defaultGraph4(){
        addNodes('a', 'b', 'c', 'f', 'e', 'd');
        addConnection('a', 'f', 20);
        addConnection('a', 'c', 10);
        addConnection('a', 'b', 8);
        addConnection('f', 'c', 2);
        addConnection('b', 'c', 5);
        addConnection('b', 'e', 4);
        addConnection('b', 'd', 3);
        addConnection('c', 'e', 1);
        addConnection('d', 'e', 6);
    }
}
