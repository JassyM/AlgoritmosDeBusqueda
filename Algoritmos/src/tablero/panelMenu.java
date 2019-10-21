package tablero;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Button;

public class panelMenu extends JPanel {

	/**
	 * Create the panel.
	 */
	Tracing algoritmo;
	JTextField cajaRuta;
	JButton btnWallTracing;
	JButton btnCargarArchivo;
	int n;
	JPanel panelFondo;
	JLabel matrizJuego[][];
	boolean wallT;

	int tablero_Aux[][];//matriz auxiliar con la cual se hacen las operaciones
	private BufferedReader br;
	private Scanner sc;
	public JPanel panel;
        boolean WayPo;
    JButton btnWayPoint;
    JButton btnLimpiar;
 

    int table_Aux[][];
    String table_Modular[][];
    String table_recorrido[][];
    WayPoint al;



    ArrayList<String> recorModu;
    private JLabel lblSeleccioneElAlgoritmo;
    private JLabel labelFondo;

	public panelMenu() {
                recorModu = new ArrayList<String>();
		setBackground(Color.BLACK);
		this.setForeground(Color.BLACK);
		this.setSize(1200, 800);
		setLayout(null);
		// this.setLayout(new GridLayout(1,2));
		this.setVisible(true);
		matrizJuego = new JLabel[20][20];
		setLayout(null);
		panelFondo = new JPanel();
		panelFondo.setBackground(new Color(190, 190, 190));
		panelFondo.setForeground(new Color(190, 190, 190));
		panelFondo.setBounds(0, 0, 665, 665);
		this.add(panelFondo);
		panelFondo.setLayout(null);
		
		labelFondo = new JLabel("");
		labelFondo.setBounds(0, 0, 665, 665);
		ImageIcon fondo = new ImageIcon(getClass().getResource("/img/fondo.JPG"));
       
        labelFondo.setIcon(fondo);
		panelFondo.add(labelFondo);
		panelFondo.setVisible(true);  
		
		

        //
		btnCargarArchivo = new JButton("Cargar Archivo");
		btnCargarArchivo.setBackground(new Color(255, 255, 255));
		btnCargarArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCargarActionPerformed(e);
			}
		});
		btnCargarArchivo.setBounds(689, 185, 142, 23);
		add(btnCargarArchivo);

		cajaRuta = new JTextField();
		cajaRuta.setBounds(689, 145, 351, 20);
		add(cajaRuta);
		cajaRuta.setColumns(10);

		btnWallTracing = new JButton("WallTracing");
		btnWallTracing.setBackground(new Color(255, 255, 255));
		btnWallTracing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnWallTracingActionPerformed(e);
			}
		});
		btnWallTracing.setBounds(689, 365, 117, 23);
		add(btnWallTracing);
		
		
		btnWayPoint = new JButton("WayPoint");
		btnWayPoint.setForeground(new Color(0, 0, 0));
		btnWayPoint.setBackground(new Color(255, 255, 255));
		btnWayPoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnWayPointActionPerformed(e);
			}
		});
		btnWayPoint.setBounds(689, 311, 117, 23);
		add(btnWayPoint);

		JLabel lblIngreseLaUbicacion = new JLabel("Ingrese la ubicacion del archivo de texto:");
		lblIngreseLaUbicacion.setForeground(Color.WHITE);
		lblIngreseLaUbicacion.setBounds(689, 110, 307, 14);
		add(lblIngreseLaUbicacion);
		
		 btnLimpiar = new JButton("Limpiar");
		 btnLimpiar.setEnabled(false);
		 btnLimpiar.setBackground(new Color(255, 255, 255));
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLimpiarActionPerformed(e);
			}
		});
		btnLimpiar.setBounds(689, 461, 117, 22);
		add(btnLimpiar);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit( 0 ); 
			}
		});
		btnCerrar.setBackground(new Color(255, 255, 255));
		btnCerrar.setBounds(854, 461, 106, 23);
		add(btnCerrar);
		
		lblSeleccioneElAlgoritmo = new JLabel("Seleccione el algoritmo que desea utilizar:");
		lblSeleccioneElAlgoritmo.setForeground(new Color(255, 255, 255));
		lblSeleccioneElAlgoritmo.setBounds(689, 279, 307, 14);
		add(lblSeleccioneElAlgoritmo);
		
		
		panel = new JPanel();
		panel.setBackground(new Color(190, 190, 190));
		panel.setForeground(new Color(190, 190, 190));
		panel.setBounds(0, 0, 665, 665);
		panel.setVisible(false);


	}
	

	public void abrir() {
		JFileChooser archivo = new JFileChooser();
		int opcion = archivo.showOpenDialog(this.btnCargarArchivo);
		if (opcion == JFileChooser.APPROVE_OPTION) {
			File fichero = archivo.getSelectedFile();
			try (FileReader fr = new FileReader(fichero)) {
				cajaRuta.setText(fichero.getPath());// muestra la ruta del
													// archivo seleccionado en
													// la caja de texto
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void imprimir_matriz(int aux[][]) {
		for (int i = 0; i < aux.length; i++) {
			for (int j = 0; j < aux.length; j++) {
				System.out.print(aux[i][j]);
			}
			System.out.println();
		}

	}
	
         public void llenarMatriz() {
        int a = 28;//ancho icono
        
        try {
            sc = new Scanner(new File(cajaRuta.getText()));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        table_Aux = new int[20][20];
        // ImageIcon ladrillo = new ImageIcon()
        matrizJuego = new JLabel[20][20];
        for (int i = 0; i < table_Aux.length; i++) {
            for (int j = 0; j < table_Aux[i].length; j++) {
                table_Aux[i][j] = sc.nextInt();
                if (table_Aux[i][j] == 1) {
                    matrizJuego[i][j] = new JLabel();
                    matrizJuego[i][j].setBounds(j * a, i * a, a, a);
                    ImageIcon bloqueAzul = new ImageIcon(getClass().getResource("/img/bloqueAzul.png"));
                    Icon icono = new ImageIcon(bloqueAzul.getImage().getScaledInstance(matrizJuego[i][j].getWidth(), matrizJuego[i][j].getHeight(), Image.SCALE_DEFAULT));
                    matrizJuego[i][j].setIcon(icono);
                    panel.add(matrizJuego[i][j]);
                }
                if (table_Aux[i][j] == 0) {
                    matrizJuego[i][j] = new JLabel();
                    matrizJuego[i][j].setBounds(j * a, i * a, a, a);
                    ImageIcon bloqueBlanco = new ImageIcon(getClass().getResource("/img/bloqueBlanco.png"));
                    Icon icono = new ImageIcon(bloqueBlanco.getImage().getScaledInstance(matrizJuego[i][j].getWidth(), matrizJuego[i][j].getHeight(), Image.SCALE_DEFAULT));
                    matrizJuego[i][j].setIcon(icono);
                    panel.add(matrizJuego[i][j]);
                }
                if (table_Aux[i][j] == 2) {
                    matrizJuego[i][j] = new JLabel();
                    matrizJuego[i][j].setBounds(j * a, i * a, a, a);
                    ImageIcon inicio = new ImageIcon(getClass().getResource("/img/perfilDerecho.jpg"));
                    Icon icono = new ImageIcon(inicio.getImage().getScaledInstance(matrizJuego[i][j].getWidth(), matrizJuego[i][j].getHeight(), Image.SCALE_DEFAULT));
                    matrizJuego[i][j].setIcon(icono);
                    panel.add(matrizJuego[i][j]);
                }
                if (table_Aux[i][j] == 5) {
                    matrizJuego[i][j] = new JLabel();
                    matrizJuego[i][j].setBounds(j * a, i * a, a, a);
                    ImageIcon meta = new ImageIcon(getClass().getResource("/img/meta.png"));
                    Icon icono = new ImageIcon(meta.getImage().getScaledInstance(matrizJuego[i][j].getWidth(), matrizJuego[i][j].getHeight(), Image.SCALE_DEFAULT));
                   
                    matrizJuego[i][j].setIcon(icono);
                    panel.add(matrizJuego[i][j]);
                } else {

                }

            }
        }
        table_recorrido = new String[1][1];
       table_Modular = new String[20][20];
        if(WayPo){
        
        for (int z = 0; z < table_Modular.length; z++) {
            for (int j = 0; j < table_Modular[z].length; j++) {
                String linea = sc.next();
                table_Modular[z][j] = linea;
            }
        }
        
        while(sc.hasNext()){
            String linea = sc.next();
            recorModu.add(linea);
        }        
        }
    }
	public String[][] llenarMatrizMod(WayPoint a){
        int c = a.cantLetrasMod()+1;
        ArrayList<String> d;
        d = new ArrayList<>();
        table_recorrido = new String[c][c];
        int n=0;
        for (int z = 0; z < table_recorrido.length; z++) {
            for (int j = 0; j < table_recorrido[z].length; j++) {
                table_recorrido[z][j] = recorModu.get(n);
                n++;
            }
        }for (int z = 0; z < table_recorrido.length; z++) {
            for (int j = 0; j < table_recorrido[z].length; j++) {
   //     System.out.print(table_recorrido[z][j]+" ");}
    //    System.out.print("\n");
            }}
    return table_recorrido;
    }        

	
	public void limpiar(){
		if(wallT){
			wallT=false;
			algoritmo.detener();
		}
		if(WayPo){
			WayPo=false;
			al.detener();
		}
		//algoritmo.f;
		 for (int i = 0; i < table_Aux.length; i++) {
	            for (int j = 0; j < table_Aux[i].length; j++) {
	            	table_Aux[i][j] = 0;
	            	}
	            }
		 for (int i = 0; i < table_Modular.length; i++) {
	            for (int j = 0; j < table_Modular[i].length; j++) {
	            	table_Modular[i][j] = null;
	            	}
	            }
		 for (int i = 0; i < table_recorrido.length; i++) {
	            for (int j = 0; j < table_recorrido[i].length; j++) {
	            	table_recorrido[i][j] = null;
	            	}
	            }
		 for (int i = 0; i <  matrizJuego.length; i++) {
	            for (int j = 0; j <  matrizJuego[i].length; j++) {
	            	 matrizJuego[i][j] = null;
	            	}
	            }
		 
		  recorModu.clear();
          btnWayPoint.setEnabled(true);
          btnWallTracing.setEnabled(true);
          btnCargarArchivo.setEnabled(true);
          cajaRuta.setEnabled(true);
          panel.setVisible(false);
          panel.removeAll();
	}
	// Evento del boton btnCargarAmbiente para seleccionar un archivo de texto
	private void btnCargarActionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == this.btnCargarArchivo) {
				abrir();
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

	}

	public void btnWallTracingActionPerformed(ActionEvent e) {

		try {

			if (e.getSource() == this.btnWallTracing) {
                            WayPo =false;
                            wallT =true;
				llenarMatriz();
				this.add(panel);
				btnWayPoint.setEnabled(false);
				btnWallTracing.setEnabled(false);
                btnCargarArchivo.setEnabled(false);
                cajaRuta.setEnabled(false);
                btnLimpiar.setEnabled(true);
				panel.setVisible(true);
				panelFondo.setVisible(false); 
				algoritmo = new Tracing(table_Aux, matrizJuego, this);
				
		        //        al.inicio();
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	public void btnWayPointActionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == this.btnWayPoint) {
                WayPo = true;
                wallT = false;
                llenarMatriz();
                this.add(panel);
                btnWallTracing.setEnabled(false);
                btnWayPoint.setEnabled(false);
                btnCargarArchivo.setEnabled(false);
                btnLimpiar.setEnabled(true);
                cajaRuta.setEnabled(false);
                panel.setVisible(true);
                panelFondo.setVisible(false); 
                al = new WayPoint(table_Aux, matrizJuego,table_Modular, this);
        //        al.inicio();
        //        al.recorrido();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
	public void btnLimpiarActionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == this.btnLimpiar) {
              limpiar();
              panelFondo.setVisible(true); 
              
        //                   panel.res   al.inicio();
        //        al.recorrido();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public JLabel[][] getMatrizJuego() {
        return matrizJuego;
    }
}
