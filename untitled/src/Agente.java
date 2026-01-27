public class Agente extends Thread{

    private int col;
    private int row;
    private boolean won;

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

    public void analizar(int c, int r) {

    }
}
