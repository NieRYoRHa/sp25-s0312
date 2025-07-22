import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // TODO: Add any necessary instance variables.
    private WeightedQuickUnionUF openUF;
    private WeightedQuickUnionUF percolationUF;
    private boolean PercolationStatus;

    private int siteL;
    private int siteS;
    private int percolatonIndex;
    private int openNum;



    public Percolation(int N) {
        // TODO: Fill in this constructor.
        siteL =N;
        siteS=N*N+1;
        percolatonIndex=N*N;
        openUF = new WeightedQuickUnionUF(siteS);
        percolationUF = new WeightedQuickUnionUF(siteS);
        for(int n=0;n<N;n++){
            percolationUF.union(n,percolatonIndex);
        }
        PercolationStatus=false;
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        if(!isOpen(row, col)){
            openNum++;
        }
        int index =(row * siteL) +col;
        openUF.union(index,percolatonIndex);
        neighbourCombine(row,col);
    }

    public void neighbourCombine(int row,int col){
        int index =(row * siteL) +col;
        //has up
        if (row>0){
            int up=((row-1) * siteL) +col;
            if(isOpen(row-1,col)){
                percolationUF.union(index,up);
                if(isFull(row-1,col)){
                    percolationUF.union(index,percolatonIndex);
                }
            }

        }
        //has down
        if (row<siteL-1){
            int down=((row+1) * siteL) +col;
            if(isOpen(row+1,col)){
                percolationUF.union(index,down);
                if(isFull(row+1,col)){
                    percolationUF.union(index,percolatonIndex);
                }
            }

        }
        //has left
        if (col>0){
            int left =(row * siteL) +col-1;
            if(isOpen(row,col-1)){
                percolationUF.union(index,left);
                if(isFull(row,col-1)){
                    percolationUF.union(index,percolatonIndex);
                }
            }

        }
        //has right
        if (col<siteL-1){
            int right =(row * siteL) +col+1;
            if(isOpen(row,col+1)){
                percolationUF.union(index,right);
                if(isFull(row,col+1)){
                    percolationUF.union(index,percolatonIndex);
                }
            }

        }

    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        int index =(row * siteL) +col;
        return openUF.connected(index,percolatonIndex);
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        int index =(row * siteL) +col;
        return percolationUF.connected(index,percolatonIndex) && isOpen(row, col);
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return openNum;
    }

    public boolean percolates() {
        // TODO: Fill in this method.

        for (int i=0;i<siteL;i++){
            if(percolationUF.connected(siteL*(siteL-1)+i,percolatonIndex)){
                PercolationStatus=true;
            }
        }
        return PercolationStatus;

    }


    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.
    public static void main(String[] args) {
        int n = 35;
        int time =0;
        System.out.print(n+" ");
        while (true) {

            if (n % 2 == 1) {

                for (int i = 4; n % 2 != 0; i--) {
                    n += i;
                    System.out.print(n+" ");
                }
            } else {

                n = n / 2;
                System.out.print(n+" ");

            }
            if (time == 10) {
                return;
            }
            time+=1;
        }
    }
}
