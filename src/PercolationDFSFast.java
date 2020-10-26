public class PercolationDFSFast extends PercolationDFS{
    /**
     * Initialize a grid so that all cells are blocked.
     *
     * @param n is the size of the simulated (square) grid
     */
    public PercolationDFSFast(int n) {
        super(n);
    }

    /**
     * Overrides inefficient PercolationDFS's updateOnOpen,
     * calls dfs once if the newly open cell should be marked as FULL because it's in the
     * top row or because it's adjacent to an already FULL cell.
     * @param row is the row coordinate of the cell being checked/marked
     * @param col is the col coordinate of the cell being checked/marked
     */
    @Override
    protected void updateOnOpen(int row, int col) {
        if (row == 0)
            dfs(row, col);
        else if(inBounds(row-1, col) && isFull(row-1, col))
            dfs(row,col);
        else if(inBounds(row+1, col) && isFull(row+1, col))
            dfs(row,col);
        else if(inBounds(row, col - 1) && isFull(row, col - 1))
            dfs(row,col);
        else if(inBounds(row, col + 1) && isFull(row, col + 1))
            dfs(row,col);
    }
}
