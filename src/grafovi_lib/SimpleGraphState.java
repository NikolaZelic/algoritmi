package grafovi_lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

class SimpleGraphState<T> implements GraphState<T> , EditableGraphState<T> {

    private int time = START_TIME;
    private List<EditableGraphNode<T>> nodes = new ArrayList<>();

    public SimpleGraphState(){

    }

    @Override
    public int getTime() {
        return time;
    }
    @Override
    public void setTime(int time){
        this.time = time;
    }

    @Override
    public List<GraphNode<T>> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    @Override
    public List<EditableGraphNode<T>> getEdtableNodes() {
        return nodes;
    }

    @Override
    public void setNodes(List<EditableGraphNode<T>> editableGraphNodes) {
        nodes = editableGraphNodes;
    }

    @Override
    public int getNodesNumber() {
        if(nodes==null)
            return 0;
        return nodes.size();
    }

    @Override
    public String toString(){
        var fmt = new Formatter();
        fmt.format("----- TIME "+getTime()+"-------\n");
        var lineFormat = "%-4s %-5s %-7s %-8s %-10s %s\n";
        fmt.format(lineFormat, "Node", "State", "Time in", "Time out", "Neighbors", "Predecessors");

        nodes.forEach( node -> {
            fmt.format(lineFormat, node.getValue(), node.getState(), node.getTimeIn(), node.getTimeOut(), formatNodes(node.getNeighbors()), formatNodes(node.getPredecessors()) );
        });

        return fmt.toString();
    }

    private static <T> String formatNodes(List<GraphNode<T>> neighbors){
        var bld = new StringBuilder();
        neighbors.forEach( n -> bld.append(n.getValue()).append(" ") );
        return bld.toString();
    }
}
