// PROGRAMA CREADO POR JUAN ESTEBAN REND�N MONTOYA

package PaqueteBuscamina;

public class principal {

	public static void main(String[] args) {
		
		incia_o_ajusta_interfaz();
	}

	private static void incia_o_ajusta_interfaz() {
		try {
			interfaz_buscaminas frame = new interfaz_buscaminas();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
