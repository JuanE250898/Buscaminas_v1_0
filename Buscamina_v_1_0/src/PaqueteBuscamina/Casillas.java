package PaqueteBuscamina;

public class Casillas {
	int posicion_fila;
	int posicion_columna;
	boolean hay_mina;
	int contador_minas_alrededor;
	boolean abierta;
	
	public int getContador_minal_alrededor() {
		return contador_minas_alrededor;
	}
	public void setContador_minal_alrededor(int contador_minal_alrededor) {
		this.contador_minas_alrededor = contador_minal_alrededor;
	}
	public Casillas(int posicion_fila , int posicion_columna) {
		this.posicion_fila = posicion_fila;
		this.posicion_columna = posicion_columna;
	}
	public int getPosicion_fila() {
		return posicion_fila;
	}
	public void setPosicion_fila(int posicion_fila) {
		this.posicion_fila = posicion_fila;
	}
	public int getPosicion_columna() {
		return posicion_columna;
	}
	public void setPosicion_columna(int posicion_columna) {
		this.posicion_columna = posicion_columna;
	}
	public boolean isHay_mina() {
		return hay_mina;
	}
	public void setHay_mina(boolean hay_mina) {
		this.hay_mina = hay_mina;
	}
	public void incrementar_numero_minas() {
		this.contador_minas_alrededor++;
	}	
	public boolean esta_abierta() {
		return abierta;
	}	
	public void setabierta( boolean abierta) {
		this.abierta = abierta;
	}
}
