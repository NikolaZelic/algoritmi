package grafovi_lib;

import java.util.Formatter;
import java.util.List;

public interface GraphState<T> {
    int START_TIME = 1;

    int getTime();
    List<GraphNode<T>> getNodes();

    default int getNodesNumber(){
        return getNodes().size();
    }

}
