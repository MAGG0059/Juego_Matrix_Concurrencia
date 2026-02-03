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

    public synchronized void mover(Tablero tablero) {
        int neoCol = 0;
        int neoRow = 0;

        Neo neo = tablero.getNeo();
        if (neo != null) {
            neoCol = neo.getCol();
            neoRow = neo.getRow();
        }

        if (this.col == neoCol && this.row == neoRow) {
            this.won = true;
            neo.kill();
            return;
        }

        int direccion = movimientoGreedy(neoCol, neoRow);

        if (direccion != -1) {
            int nuevaCol = col;
            int nuevaRow = row;

            if (direccion == 0 && row > 0) {
                nuevaRow = row - 1;
            }
            else if (direccion == 1 && row < tablero.getTam() - 1) {
                nuevaRow = row + 1;
            }
            else if (direccion == 2 && col > 0) {
                nuevaCol = col - 1;
            }
            else if (direccion == 3 && col < tablero.getTam() - 1) {
                nuevaCol = col + 1;
            }

            if (nuevaCol != col || nuevaRow != row) {
                int valorNuevaPos = tablero.getValorCasilla(nuevaRow, nuevaCol);

                if (valorNuevaPos == -1 || (nuevaCol == neoCol && nuevaRow == neoRow)) {
                    tablero.actualizarPosicion(col, row, nuevaCol, nuevaRow, 1);

                    synchronized(lock) {
                        this.col = nuevaCol;
                        this.row = nuevaRow;
                    }

                    System.out.println("Agente movido de (" + col + "," + row + ") a (" + nuevaCol + "," + nuevaRow + ")");

                    if (nuevaCol == neoCol && nuevaRow == neoRow) {
                        this.won = true;
                        neo.kill();
                        System.out.println("¡Agente atrapó a Neo!");
                    }
                }
            }
        }
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