package Logica;
import Exception.*;

public class Celda 
{
	private int num;
	private Numero imagen;
	
	/**
	 * crea una celda vacia
	 */
	public Celda()
	{
		num = 10;
		imagen = null;
	}
	/**
	 * Crea una celda con un numero seteado
	 * @param n numero a setear
	 * @throws ErrorIndexException el numero pasado no es valido
	 */
	
	public Celda(int n) throws ErrorIndexException
	{
		if(0< n && n < 10)
		{
			num = n;
			imagen = new Numero();
			imagen.actualizar(n-1);
		}
		else
			throw new ErrorIndexException("Numero de indice Invalido");
	}
	
	/**
	 * setea el numero de la celda
	 * @param n numero a setear
	 * @throws ErrorIndexException el numero pasado no es valido
	 */
	public void setNumero(int n) throws ErrorIndexException
	{
		if(0< n && n < 10)
			num = n;
		else
			throw new ErrorIndexException("Numero de indice Invalido");
	}
	
	/**
	 * setea una nueva imagen para el numero
	 * @param n numero que se representa
	 * @throws ErrorIndexException si el numero pasado no es valido
	 */
	public void setImagen(int n) throws ErrorIndexException
	{
		if(imagen == null)
		{
			imagen = new Numero();
			imagen.actualizar(n-1);
		}
		if(0< n && n < 10)
			imagen.actualizar(n-1);
		else
			throw new ErrorIndexException("Numero de indice Invalido");
	}
	
	/**
	 * devulve el numero que posee la celda
	 * @return numero de la celda
	 */
	public int getNumero()
	{
		return num;
	}
	
	/**
	 * devuelve la imagen que posee la celda
	 * @return imagen de la celda
	 */
	public Numero getImagen()
	{
		return imagen;
	}
}
