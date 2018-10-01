package zivkovic.podeliparesi;

public class TowersOfHanoi
{
    private int [] a, b, c;
    public TowersOfHanoi(){
        a = new int[5];
        b = new int[5];
        c = new int[5];
        for(int i=0; i<5; i++)
            a[i] = i+1;
    }
    public static void appendTowerElement(int i, int[] tower, StringBuilder bld){
        if(tower[i]!=0)
                bld.append(tower[i]).append(" ");
            else
                bld.append("| ");
    }
    public String toString(){
        StringBuilder bld = new StringBuilder();
        for(int i=0; i<5; i++){
            appendTowerElement(i, a, bld);
            appendTowerElement(i, b, bld);
            appendTowerElement(i, c, bld);
            bld.append("\n");
        }
        return bld.toString();
    }
    public void move(int[] a, int[] b){
        System.out.println(this);
        // Find top of towers
        int i=0, j=0;
        for(; i<5; i++)
            if(a[i]!=0)
                break;
        for(; j<5; j++)
            if(b[j]!=0)
                break;
        if(j>0)
            j=j-1;
        // Move elements
        b[j] = a[i];
        a[i] = 0;
    }
    public void toh(int n, int[] a, int[] b, int[] c){
//        System.out.println(this);
        if(n==1)
            move(a, c);
        else{
            toh(n-1, a, c, b);
            move(a, c);
            toh(n-1, b, a, c);
        }
    }
    public static void main(String[] args)
    {
        TowersOfHanoi ob = new TowersOfHanoi();
//        System.out.println(ob);
        ob.toh(5, ob.a, ob.b, ob.c);
        System.out.println(ob);
    }
    
}
