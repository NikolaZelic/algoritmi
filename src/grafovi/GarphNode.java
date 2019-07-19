package grafovi;

import java.util.ArrayList;
import java.util.List;

public class GarphNode<T> {
    protected List<GarphNode<T>> connections;

    public void addConnection(GarphNode<T> value){
        if(connections==null)
            connections = new ArrayList<>();
        connections.add(value);
    }

}
