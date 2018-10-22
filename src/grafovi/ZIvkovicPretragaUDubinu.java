package grafovi;

import java.util.*;

public class ZIvkovicPretragaUDubinu {
}

class Node implements Iterable<Node>{
    private char name;
    private List<Node> neighbors;
    private boolean visited = false;
    public void visit(){
        visited = true;
    }
    public boolean isVisited(){
        return visited;
    }
    public void refreshVisit(){
        visited = false;
    }
    public Node(char name, Node ... neighbors){
        this.name = name;
        this.neighbors = new ArrayList<>();
        for(var node:neighbors)
            this.neighbors.add(node);
    }
    @Override
    public String toString(){
        StringBuilder bld = new StringBuilder();
        bld.append("(").append(name).append(") ");
        if(neighbors.size()>0){
            bld.append("neighbors: ");
            for(var neighbor:neighbors)
                bld.append(neighbor.name).append(", ");
            bld.delete(bld.length()-2, bld.length());
        }
        return bld.toString();
    }
    @Override
    public Iterator<Node> iterator(){
        return neighbors.iterator();
    }
    public void addNeighbor(Node node){
        neighbors.add(node);
    }
    public char getName(){
        return name;
    }
    public List<Node> getNeighbors(){
        return neighbors;
    }
}

class AdjacencyListGraph implements Iterable<Node>{
    private Map<Character, Node> nodes;
    private char lastName = 'a';

    public AdjacencyListGraph(int nodesNum){
        nodes = new LinkedHashMap<>();
        for(var i=0; i<nodesNum; i++){
            nodes.put(lastName, new Node(lastName));
            lastName++;
        }
    }

    public void DepthFirstSearch(){
        nodes.forEach((name, node)->{
            if( !node.isVisited() )
                dfs(node);
        });
    }
    private void dfs(Node node){
        node.visit();
        for(var neighbor : node){
            if( !neighbor.isVisited() )
                dfs(neighbor);
        }
    }
    public void addConnection(char from, char to){
        nodes.get(from).addNeighbor(nodes.get(to));
        nodes.get(to).addNeighbor(nodes.get(from));
    }
    @Override
    public String toString(){
        var bld = new StringBuilder();
        for(var node:nodes.values())
            bld.append(node.toString()).append("\n");
        return bld.toString();
    }

    @Override
    public Iterator<Node> iterator(){
        return new AdjacencyListGraphIterator(this);
    }

    class AdjacencyListGraphIterator implements Iterator<Node> {
        private AdjacencyListGraph graph;
        private HashMap<Character, Boolean> visitedNodes;
        private List<Node> visitList;
        private Iterator<Node> iterator;

        AdjacencyListGraphIterator(AdjacencyListGraph graph){
            this.graph = graph;
            visitedNodes = new HashMap<>();
            graph.nodes.keySet().forEach( nodeName -> visitedNodes.put(nodeName, false) );
            visitList = new ArrayList<>(graph.nodes.size());
            DepthFirstSearch();
            iterator = visitList.iterator();
        }

        private void DepthFirstSearch(){
            graph.nodes.forEach((name, node) -> {
                if( !visitedNodes.get(name) )
                    dfs(node);
            });
        }
        private void dfs(Node node){
            visitList.add(node);
            visitedNodes.put(node.getName(), true);
            for(var neigber : node.getNeighbors()){
                if( !visitedNodes.get(neigber.getName()) )
                    dfs(neigber);
            }
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }
        @Override
        public Node next() {
            return iterator.next();
        }
    }

    public static void main(String[] args) {
        var graph = new AdjacencyListGraph(15);
        graph.addConnection('a', 'b');
        graph.addConnection('a', 'c');
        graph.addConnection('a', 'd');
        graph.addConnection('c', 'e');
        graph.addConnection('c', 'f');
        graph.addConnection('d', 'f');
        graph.addConnection('e', 'h');
        graph.addConnection('f', 'i');
        graph.addConnection('f', 'j');
        graph.addConnection('f', 'g');
        graph.addConnection('g', 'j');
        graph.addConnection('h', 'i');
        graph.addConnection('h', 'k');
        graph.addConnection('i', 'k');

        graph.addConnection('l', 'm');
        graph.addConnection('m', 'n');
        graph.addConnection('n', 'o');
        graph.addConnection('o', 'l');
        System.out.println(graph);

//        graph.DepthFirstSearch();
//        graph.nodes.forEach((name, node)->System.out.println(name+" "+node.isVisited()));
        System.out.println("DEPTH FIRST SEARCH");
        for(var node:graph)
            System.out.println(node);
    }
}

class AdjacencyMatrixGraph {
    private boolean[][] matrix;
    private char[] nodeNames;
    private int nodesStation[];
    public static final int UNACTIVE=0, ACTIVE=1, FINISHED=2;
    private int timer = 0;
    private Integer[] timeIn;
    private Integer[] timeOut;

