package grafovi_lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class GraphUtility {
    private GraphUtility(){

    }

    public static <T> EditableGraphNode<T> findEditableNode(List<EditableGraphNode<T>> nodes, T nodeValue){
        return nodes.stream().filter( node -> node.getValue().equals(nodeValue) ).findAny().orElse(null);
    }

    public static <T> EditableGraphNode<T> findEditableNodeOrException(List<EditableGraphNode<T>> nodes, T nodeValue){
        return nodes.stream().filter( node -> node.getValue().equals(nodeValue) ).findAny().
                orElseThrow( () -> missingNodeException(nodeValue) );
    }

    public static <T> GraphNode<T> findNode(List<GraphNode<T>> nodes, T nodeValue){
        return nodes.stream().filter( node -> node.getValue().equals(nodeValue) ).findAny().orElse(null);
    }

    public static <T> GraphNode<T> findNodeOrException(List<GraphNode<T>> nodes,  T nodeValue){
        return nodes.stream().filter( node -> node.getValue().equals(nodeValue) ).findAny().
                orElseThrow( () -> missingNodeException(nodeValue) );
    }

    public static <T> IllegalArgumentException missingNodeException(T nodeValue){
        return new IllegalArgumentException("There isn't node with value: " + nodeValue);
    }

    public static <T> IllegalArgumentException duplicateNodeException(T nodeValue){
        return new IllegalArgumentException("There is already node with value: " + nodeValue);
    }

}
