package grafovi_zadaci;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ShortestPath extends Graph {

    public List<Integer> shortestPathsFrom(char from, char to){
        var fromPos = getNodePosition(from);
        setNodeState(fromPos, ACTIVE);
        setPreviousNode(fromPos, null);
        var queue = new LinkedList<Integer>();
        queue.add(fromPos);
        while(!queue.isEmpty()){
            var node = queue.poll();
            var neighbors = getNodeNeighbors(node);
            for( var neighbor : neighbors){
                if(getNodeState(neighbor)==NOTACTIVE){
                    setNodeState(neighbor, ACTIVE);
                    setPreviousNode(neighbor, node);
                    if(getNodeName(neighbor)==to){
                        return getNodePath(neighbor);
                    }
                    queue.add(neighbor);
                }
            };
            setNodeState(node, FINISHED);
        }
        return Collections.emptyList();
    }

    public static void main(String[] args) {
        var graph = new ShortestPath();
        graph.defaultGraph1();
        System.out.println(graph);
        System.out.println( graph.formatNodes(graph.shortestPathsFrom('a', 'k')) );
    }
}
