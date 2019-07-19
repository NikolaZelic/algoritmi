package grafovi_zadaci;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;

public class Graph {

    public static final int NOTACTIVE = 0, ACTIVE = 1, FINISHED = 2;

    private List<Character> nodeNames;
    private List<Integer> nodeStates;
    private List<Integer> nodesTimeIn;
    private List<Integer> nodesTimeOut;
    private List<List<Integer>> nodeNeighbors;
    private List<Integer> previousNodes;

    public void clean(){
        nodeNames = new ArrayList<>();
        nodeStates = new ArrayList<>();
        nodesTimeIn = new ArrayList<>();
        nodesTimeOut = new ArrayList<>();
        nodeNeighbors = new ArrayList<>();
        previousNodes = new ArrayList<>();
    }

    public void refresh(){
        for(var i=0; i<nodeStates.size(); i++){
            nodeStates.set(i, NOTACTIVE);
            nodesTimeIn.set(i, null);
            nodesTimeOut.set(i, null);
            previousNodes.set(i, null);
        }
    }

    public Graph(){
        clean();
    }


    // EXCEPTION FUNCTIONS
    protected void checkDuplicateNode(char nodeName){
        if(nodeNames.contains(nodeName)){
            throw new IllegalArgumentException("There is already node with name " +nodeName );
        }
    }

    protected IllegalArgumentException notExistedNodeException(char nodeName){
        return new IllegalArgumentException("There isn't node with name " + nodeName);
    }

    protected void checkNodePosition(int nodePos){
        if(nodePos<0 || nodePos>=nodeNames.size()){
            throw new IllegalArgumentException("Invalid node position "+nodePos);
        }
    }

    // PRIVATE FUNCTIONS

    protected void defaultGraph1(){
        addNodes('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k');
        addConnection('a', 'b');
        addConnection('a', 'c');
        addConnection('a', 'd');
        addConnection('c', 'e');
        addConnection('c', 'f');
        addConnection('d', 'f');
        addConnection('e', 'h');
        addConnection('f', 'g');
        addConnection('f', 'j');
        addConnection('f', 'i');
        addConnection('g', 'j');
        addConnection('h', 'i');
        addConnection('h', 'k');
        addConnection('i', 'k');
    }

    protected int getNodePosition(char nodeName){
        for(var i=0; i< nodeNames.size(); i++){
            if(nodeNames.get(i).equals(nodeName))
                return i;
        }
        throw notExistedNodeException(nodeName);
    }

    protected List<Integer> getNodeNeighbors(int nodePos){
        checkNodePosition(nodePos);
        return nodeNeighbors.get(nodePos);
    }

    protected List<Integer> getNodeNeighbors(char nodeName){
        var nodePos = getNodePosition(nodeName);
        return getNodeNeighbors(nodePos);
    }

    protected int getNodeState(int nodePos){
        checkNodePosition(nodePos);
        return nodeStates.get(nodePos);
    }

    protected void setNodeState(int nodePos, int state){
        checkNodePosition(nodePos);
        nodeStates.set(nodePos, state);
    }

    protected Integer getNodeTimeIn(int nodePos){
        checkNodePosition(nodePos);
        return nodesTimeIn.get(nodePos);
    }

    protected void setNodeTimeIn(int nodePos, Integer timeIn){
        checkNodePosition(nodePos);
        nodesTimeIn.set(nodePos, timeIn);
    }

    protected Integer getNodeTimeOut(int nodePos){
        checkNodePosition(nodePos);
        return nodesTimeOut.get(nodePos);
    }

    protected void setNodeTimeOut(int nodePos, Integer timeOut){
        checkNodePosition(nodePos);
        nodesTimeOut.set(nodePos, timeOut);
    }

    public void addNode(char nodeName){
        checkDuplicateNode(nodeName);
        nodeNames.add(nodeName);
        nodeStates.add(NOTACTIVE);
        nodesTimeIn.add(null);
        nodesTimeOut.add(null);
        nodeNeighbors.add(new ArrayList<>());
        previousNodes.add(null);
    }

    public void addNodes(char ... nodeNames){
        for(var node : nodeNames){
            addNode(node);
        }
    }

    public void addConnection(char node1, char node2){
        if(node1 == node2){
            throw new IllegalArgumentException("Node "+node1+" can't be connected to itself");
        }
        var pos1 = getNodePosition(node1);
        var pos2 = getNodePosition(node2);
        var neighbors1 = getNodeNeighbors(pos1);
        var neighbors2 = getNodeNeighbors(pos2);
        if(neighbors1.contains(pos2) || neighbors2.contains(pos1)){
            throw new IllegalArgumentException(node1 + " is already connected to " + node2);
        }
        neighbors1.add(pos2);
        neighbors2.add(pos1);
    }

    public void addConnection(char node, char... neighbors){
        for(var neighbor : neighbors){
            addConnection(node, neighbor);
        }
    }

    public int getNodeNumber(){
        return nodeNames.size();
    }

    protected char getNodeName(int nodePos){
        checkNodePosition(nodePos);
        return nodeNames.get(nodePos);
    }

    protected Integer getPreviousNode(int nodePos){
        checkNodePosition(nodePos);
        return previousNodes.get(nodePos);
    }

    protected void setPreviousNode(int nodePos, Integer previose){
        checkNodePosition(nodePos);
        if(previose!=null)
            checkNodePosition(previose);
        previousNodes.set(nodePos, previose);
    }

    @Override
    public String toString(){
        var bld = new Formatter();
        var lineFormat = "%-4s %s\n";
        bld.format(lineFormat, "Name", "Neighbors");
        for(var i=0; i<getNodeNumber(); i++){
            bld.format(lineFormat, getNodeName(i), formatNodes(getNodeNeighbors(i)));
        }
        return bld.toString();
    }

    protected String getStateName(int nodeState){
        switch (nodeState){
            case NOTACTIVE: return "NOTACTIVE";
            case ACTIVE: return  "ACTIVE";
            case FINISHED: return "FINISHED";
        }
        return String.valueOf(nodeState);
    }

    public String writeGraphSearch(){
        var bld = new Formatter();
        var lineFormat = "%-4s %-9s %-5s %-5s %s\n";
        bld.format(lineFormat, "Name", "State", "In", "Out", "Neighbors" );
        for(var i=0; i<getNodeNumber(); i++){
            bld.format(lineFormat, getNodeName(i), getStateName(getNodeState(i)), getNodeTimeIn(i), getNodeTimeOut(i), formatNodes(getNodeNeighbors(i)));
        }
        return bld.toString();
    }

    protected String formatNodes(List<Integer> nodes){
        var bld = new StringBuilder();
        nodes.forEach( node -> bld.append(getNodeName(node)).append(" ") );
        return bld.toString();
    }

    protected List<Integer> getNodePath(int nodePos){
        var rez = new LinkedList<Integer>();
        var previous = getPreviousNode(nodePos);
        while(previous!=null){
            rez.addFirst(previous);
            previous = getPreviousNode(previous);
        }
        return rez;
    }
}
