package backtracking;

import java.util.Formatter;

public class MagnetPuzzle
{
    private char[][] rules;
    private int[] top, bottom, left, right;
    private char[][] result;
    private static final char[] options = new char[]{'X','+', '-'};

    public String toString() {
        Formatter bld = new Formatter();
//        bld.format("+   ");
//        for(int i:top)
//            bld.format("%3d", i);
//        bld.format("\n");
//        bld.format("     ");
//        for(int i:top)
//            bld.format("---");
//        bld.format("\n");
//        for(int i=0; i<rules.length; i++){
//            bld.format("%3d|", left[i]);
//            for(char j:rules[i])
//                bld.format("%3c", j);
//            bld.format("   |%3d\n", right[i]);
//        }
//        bld.format("     ");
//        for(int i:top)
//            bld.format("---");
//        bld.format("\n   ");
//        for(int i:bottom)
//            bld.format("%3d", i);
//        bld.format("       -\n");
//        bld.format("RESULT\n");
        bld.format("+   ");
        for (int i : top) {
            bld.format("%3d", i);
        }
        bld.format("\n");
        bld.format("     ");
        for (int i : top) {
            bld.format("---");
        }
        bld.format("\n");
        for (int i = 0; i < result.length; i++) {
            bld.format("%3d|", left[i]);
            for (int j = 0; j < result[i].length; j++) {
                bld.format("%3c", result[i][j] == 0 ? rules[i][j] : result[i][j]);
            }
            bld.format("   |%3d\n", right[i]);
        }
        bld.format("     ");
        for (int i : top) {
            bld.format("---");
        }
        bld.format("\n   ");
        for (int i : bottom) {
            bld.format("%3d", i);
        }
        bld.format("       -\n");
        return bld.toString();
    }

    public MagnetPuzzle(char[][] rules, int[] top, int[] bottom, int[] left, int[] right) {
        this.rules = rules;
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        result = new char[rules.length][rules[0].length];
    }

    public void setValue(int i, int j, char val) {
        if (i < 0 || j < 0 || i >= result.length || j >= result[0].length) {
            throw new IllegalArgumentException("Invalid coordinate: (" + i + ", " + j + ")");
        }

        char other;
        switch (val) {
            case 'X':
                other = 'X';
                break;
            case '+':
                other = '-';
                break;
            case '-':
                other = '+';
                break;
            case 0:
                other = 0;
                break;
            default:
                throw new IllegalArgumentException("Invalid char value: " + val);
        }

        int i2, j2;
        switch (rules[i][j]) {
            case 'T':
                i2 = i + 1;
                j2 = j;
                break;
            case 'B':
                i2 = i - 1;
                j2 = j;
                break;
            case 'L':
                i2 = i;
                j2 = j + 1;
                break;
            case 'R':
                i2 = i;
                j2 = j - 1;
                break;
            default:
                throw new IllegalArgumentException("Invalid coordinate: (" + i + ", " + j + ")");
        }
        if (i2 < 0 || j2 < 0 || i2 >= result.length || j2 >= result[0].length) {
            throw new IllegalArgumentException("Invalid coordinate: (" + i2 + ", " + j2 + ")");
        }

        result[i][j] = val;
        result[i2][j2] = other;
    }

    public void cleatValue(int i, int j) {
        setValue(i, j, (char) 0);
    }

    private boolean haveContradictions(){
        // Check columns 
        for(int j=0; j<result[0].length; j++){
            int plusNum = 0, minusNum = 0, elNum = 0;
            for(int i=0; i<result.length; i++){
                if(result[i][j]!=0)
                    elNum++;
                if(result[i][j]=='+')
                    plusNum++;
                else if(result[i][j]=='-')
                    minusNum++;
            }
            boolean fullColumn = elNum>=result.length;
            if(fullColumn && ((top[j]!=-1 && plusNum!=top[j]) || (bottom[j]!=-1 && minusNum!=bottom[j])) )
                return true;
            if( (top[j]!=-1 && plusNum>top[j]) || (bottom[j]!=-1 && minusNum>bottom[j]) )
                return true;
        }
        // Check rows
        for(int i=0; i<result.length; i++){
            int plusNum = 0, minusNum = 0, elNum = 0;
            for(int j=0; j<result[i].length; j++){
                if(result[i][j]!=0)
                    elNum++;
                if(result[i][j]=='+')
                    plusNum++;
                else if(result[i][j]=='-')
                    minusNum++;
            }
            boolean fullRow = elNum >= result[i].length;
            if( fullRow && ((left[i]!=-1 && plusNum!=left[i]) || (right[i]!=-1 && minusNum!=right[i])) )
                return true;
            if( (left[i]!=-1 && plusNum>left[i]) || (right[i]!=-1 && minusNum>right[i]) )
                return true;
        }
        return false;
    }
    private boolean isFinished() {
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                if (result[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean set(int i, int j) {
//        System.out.println(this);
        for (char option : options) {
            setValue(i, j, option);
//            System.out.println(this);
            if( haveContradictions() ){
                cleatValue(i, j);
                continue;
            }
            if (isFinished()) {
                return true;
            }
            // find next
            int i2 = i, j2 = j;
            boolean set = false;
            big: for (; i2 < rules.length; i2++) {
                for (; j2 < rules[i].length; j2++) {
                    if (result[i2][j2] == 0) {
                        set = true;
                        break big;
                    }
                }
                j2 = 0;
            }
            if (set && set(i2, j2)) {
                return true;
            }
            cleatValue(i, j);
        }
        return false;
    }
    public boolean solve() {
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                if (set(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] top = new int[]{2, -1, -1};
        int bottom[] = new int[]{-1, -1, 2};
        int left[] = new int[]{-1, -1, 2, -1};
        int right[] = new int[]{0, -1, -1, -1};
        char rules[][] = new char[][]{{'T', 'T', 'T'},
        {'B', 'B', 'B'},
        {'T', 'L', 'R'},
        {'B', 'L', 'R'}};
        /*
        int top[] = new int[]{ 1, -1, -1, 2, 1, -1 };
        int bottom[] = new int[]{ 2, -1, -1, 2, -1, 3 };
        int left[] = new int[]{ 2, 3, -1, -1, -1 };
        int right[] = new int[]{ -1, -1, -1, 1, -1 };
        char rules[][] = new char[][]{ { 'L', 'R', 'L', 'R', 'T', 'T' },
                      { 'L', 'R', 'L', 'R', 'B', 'B' },
                      { 'T', 'T', 'T', 'T', 'L', 'R' },
                      { 'B', 'B', 'B', 'B', 'T', 'T' },
                      { 'L', 'R', 'L', 'R', 'B', 'B' }};
*/
        
        MagnetPuzzle puzzle = new MagnetPuzzle(rules, top, bottom, left, right);
//        puzzle.setValue(0,0,'+');
//        puzzle.setValue(0,1,'+');
//        puzzle.setValue(0,2,'+');
//        System.out.println(puzzle.haveContradictions());
        System.out.println( puzzle.solve() );
        System.out.println( puzzle );
        
        
    }

}
