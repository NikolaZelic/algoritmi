package grafovi_zadaci;


import java.util.*;

public class KruskovAlgoritam extends WeightedGraph {


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

        public char getNode1Name() {
            return getNodeName(node1);
        }

        public char getNode2name() {
            return getNodeName(node2);
        }

        public int getConnectionDistance() {
            return getDistance(node1, node2);
        }

        @Override
        public String toString() {
            return String.format("%-3s %-3s %s", getNode1Name(), getNode2name(), getConnectionDistance());
        }

        @Override
        public int compareTo(Connection o) {
            return Integer.compare(getConnectionDistance(), o.getConnectionDistance());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Connection that = (Connection) o;
            return (node1 == that.node1 && node2 == that.node2) || (node2 == that.node1 && node1 == that.node2);
        }

        @Override
        public int hashCode() {
            return Objects.hash(node1 + node2);
        }
    }

    public List<Connection> minimalSpamingTree() {
        var rez = new ArrayList<Connection>();

        var queue = new PriorityQueue<Connection>();
        var set = new HashSet<Connection>();

        for (var i = 0; i < getNodeNumber(); i++) {
            for (Integer neighbor : getNodeNeighbors(i)) {
                var con = new Connection(i, neighbor);
                if (set.contains(con))
                    continue;
                set.add(con);
                queue.add(con);
            }
        }

        var connectedNodes = 0;
        var nodes = new Integer[getNodeNumber()];
        for (var i = 0; i < nodes.length; i++)
            nodes[i] = i;
        var disjoinSet = new DisjointSet<Integer>(nodes);

        while (connectedNodes < getNodeNumber()) {
            var next = queue.poll();
            if(next==null)
                break;

            var nod1 = next.getNode1();
            var nod2 = next.getNode2();

            if(disjoinSet.connected(nod1, nod2))
                continue;
//
            disjoinSet.unify(nod1, nod2);

            connectedNodes++;
            rez.add(next);
        }

        return rez;
    }

    public static void main(String[] args) {
        var graph = new KruskovAlgoritam();
        graph.defaultGraph4();
        System.out.println(graph);

        System.out.println("MINIMAL SPAMING TREE");
        var newTree = graph.minimalSpamingTree();
        newTree.forEach(System.out::println);
    }
}

