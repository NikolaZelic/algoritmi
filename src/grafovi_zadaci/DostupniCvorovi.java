package grafovi_zadaci;

import java.util.*;

public class DostupniCvorovi extends DirectedGraph {

    public Map<Character, List<Character>> dostupniCvorovi(){
        var rez = new HashMap<Character, List<Character>>(getNodeNumber());

        for(var i=0; i<getNodeNumber(); i++){
            rez.put( getNodeName(i), new ArrayList<>() );
        }

        for(var i=0; i<getNodeNumber(); i++){
            for(var j=0; j<getNodeNumber(); j++){
                setNodeState(j, NOTACTIVE);
            }
            dfs(i, rez.get(getNodeName(i)));
        }

        return rez;
    }

    private void dfs(int nodePos, List<Character> rez){
        setNodeState(nodePos, ACTIVE);

        getNodeNeighbors(nodePos).forEach( neighbor -> {
            if( getNodeState(neighbor)==NOTACTIVE ){
                rez.add(getNodeName(neighbor));
                dfs(neighbor, rez);
            }
        });
    }


    public static void main(String[] args) {
        var graph = new DostupniCvorovi();
        graph.defaultGraph1();
        graph.addNode('l');
        graph.addNode('m');
        graph.addConnection('l', 'm');
        System.out.println(graph);

        System.out.println("\n---DOSTUPNI CVOROVI OD SVAKOG CVORA-----\n");
        var rez = graph.dostupniCvorovi();
        rez.forEach( (cvor, dostupni) -> {
            System.out.print(cvor+": ");
            Collections.sort(dostupni);
            dostupni.forEach( d -> System.out.print(d+" ") );
            System.out.println();
        } );
    }

}
