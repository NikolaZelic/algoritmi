package grafovi;

public class Tree
{
    class Node
    {
        public Node(Comparable data)
        {
            this.data = data;
        }
        private Comparable data;
        private Node left, right;
    }
    
    private Node startNode;
    
    public boolean put(Comparable data)
    {
        if( startNode==null )
            startNode = new Node(data);
        else
            return put(data, startNode);
        return true;
    }
    
    private boolean put(Comparable data, Node node)
    {
        // Proveravamo da li je isti
        int c = data.compareTo(node.data);
        if(c==0)
            return false;
        if(c<0)
        {
            if(node.left==null)
            {
                node.left = new Node(data);
                return true;
            }
            return put(data, node.left);
        }
        else
        {
            if(node.right==null)
            {
                node.right = new Node(data);
                return true;
            }
            return put(data, node.right);
        }
    }
    
    public void writeAll()
    {
        writeNode(startNode);
    }
    
    private void writeNode(Node node)
    {
        if(node==null)
            return;
        System.out.println(node.data);
        writeNode(node.left);
        writeNode(node.right);
    }
    
    public static void main(String[] args)
    {
        Tree tree = new Tree();
        tree.put("SimpleGrahpNode");
        tree.put("qwer");
        tree.put("c");
        tree.writeAll();
    }
}
