package grafovi_lib;

import java.util.List;

public interface EditableGraphState <T> extends GraphState<T> {
    void setTime(int time);
    void setNodes(List<EditableGraphNode<T>> nodes);
    List<EditableGraphNode<T>> getEdtableNodes();
}
