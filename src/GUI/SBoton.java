package GUI;

import javax.swing.JButton;

public class SBoton extends JButton
{
	private static final long serialVersionUID = 1L;
	protected int fila; //El boton conoce la fila a la que pertenece
	protected int columna; //El boton conoce la columna a la que pertenece
	
	/**
	 * crea un boton que conoce su posicion en la matriz
	 * @param f fila a la que pertenece
	 * @param c columna a la que pertene
	 */
	public SBoton(int f, int c)
	{
		fila = f;
		columna = c;
	}
	
	/**
	 * retorna la fila a la que pertenece el boton
	 * @return fila a la que pertenece el boton
	 */
	public int getFila()
	{
		return fila;
	}
	
	/**
	 * retorna la columna a la que pertenece el boton
	 * @return columna a la que pertenece el boton
	 */
	public int getColumna()
	{
		return columna;
	}
}
