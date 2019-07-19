package grafovi_zadaci;

public class TestAciklicnosti extends DirectedGraph {

    public boolean imaCiklus(){
        refresh();
        for(var i=0; i<getNodeNumber(); i++){
            if(getNodeState(i)==NOTACTIVE) {
                if(dfs(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(int nodePos){
        setNodeState(nodePos, ACTIVE);

        for (Integer neighbor : getNodeNeighbors(nodePos)) {
            var state = getNodeState(neighbor);
            if( state==ACTIVE ){
                return true;
            }
            if( state==NOTACTIVE ){
                if(dfs(neighbor)){
                    return true;
                }
            }
        }

        setNodeState(nodePos, FINISHED);
        return false;
    }

    public static void main(String[] args) {
        var graph = new TestAciklicnosti();
        graph.addNodes('a', 'b', 'c');
        graph.addConnection('a', 'b', 'c');
        graph.addConnection('b', 'c');
        graph.addConnection('c', 'a');

        var ciklus = graph.imaCiklus();
        System.out.println(graph.writeGraphSearch());
        System.out.println("Graf ima ciklus: " + ciklus);
    }

}