    public AdjacencyMatrixGraph(int n){
        matrix = new boolean[n][n];
        nodeNames = new char[n];
        char name = 'a';
        for(var i=0; i<n; i++){
            nodeNames[i] = name;
            name++;
        }
        nodesStation = new int[n];
        timeIn = new Integer[n];
        timeOut = new Integer[n];
    }

    @Override
    public String toString(){
        StringBuilder bld = new StringBuilder();
        for(var i=0; i<nodeNames.length; i++){
            bld.append("(").append(nodeNames[i]).append(") ");
            var first = true;
            for(var j=0; j<matrix[i].length; j++){
                if(matrix[i][j]){
                    if(first) {
                        first = false;
                        bld.append("neighbors ");
                    }
                    bld.append(nodeNames[j]).append(", ");
                }
            }
            if(!first)
                bld.delete(bld.length()-2, bld.length());
            bld.append("\n");
        }
        return bld.toString();
    }

    public void addConnection(char from, char to){
        int fromNum=-1, toNum=-1;
        for(var i=0; i<nodeNames.length; i++){
            if(nodeNames[i]==from)
                fromNum = i;
            else if(nodeNames[i]==to)
                toNum = i;
        }
        if(fromNum==-1||toNum==-1)
            throw new IllegalArgumentException("WTF are u doing?!!");
        matrix[fromNum][toNum] = true;
        matrix[toNum][fromNum] = true;
    }

    public void DepthFirstSearch(){
        for(var i=0; i<nodeNames.length; i++){
            if( nodesStation[i]==UNACTIVE ) {
                dfs(i);
            }
        }
        writeTimes();
        refreshVisit();
    }
    private void dfs(int nodePos){
//        System.out.println(nodeNames[nodePos]);
        timeIn[nodePos] = timer;
        timer++;
        nodesStation[nodePos] = ACTIVE;
        for(var j=0; j<matrix[nodePos].length; j++){
            if( !matrix[nodePos][j] || nodesStation[j]!=UNACTIVE )
                continue;
            dfs(j);
        }
        timeOut[nodePos] = timer;
        timer++;
    }

    private void refreshVisit(){
        for(var i = 0; i< nodesStation.length; i++) {
            nodesStation[i] = UNACTIVE;
            timeIn[i] = null;
            timeOut[i] = null;
        }
        timer = 0;
    }
    private void writeTimes(){
        for(var i=0; i<nodeNames.length; i++){
            System.out.printf("%c in: %2d out: %2d\n", nodeNames[i], timeIn[i], timeOut[i]);
        }
    }

    public int numberOfComponents(){
        var num = 0;
        for(var i=0; i<nodeNames.length; i++){
            if( nodesStation[i]==UNACTIVE ) {
                num++;
                dfs(i);
            }
        }
        refreshVisit();
        return num;
    }

    // Vraca sve pretke
    public List<Integer> allAcestries(char node){
        int nodeInt = -1;
        for(var i=0; i<nodeNames.length; i++){
            if(nodeNames[i]==node) {
                nodeInt = i;
                break;
            }
        }
        if(nodeInt==-1)
            return null;
        List<Integer> result = new ArrayList<>();
        for(var i=0; i<nodeNames.length; i++){
            if( nodesStation[i]==UNACTIVE ) {
                dfsAcestries(i, nodeInt, result);
            }
        }
        refreshVisit();
        return result;
    }
    private boolean dfsAcestries(int nodePos, int searchNode, List<Integer> acestries){
        nodesStation[nodePos] = ACTIVE;
        timeIn[nodePos] = timer;
        timer++;
        if(nodePos==searchNode){
            for(var k=0; k<nodesStation.length; k++){
                if( nodesStation[k]==ACTIVE && k!=nodePos )
                    acestries.add(k);
            }
            return true;
        }
        for(var j=0; j<matrix[nodePos].length; j++){
            if( !matrix[nodePos][j] || nodesStation[j]!=UNACTIVE )
                continue;

            if(dfsAcestries(j, searchNode, acestries))
                return true;
        }
        nodesStation[nodePos] = FINISHED;
        timeOut[nodePos] = timer;
        timer++;
        return false;
    }

    private int getNodePosition(char node){
        for(var i=0; i<nodeNames.length; i++)
            if(nodeNames[i]==node)
                return i;
        throw new IllegalArgumentException("Nema tog karaktera");
    }

    // Vraca sve potomke
    public List<Integer> allDescendants(char node){
        int nodePosition = getNodePosition(node);
        List<Integer> result = new ArrayList<>();
        for(var i=0; i<nodeNames.length; i++){
            if( nodesStation[i]==UNACTIVE ) {
                dfsDescendants(i, nodePosition, result);
            }
        }
        refreshVisit();
        return result;
    }
    private boolean dfsDescendants(int nodePos, int searchNode, List<Integer> descendants){
        nodesStation[nodePos] = ACTIVE;
        timeIn[nodePos] = timer;
        timer++;
        for(var j=0; j<matrix[nodePos].length; j++){
            if( !matrix[nodePos][j] || nodesStation[j]!=UNACTIVE )
                continue;

            if(dfsDescendants(j, searchNode, descendants))
                return true;
        }

        nodesStation[nodePos] = FINISHED;
        timeOut[nodePos] = timer;
        timer++;

        if(nodePos==searchNode){
            for(var k=0; k<timeIn.length; k++){
                if(timeIn[k]==null || timeOut[k]==null)
                    continue;
                if( k!=nodePos && (timeIn[k]>timeIn[nodePos] && timeOut[k]<timeOut[nodePos]) ){
                    descendants.add(k);
                }
            }
            return true;
        }
        return false;
    }

