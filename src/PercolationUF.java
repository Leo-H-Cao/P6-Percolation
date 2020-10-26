import java.util.Arrays;

public class PercolationUF implements IPercolate {
    private boolean[][] myGrid;
    private int myOpenCount;
    private IUnionFind myFinder;
    private final int VTOP;
    private final int VBOTTOM;

    PercolationUF(IUnionFind finder, int size){
        myGrid = new boolean[size][size];
        finder.initialize(size*size + 2);
        myFinder = finder;
        myOpenCount = 0;
        VTOP = size*size;
        VBOTTOM = size*size+1;
        for (boolean[] row : myGrid)
            Arrays.fill(row, false);
    }

    @Override
    public void open(int row, int col) {
        if (! inBounds(row,col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        if (myGrid[row][col] != false)
            return;
        myOpenCount++;
        myGrid[row][col] = true;
        if(row == 0)
            myFinder.union(row*myGrid.length + col, VTOP);
        if(row == myGrid.length-1)
            myFinder.union(row*myGrid.length + col, VBOTTOM);
        if(inBounds(row - 1, col) && isOpen(row - 1, col))
            myFinder.union(row*myGrid.length + col,(row-1)*myGrid.length + col );
        if(inBounds(row + 1, col) && isOpen(row + 1, col))
            myFinder.union(row*myGrid.length + col,(row+1)*myGrid.length + col );
        if(inBounds(row, col - 1) && isOpen(row, col - 1))
            myFinder.union(row*myGrid.length + col,row*myGrid.length + col-1);
        if(inBounds(row, col + 1) && isOpen(row, col + 1))
            myFinder.union(row*myGrid.length + col,row*myGrid.length + col+1);
    }

    @Override
    public boolean isOpen(int row, int col) {
        if (!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row, col));
        }
        return myGrid[row][col];
    }

    @Override
    public boolean isFull(int row, int col) {
        if (!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row, col));
        }
        int cell =  row*myGrid.length + col;
        return myFinder.connected(cell,VTOP);
    }

    @Override
    public boolean percolates() {
        return myFinder.connected(VTOP, VBOTTOM);
    }

    @Override
    public int numberOfOpenSites() {
        return myOpenCount;
    }
    /**
     * Determine if (row,col) is valid for given grid
     * @param row specifies row
     * @param col specifies column
     * @return true if (row,col) on grid, false otherwise
     */
    protected boolean inBounds(int row, int col) {
        if (row < 0 || row >= myGrid.length) return false;
        if (col < 0 || col >= myGrid[0].length) return false;
        return true;
    }
}
