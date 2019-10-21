package tablero;

import java.awt.Image;
import static java.lang.Math.abs;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class WayPoint {

    panelMenu pm;

    int[][] tablaMatriz;
    JLabel matrizJ[][];
    int filaA, columnaA, direccionA;
    ImageIcon bloqueBlanco;
    Icon iconoBlanco;
    ImageIcon camino;
    Icon iconoCamino;
    int filaM;
    int columnaM;
    
    int[][] tablaRecorrido;
    String[][] tablaModulo;
    int inicioFila, inicioColumna;
    int finFila, finColumna;
    int filaCero, columnaCero;
    ArrayList<Integer> fila;
    ArrayList<Integer> columna;
    ArrayList<String> recorrido;
    ArrayList<String> arregloRecorrido;
    String[][] matrizModular;
    ArrayList<Integer> filaModulada;
    ArrayList<Integer> columnaModulada;
    ArrayList<Integer> filaMod;
    ArrayList<Integer> columnaMod;
    ArrayList<Integer> ff;
    ArrayList<Integer> cc;
    boolean ejecutar = false;
    Hilo h;

    public WayPoint(int[][] matriz, JLabel[][] matrizJuego, String[][] matrizModulo, panelMenu pm) {
       this.pm = pm;
        matrizJ = matrizJuego;
        tablaMatriz = matriz;
        filaA = 0;
        columnaA = 0;
        direccionA = 4;
        filaM = 0;
        columnaM = 0;
              
        fila = new ArrayList<>();
        columna = new ArrayList<>();

        filaModulada = new ArrayList<>();
        columnaModulada = new ArrayList<>();

        recorrido = new ArrayList<>();
        arregloRecorrido = new ArrayList<>();
        tablaRecorrido = matriz;
        tablaModulo = matrizModulo;
        inicioFila = 0;
        inicioColumna = 0;
        finFila = 0;
        finColumna = 0;
        inicio();
        figuraCuadros();
        fin();
        //B();
        Bresenhman(inicioFila,inicioColumna, finFila, finColumna, fila, columna);
        //BresInicial();
        h = new Hilo();
        h.start();
    }

    public void inicio() {
        for (int i = 0; i < tablaMatriz.length; i++) {
            for (int j = 0; j < tablaMatriz[i].length; j++) {
                if (tablaMatriz[i][j] == 2) {
                    inicioFila = i;
                    inicioColumna = j;

                }
            }
        }
    }
    
    public void detener(){
    	h.stop();
    }

    public void fin() {
        for (int i = 0; i < tablaMatriz.length; i++) {
            for (int j = 0; j < tablaMatriz[i].length; j++) {
                if (tablaMatriz[i][j] == 5) {
                    finFila = i;
                    finColumna = j;
                }
            }
        }
    }
    
    public void figuraCuadros() {
        bloqueBlanco = new ImageIcon(getClass().getResource("/img/bloqueBlanco.png"));
        iconoBlanco = new ImageIcon(bloqueBlanco.getImage().getScaledInstance(matrizJ[filaA][columnaA].getWidth(),
                matrizJ[filaA][columnaA].getHeight(), Image.SCALE_DEFAULT));


        camino = new ImageIcon(getClass().getResource("/img/recorrido.png"));
        iconoCamino = new ImageIcon(camino.getImage().getScaledInstance(matrizJ[filaA][columnaA].getWidth(),
                matrizJ[filaA][columnaA].getHeight(), Image.SCALE_DEFAULT));
    }
    

    
    public void Bresenhman(int inicF, int inicCol, int finFil, int finColum, ArrayList<Integer> f, ArrayList<Integer> c ) {
        int nextCol = inicCol;
        int nextRow = inicF;
        int deltaRow = finFil - inicF;
        int deltaCol = finColum - inicCol;
        int stepCol, stepRow;
        int currentStep, fraction;
        currentStep = 0;

        if (deltaRow < 0) {
            stepRow = -1;
        } else {
            stepRow = 1;
        }
        if (deltaCol < 0) {
            stepCol = -1;
        } else {
            stepCol = 1;
        }
        deltaRow = abs(deltaRow * 2);
        deltaCol = abs(deltaCol * 2);
        f.add(currentStep, nextRow);
        c.add(currentStep, nextCol);
        currentStep++;

        if (deltaCol > deltaRow) {
            fraction = deltaRow * 2 - deltaCol;
            while (nextCol != finColum) {
                if (fraction >= 0) {
                    nextRow = nextRow + stepRow;
                    fraction = fraction - deltaCol;
                }
                nextCol = nextCol + stepCol;
                fraction = fraction + deltaRow;
                f.add(currentStep, nextRow);
                c.add(currentStep, nextCol);
                currentStep++;
            }
        } else {
            fraction = deltaCol * 2 - deltaRow;
            while (nextRow != finFil) {
                if (fraction >= 0) {
                    nextCol = nextCol + stepCol;
                    fraction = fraction - deltaRow;
                }
                nextRow = nextRow + stepRow;
                fraction = fraction + deltaCol;
                f.add(currentStep, nextRow);
                c.add(currentStep, nextCol);
                currentStep++;
            }
        }
    }

    public void recorridoTotal() {
        for (int i = 0; i < filaMod.size() - 1; i++) {
            Bresenhman(filaMod.get(i + 1), columnaMod.get(i + 1), filaMod.get(i), columnaMod.get(i), filaModulada, columnaModulada);
        }
    }

    //Define las letras en la matriz de nodos
    public ArrayList<String> cantLetrasModulos() {
        ArrayList<String> letras = new ArrayList<>();
        for (int i = 0; i < tablaModulo.length; i++) {
            for (int j = 0; j < tablaModulo[i].length; j++) {
                if (!tablaModulo[i][j].equals("1")) {
                    letras.add(tablaModulo[i][j]);
                }
            }
        }
        return letras;
    }

    //Crea arreglo de todas las letras de la matriz de nodos sin repetir
    public ArrayList<String> arregloSinRepetir(ArrayList<String> arreglo) {
        ArrayList<String> arregloTotal = new ArrayList<>();
        for (int i = 0; i < arreglo.size(); i++) {
            String a = arreglo.get(i);
            if (!a.equals("1")) {
                arregloTotal.add(a);
            }
        }
        int d = arregloTotal.size();
        String[] camino = new String[d];
        for (int i = 0; i < camino.length; i++) {
            camino[i] = arregloTotal.get(i);
        }

        for (int i = 0; i < camino.length; i++) {
            for (int j = 0; j < camino.length - 1; j++) {
                if (i != j) {
                    if (camino[i].equals(camino[j])) {
                        camino[j] = "";
                    }
                }
            }
        }
        arregloTotal.clear();
        for (int i = 0; i < camino.length; i++) {
            if (!camino[i].equals("")) {
                arregloTotal.add(camino[i]);
            }
        }
        return arregloTotal;
    }
    
    //Funcion que cuenta las letras en la matriz de nodos
    public int cantLetrasMod() {
        //C:\Users\Natalia\Desktop\AA.txt
        int cantidad;
        ArrayList<String> arreg;
        arreg = arregloSinRepetir(cantLetrasModulos());
        cantidad = arreg.size();
        System.out.print("A " + cantidad);
        return cantidad;
    }

    //Recorrido del agente segun la matriz de conexiones de nodos
    public ArrayList<String> recorridoModulos() {
        int c = cantLetrasMod() + 1;
        matrizModular = new String[c][c];
        arregloRecorrido = arregloSinRepetir(recorrido);
        matrizModular = pm.llenarMatrizMod(this);
        ArrayList<String> r = new ArrayList<>();
        ArrayList<String> aux = new ArrayList<>();
        int x = 0;
        int y = 0;
        aux.add(arregloRecorrido.get(0));
        r.add(arregloRecorrido.get(0));
        int j = 0;
        while (!aux.get(aux.size() - 1).equals(arregloRecorrido.get(arregloRecorrido.size() - 1))) {
            for (int i = 0; i < c; i++) {
                if ((aux.get(j).equals(matrizModular[i][0]))) {
                    x = i;
                }
                if (arregloRecorrido.get(arregloRecorrido.size() - 1).equals(matrizModular[0][i])) {
                    y = i;
                }
            }
            aux.add(matrizModular[x][y]);
            r.add(matrizModular[x][y]);
            j++;
        }
        aux.add(arregloRecorrido.get(arregloRecorrido.size() - 1));
        return r;
    }

    //Puertas de cada nodo a recorrer
    public void puertasNodos() {
        ArrayList<String> r = recorridoModulos();
        int z = 1;
        filaMod = new ArrayList<>();
        columnaMod = new ArrayList<>();
        filaMod.add(inicioFila);
        columnaMod.add(inicioColumna);
        while (z < r.size()) {
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    if (tablaModulo[i][j].equals(r.get(z))) {
                        if (tablaModulo[i][j + 1].equals(r.get(z - 1))) {
                            filaMod.add(i);
                            columnaMod.add(j + 1);
                            filaMod.add(i);
                            columnaMod.add(j);
                            if(!tablaModulo[i][j - 1].equals("1")){
                               filaMod.add(i);
                               columnaMod.add(j-1); 
                            }
                            i = 20;
                            j = 20;
                        } else if (tablaModulo[i][j - 1].equals(r.get(z - 1))) {
                            filaMod.add(i);
                            columnaMod.add(j - 1);
                            filaMod.add(i);
                            columnaMod.add(j);
                            if(!tablaModulo[i][j + 1].equals("1")){
                               filaMod.add(i);
                               columnaMod.add(j+1); 
                            }
                            i = 20;
                            j = 20;
                        } else if (tablaModulo[i + 1][j].equals(r.get(z - 1))) {
                            filaMod.add(i + 1);
                            columnaMod.add(j);
                            filaMod.add(i);
                            columnaMod.add(j);
                            if(!tablaModulo[i-1][j].equals("1")){
                               filaMod.add(i-1);
                               columnaMod.add(j); 
                            }
                            i = 20;
                            j = 20;
                        } else if (tablaModulo[i - 1][j].equals(r.get(z - 1))) {
                            filaMod.add(i - 1);
                            columnaMod.add(j);
                            filaMod.add(i);
                            columnaMod.add(j);
                            if(!tablaModulo[i-1][j].equals("1")){
                               filaMod.add(i-1);
                               columnaMod.add(j); 
                            }
                            i = 20;
                            j = 20;
                        } else if (tablaModulo[i - 1][j - 1].equals(r.get(z - 1))) {
                            filaMod.add(i - 1);
                            columnaMod.add(j - 1);
                            filaMod.add(i);
                            columnaMod.add(j);
                            if(!tablaModulo[i+1][j+1].equals("1")){
                               filaMod.add(i+1);
                               columnaMod.add(j+1); 
                            }
                            i = 20;
                            j = 20;
                        } else if (tablaModulo[i + 1][j + 1].equals(r.get(z - 1))) {
                            filaMod.add(i - 1);
                            columnaMod.add(j + 1);
                            filaMod.add(i);
                            columnaMod.add(j);
                            if(!tablaModulo[i-1][j-1].equals("1")){
                               filaMod.add(i-1);
                               columnaMod.add(j-1); 
                            }
                            i = 20;
                            j = 20;
                        } else if (tablaModulo[i + 1][j - 1].equals(r.get(z - 1))) {
                            filaMod.add(i + 1);
                            columnaMod.add(j - 1);
                            filaMod.add(i);
                            columnaMod.add(j);
                            if(!tablaModulo[i-1][j+1].equals("1")){
                               filaMod.add(i-1);
                               columnaMod.add(j+1); 
                            }
                            i = 20;
                            j = 20;
                        }
                    }
                }
            }
            z++;

        }
        filaMod.add(finFila);
        columnaMod.add(finColumna);
        for(int i=0; i<filaMod.size();i++){
            System.out.println("F: "+ filaMod.get(i)+" C: " +columnaMod.get(i)  );
        }
    }

   public void validar(){
       ff = new ArrayList<>();
        cc = new ArrayList<>();

        for (int i = filaModulada.size() - 1; i >= 0; i--) {
            int f = filaModulada.get(i);
            int c = columnaModulada.get(i);
        if(tablaMatriz[f][c] == 0){
        ff.add(f);
        cc.add(c);       
        }
   
   if(tablaMatriz[f][c] == 1){
        if (tablaMatriz[f][c-1]==0){
            ff.add(f);
            cc.add(c-1);
        }
        else if (tablaMatriz[f][c+1]==0){
            ff.add(f);
            cc.add(c+1);
        }
        else if (tablaMatriz[f-1][c]==0){
            ff.add(f-1);
            cc.add(c);
        }
        else if (tablaMatriz[f+1][c]==0){
            ff.add(f+1);
            cc.add(c);
        }}
   }} 

    public class Hilo extends Thread {

        public void run() {
        		try {
                    for (int i = 0; i < fila.size(); i++) {
                        int f = fila.get(i);
                        int c = columna.get(i);
                        recorrido.add(tablaModulo[f][c]);
                    }
                    puertasNodos();
                    recorridoTotal();
                    validar();
                    Icon ndi = matrizJ[inicioFila][inicioColumna].getIcon();
                    for (int i = 0; i < ff.size(); i++) {
                        int f = ff.get(i);
                        int c = cc.get(i);
                        matrizJ[f][c].setIcon(ndi);

                        Thread.sleep(1000);
                        matrizJ[inicioFila][inicioColumna].setIcon(iconoCamino);    
                            if (matrizJ[f][c].getIcon().equals(ndi)) {
                                matrizJ[f][c].setIcon(iconoCamino);
                            }
                    } matrizJ[finFila][finColumna].setIcon(ndi);
                                JOptionPane.showMessageDialog(null, "¡Meta encontrada!");
                           detener();
                    for (int i = 0; i < filaModulada.size(); i++) {
                        System.out.println("Filas2: " + filaModulada.get(i) + " columnas2: " + columnaModulada.get(i));
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
        }
    }
    
}
