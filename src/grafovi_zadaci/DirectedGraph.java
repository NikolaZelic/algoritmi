package grafovi_zadaci;

public class DirectedGraph extends Graph {

    @Override
    public void addConnection(char node1, char node2) {
        if(node1 == node2){
            throw new IllegalArgumentException("Node "+node1+" can't be connected to itself");
        }
        var pos1 = getNodePosition(node1);
        var pos2 = getNodePosition(node2);
        var neighbors1 = getNodeNeighbors(pos1);
        if(neighbors1.contains(pos2) ){
            throw new IllegalArgumentException(node1 + " is already connected to " + node2);
        }
        neighbors1.add(pos2);
    }

    @Override
    protected void defaultGraph1() {
        addNodes('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k');

        addConnection('a', 'b', 'd');
        addConnection('c', 'a');
        addConnection('d', 'f', 'g');
        addConnection('e', 'b', 'c');
        addConnection('f', 'i', 'j');
        addConnection('g', 'j');
        addConnection('h', 'e', 'i');
        addConnection('i', 'k', 'f');
        addConnection('k', 'h');
    }

    int t = 0;

    private void depthFirstSearch(){
        refresh();
        t = 0;
        for(var i=0; i<getNodeNumber(); i++){
            if(getNodeState(i)==NOTACTIVE){
                dfs(i);
            }
        }
    }

    private void dfs(int nodePos){
        setNodeState(nodePos, ACTIVE);
        setNodeTimeIn(nodePos, t++);
        System.out.println(getNodeName(nodePos));

        getNodeNeighbors(nodePos).forEach( neighbor -> {
            if(getNodeState(neighbor)==NOTACTIVE){
                dfs(neighbor);
            }
        });

        setNodeState(nodePos, FINISHED);
        setNodeTimeOut(nodePos, t++);
    }

    public static void main(String[] args) {
        var graph = new DirectedGraph();
        graph.defaultGraph1();
        graph.addNode('l');
        System.out.println(graph);

        System.out.println("\n--------DEPTH FIRTS SEARCH----\n");
//        graph.depthFirstSearch();
        graph.dfs(0);
        System.out.println("\n----SEARCH RESULT-----\n");
        System.out.println(graph.writeGraphSearch());
    }
}
