package GUI;

import javax.swing.JButton;

public class NBoton extends JButton
{
	private static final long serialVersionUID = 1L;
	int valor; //El boton sabe el valor que posee
	
	/**
	 * crea un boton con un valor
	 * @param v valor del boton
	 */
	public NBoton (int v)
	{
		valor = v;
	}
	 /**
	  * devuelve el valor del boton
	  * @return valor del boton
	  */
	public int getValor()
	{
		return valor;
	}

}
