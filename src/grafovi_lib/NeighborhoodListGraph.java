package grafovi_lib;

import java.util.LinkedList;
import java.util.List;

public class NeighborhoodListGraph<T> implements Graph<T> {

    private EditableGraphState<T> graphState = new SimpleGraphState<>();

    protected List<EditableGraphNode<T>> getEditableNodes(){
        return graphState.getEdtableNodes();
    }
    protected int getTime(){
        return graphState.getTime();
    }
    protected void setTime(int time){
        graphState.setTime(time);
    }
    protected void incrementTime(){
        graphState.setTime(getTime()+1);
    }

    public NeighborhoodListGraph(){
        graphState = new SimpleGraphState<>();
    }

    @Override
    public List<GraphNode<T>> getNodes() {
        return graphState.getNodes();
    }

    @Override
    public GraphState<T> getGraphState() {
        return graphState;
    }

    @Override
    public void addNode(T nodeValue) {
        if(GraphUtility.findEditableNode(getEditableNodes(), nodeValue)!=null){
            alreadyExistedNodeException(nodeValue);
        }
        var node = new SimpleGrahpNode<>(nodeValue);
        getEditableNodes().add(node);
    }

    @Override
    public void addNodes(T... nodeValues) {
        for(var node : nodeValues){
            addNode(node);
        }
    }

    @Override
    public void addConnection(T nodeValue1, T nodeValue2) {
        var node1 = GraphUtility.findEditableNodeOrException(getEditableNodes(), nodeValue1);
        var node2 = GraphUtility.findEditableNodeOrException(getEditableNodes(), nodeValue2);
        node1.addConnection(node2);
        node2.addConnection(node1);
    }

    @Override
    public void depthFirstSearch(GraphNodeVisitor<T> nodeVisitor) {
        getEditableNodes().forEach( node -> {
            if(node.getState()==NOTACTIVE){
                node.setPredecessors(null);
                dfs(node, nodeVisitor);
            }
        });
        System.out.println(graphState);
    }

    protected void dfs(EditableGraphNode<T> node, GraphNodeVisitor<T> nodeVisitor){
        node.setState(ACTIVE);
        node.setTimeIn(getTime());
        incrementTime();
        nodeVisitor.visitNode(graphState, node);

        node.getEditableNeighbors().forEach( neighbor -> {
            if(neighbor.getState()==NOTACTIVE){
                neighbor.setPredecessors(node);
                dfs(neighbor, nodeVisitor);
            }
        });

        node.setState(FINISHED);
        node.setTimeOut(getTime());
        incrementTime();
    }

    @Override
    public void breadthFirstSearch(GraphNodeVisitor<T> nodeVisitor) {
        getEditableNodes().forEach( node -> {
            if(node.getState()==NOTACTIVE){
                node.setPredecessors(null);
                bfs(node, nodeVisitor);
            }
        });
    }

    protected void bfs(EditableGraphNode<T> node, GraphNodeVisitor<T> visitor){
        var queue = new LinkedList<EditableGraphNode<T>>();
        node.setState(ACTIVE);
        node.setTimeIn(getTime());
        incrementTime();
        queue.add(node);

        while( !queue.isEmpty() ){
            var currNode = queue.poll();

            visitor.visitNode(graphState, currNode);

            currNode.getEditableNeighbors().forEach( neighbor -> {
                if(neighbor.getState()==NOTACTIVE){
                    neighbor.setState(ACTIVE);
                    neighbor.setTimeIn(getTime());
                    incrementTime();
                    neighbor.setPredecessors(currNode);
                    queue.add(neighbor);
                }
            });
            currNode.setTimeOut(getTime());
            incrementTime();
        }
    }

    public static void main(String[] args) {
        NeighborhoodListGraph<Character> graph = zivkovicTest();
        graph.breadthFirstSearch( (state, node)-> {
            System.out.println(node.getValue());
            System.out.println(state);
        });
    }

    private static NeighborhoodListGraph<Character> zivkovicTest(){
        var graph = new NeighborhoodListGraph<Character>();
        graph.addNodes('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k');
        graph.addConnection('a', 'b');
        graph.addConnection('a', 'c');
        graph.addConnection('a', 'd');
        graph.addConnection('c', 'e');
        graph.addConnection('c', 'f');
        graph.addConnection('d', 'f');
        graph.addConnection('e', 'h');
        graph.addConnection('f', 'g');
        graph.addConnection('f', 'j');
        graph.addConnection('f', 'i');
        graph.addConnection('g', 'j');
        graph.addConnection('h', 'i');
        graph.addConnection('h', 'k');
        graph.addConnection('i', 'k');
        return graph;
    }
}
