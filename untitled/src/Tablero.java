import java.util.ArrayList;
import java.util.Scanner;

public class Tablero {
    private int tam;
    private Neo neo;
    private ArrayList<ArrayList<Integer>> posiciones;
    private ArrayList<Agente> agentes;
    private ArrayList<Telefono> telefonos;

    public Tablero(int tam) {
        this.tam = tam;
        this.agentes = new ArrayList<>();
        this.telefonos = new ArrayList<>();
        this.posiciones = new ArrayList<>();
        iniciarTablero(tam);
    }

    private void iniciarTablero(int tam) {
        System.out.println("Iniciando el tablero, va a tener un tamaño de: " + tam + "x" + tam);
        IniciarMatriz(tam);
        preNeo(tam);
        preTel(tam);
        preAgen(tam);
        iniciarPartida();
    }

    private void IniciarMatriz(int tam) {
        for (int i = 0; i < tam; i++) {
            ArrayList<Integer> columna = new ArrayList<>();
            for (int j = 0; j < tam; j++) {
                columna.add(-1);
            }
            posiciones.add(columna);
        }
    }

    public ArrayList<Agente> getAgentes() {
        return agentes;
    }

    public Telefono getTelefono(int i) {
        return telefonos.get(i);
    }

    public Agente getAgente(int i) {
        return agentes.get(i);
    }

    public ArrayList<Telefono> getTelefonos() {
        return telefonos;
    }

    public int getTam() {
        return tam;
    }

    public Neo getNeo() {
        return neo;
    }

    public synchronized boolean verificarPos(int col, int row) {
        if (tam <= col || tam <= row || col < 0 || row < 0) {
            return false;
        }
        return posiciones.get(row).get(col) == -1;
    }

    public synchronized void moverAgentes() {
        System.out.println("Moviendo agentes... Neo está en (" + neo.getCol() + "," + neo.getRow() + ")");

        for (Agente agente : agentes) {
            System.out.println("Agente en (" + agente.getCol() + "," + agente.getRow() + ")");

            if (!agente.isWon() && neo.stillAlive() && !fullTelefono()) {
                agente.mover(this);
            }
        }
    }

    public synchronized int getValorCasilla(int row, int col) {
        if (row >= 0 && row < tam && col >= 0 && col < tam) {
            return posiciones.get(row).get(col);
        }
        return -2;
    }

    public synchronized void actualizarPosicion(int colAnt, int rowAnt, int colNuevo, int rowNuevo, int tipo) {
        if (colAnt >= 0 && rowAnt >= 0 && colAnt < tam && rowAnt < tam) {
            posiciones.get(rowAnt).set(colAnt, -1);
        }

        if (colNuevo >= 0 && rowNuevo >= 0 && colNuevo < tam && rowNuevo < tam) {
            posiciones.get(rowNuevo).set(colNuevo, tipo);
        }
    }

    public synchronized boolean esTelefono(int col, int row) {
        if (col < 0 || row < 0 || col >= tam || row >= tam) {
            return false;
        }
        return posiciones.get(row).get(col) == 2;
    }

    public void preTel(int tam) {
        int telefonosColocados = 0;
        int maxIntentos = tam * tam * 2;

        while (telefonosColocados < 4 && maxIntentos > 0) {
            int c = (int) (Math.random() * tam);
            int r = (int) (Math.random() * tam);

            if (verificarPos(c, r)) {
                Telefono telefono = new Telefono(c, r);
                telefonos.add(telefono);
                posiciones.get(r).set(c, 2);
                System.out.println("Teléfono Ubicado en la posición: " + c + "," + r);
                telefonosColocados++;
            }
            maxIntentos--;
        }
        if (telefonosColocados < 4) {
            System.out.println("Advertencia: Solo se colocaron " + telefonosColocados + " teléfonos");
        }
    }

    public void preAgen(int tam) {
        int agentesColocados = 0;
        int maxIntentos = tam * tam * 2;

        while (agentesColocados < 2 && maxIntentos > 0) {
            int c = (int) (Math.random() * tam);
            int r = (int) (Math.random() * tam);

            if (verificarPos(c, r)) {
                Agente agente = new Agente(c, r);
                agentes.add(agente);
                posiciones.get(r).set(c, 1);
                System.out.println("Agente Ubicado en la posición: " + c + "," + r);
                agentesColocados++;
            }
            maxIntentos--;
        }
        if (agentesColocados < 2) {
            System.out.println("Advertencia: Solo se colocaron " + agentesColocados + " agentes");
        }
    }

    public void preNeo(int tam) {
        int cantNeo = 0;
        while (cantNeo < 1) {
            int c = (int) (Math.random() * tam);
            int r = (int) (Math.random() * tam);
            if (verificarPos(c, r)) {
                Neo neo = new Neo(c, r);
                this.neo = neo;
                posiciones.get(r).set(c, 0);
                System.out.println("Neo esta en la posición: " + c + "," + r);
                cantNeo++;
            }
        }
    }

    public void mostrarMatriz(int tam) {
        for (int row = 0; row < tam; row++) {
            for (int col = 0; col < tam; col++) {
                System.out.print(" " + posiciones.get(row).get(col) + " ");
            }
            System.out.println();
        }
    }

    public boolean fullTelefono() {
        for (int i = 0; i < telefonos.size(); i++) {
            if (telefonos.get(i).isFull()) {
                return true;
            }
        }
        return false;
    }

    public boolean agenteHasWon() {
        for (int i = 0; i < agentes.size(); i++) {
            if (agentes.get(i).isWon()) {
                return true;
            }
        }
        return false;
    }

    public synchronized boolean moverNeo(int direccion) {
        return neo.mover(direccion, this);
    }

    public void iniciarPartida() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Iniciando partida");
        mostrarMatriz(tam);
        System.out.println("Bienvenido en el tablero neo es el 0");
        System.out.println("Recuerda que solo puedes moverte una casilla hacia arriba(0), hacia abajo(1)," +
                " hacia la izquierda(2) o hacia la derecha(3)");

        while (this.neo.stillAlive() && !fullTelefono() && !agenteHasWon()) {
            int d = sc.nextInt();
            if (d >= 0 && d <= 3) {
                if (moverNeo(d)) {
                    mostrarMatriz(tam);
                }

                if (fullTelefono()) {
                    System.out.println("¡Neo ganó al alcanzar un teléfono!");
                } else if (agenteHasWon()) {
                    System.out.println("¡Los agentes ganaron al atrapar a Neo!");
                }

            } else {
                System.out.println("Dirección no válida. Usa: 0=arriba, 1=abajo, 2=izquierda, 3=derecha");
            }
        }
        sc.close();
    }

    public static void main(String[] args) {
        int tam = 8;
        Tablero tablero = new Tablero(tam);
    }
}