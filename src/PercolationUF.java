import java.util.Arrays;

public class PercolationUF implements IPercolate {
    private boolean[][] myGrid;
    private int myOpenCount;
    private IUnionFind myFinder;
    private final int VTOP;
    private final int VBOTTOM;

    /**
     * Initialize a grid so that no cells are open, initialize and store finder in myFinder
     * set values for final values VTOP and VBOTTOM
     * @param finder IUnionFind object to keep track of open and full cells
     * @param size is the size of the simulated (square) grid
     */
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

    /**
     * Open site (row, col) if it is not already open. By convention, (0, 0)
     * is the upper-left site
     *
     * The method modifies internal state so that determining if percolation
     * occurs could change after taking a step in the simulation.
     *
     * throws IndexOutOfBoundsException when row or col is not in bounds,
     * checks to make sure the cell is not already open,and then marks the cell as open
     * and connects with open neighbors using myFinder
     *
     * @param row
     *            row index in range [0,N-1]
     * @param col
     *            column index in range [0,N-1]
     */
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

    /**
     * Checks if site (row, col) is open by returning the appropriate myGrid value.
     *
     * @param row
     *            row index in range [0,N-1]
     * @param col
     *            column index in range [0,N-1]
     */
    @Override
    public boolean isOpen(int row, int col) {
        if (!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row, col));
        }
        return myGrid[row][col];
    }

    /**
     * Checks if the (row,col) cell is full by checking if it is connected to VTOP using myFinder
     *
     * @param row
     *            row index in range [0,N-1]
     * @param col
     *            column index in range [0,N-1]
     */
    @Override
    public boolean isFull(int row, int col) {
        if (!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row, col));
        }
        int cell =  row*myGrid.length + col;
        return myFinder.connected(cell,VTOP);
    }

    /**
     * Returns true if the simulated percolation actually percolates
     * by checking if VTOP is connected to VBOTTOM using myFinder.
     *
     * @return true iff the simulated system percolates
     */
    @Override
    public boolean percolates() {
        return myFinder.connected(VTOP, VBOTTOM);
    }

    /**
     * returns myOpenCount
     * @return the number of open cells in the grid
     */
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
