public class Neo extends Thread {

    private int col;
    private int row;
    private boolean isAlive;

    public Neo(int col, int row) {
        this.col = col;
        this.row = row;
        this.isAlive = true;
    }

    public void kill() {
        isAlive = false;
    }

    public boolean stillAlive(){
        return isAlive;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void moveUp(int c, int r) {
        this.row = r - 1;
        this.col = c;
    }

    public void moveDown(int c, int r) {
        this.row = r + 1;
        this.col = c;
    }

    public void moveLeft(int c, int r) {
        this.col = c - 1;
        this.row = r;
    }

    public void moveRight(int c, int r) {
        this.col = c + 1;
        this.row = r;
    }
}
