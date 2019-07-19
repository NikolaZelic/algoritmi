package grafovi_zadaci;

import java.util.Iterator;

public class Maze implements Iterable<Maze.Coordinate> {

    class Coordinate {
        private int top;
        private int left;
        private char value;

        public Coordinate(int top, int left, char value) {
            this.top = top;
            this.left = left;
            this.value = value;
        }

        public int getTop() {
            return top;
        }

        public int getLeft() {
            return left;
        }

        public char getValue() {
            return value;
        }

        @Override
        public String toString() {
//            return "("+top+", "+left+"): '" + String.valueOf(value) +"'";
            return "("+top+", "+left+")";
        }
    }

    class MazeIterator implements Iterator<Coordinate> {
        private int currTop = 0;
        private int currLeft = -1;

        protected MazeIterator(){

        }

        @Override
        public boolean hasNext() {
            return (currTop * getLeftCount() + currLeft)+1 < getCellCount();
//            return currTop<getTopCount() && currLeft<getLeftCount();
        }

        @Override
        public Coordinate next() {
            if(!hasNext())
                throw new IllegalStateException("There is no more cells in matrix");
            currLeft++;
            if(currLeft>=getLeftCount()){
                currLeft = 0;
                currTop++;
            }
            return new Coordinate(currTop, currLeft, matrix[currTop][currLeft]);
        }
    }

    public static final char WALL = '*';
    public static final char SPACE = ' ';

    protected char [][] matrix;

    public int getTopCount(){
        return matrix.length;
    }

    public int getLeftCount(){
        return matrix[0].length;
    }

    public int getCellCount(){
        return getTopCount() * getLeftCount();
    }

    @Override
    public Iterator<Coordinate> iterator() {
        return new MazeIterator();
    }

    protected void fillMatrixWithSpaces(){
        forEach( cor -> {
            setCell(cor.getTop(), cor.getLeft(), SPACE);
        });
    }

    public Maze(int n){
        matrix = new char[n][n];
        fillMatrixWithSpaces();
    }

    public Maze(int topCount, int leftCount){
        matrix = new char[topCount][leftCount];
        fillMatrixWithSpaces();
    }

    public Maze(char[][] matrix){
        this.matrix = matrix;
    }

    public Maze(Maze maze){
        this.matrix = maze.matrix;
    }

    protected boolean coordinateExist(int top, int left){
        return top>=0 && left>=0 && top<getTopCount() && left<getLeftCount();
    }

    protected void checkCoordinate(int top, int left){
        if(!coordinateExist(top, left)){
            throw new IllegalArgumentException("Out of range for cordinate ("+top+", "+left+")");
        }
    }

    public Coordinate getCell(int top, int left){
        checkCoordinate(top, left);
        return new Coordinate(top, left, matrix[top][left]);
    }

    public void setCell(int top, int left, char value){
        checkCoordinate(top, left);
        matrix[top][left] = value;
    }

    @Override
    public String toString(){
        var bld = new StringBuilder();
        forEach( cor -> {
            bld.append(cor.getValue()).append(" ");
            if(cor.getLeft()==getLeftCount()-1)
                bld.append("\n");
        });
        return bld.toString();
    }

    public static Maze defaultMaze1(){
        var v = WALL;
        var s = SPACE;
        var matrix = new char[][]{
            {v,v,v,v,v,v,v,v,v,v,v,v,v,v,v,v,v},
            {v,s,s,s,v,s,s,s,v,s,s,s,s,s,s,s,v},
            {v,s,s,s,v,s,v,s,v,s,v,v,v,s,s,s,v},
            {v,s,s,s,s,s,v,s,s,s,s,s,v,v,v,s,v},
            {v,v,s,v,v,s,v,v,v,v,v,s,s,s,s,s,v},
            {v,s,s,s,v,s,v,s,s,s,v,s,v,s,s,s,v},
            {v,s,s,s,v,s,v,s,s,s,v,s,v,v,v,v,v},
            {s,s,v,s,s,s,v,s,v,s,v,s,s,s,s,s,s},
            {v,v,v,v,v,s,v,s,v,v,v,v,v,s,s,s,v},
            {v,s,s,s,v,s,v,s,s,s,v,s,v,s,s,s,v},
            {v,s,s,s,v,s,s,s,v,s,s,s,v,s,s,s,v},
            {v,s,s,s,s,s,v,s,v,v,s,s,s,s,s,s,v},
            {v,s,v,v,v,v,v,v,v,v,v,v,v,v,s,v,v},
            {v,s,v,s,s,s,v,s,s,s,s,s,v,s,s,s,v},
            {v,s,s,s,v,s,s,s,s,s,v,s,s,s,s,s,v},
            {v,v,v,v,v,v,v,v,v,v,v,v,v,v,v,v,v}
        };
        return new Maze(matrix);
    }

    public static void main(String[] args) {
        var maze = defaultMaze1();
        System.out.println(maze);
    }
}
