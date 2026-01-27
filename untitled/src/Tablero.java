import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

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
        System.out.println("Iniciando el tablero, va a tener un tamaño de: "+ tam +"x"+ tam);
        IniciarMatriz(tam);
        preNeo(tam);
        preTel(tam);
        preAgen(tam);
        iniciarPartida();

    }

    private void IniciarMatriz(int tam) {
        for(int i = 0; i < tam; i++){
            ArrayList<Integer> columna = new ArrayList<>();
            for(int j = 0; j < tam; j++){
                columna.add(-1);
            }
            posiciones.add(columna);
        }
    }

    public ArrayList<Agente>  getAgentes() {
        return agentes;
    }

    public Telefono getTelefonos(int i) {
        return getTelefonos().get(i);
    }

    public Agente getAgente(int i) {
        return getAgentes().get(i);
    }

    public ArrayList<Telefono> getTelefonos() {
        return telefonos;
    }


    public boolean verificarPos(int tam, int col, int row) {
        if(tam<=col || tam<=row){
            return false;
        }
        if(posiciones.get(row).get(col)==-1){
            return true;
        }
        else{
            return false;
        }
    }

    public void preTel(int tam) {
        int telefonosColocados = 0;
        int maxIntentos = tam * tam * 2;

        while (telefonosColocados < 4 && maxIntentos > 0) {
            int c = (int)(Math.random() * tam);
            int r = (int)(Math.random() * tam);

            if (verificarPos(tam, c, r)) {
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
            int c = (int)(Math.random() * tam);
            int r = (int)(Math.random() * tam);

            if (verificarPos(tam, c, r)) {
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

    public void preNeo(int tam){
        int cantNeo = 0;
        while (cantNeo<1){
            int c = (int)(Math.random() * tam);
            int r = (int)(Math.random() * tam);
            if (verificarPos(tam, c, r)) {
                Neo neo = new Neo(c,r);
                this.neo = neo;
                posiciones.get(r).set(c,0);
                System.out.println("Neo esta en la posición: "+ c + "," + r);
                cantNeo++;
            }
        }
    }

    public void mostrarMatriz(int tam) {
        for (int row = 0; row < tam; row++) {
            for (int col = 0; col < tam; col++) {
                System.out.print(" "+ posiciones.get(col).get(row) + " ");
            }
            System.out.println(" ");
        }
    }

    public boolean fullTelefono(){
        for (int i = 0; i<telefonos.size(); i++){
            if(telefonos.get(i).isFull()){
                return true;
            }
        }
        return false;
    }

    public boolean agenteHasWon(){
        for (int i = 0; i<agentes.size(); i++){
            if(agentes.get(i).isWon()){
                return true;
            }
        }
        return false;
    }

    public void cambiarPos(int c, int r, int d, boolean isNeo) {
        if (isNeo) {
            if (d == 0) {
                if (r > 0 && posiciones.get(r - 1).get(c) == -1) {
                    neo.moveUp(c, r);
                    posiciones.get(r).set(c, -1);
                    posiciones.get(r).set(c-1, 0);
                    System.out.println("Moviendo hacia arriba");
                    for (int i = 0; i < agentes.size(); i++) {
                        getAgente(i).analizar(c, r);
                    }
                } else if (r > 0 && posiciones.get(r - 1).get(c) == 1) {
                    // para que gane agente comparar agentes por pocisiones y matar neo,
                    // tambien marcar que agente gana
                } else if (r > 0 && posiciones.get(r - 1).get(c) == 2) {
                    // Marcar para que Neo gane y el telefono se llene
                }
            }
            if (d == 1) {
                if (r < tam - 1 && posiciones.get(r + 1).get(c) == -1) {
                    neo.moveDown(c, r);
                    posiciones.get(r).set(c, -1);
                    posiciones.get(r).set(c+1, 0);
                    for (int i = 0; i < agentes.size(); i++) {
                        getAgente(i).analizar(c, r);
                    }
                } else if (r < tam - 1 && posiciones.get(r + 1).get(c) == 1) {
                    // para que gane agente comparar agentes por pocisiones y matar neo,
                    // tambien marcar que agente gana
                } else if (r < tam - 1 && posiciones.get(r + 1).get(c) == 2) {
                    // Marcar para que Neo gane y el telefono se llene
                }
            }
            if (d == 2) {
                if (c > 0 && posiciones.get(r).get(c - 1) == -1) {
                    neo.moveLeft(c, r);
                    posiciones.get(r).set(c, -1);
                    posiciones.get(r-1).set(c, 0);
                    for (int i = 0; i < agentes.size(); i++) {
                        getAgente(i).analizar(c, r);
                    }
                } else if (c > 0 && posiciones.get(r).get(c - 1) == 1) {
                    // para que gane agente comparar agentes por pocisiones y matar neo,
                    // tambien marcar que agente gana
                } else if (c > 0 && posiciones.get(r).get(c - 1) == 2) {
                    // Marcar para que Neo gane y el telefono se llene
                }
            }
            if (d == 3) {
                if (c < tam - 1 && posiciones.get(r).get(c + 1) == -1) {
                    neo.moveRight(c, r);
                    posiciones.get(r).set(c, -1);
                    posiciones.get(r+1).set(c , 0);
                    for (int i = 0; i < agentes.size(); i++) {
                        getAgente(i).analizar(c, r);
                    }
                } else if (c < tam - 1 && posiciones.get(r).get(c + 1) == 1) {
                    // para que gane agente comparar agentes por pocisiones y matar neo,
                    // tambien marcar que agente gana
                } else if (c < tam - 1 && posiciones.get(r).get(c + 1) == 2) {
                    // Marcar para que Neo gane y el telefono se llene
                }
            }
        }
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
                cambiarPos(neo.getCol(), neo.getRow(), d, true);
                mostrarMatriz(tam);
            } else {
                System.out.println("Dirección no válida. Usa: 0=arriba, 1=abajo, 2=izquierda, 3=derecha");
            }

        }
    }

    public static void main(String[] args) {
        int tam = 8;
        Tablero tablero = new Tablero(tam);

    }

}
