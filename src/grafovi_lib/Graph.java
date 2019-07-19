package grafovi_lib;

import java.util.List;

public interface Graph <T> {
    int NOTACTIVE=0, ACTIVE=1, FINISHED=2;

    void depthFirstSearch(GraphNodeVisitor<T> nodeVisitor);

    void breadthFirstSearch(GraphNodeVisitor<T> nodeVisitor);

    void addNode(T nodeValue);

    void addNodes(T ... nodeValues);

    void addConnection(T nodeValue1, T nodeValue2);

    List<GraphNode<T>> getNodes();

    GraphState<T> getGraphState();

    default void alreadyExistedNodeException(T nodeValue){
        throw GraphUtility.duplicateNodeException(nodeValue);
    }

    default void notExistedNodeException(T nodeValue){
        throw GraphUtility.missingNodeException(nodeValue);
    }
}
