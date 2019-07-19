package grafovi_zadaci;

import java.util.Formatter;

public class WeightenMatrixGraph {

    protected double[][] weights;
    protected char[] nodeNmase;

    private void resetWeights(){
        weights = new double[nodesNumber()][nodesNumber()];
        for (int i = 0; i < nodesNumber(); i++) {
            for (int j = 0; j < nodesNumber(); j++) {
                if(i!=j)
                    weights[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    public WeightenMatrixGraph(char ... nodeNames){
        var n = nodeNames.length;
        if(n<0)
            throw new IllegalArgumentException("n mast be greater then 0");

        this.nodeNmase = nodeNames;
        resetWeights();
    }

    public int nodesNumber(){
        return nodeNmase.length;
    }

    protected char getNodeName(int nodePos){
        return nodeNmase[nodePos];
    }

    protected int getNodePos(char nodeNmae){
        for (int i = 0; i < nodeNmase.length; i++) {
            if(nodeNmase[i]==nodeNmae)
                return i;
        }
        throw new IllegalArgumentException("There is'n node with name: " + nodeNmae);
    }

    @Override
    public String toString(){
        var bld = new Formatter();

        // Table headers
        bld.format("   ");
        for (char name : nodeNmase) {
            bld.format("%-3s", name);
        }
        bld.format("\n");

        for(var i=0; i<nodesNumber(); i++){
            // Table left header
            bld.format("%-3s", getNodeName(i));

            // Table body - distances
            for(var j=0; j<nodesNumber(); j++){
                if(weights[i][j]==Double.POSITIVE_INFINITY)
                    bld.format("%-3s", '∞');
                else
                    bld.format("%-3.0f", weights[i][j]);

            }
            bld.format("\n");
        }

        return bld.toString();
    }

    public void setWeight(char a, char b, double weight){
        setWeight(getNodePos(a), getNodePos(b), weight);
    }

    public void setWeight(int i, int j, double weight){
        weights[i][j] = weight;
        weights[j][i] = weight;
    }

    public void defaultGraph1(){
        nodeNmase = new char[]{'a', 'b', 'c', 'd', 'e', 'f'};
        resetWeights();

//        setWeight('a', 'b', 8);
        setWeight('a', 'c', 10);
        setWeight('a', 'f', 20);

        setWeight('b', 'c', 5);
        setWeight('b', 'e', 4);
        setWeight('b', 'd', 3);

        setWeight('c', 'f', 2);
        setWeight('c', 'e', 1);

        setWeight('d', 'e', 6);
    }

    public double getWeight(char a, char b){
        return weights[getNodePos(a)][getNodePos(b)];
    }

    public double getWeight(int a, int b){
        return weights[a][b];
    }

    public static void main(String[] args) {
        var graph = new WeightenMatrixGraph();
        graph.defaultGraph1();
        System.out.println(graph);
    }
}
