package Logica;
import javax.swing.ImageIcon;

public class Numero 
{
	private ImageIcon imagen;
	private String[] imagenes;
	
	/**
	 *  Crea un numero vacio
	 */
	public Numero()
	{
		imagen = new ImageIcon();
		imagenes = new String[]{"/img/1.png", "/img/2.png", "/img/3.png", "/img/4.png", "/img/5.png", "/img/6.png", "/img/7.png", "/img/8.png", "/img/9.png"};
	}
	
	/**
	 * actualiza el numero por otro
	 * @param indice nuevo numero
	 */
	public void actualizar(int indice) 
	{
		if (indice < imagenes.length) 
		{
			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(imagenes[indice]));
			imagen.setImage(imageIcon.getImage());
		}
	}
	
	/**
	 * devuelve la imagen del numero
	 * @return imagen del numero
	 */
	public ImageIcon getNumero() 
	{
		return imagen;
	}
	
	/**
	 * setea la imagen del numero
	 * @param imagen nueva del numero
	 */
	public void setNumero(ImageIcon num) 
	{
		imagen = num;
	}
	
	/**
	 * devuelve un arreglo de imagens
	 * @return arreglo de imagenes
	 */
	public String[] getImagenes() 
	{
		return imagenes;
	}
	
	/**
	 * setea un nuevo arreglo de imagenes
	 * @param im nuevo arreglo de imagenes
	 */
	public void setImagenes(String[] im) 
	{
		imagenes = im;
	}

}
