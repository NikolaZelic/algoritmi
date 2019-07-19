package grafovi_zadaci;

public class FloydsAlgorithm extends WeightenMatrixGraph {

    public void allPairsShortestPath() {
        // Set up dp
//        var dp = new double[nodesNumber()][nodesNumber()];
//        for(var i=0; i<nodesNumber(); i++){
//            for(var j=0; j<nodesNumber(); j++)
//                dp[i][j] = getWeight(i, j);
//        }

        for (var k = 0; k < nodesNumber(); k++) {
            for (var i = 0; i < nodesNumber(); i++) {
                for (var j = 0; j < nodesNumber(); j++) {
                    var distance = weights[i][k] + weights[k][j];
                    if (distance < weights[i][j])
                        weights[i][j] = distance;
                }
            }
        }

        System.out.println(this);
    }

    public static void main(String[] args) {
        var graph = new FloydsAlgorithm();
        graph.defaultGraph1();
        System.out.println(graph);

        System.out.println("--------------- FLOYD ------------");
        graph.allPairsShortestPath();
    }

}
