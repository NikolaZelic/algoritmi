package grafovi;

import java.util.ArrayList;
import java.util.List;

public class GarphNode {
    protected List<GarphNode> connections;

    public void addConnection(GarphNode value){
        if(connections==null)
            connections = new ArrayList<>();
        connections.add(value);
    }

}
