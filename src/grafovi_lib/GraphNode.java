package grafovi_lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static grafovi_lib.Graph.NOTACTIVE;

public abstract class GraphNode<T> {
    protected T value;
    protected int state = NOTACTIVE;
    protected Integer timeIn = null;
    protected Integer timeOut = null;
    protected List<EditableGraphNode<T>> neighbors = new ArrayList<>();
    protected GraphNode<T> predecessor = null;

    public T getValue(){
        return value;
    }

    public int getState(){
        return state;
    }

    public Integer getTimeIn(){
        return timeIn;
    }

    public Integer getTimeOut(){
        return timeOut;
    }

    public List<GraphNode<T>> getNeighbors(){
        return Collections.unmodifiableList(neighbors);
    }

    public GraphNode<T> getPredecessor(){
        return predecessor;
    }

    public List<GraphNode<T>> getPredecessors(){
        var previous = getPredecessor();
        if(previous==null)
            return Collections.emptyList();
        var predecessors = new LinkedList<GraphNode<T>>();
        while(previous!=null){
            predecessors.addFirst(previous);
            previous = previous.getPredecessor();
        }
        return predecessors;
    }

    public GraphNode(T value){
        this.value = value;
    }

    protected boolean haveConnections(){
        return neighbors != null && neighbors.size()>0;
    }

    @Override
    public String toString(){
        var bld = new StringBuilder();
        bld.append("Node ").append(value.toString());
        if(haveConnections()){
            bld.append(" neighbors: ");
            neighbors.forEach(node -> bld.append(node.getValue().toString()).append(" ") );
        }
        return bld.toString();
    }
}
