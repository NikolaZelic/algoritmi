package grafovi_lib;

public interface GraphNodeVisitor<T> {
    void visitNode(GraphState<T> currentState, GraphNode<T> currentNode);
}
