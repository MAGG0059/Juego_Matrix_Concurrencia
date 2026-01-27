public class Telefono {

    private int col;
    private int row;
    private boolean isFull;

    public Telefono(int col, int row) {
        this.isFull = false;
        this.col = col;
        this.row = row;
    }

    public int  getCol() {
        return col;
    }
    public int getRow() {
        return row;
    }

    public boolean isFull() {
        return isFull;
    }

    public void  setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setIsFull(boolean isFull) {
        this.isFull = isFull;
    }
}
