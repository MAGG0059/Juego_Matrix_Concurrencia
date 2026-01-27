public class Agente extends Thread {
    private int col;
    private int row;
    private boolean won;
    private static final Object lock = new Object();

    public Agente(int col, int row) {
        this.col = col;
        this.row = row;
        this.won = false;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public boolean isWon() {
        return won;
    }

    public int getCol() {
        return this.col;
    }

    public int getRow() {
        return row;
    }

    public void setPosicion(int col, int row) {
        synchronized(lock) {
            this.col = col;
            this.row = row;
        }
    }

    public synchronized int analizar(int neoCol, int neoRow) {
        if (this.col == neoCol && this.row == neoRow) {
            this.won = true;
            return -1;
        }

        return movimientoGreedy(neoCol, neoRow);
    }

    private int movimientoGreedy(int destinoCol, int destinoRow) {
        int difFila = destinoRow - this.row;
        int difCol = destinoCol - this.col;

        if (Math.abs(difFila) >= Math.abs(difCol)) {
            if (difFila > 0) {
                return 1;
            } else if (difFila < 0) {
                return 0;
            }
        }

        if (difCol > 0) {
            return 3;
        } else if (difCol < 0) {
            return 2;
        }

        return -1;
    }
}