    public void tackaArtikulacije(){
        for(var i=0; i<nodeNames.length; i++){
            if( nodesStation[i]==UNACTIVE ) {
                dfsArtikulacije(i);
            }
        }
        refreshVisit();
    }
    private void dfsArtikulacije(int nodePos){
        nodesStation[nodePos] = ACTIVE;
        timeIn[nodePos] = timer;
        timer++;
        for(var j=0; j<matrix[nodePos].length; j++){
            if( !matrix[nodePos][j] || nodesStation[j]!=UNACTIVE )
                continue;

            dfsArtikulacije(j);
        }

        nodesStation[nodePos] = FINISHED;
        timeOut[nodePos] = timer;
        timer++;

        // Pronalazenje predaka i potomaka
        List<Integer> preci = new ArrayList<>();
        List<Integer> potomci = new ArrayList<>();
        for(var i=0; i<timeIn.length; i++){
            // Predak
            if( i!=nodePos && nodesStation[i]==ACTIVE && i!=nodePos )
                preci.add(i);
            // Potomak
            if(timeIn[i]==null || timeOut[i]==null)
                continue;
            if(i!=nodePos && timeIn[i]>timeIn[nodePos] && timeOut[i]<timeOut[nodePos])
                potomci.add(i);
        }
        // Provera da li neki potomak referencira pretka
        boolean jeste = true;
        boolean haveOne = false;
        big: for(var i=0; i<potomci.size(); i++){
            for(var j=0; j<preci.size(); j++){
                haveOne = true;
                if( matrix[potomci.get(i)][preci.get(j)] ){
                    jeste = false;
                    break big;
                }
            }
        }
        if(jeste&&haveOne){
            System.out.println("Tacka artikuacije: " + nodeNames[nodePos]);
        }
    }

    public boolean isCycle(){
        for(var i=0; i<nodeNames.length; i++){
            if( nodesStation[i]==UNACTIVE ) {
                dfs(i);
            }
        }
        var result = true;
        big: for(var i=0;i<matrix.length; i++){
            for(var j=0; j<matrix[i].length; j++){
                if( !matrix[i][j] ) // There isn't that connection
                    continue;
                if( timeOut[i] <= timeOut[j] ){
                    result = false;
                    break big;
                }
            }
        }

        refreshVisit();
        return result;
    }

    public void IterDepthFirstSearch(){
        var q = new LinkedList<Integer>();
        nodesStation[0] = ACTIVE;
        q.push(0);

        while( !q.isEmpty() ){
            var cur = q.pop();
            for(var i=0; i<matrix[cur].length; i++){
                if(matrix[cur][i] && nodesStation[cur]==UNACTIVE){
                    nodesStation[i] = ACTIVE;
                    q.push(i);
                }
            }
        }

        refreshVisit();
    }

    public static void main(String[] args) {
        var graph = new AdjacencyMatrixGraph(15);

        graph.addConnection('a', 'b');
        graph.addConnection('a', 'c');
        graph.addConnection('a', 'd');
        graph.addConnection('c', 'e');
        graph.addConnection('c', 'f');
        graph.addConnection('d', 'f');
        graph.addConnection('e', 'h');
        graph.addConnection('f', 'i');
        graph.addConnection('f', 'j');
        graph.addConnection('f', 'g');
        graph.addConnection('g', 'j');
        graph.addConnection('h', 'i');
        graph.addConnection('h', 'k');
        graph.addConnection('i', 'k');

//        graph.addConnection('k', 'l');
//        graph.addConnection('k', 'm');

        graph.addConnection('l', 'm');
        graph.addConnection('m', 'n');
        graph.addConnection('n', 'o');
        graph.addConnection('o', 'l');

//        System.out.println(graph);

//        graph.DepthFirstSearch();

//        System.out.println("Number of components");
//        System.out.println( graph.numberOfComponents() );

//        graph.tackeArtiulacije();

//        System.out.println("Preci");
//        var res = graph.allAcestries('k');
//        res.forEach(e->System.out.println(graph.nodeNames[e]));

//        System.out.println("Potomci");
//        var res2 = graph.allDescendants('i');
//        res2.forEach(e->System.out.println(graph.nodeNames[e]));

//        graph.tackaArtikulacije();

//        System.out.println("Aciklican graf: " + graph.isCycle() );


    }
}