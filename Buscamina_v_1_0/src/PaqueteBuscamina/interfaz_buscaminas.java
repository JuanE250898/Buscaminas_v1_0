package PaqueteBuscamina;

//PROYECTO DISEÑADO POR Juan Esteban Rendón M. INGENIERO ELECTRONICO
// SOLO CON FINES EDUCATIVOS
/*
	Version del proyecto 1.0
	futuras versiones manejaran
		*cambio de color de fondo
		*paquete de imagenes para botones y minas
		*permitir guardar y cargar archivos
		*creacion del modo de juego tutorial
		*cambios del tipo de diseño
		*evitar perder en el primer clic
*/

// CREDITOS PRINCIPALES
/*
	Principal apoyo y guia: https://www.youtube.com/watch?v=Uo-6KMUHZ44&list=PLhbSLFs0SUZbafb6mA5JeRLqnl7S41wIj
	otros apoyos y guias: https://www.youtube.com/watch?v=AH4lRZ5EvNI
		https://www.youtube.com/watch?v=IgU4DFrpbsY
		https://www.youtube.com/watch?v=jnu0aM4zhxk
		https://www.java.com/es/download/help/
		https://www.lawebdelprogramador.com/foros/Java/
*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class interfaz_buscaminas extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel panel_principal;
	
	int numero_filas = 10;
	int numero_columnas = 10;
	int numero_minas = 20;
	int ancho_boton = 50;
	
	JButton[][] botones_tablero;
	
	JMenuItem nueva_partida,cerrar_juego,tamano_bloques,
	s_principiante,s_intermedio,s_avanzado,s_dificil,s_personalizado;
	
	Tablero Tablero;
	
	public interfaz_buscaminas() {
		// Constructor de la interfaz
				
		iniciar_pantalla();
		iniciar_componentes_barra_menu();
		juego_nuevo();
		
	}
	
	void limpiar_botones() {  
		if(botones_tablero != null) {
			for(int i = 0 ; i < botones_tablero.length ; i++ ) {
				for(int j = 0 ; j < botones_tablero[i].length ; j++) {
					getContentPane().remove(botones_tablero[i][j]);
				}
			}
		}
	}
	
	private void juego_nuevo() {
		
		limpiar_botones();
		cargar_controles();
		crear_tablero_buscaminas();
		repaint();
	}
	
	private void iniciar_pantalla() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 580);
		panel_principal = new JPanel();
		panel_principal.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(panel_principal);
		panel_principal.setLayout(null);
		
		setTitle("BUSCAMINAS DE JUAN :D");
		setLocationRelativeTo(null);

	}
	
	private void iniciar_componentes_barra_menu() {
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu_juego = new JMenu("Juego");
		menuBar.add(menu_juego);
		
		JMenu menu_dificultades = new JMenu("Dificultades");
		menuBar.add(menu_dificultades);
		
		JMenu menu_configuracion = new JMenu("Configuraci\u00F3n");
		menuBar.add(menu_configuracion);
		
		nueva_partida = new JMenuItem("Nueva partida");
		nueva_partida.addActionListener(this);
		menu_juego.add(nueva_partida);
		
		cerrar_juego = new JMenuItem("Cerrar juego");
		cerrar_juego.addActionListener(this);
		menu_juego.add(cerrar_juego);
		
		tamano_bloques = new JMenuItem("Tamaño bloques");
		tamano_bloques.addActionListener(this);
		menu_configuracion.add(tamano_bloques);
		
		s_principiante = new JMenuItem("Principiante");
		s_principiante.addActionListener(this);
		menu_dificultades.add(s_principiante);
		
		s_intermedio = new JMenuItem("Intermedio");
		s_intermedio.addActionListener(this);
		menu_dificultades.add(s_intermedio);
		
		s_avanzado = new JMenuItem("Avanzado");
		s_avanzado.addActionListener(this);
		menu_dificultades.add(s_avanzado);
		
		s_dificil = new JMenuItem("Muy dificil");
		s_dificil.addActionListener(this);
		menu_dificultades.add(s_dificil);
		
		s_personalizado = new JMenuItem("Personalizado");
		s_personalizado.addActionListener(this);
		menu_dificultades.add(s_personalizado);
	}
	
	private void crear_tablero_buscaminas() {
		Tablero = new Tablero(numero_filas, numero_columnas, numero_minas);
		
		Tablero.setEvento_partida_perdida( new Consumer<List<Casillas>>() {
			@Override
			public void accept(List<Casillas> t) {
				for (Casillas casillas_con_minas: t) {
					botones_tablero[casillas_con_minas.getPosicion_fila()][casillas_con_minas.getPosicion_columna()].setText("*");
				}
			}
		});
		
		Tablero.setEvento_partida_ganada( new Consumer<List<Casillas>>() {
			@Override
			public void accept(List<Casillas> t) {
				for (Casillas casillas_con_minas: t) {
					botones_tablero[casillas_con_minas.getPosicion_fila()][casillas_con_minas.getPosicion_columna()].setText(":D");
				}
			}
		});
		
		
		Tablero.setEvento_casilla_abierta(new Consumer<Casillas>() {
			@Override
			public void accept(Casillas t) {
				botones_tablero[t.getPosicion_fila()][t.getPosicion_columna()].setEnabled(false);
				botones_tablero[t.getPosicion_fila()][t.getPosicion_columna()].setText(
						t.getContador_minal_alrededor() == 0? "": t.getContador_minal_alrededor()+"");
			}
		});
		
	}
	
	private void cargar_controles() {
		int pos_referencia_X = 10;
		int pos_referencia_Y = 10;
		
		botones_tablero = new JButton[numero_filas][numero_columnas];
		
		for (int i = 0 ; i < botones_tablero.length; i++) {
			for (int j = 0 ; j < botones_tablero[i].length; j++) {
				
				botones_tablero[i][j] = new JButton();
				botones_tablero[i][j].setName(i+","+j);
				//botones_tablero[i][j].setBorder(null); //quita o pone el borde del botón y ya
				
				if (i == 0 && j == 0) {
					botones_tablero[i][j].setBounds(pos_referencia_X, pos_referencia_Y, this.ancho_boton, this.ancho_boton);
				} else if (i == 0 && j!=0) {
					botones_tablero[i][j].setBounds(
							botones_tablero[i][j-1].getX() + botones_tablero[i][j-1].getWidth(), 
							pos_referencia_Y, ancho_boton, this.ancho_boton);
				} else {
					botones_tablero[i][j].setBounds(
							botones_tablero[i-1][j].getX(),
							botones_tablero[i-1][j].getY() + botones_tablero[i-1][j].getHeight(), 
							this.ancho_boton, this.ancho_boton);

				}
				
				botones_tablero[i][j].addActionListener(new ActionListener() {
					@Override
				public void actionPerformed(ActionEvent e) {
						btnClick(e);
					}
				});
				getContentPane().add(botones_tablero[i][j]);
			}
		}
		
		this.setSize(botones_tablero[numero_filas-1][numero_columnas-1].getX() +										
				botones_tablero[numero_filas-1][numero_columnas-1].getWidth() + 30,									
				botones_tablero[numero_filas-1][numero_columnas-1].getY() +										
				botones_tablero[numero_filas-1][numero_columnas-1].getHeight() + 80);									
	}																				
																					
	private void modificar_boton() {
		this.ancho_boton = Integer.parseInt(
				JOptionPane.showInputDialog("Elige el tamaño del boton"));
	}

	private void btnClick(ActionEvent e) {																
		JButton btn = (JButton)e.getSource();															
		String[] coordenada = btn.getName().split(",");														
		int posicion_fila = Integer.parseInt(coordenada[0]);													
		int posicion_columna = Integer.parseInt(coordenada[1]);													
		Tablero.seleccionar_casillas(posicion_fila, posicion_columna);												
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(nueva_partida==e.getSource()) {
			juego_nuevo();
		}else if(cerrar_juego == e.getSource()) {
			System.exit(0);
		}else if(tamano_bloques == e.getSource()) {
			
		}else if(s_principiante == e.getSource()) {
			
			this.numero_filas = 6;
			this.numero_columnas = 5;
			this.numero_minas = 5;
			juego_nuevo();
			
		}else if(s_intermedio == e.getSource()) {
			
			this.numero_filas = 8;
			this.numero_columnas = 7;
			this.numero_minas = 10;
			juego_nuevo();
			
		}else if(s_avanzado == e.getSource()) {
			
			this.numero_filas = 10;
			this.numero_columnas = 10;
			this.numero_minas = 20;
			juego_nuevo();
			
		}else if(s_dificil == e.getSource()) {
			
			this.numero_filas = 11;
			this.numero_columnas = 10;
			this.numero_minas = 25;
			juego_nuevo();
			
		}else if(s_personalizado == e.getSource()) {
			
			this.numero_filas = Integer.parseInt(
					JOptionPane.showInputDialog("Ingrese numero de filas"));
			
			this.numero_columnas = Integer.parseInt(
					JOptionPane.showInputDialog("Ingrese numero de columnas"));
			
			this.numero_minas = Integer.parseInt(
					JOptionPane.showInputDialog("Ingrese numero de minas"));
			
			while (this.numero_filas*this.numero_columnas < this.numero_minas+5) {
				this.numero_minas = Integer.parseInt(
						JOptionPane.showInputDialog("Numero de minas excesivo,\nPor favor ingrese numero valido"));
			}
			juego_nuevo();
		}
		if(tamano_bloques == e.getSource()) {
			modificar_boton();
			juego_nuevo();
		}
	}
}