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

    public synchronized boolean mover(int direccion, Tablero tablero) {
        if (!isAlive) {
            return false;
        }

        int nuevaCol = col;
        int nuevaRow = row;

        if (direccion == 0) {
            if (row > 0) {
                nuevaRow = row - 1;
            } else {
                System.out.println("Movimiento inválido: Neo no puede salirse del tablero (arriba)");
                return false;
            }
        }
        else if (direccion == 1) {
            if (row < tablero.getTam() - 1) {
                nuevaRow = row + 1;
            } else {
                System.out.println("Movimiento inválido: Neo no puede salirse del tablero (abajo)");
                return false;
            }
        }
        else if (direccion == 2) {
            if (col > 0) {
                nuevaCol = col - 1;
            } else {
                System.out.println("Movimiento inválido: Neo no puede salirse del tablero (izquierda)");
                return false;
            }
        }
        else if (direccion == 3) {
            if (col < tablero.getTam() - 1) {
                nuevaCol = col + 1;
            } else {
                System.out.println("Movimiento inválido: Neo no puede salirse del tablero (derecha)");
                return false;
            }
        }
        else {
            return false;
        }

        int valorNuevaPos = tablero.getValorCasilla(nuevaRow, nuevaCol);

        if (valorNuevaPos == -1) {
            tablero.actualizarPosicion(col, row, nuevaCol, nuevaRow, 0);
            this.col = nuevaCol;
            this.row = nuevaRow;

            if (direccion == 0) {
                System.out.println("Moviendo hacia arriba");
            }
            else if (direccion == 1) {
                System.out.println("Moviendo hacia abajo");
            }
            else if (direccion == 2) {
                System.out.println("Moviendo hacia izquierda");
            }
            else if (direccion == 3) {
                System.out.println("Moviendo hacia derecha");
            }

            tablero.moverAgentes();
            return true;
        } else if (valorNuevaPos == 1) {
            System.out.println("¡Agente atrapó a Neo!");
            kill();
            for (Agente agente : tablero.getAgentes()) {
                if (agente.getCol() == nuevaCol && agente.getRow() == nuevaRow) {
                    agente.setWon(true);
                }
            }
            return true;
        } else if (valorNuevaPos == 2) {
            System.out.println("¡Neo alcanzó un teléfono y gana!");
            for (Telefono telefono : tablero.getTelefonos()) {
                if (telefono.getCol() == nuevaCol && telefono.getRow() == nuevaRow) {
                    telefono.setIsFull(true);
                }
            }
            return true;
        }

        return false;
    }
}