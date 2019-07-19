package grafovi_zadaci;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class PrimovAlgoritam extends WeightedGraph {

    class Connection implements Comparable<Connection> {
        private int node1;
        private int node2;

        public Connection(int node1, int node2) {
            this.node1 = node1;
            this.node2 = node2;
        }

        public int getNode1() {
            return node1;
        }

        public int getNode2() {
            return node2;
        }

        public char getNode1Name(){
            return getNodeName(node1);
        }

        public char getNode2name(){
            return getNodeName(node2);
        }

        public int getConnectionDistance(){
            return getDistance(node1, node2);
        }

        @Override
        public String toString(){
            return String.format("%-3s %-3s %s", getNode1Name(), getNode2name(), getConnectionDistance());
        }

        @Override
        public int compareTo(Connection o) {
            return Integer.compare(getConnectionDistance(), o.getConnectionDistance());
        }
    }

    public List<Connection> minimalLinkedTree(){
        var rez = new ArrayList<Connection>();
        prims(0, rez);
        return rez;
    }

    private void prims(int nodePos, List<Connection> rez){
        var visitedNodes = 1;

        var queue = new PriorityQueue<Connection>();

        var tek = nodePos;

        while( visitedNodes<getNodeNumber() ){
            if(getNodeState(tek)==NOTACTIVE) {
                setNodeState(tek, ACTIVE);
                for (Integer neighbor : getNodeNeighbors(tek)) {
                    if (getNodeState(neighbor) == NOTACTIVE) {
                        queue.add(new Connection(tek, neighbor));
                    }
                }
            }

            var next = queue.poll();
            rez.add(next);
            visitedNodes++;
            tek = next.getNode2();
        }
    }

    public static void main(String[] args) {
        var graph = new PrimovAlgoritam();
        graph.defaultGraph4();
        System.out.println(graph);

        var minimalTreee = graph.minimalLinkedTree();
        minimalTreee.forEach(System.out::println);


    }

}
