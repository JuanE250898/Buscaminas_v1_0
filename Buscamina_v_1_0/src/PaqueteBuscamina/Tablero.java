package PaqueteBuscamina;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
/**
 * @author Juan Esteban Rendon M
 */
public class Tablero {
	Casillas[][] Casillas;
	
	int numero_filas;
	int numero_columnas;
	int numero_minas;
	int numero_casillas_abiertas;
	boolean juego_terminado;
	private Consumer<List<Casillas>> evento_partida_perdida;
	private Consumer<List<Casillas>> evento_partida_ganada;
	Consumer <Casillas> evento_casilla_abierta;

	public Tablero(int numero_filas, int numero_columnas, int numero_minas) {
		this.numero_filas = numero_filas;
		this.numero_columnas = numero_columnas;
		this.numero_minas = numero_minas;
		inicializa_casillas();
	
	}	

	public void inicializa_casillas() {
		Casillas =new Casillas[this.numero_filas][this.numero_columnas];
		
		for (int i = 0; i<Casillas.length; i++) {
			for (int j = 0; j<Casillas[i].length; j++) {
				Casillas[i][j] = new Casillas(i,j);
			}
		}
		generar_minas();
	}
	
	private void generar_minas() {
		int minas_generadas = 0;
		while(minas_generadas!=numero_minas) {
			int posicion_temp_fila = (int) (Math.random()*Casillas.length);
			int posicion_temp_columna = (int) (Math.random()*Casillas[0].length);
			
			if (!Casillas[posicion_temp_fila][posicion_temp_columna].isHay_mina()) {
				
				Casillas[posicion_temp_fila][posicion_temp_columna].setHay_mina(true);
				minas_generadas++;
				
			}
			
		}
		Buscar_minas_alrededor();
	}
	
	private void Buscar_minas_alrededor() {
		
		for (int i = 0; i<Casillas.length; i++) {
			for (int j = 0; j<Casillas[i].length; j++) {

				if(Casillas[i][j].isHay_mina()) {
					List<Casillas> casillas_alrededor = obtener_casillas_alrededor(i,j);
					// permite sumar el numero de la variable casillas
					// El metodo .forEach permite revisar todos los valores dentro de la lista sin usar For y una variable
					// Esta linea llama la funcion para sumar el numero alrededor segun su pocision
					casillas_alrededor.forEach((c)->c.incrementar_numero_minas());
				}
				
			}
			
		}
	}

	private List<Casillas> obtener_casillas_alrededor(int posicion_fila,int posicion_columna){
		
		List<Casillas> lista_casillas = new LinkedList<>();
		for (int i = 0; i < 8 ; i++) {
			int posicion_temp_fila = posicion_fila;
			int posicion_temp_columna = posicion_columna;
			
			switch(i) {
				case 0: 
					posicion_temp_fila--;
					posicion_temp_columna--;
				break; //Arriba Izquierda
				
				case 1: 
					posicion_temp_fila--;
				break; //Arriba Centro
				
				case 2:
					posicion_temp_fila--;
					posicion_temp_columna++;
				break; //Arriba Derecha
				
				case 3: posicion_temp_columna--; break; //Izquierda
				
				case 4: posicion_temp_columna++; break; //Derecha
				
				case 5: posicion_temp_fila++; posicion_temp_columna--; break; //Abajo Izquierda
				
				case 6: posicion_temp_fila++; break; //Abajo Centro
				
				case 7: posicion_temp_fila++; posicion_temp_columna++; break; //Abajo Derecha
				
			}
			if(posicion_temp_fila>=0 && posicion_temp_fila<this.Casillas.length &&
					posicion_temp_columna>=0 && posicion_temp_columna<this.Casillas[0].length) {
				// La linea importante del codigo
				lista_casillas.add(this.Casillas[posicion_temp_fila][posicion_temp_columna]);
			}
		}
		return lista_casillas;
	}
	
	List<Casillas> obtener_casillas_con_minas(){
		
		List<Casillas> casillas_con_minas = new LinkedList<>();
		for (int i = 0; i<Casillas.length; i++) {
			for (int j = 0; j<Casillas[i].length; j++) {
				if (Casillas[i][j].isHay_mina()) {
					casillas_con_minas.add(Casillas[i][j]);
				}
				
			}
		}
		return casillas_con_minas;
	}
	
	public void seleccionar_casillas(int posicion_fila,int posicion_columna) {
		evento_casilla_abierta.accept(this.Casillas[posicion_fila][posicion_columna]);
		
		if (this.Casillas[posicion_fila][posicion_columna].isHay_mina()) {
			evento_partida_perdida.accept(obtener_casillas_con_minas());
			
		} else if (this.Casillas[posicion_fila][posicion_columna].getContador_minal_alrededor() == 0) {
			
			marca_casillas_abiertas(posicion_fila , posicion_columna);
			List<Casillas> casillas_alrededor = obtener_casillas_alrededor(posicion_fila, posicion_columna);
			for (Casillas casilla: casillas_alrededor) {
				
				if (!casilla.esta_abierta()) {
					//casilla.setabierta(true); //ESTO YA LO HACEMOS EN EL METODO DE MARCAR LAS CASILLAS ABIERTAS
					seleccionar_casillas(casilla.getPosicion_fila() , casilla.getPosicion_columna());
				}
			}
		}else {
			marca_casillas_abiertas(posicion_fila , posicion_columna);
		}
		if (partida_ganada()) {
			evento_partida_ganada.accept(obtener_casillas_con_minas());
		}
		
	}
	
	void marca_casillas_abiertas (int posicion_fila, int posicion_columna) {
		if (!this.Casillas[posicion_fila][posicion_columna].esta_abierta()) {
			numero_casillas_abiertas ++;
			this.Casillas[posicion_fila][posicion_columna].setabierta(true);
		}
	}
	
	boolean partida_ganada() {
		return numero_casillas_abiertas >= (numero_filas*numero_columnas)-numero_minas;
	}

	public void setEvento_partida_perdida(Consumer<List<Casillas>> evento_partida_perdida) {
		this.evento_partida_perdida = evento_partida_perdida;
	}

	public void setEvento_casilla_abierta(Consumer<Casillas> evento_casilla_abierta) {
		this.evento_casilla_abierta = evento_casilla_abierta;
	}

	public void setEvento_partida_ganada(Consumer<List<Casillas>> evento_partida_ganada) {
		this.evento_partida_ganada = evento_partida_ganada;
	}
	
}
