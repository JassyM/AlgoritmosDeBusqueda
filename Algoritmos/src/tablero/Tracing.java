package tablero;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Tracing {

	int x0;
	int y0;
	int x1;
	int y1;

	panelMenu pm;
    int[][] tablaMatriz;
    JLabel matrizJ[][];

	int filaA, columnaA, direccionA;
	ImageIcon bloqueBlanco;
	Icon iconoBlanco;
	ImageIcon bloqueFrontal;
	Icon iconoFrontal;
	ImageIcon bloquePerfilDerecho;
	Icon iconoPerfilDerecho;
	ImageIcon bloquePerfilIzquierdo;
	Icon iconoPerfilIzquierdo;
	ImageIcon bloqueTracero;
	Icon iconoTracero;
	ImageIcon camino;
	Icon iconoCamino;
	ArrayList<Integer> puntosMetaX;// = new ArrayList<>();
	ArrayList<Integer> puntosMetaY; //= new ArrayList<>();
	boolean valMeta;
	boolean meta;
	int filaM;
	int columnaM;
	 Hilo h;
	boolean ejecutar;
	
	public Tracing(int[][] matriz, JLabel[][] matrizJuego, panelMenu pm) {
		this.pm = pm;
        matrizJ = matrizJuego;
        tablaMatriz = matriz;
        puntosMetaX = new ArrayList<>();
        puntosMetaY = new ArrayList<>();
        
        filaA = 0; 
        columnaA = 0; 
        direccionA = 4; 
        filaM = 0; 
        columnaM = 0; 
        meta = false;
        valMeta = false;
        		
        inicio();
        figuraCuadros();
        h = new Hilo();
        h.start();
	}

	public void inicio() {  
        for (int i = 0; i < tablaMatriz.length; i++) {
            for (int j = 0; j < tablaMatriz.length; j++) {
                if (tablaMatriz[i][j] == 2) {// Define dónde está el agente.
                    filaA = i;
					columnaA = j;
                }
                if(tablaMatriz[i][j] == 5){// Define dónde está la meta.
                	filaM = i;
                	columnaM = j;
                }
            }
        }
    }
	
    public void detener(){
    	h.stop();
    }
	
	public void figuraCuadros(){
		bloqueBlanco = new ImageIcon(getClass().getResource("/img/bloqueBlanco.png"));
        iconoBlanco = new ImageIcon(bloqueBlanco.getImage().getScaledInstance(matrizJ[filaA][columnaA].getWidth(), 
        		matrizJ[filaA][columnaA].getHeight(), Image.SCALE_DEFAULT));

        bloquePerfilDerecho = new ImageIcon(getClass().getResource("/img/perfilDerecho.jpg"));
    	iconoPerfilDerecho = new ImageIcon(bloquePerfilDerecho.getImage().getScaledInstance(matrizJ[filaA][columnaA].getWidth(), 
        		matrizJ[filaA][columnaA].getHeight(), Image.SCALE_DEFAULT));
    	
    	bloqueFrontal = new ImageIcon(getClass().getResource("/img/frontal.jpg"));
    	iconoFrontal = new ImageIcon(bloqueFrontal.getImage().getScaledInstance(matrizJ[filaA][columnaA].getWidth(), 
        		matrizJ[filaA][columnaA].getHeight(), Image.SCALE_DEFAULT));
        
    	bloquePerfilDerecho = new ImageIcon(getClass().getResource("/img/perfilDerecho.jpg"));
    	iconoPerfilDerecho = new ImageIcon(bloquePerfilDerecho.getImage().getScaledInstance(matrizJ[filaA][columnaA].getWidth(), 
        		matrizJ[filaA][columnaA].getHeight(), Image.SCALE_DEFAULT));
    	
    	bloquePerfilIzquierdo = new ImageIcon(getClass().getResource("/img/perfilIzquierdo.jpg"));
    	iconoPerfilIzquierdo = new ImageIcon(bloquePerfilIzquierdo.getImage().getScaledInstance(matrizJ[filaA][columnaA].getWidth(), 
        		matrizJ[filaA][columnaA].getHeight(), Image.SCALE_DEFAULT));
        
    	bloqueTracero = new ImageIcon(getClass().getResource("/img/tracero.jpg"));
    	iconoTracero = new ImageIcon(bloqueTracero.getImage().getScaledInstance(matrizJ[filaA][columnaA].getWidth(), 
        		matrizJ[filaA][columnaA].getHeight(), Image.SCALE_DEFAULT));
    	
    	camino = new ImageIcon(getClass().getResource("/img/recorrido.png"));
		iconoCamino = new ImageIcon(camino.getImage().getScaledInstance(matrizJ[filaA][columnaA].getWidth(),
				matrizJ[filaA][columnaA].getHeight(), Image.SCALE_DEFAULT));
	}
	
	public void wallTracing() {
		if (tablaMatriz[filaA - 1][columnaA] == 0 && tablaMatriz[filaA + 1][columnaA] == 0
				&& tablaMatriz[filaA][columnaA - 1] == 0 && tablaMatriz[filaA][columnaA + 1] == 0) {
			if (direccionA == 4) {
				// Valido las puertas:
				if (tablaMatriz[filaA-1][columnaA-1] == 1 && tablaMatriz[filaA-1][columnaA+1] == 1) { // Tracero
					System.out.println("Caso 1");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA - 1][columnaA].setIcon(iconoTracero);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA - 1][columnaA] = 2;
					filaA--;
					direccionA = 2;
				} 
				else if (tablaMatriz[filaA-1][columnaA+1] == 1 && tablaMatriz[filaA+1][columnaA+1] == 1) { // Derecha
					System.out.println("Caso 2");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA + 1].setIcon(iconoPerfilDerecho);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA + 1] = 2;
					columnaA++;
					direccionA = 4;
				} 
				else if (tablaMatriz[filaA + 1][columnaA-1] == 1 && tablaMatriz[filaA+1][columnaA+1] == 1) { // Frontal
					System.out.println("Caso 3");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA + 1][columnaA].setIcon(iconoFrontal);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA + 1][columnaA] = 2;
					filaA++;
					direccionA = 6;
				}
				// Valido si hay algún bloque en los extremos.
				else if (tablaMatriz[filaA-1][columnaA-1] == 1) { // Tracero
					System.out.println("Caso 4");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA - 1][columnaA].setIcon(iconoTracero);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA - 1][columnaA] = 2;
					filaA--;
					direccionA = 2;
				} 
				else if (tablaMatriz[filaA-1][columnaA+1] == 1 || tablaMatriz[filaA+1][columnaA+1] == 1) { //Derecha
					System.out.println("Caso 5");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA + 1].setIcon(iconoPerfilDerecho);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA + 1] = 2;
					columnaA++;
					direccionA = 4;
				} 
				else if (tablaMatriz[filaA + 1][columnaA - 1] == 1) { // Frontal
					System.out.println("Caso 6");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA + 1][columnaA].setIcon(iconoFrontal);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA + 1][columnaA] = 2;
					filaA++;
					direccionA = 6;
				}
				else{
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA-1][columnaA].setIcon(iconoTracero);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA - 1][columnaA] = 2;
					filaA--;
					direccionA = 2;
				}
			}

			else if (direccionA == 8) {
				System.out.println("Con la dirección caso vacío A: " + direccionA);
				// Valido las puertas:
				if (tablaMatriz[filaA + 1][columnaA - 1] == 1 && tablaMatriz[filaA + 1][columnaA + 1] == 1) { // Frontal
					System.out.println("Caso 7");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA + 1][columnaA].setIcon(iconoFrontal);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA + 1][columnaA] = 2;
					filaA++;
					direccionA = 6;
				} 
				else if (tablaMatriz[filaA-1][columnaA-1] == 1 && tablaMatriz[filaA+1][columnaA-1] == 1) { // Izquierdo
					System.out.println("Caso 8");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA - 1].setIcon(iconoPerfilIzquierdo);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA - 1] = 2;
					columnaA--;
					direccionA = 8;
				} 
				else if (tablaMatriz[filaA-1][columnaA-1] == 1 && tablaMatriz[filaA-1][columnaA+1] == 1) { // Tracero
					System.out.println("Caso 9");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA - 1][columnaA].setIcon(iconoTracero);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA - 1][columnaA] = 2;
					filaA--;
					direccionA = 2;
				}
				// Valido bloque extremo.
				else if (tablaMatriz[filaA + 1][columnaA + 1] == 1) { // Frontal
					System.out.println("Caso 10");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA + 1][columnaA].setIcon(iconoFrontal);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA + 1][columnaA] = 2;
					filaA++;
					direccionA = 6;
				} 
				else if (tablaMatriz[filaA-1][columnaA-1] == 1 || tablaMatriz[filaA+1][columnaA-1] == 1) { // Izquierdo
					System.out.println("Caso 11");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA - 1].setIcon(iconoPerfilIzquierdo);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA - 1] = 2;
					columnaA--;
					direccionA = 8;
				} 
				else if (tablaMatriz[filaA - 1][columnaA + 1] == 1) { // Tracero
					System.out.println("Caso 12");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA - 1][columnaA].setIcon(iconoTracero);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA - 1][columnaA] = 2;
					filaA--;
					direccionA = 2;
				}
				else{
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA+1][columnaA].setIcon(iconoFrontal);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA + 1][columnaA] = 2;
					filaA++;
					direccionA = 6;
				}
			}

			else if (direccionA == 6) {
				System.out.println("Con la dirección caso vacío A: " + direccionA);
				// Valido las puertas:
				if (tablaMatriz[filaA - 1][columnaA + 1] == 1 && tablaMatriz[filaA + 1][columnaA + 1] == 1) { // Derecho
					System.out.println("Caso 13");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA + 1].setIcon(iconoPerfilDerecho);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA + 1] = 2;
					columnaA++;
					direccionA = 4;
				} 
				else if (tablaMatriz[filaA + 1][columnaA - 1] == 1 && tablaMatriz[filaA + 1][columnaA + 1] == 1) { // Frontal
					System.out.println("Caso 14");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA + 1][columnaA].setIcon(iconoFrontal);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA + 1][columnaA] = 2;
					filaA++;
					direccionA = 6;
				} 
				else if (tablaMatriz[filaA - 1][columnaA - 1] == 1 && tablaMatriz[filaA + 1][columnaA - 1] == 1) { // Izquierdo
					System.out.println("Caso 15");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA - 1].setIcon(iconoPerfilIzquierdo);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA - 1] = 2;
					columnaA--;
					direccionA = 8;
				}
				// Valido bloque extremo
				else if (tablaMatriz[filaA - 1][columnaA + 1] == 1) { //  Derecho
					System.out.println("Caso 16");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA + 1].setIcon(iconoPerfilDerecho);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA + 1] = 2;
					columnaA++;
					direccionA = 4;
				} 
				else if (tablaMatriz[filaA + 1][columnaA - 1] == 1 || tablaMatriz[filaA + 1][columnaA + 1] == 1) { // Frontal
					System.out.println("Caso 17");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA + 1][columnaA].setIcon(iconoFrontal);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA + 1][columnaA] = 2;
					filaA++;
					direccionA = 6;
				} 
				else if (tablaMatriz[filaA - 1][columnaA - 1] == 1) { // Izquierdo
					System.out.println("Caso 18");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA - 1].setIcon(iconoPerfilIzquierdo);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA - 1] = 2;
					columnaA--;
					direccionA = 8;
				}
				else{
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA+1].setIcon(iconoPerfilDerecho);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA+1] = 2;
					columnaA++;
					direccionA = 4;
				}
			}

			else if (direccionA == 2) {
				// Valido las puertas:
				if (tablaMatriz[filaA-1][columnaA-1] == 1 && tablaMatriz[filaA+1][columnaA-1] == 1) { // Izquierdo
					System.out.println("Caso 19");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA - 1].setIcon(iconoPerfilIzquierdo);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA - 1] = 2;
					columnaA--;
					direccionA = 8;
				} 
				else if (tablaMatriz[filaA-1][columnaA-1] == 1 && tablaMatriz[filaA-1][columnaA+1] == 1) { // Tracero
					System.out.println("Caso 20");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA - 1][columnaA].setIcon(iconoTracero);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA - 1][columnaA] = 2;
					filaA--;
					direccionA = 2;
				} 
				else if (tablaMatriz[filaA-1][columnaA+1] == 1 && tablaMatriz[filaA+1][columnaA+1] == 1) { // Derecho
					System.out.println("Caso 21");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA + 1].setIcon(iconoPerfilDerecho);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA + 1] = 2;
					columnaA++;
					direccionA = 4;
				}
				// Valido bloque extremo.
				else if (tablaMatriz[filaA + 1][columnaA - 1] == 1) { // Izquierdo
					System.out.println("Caso 22");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA - 1].setIcon(iconoPerfilIzquierdo);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA - 1] = 2;
					columnaA--;
					direccionA = 8;
				} 
				else if (tablaMatriz[filaA-1][columnaA-1] == 1 || tablaMatriz[filaA-1][columnaA+1] == 1) { // Tracero
					System.out.println("Caso 23");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA - 1][columnaA].setIcon(iconoTracero);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA - 1][columnaA] = 2;
					filaA--;
					direccionA = 2;
				} 
				else if (tablaMatriz[filaA + 1][columnaA + 1] == 1) { //  Derecho
					System.out.println("Caso 24");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA + 1].setIcon(iconoPerfilDerecho);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA + 1] = 2;
					columnaA++;
					direccionA = 4;
				}
				else{
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA-1].setIcon(iconoPerfilIzquierdo);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA-1] = 2;
					columnaA--;
					direccionA = 8;
				}
			}
		}
		
		
		else {
			if (direccionA == 4) {
				if (tablaMatriz[filaA - 1][columnaA - 1] == 1 && tablaMatriz[filaA - 1][columnaA + 1] == 1
						&& tablaMatriz[filaA - 1][columnaA] == 0) { // Tracero
					System.out.println("Caso 25");
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA - 1][columnaA] = 2;
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA - 1][columnaA].setIcon(iconoTracero);
					filaA--;
					direccionA = 2;
				} 
				else if (tablaMatriz[filaA - 1][columnaA + 1] == 1 && tablaMatriz[filaA + 1][columnaA + 1] == 1
						&& tablaMatriz[filaA][columnaA+1] == 0) { // Derecho
					System.out.println("Caso 26");
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA + 1] = 2;
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA + 1].setIcon(iconoPerfilDerecho);
					columnaA++;
					direccionA = 4;
				} 
				else if (tablaMatriz[filaA + 1][columnaA - 1] == 1 && tablaMatriz[filaA + 1][columnaA + 1] == 1
						&& tablaMatriz[filaA + 1][columnaA] == 0) { // Frontal
					System.out.println("Caso 27");
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA + 1][columnaA] = 2;
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA + 1][columnaA].setIcon(iconoFrontal);
					filaA++;
					direccionA = 6;
				} 
				else if (tablaMatriz[filaA + 1][columnaA] == 1 && tablaMatriz[filaA][columnaA + 1] == 0) {
					System.out.println("Caso 28"); // Perfil Derecho
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA + 1] = 2;
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA + 1].setIcon(iconoPerfilDerecho);
					columnaA++;
					direccionA = 4;
				} 
				else if (tablaMatriz[filaA - 1][columnaA] == 0 || tablaMatriz[filaA - 1][columnaA] == 5
						|| tablaMatriz[filaA - 1][columnaA] == 3) { // Left
					if (tablaMatriz[filaA - 1][columnaA] == 5) {
						meta = true;
					}
					System.out.println("Caso 29");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA - 1][columnaA].setIcon(iconoTracero);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA - 1][columnaA] = 2;
					filaA--;
					direccionA = 2;
				} 
				else if (tablaMatriz[filaA][columnaA + 1] == 0 || tablaMatriz[filaA][columnaA + 1] == 5
						|| tablaMatriz[filaA][columnaA + 1] == 3) { // Front
					if (tablaMatriz[filaA][columnaA + 1] == 5) {
						meta = true;
					}
					System.out.println("Caso 30");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA + 1].setIcon(iconoPerfilDerecho);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA + 1] = 2;
					columnaA++;
					direccionA = 4;
				} 
				else if (tablaMatriz[filaA + 1][columnaA] == 0 || tablaMatriz[filaA + 1][columnaA] == 5
						|| tablaMatriz[filaA + 1][columnaA] == 3) { // Right
					if (tablaMatriz[filaA + 1][columnaA] == 5) {
						meta = true;
					}
					System.out.println("Caso 31");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA + 1][columnaA].setIcon(iconoFrontal);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA + 1][columnaA] = 2;
					filaA++;
					direccionA = 6;
				} 
				else if (tablaMatriz[filaA][columnaA - 1] == 0 || tablaMatriz[filaA][columnaA - 1] == 5
						|| tablaMatriz[filaA][columnaA - 1] == 3) { // Back
					if (tablaMatriz[filaA][columnaA - 1] == 5) {
						meta = true;
					}
					System.out.println("Caso 32");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA - 1].setIcon(iconoPerfilIzquierdo);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA - 1] = 2;
					columnaA--;
					direccionA = 8;
				}
			}

			else if (direccionA == 2) {
				if (tablaMatriz[filaA - 1][columnaA - 1] == 1 && tablaMatriz[filaA + 1][columnaA - 1] == 1
						&& tablaMatriz[filaA][columnaA - 1] == 0) { // Izquierdo
					System.out.println("Caso 33");
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA - 1] = 2;
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA - 1].setIcon(iconoPerfilIzquierdo);
					columnaA--;
					direccionA = 8;
				} 
				else if (tablaMatriz[filaA - 1][columnaA - 1] == 1 && tablaMatriz[filaA - 1][columnaA + 1] == 1
						&& tablaMatriz[filaA - 1][columnaA] == 0) { // Tracero
					System.out.println("Caso 34");
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA - 1][columnaA] = 2;
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA - 1][columnaA].setIcon(iconoTracero);
					filaA--;
					direccionA = 2;
				} 
				else if (tablaMatriz[filaA - 1][columnaA + 1] == 1 && tablaMatriz[filaA + 1][columnaA + 1] == 1
						&& tablaMatriz[filaA][columnaA + 1] == 0) { // Derecho
					System.out.println("Caso 35");
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA + 1] = 2;
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA + 1].setIcon(iconoPerfilDerecho);
					columnaA++;
					direccionA = 4;
				} 
				else if (tablaMatriz[filaA][columnaA + 1] == 1 && tablaMatriz[filaA - 1][columnaA] == 0) {
					System.out.println("Caso 36"); // Tracero
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA - 1][columnaA] = 2;
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA - 1][columnaA].setIcon(iconoTracero);
					filaA--;
					direccionA = 2;
				} 
				else if (tablaMatriz[filaA][columnaA - 1] == 0 || tablaMatriz[filaA][columnaA - 1] == 5
						|| tablaMatriz[filaA][columnaA - 1] == 5) { // Left
					if (tablaMatriz[filaA][columnaA - 1] == 5) {
						meta = true;
					}
					System.out.println("Caso 37");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA - 1].setIcon(iconoPerfilIzquierdo);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA - 1] = 2;
					columnaA--;
					direccionA = 8;
				} 
				else if (tablaMatriz[filaA - 1][columnaA] == 0 || tablaMatriz[filaA - 1][columnaA] == 5
						|| tablaMatriz[filaA - 1][columnaA] == 3) { // Front
					if (tablaMatriz[filaA - 1][columnaA] == 5) {
						meta = true;
					}
					System.out.println("Caso 38");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA - 1][columnaA].setIcon(iconoTracero);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA - 1][columnaA] = 2;
					filaA--;
					direccionA = 2;
				} 
				else if (tablaMatriz[filaA][columnaA + 1] == 0 || tablaMatriz[filaA][columnaA + 1] == 5
						|| tablaMatriz[filaA][columnaA + 1] == 3) { // Right
					if (tablaMatriz[filaA][columnaA + 1] == 5) {
						meta = true;
					}
					System.out.println("Caso 39");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA + 1].setIcon(iconoPerfilDerecho);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA + 1] = 2;
					columnaA++;
					direccionA = 4;
				} 
				else if (tablaMatriz[filaA + 1][columnaA] == 0 || tablaMatriz[filaA + 1][columnaA] == 5
						|| tablaMatriz[filaA + 1][columnaA] == 3) { // Back
					if (tablaMatriz[filaA + 1][columnaA] == 5) {
						meta = true;
					}
					System.out.println("Caso 40");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA + 1][columnaA].setIcon(iconoFrontal);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA + 1][columnaA] = 2;
					filaA++;
					direccionA = 6;
				}
			}

			else if (direccionA == 6) {
				if (tablaMatriz[filaA - 1][columnaA + 1] == 1 && tablaMatriz[filaA + 1][columnaA + 1] == 1
						&& tablaMatriz[filaA][columnaA + 1] == 0) { // Derecho
					System.out.println("Caso 41");
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA + 1] = 2;
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA + 1].setIcon(iconoPerfilDerecho);
					columnaA++;
					direccionA = 4;
				} 
				else if (tablaMatriz[filaA + 1][columnaA - 1] == 1 && tablaMatriz[filaA + 1][columnaA + 1] == 1
						&& tablaMatriz[filaA + 1][columnaA] == 0) { // Frontal
					System.out.println("Caso 42");
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA + 1][columnaA] = 2;
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA + 1][columnaA].setIcon(iconoFrontal);
					filaA++;
					direccionA = 6;
				} 
				else if (tablaMatriz[filaA - 1][columnaA - 1] == 1 && tablaMatriz[filaA + 1][columnaA - 1] == 1
						&& tablaMatriz[filaA][columnaA - 1] == 0) { // Izquierdo
					System.out.println("Caso 43");
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA - 1] = 2;
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA - 1].setIcon(iconoPerfilIzquierdo);
					columnaA--;
					direccionA = 8;
				} 
				else if (tablaMatriz[filaA][columnaA - 1] == 1 && tablaMatriz[filaA + 1][columnaA] == 0) {
					System.out.println("Caso 44"); // Frontal
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA + 1][columnaA] = 2;
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA + 1][columnaA].setIcon(iconoFrontal);
					filaA++;
					direccionA = 6;
				} 
				else if (tablaMatriz[filaA][columnaA + 1] == 0 || tablaMatriz[filaA][columnaA + 1] == 5
						|| tablaMatriz[filaA][columnaA + 1] == 3) { // Left
					if (tablaMatriz[filaA][columnaA + 1] == 5) {
						meta = true;
					}
					System.out.println("Caso 45");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA + 1].setIcon(iconoPerfilDerecho);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA + 1] = 2;
					columnaA++;
					direccionA = 4;
				} 
				else if (tablaMatriz[filaA + 1][columnaA] == 0 || tablaMatriz[filaA + 1][columnaA] == 5
						|| tablaMatriz[filaA + 1][columnaA] == 3) { // Front
					if (tablaMatriz[filaA + 1][columnaA] == 5) {
						meta = true;
					}
					System.out.println("Caso 46");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA + 1][columnaA].setIcon(iconoFrontal);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA + 1][columnaA] = 2;
					filaA++;
					direccionA = 6;
				} 
				else if (tablaMatriz[filaA][columnaA - 1] == 0 || tablaMatriz[filaA][columnaA - 1] == 5
						|| tablaMatriz[filaA][columnaA - 1] == 3) { // Right
					if (tablaMatriz[filaA][columnaA - 1] == 5) {
						meta = true;
					}
					System.out.println("Caso 47");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA - 1].setIcon(iconoPerfilIzquierdo);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA - 1] = 2;
					columnaA--;
					direccionA = 8;
				} 
				else if (tablaMatriz[filaA - 1][columnaA] == 0 || tablaMatriz[filaA - 1][columnaA] == 5
						|| tablaMatriz[filaA - 1][columnaA] == 3) { // Back
					if (tablaMatriz[filaA - 1][columnaA] == 5) {
						meta = true;
					}
					System.out.println("Caso 48");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA - 1][columnaA].setIcon(iconoTracero);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA - 1][columnaA] = 2;
					filaA--;
					direccionA = 2;
				}
			}

			else if (direccionA == 8) {
				if (tablaMatriz[filaA + 1][columnaA - 1] == 1 && tablaMatriz[filaA + 1][columnaA + 1] == 1
						&& tablaMatriz[filaA + 1][columnaA] == 0) { // Frontal
					System.out.println("Caso 49");
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA + 1][columnaA] = 2;
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA + 1][columnaA].setIcon(iconoFrontal);
					filaA++;
					direccionA = 6;
				} 
				else if (tablaMatriz[filaA - 1][columnaA - 1] == 1 && tablaMatriz[filaA + 1][columnaA - 1] == 1
						&& tablaMatriz[filaA][columnaA - 1] == 0) { // Izquierdo
					System.out.println("Caso 50");
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA - 1] = 2;
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA - 1].setIcon(iconoPerfilIzquierdo);
					columnaA--;
					direccionA = 8;
				} 
				else if (tablaMatriz[filaA - 1][columnaA - 1] == 1 && tablaMatriz[filaA - 1][columnaA + 1] == 1
						&& tablaMatriz[filaA - 1][columnaA] == 0) { // Tracero
					System.out.println("Caso 51");
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA - 1][columnaA] = 2;
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA - 1][columnaA].setIcon(iconoTracero);
					filaA--;
					direccionA = 2;
				} 
				else if (tablaMatriz[filaA - 1][columnaA] == 1 && tablaMatriz[filaA][columnaA - 1] == 0) {
					System.out.println("Caso 52"); // Izquierdo
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA - 1] = 2;
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA - 1].setIcon(iconoPerfilIzquierdo);
					columnaA--;
					direccionA = 8;
				} 
				else if (tablaMatriz[filaA + 1][columnaA] == 0 || tablaMatriz[filaA + 1][columnaA] == 5
						|| tablaMatriz[filaA + 1][columnaA] == 3) { // Left
					if (tablaMatriz[filaA + 1][columnaA] == 5) {
						meta = true;
					}
					System.out.println("Caso 53");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA + 1][columnaA].setIcon(iconoFrontal);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA + 1][columnaA] = 2;
					filaA++;
					direccionA = 6;
				} 
				else if (tablaMatriz[filaA][columnaA - 1] == 0 || tablaMatriz[filaA][columnaA - 1] == 5
						|| tablaMatriz[filaA][columnaA - 1] == 3) { // Front
					if (tablaMatriz[filaA][columnaA - 1] == 5) {
						meta = true;
					}
					System.out.println("Caso 54");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA - 1].setIcon(iconoPerfilIzquierdo);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][columnaA - 1] = 2;
					columnaA--;
					direccionA = 8;
				} 
				else if (tablaMatriz[filaA - 1][columnaA] == 0 || tablaMatriz[filaA - 1][columnaA] == 5
						|| tablaMatriz[filaA - 1][columnaA] == 3) { // Right
					if (tablaMatriz[filaA - 1][columnaA] == 5) {
						meta = true;
					}
					System.out.println("Caso 55");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA - 1][columnaA].setIcon(iconoTracero);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA - 1][columnaA] = 2;
					filaA--;
					direccionA = 2;
				} 
				else if (tablaMatriz[filaA][columnaA + 1] == 0 || tablaMatriz[filaA][columnaA + 1] == 5
						|| tablaMatriz[filaA][columnaA + 1] == 3) { // Back
					if (tablaMatriz[filaA][columnaA + 1] == 5) {
						meta = true;
					}
					System.out.println("Caso 56");
					matrizJ[filaA][columnaA].setIcon(iconoBlanco);
					matrizJ[filaA][columnaA + 1].setIcon(iconoPerfilDerecho);
					tablaMatriz[filaA][columnaA] = 0;
					tablaMatriz[filaA][filaA + 1] = 2;
					System.out.print(tablaMatriz[filaA][columnaA + 1]);
					columnaA++;
					direccionA = 4;
				}
			}
		}
	}
	
	public void bresenhman() {
        int nextCol = columnaA; 
        int nextRow = filaA; 
        int deltaRow = filaM - filaA; 
        int deltaCol = columnaM - columnaA; 
        int stepCol, stepRow;
        int fraction;
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
        deltaRow =  Math.abs(deltaRow * 2);
        deltaCol =  Math.abs(deltaCol * 2);
        System.out.println(" Meta: " + columnaM + "," + filaM + "  Agente: "  + columnaA + "," + filaA +".");
        if (deltaCol > deltaRow) {
            fraction = deltaRow * 2 - deltaCol;
            while (nextCol != columnaM) { 
                if (fraction >= 0) {
                    nextRow = nextRow + stepRow;
                    fraction = fraction - deltaCol;
                }
                nextCol = nextCol + stepCol;
                fraction = fraction + deltaRow;
                puntosMetaX.add(nextCol);
        		puntosMetaY.add(nextRow);
                System.out.println("Punto: " + nextCol + "," + nextRow);
                if (tablaMatriz[nextRow][nextCol] == 0) {  
					tablaMatriz[nextRow][nextCol] = 3;
	    			matrizJ[nextRow][nextCol].setIcon(iconoCamino);
	    		}
            }
        } else {
            fraction = deltaCol * 2 - deltaRow;
            while (nextRow != filaM) { 
                if (fraction >= 0) {
                    nextCol = nextCol + stepCol;
                    fraction = fraction - deltaRow;
                }
                nextRow = nextRow + stepRow;
                fraction = fraction + deltaCol;
                puntosMetaX.add(nextCol);
        		puntosMetaY.add(nextRow);
                System.out.println("Punto: " + nextCol + "," + nextRow);
                if (tablaMatriz[nextRow][nextCol] == 0) {
					tablaMatriz[nextRow][nextCol] = 3;
	    			matrizJ[nextRow][nextCol].setIcon(iconoCamino);
	    		}
            }
        }
    }

	
	public void validarMeta(){
		int x, y;
		for(int i = 0; i < puntosMetaX.size(); i++){		
			x = puntosMetaX.get(i).intValue();
			y = puntosMetaY.get(i).intValue();
			
			if(tablaMatriz[y][x] == 1){
				valMeta = false;
				i = puntosMetaX.size();
			}
			else{
				valMeta = true;
			}
		}
	}
	
	public void limpiarCamino(){
        if(puntosMetaX.size() != 0){
        	int x, y;
			for(int i = 0; i < puntosMetaX.size(); i++){
    			x = puntosMetaX.get(i).intValue();
    			y = puntosMetaY.get(i).intValue();
    			if(tablaMatriz[y][x] == 3){
        			tablaMatriz[y][x] = 0;
        			matrizJ[y][x].setIcon(iconoBlanco);
    			}
			}
		}
        puntosMetaX.clear();
		puntosMetaY.clear();
	}

	public class Hilo extends Thread {

        public void run() {
        	
        		while (!meta) {
                    try { 
                    	Thread.sleep(800);
                    	limpiarCamino();
                    	wallTracing();
                    	bresenhman();
                    	validarMeta();
                    	System.out.println("Validar Meta: " + valMeta);
                    	if(valMeta){
                    		int x,y;
                    		for(int i = 0; i < puntosMetaX.size(); i++){
                    			Thread.sleep(800);
                    			x = puntosMetaX.get(i).intValue();
                    			y = puntosMetaY.get(i).intValue();
                    			matrizJ[y][x].setIcon(matrizJ[filaA][columnaA].getIcon());
                    			matrizJ[filaA][columnaA].setIcon(iconoBlanco);
                    			tablaMatriz[y][x]=2;
                    			tablaMatriz[filaA][columnaA]=0;
                    			filaA = y;
                    			columnaA = x;
                    		}
                    		meta = true;
                    	}
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
                JOptionPane.showMessageDialog(null, "¡Meta encontrada!");
                detener();
        }
    }
	
}
