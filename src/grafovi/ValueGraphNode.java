package grafovi;

import java.util.*;

public class ValueGraphNode<T> implements Iterable<ValueGraphNode<T>> {
    protected T value;
    protected List<ValueGraphNode<T>> connections;
    private boolean visited = false;
    private Set<Integer> visitChecker = new HashSet<>();

    public void visit(int id){
        visitChecker.add(id);
    }
    public boolean isVisited(int id){
        return visitChecker.contains(id);
    }

    public void visit(){
        visited = true;
    }
    public void unvisit(){
        visited = false;
    }
    public boolean isVisited(){
        return visited;
    }

    public ValueGraphNode(T value){
        this.value = value;
    }
    public void addConnection(ValueGraphNode<T> node){
        if(connections==null)
            connections = new ArrayList<>();
        connections.add(node);
    }
    public T getValue(){
        return value;
    }

    @Override
    public String toString(){
        StringBuilder bld = new StringBuilder();
        bld.append("GraphNode: ").append(value.toString());
        if(connections!=null){
            bld.append(" connections: ");
            for(var i:connections){
                bld.append(i.value.toString()).append(", ");
            }
            bld.deleteCharAt(bld.length()-2);
        }
        return bld.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueGraphNode<?> that = (ValueGraphNode<?>) o;
        return Objects.equals(value, that.value);
    }
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    @Override
    public Iterator<ValueGraphNode<T>> iterator() {
        if(connections==null)
            return Collections.EMPTY_LIST.iterator();
        return connections.iterator();
    }
}


