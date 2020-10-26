import java.util.LinkedList;
import java.util.Queue;

public class PercolationBFS extends PercolationDFSFast{

    /**
     * Initialize a grid so that all cells are blocked.
     *
     * @param n is the size of the simulated (square) grid
     */
    public PercolationBFS(int n) {
        super(n);
    }

    /**
     * Marks all cells that are open and reachable from (row,col),
     * using a Queue and a breadth-first-search (BFS) approach.
     * @param row is the row coordinate of the cell being checked/marked
     * @param col is the col coordinate of the cell being checked/marked
     */
    @Override
    protected void dfs(int row, int col) {
        int[] rowDelta = {-1,1,0,0};
        int[] colDelta = {0,0,-1,1};

        Queue<int[]> qp = new LinkedList<>();
        qp.add(new int[]{row,col});
        myGrid[row][col] = FULL;
        while(qp.size() > 0){
            int[] p = qp.remove();
            for(int i = 0; i<4; i++) {
                row = p[0] + rowDelta[i];
                col = p[1] + colDelta[i];
                if (inBounds(row, col) && !(isFull(row, col)) && isOpen(row, col)) {
                    qp.add(new int[]{row, col});
                    myGrid[row][col] = FULL;
                }
            }
        }
    }
}
