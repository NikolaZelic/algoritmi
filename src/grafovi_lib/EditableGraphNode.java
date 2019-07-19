package grafovi_lib;

import java.util.List;

public abstract class EditableGraphNode<T> extends GraphNode<T> {

    public EditableGraphNode(T value){
        super(value);
    }

    public void setState(int state){
        this.state = state;
    }

    public void setTimeIn(Integer timeIn){
        this.timeIn = timeIn;
    }

    public void setTimeOut(Integer timeOut){
        this.timeOut = timeOut;
    }

    public void setPredecessors(GraphNode<T> predecessor){
        if(predecessor!=null && !neighbors.contains(predecessor) ){
            throw new IllegalArgumentException("Node "+ toString() +" is not connected with "+ predecessor);
        }
        this.predecessor = predecessor;
    }

    public void addConnection(EditableGraphNode<T> newNeighbor){
        var node = GraphUtility.findEditableNode(neighbors, newNeighbor.getValue());
        if(node!=null){
            throw new IllegalArgumentException("Node " +toString() + " is already connected with " + newNeighbor);
        }
        neighbors.add(newNeighbor);
    }

    public List<EditableGraphNode<T>> getEditableNeighbors(){
        return neighbors;
    }
}
