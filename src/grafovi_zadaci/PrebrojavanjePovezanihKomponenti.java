package grafovi_zadaci;

public class PrebrojavanjePovezanihKomponenti extends Graph {
    public PrebrojavanjePovezanihKomponenti(){
        super();
    }

    private int componentsCounter = 0;
    public int numberOfComponents(){
        componentsCounter = 0;
        depthFirstSearc();
        return componentsCounter;
    }

    private void depthFirstSearc(){
        for(var i=0; i<getNodeNumber(); i++){
            if(getNodeState(i)==NOTACTIVE){
                componentsCounter++;
                dfs(i);
            }
        }
    }

    private void dfs(int nodePos){
        setNodeState(nodePos, ACTIVE);

        var neighbors = getNodeNeighbors(nodePos);
        neighbors.forEach( neighbor -> {
            if(getNodeState(neighbor)==NOTACTIVE){
                dfs(neighbor);
            }
        });

        setNodeState(nodePos, FINISHED);
    }

    public static void main(String[] args) {
        var graph = new PrebrojavanjePovezanihKomponenti();
        graph.defaultGraph1();
        graph.addNodes('l', 'm');
        graph.addConnection('l', 'm');
        System.out.println(graph);
        System.out.println( graph.numberOfComponents() );
    }
}
