package Logica;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
public class Reloj extends JLabel
{
	private static final long serialVersionUID = 1L;
	
	protected String[] imagenes;
	protected ImageIcon[] numeros;
	protected JLabel horasD, horasU, minutosD, minutosU, segundosD, segundosU; //un label para cada imagen que se va a representar en el timer
	protected JLabel separador1, separador2; //Un timer para cada separador entre horas y minutos y entre minutos y segundos
	protected Timer tiempo;
	protected int horas, minutos, segundos;//numeros que van a manejar las horas, minutos y segundos
	
	public Reloj()
	{
		//creo cada label
		horasD = new JLabel();
		horasU = new JLabel();
		minutosD = new JLabel();
		minutosU = new JLabel();
		segundosD = new JLabel();
		segundosU = new JLabel();
		separador1 = new JLabel();
		separador2 = new JLabel();
		
		//inicio las horas minutos y segundos en 0
		horas = minutos = segundos = 0;
		
		//agrego todas la imagenes en el arreglo
		imagenes = new String[] {"/img/0R.png" , "/img/1R.png" , "/img/2R.png" , "/img/3R.png" , "/img/4R.png" , "/img/5R.png" , "/img/6R.png" , "/img/7R.png" , "/img/8R.png" , "/img/9R.png" , "/img/puntos.png"};
		numeros = setImagenes();
		
		tiempo = new Timer(1000,new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(segundos < 59)
				{
					segundos++;
				}
				else
				{
					if(minutos < 59)
					{
						segundos = 0;
						minutos++;
					}
					else
					{
						minutos = 0;
						horas++;
					}
				}

				//seteo las imagenes en los labels
				segundosU.setIcon(numeros[segundos%10]);
				reDimensionar(segundosU,numeros[segundos%10]);
				segundosD.setIcon(numeros[segundos/10]);
				reDimensionar(segundosD,numeros[segundos/10]);
				minutosU.setIcon(numeros[minutos%10]);
				reDimensionar(minutosU,numeros[minutos%10]);
				minutosD.setIcon(numeros[minutos/10]);
				reDimensionar(minutosD,numeros[minutos/10]);
				horasU.setIcon(numeros[horas%10]);
				reDimensionar(horasU,numeros[horas%10]);
				horasD.setIcon(numeros[horas/10]);
				reDimensionar(horasD,numeros[horas/10]);
			}

		});
		
		HorasMinutosSegundos(separador1,separador2);
	}
	
	/**
	 * organiza los Label dentro del Label
	 * @param sep1 Label del separador1
	 * @param sep2 Label del separador2
	 */
	public void HorasMinutosSegundos(JLabel sep1, JLabel sep2)
	{
		this.setLayout(new GridLayout(0,8,0,0));
		this.setBackground(Color.white);
		
		this.add(horasD);
		this.add(horasU);
		
		sep1.setIcon(new ImageIcon(this.getClass().getResource(imagenes[10])));
		this.add(sep1);
		
		this.add(minutosD);
		this.add(minutosU);
		
		sep2.setIcon(new ImageIcon(this.getClass().getResource(imagenes[10])));
		this.add(sep2);

		this.add(segundosD);
		this.add(segundosU);
	}

	/**
	 * da inicio al reloj
	 */
	public void inicio()
	{
		tiempo.start();
	}

	/**
	 * detiene el reloj
	 */
	public void detener()
	{
		tiempo.stop();
	}

	/**
	 * reanuda el reloj
	 */
	public void reanudar()
	{
		tiempo.start();
	}
	
	public ImageIcon[] setImagenes() 
	{		
		ImageIcon[] toReturn = new ImageIcon[11];
		for(int i=0; i<11; i++)
			toReturn[i] = new ImageIcon(getClass().getResource(imagenes[i]));
		return toReturn;
	}
	
	/**
	 * redimensiona las imagenes que pertenecen al label
	 * @param label lebel a acomodar
	 * @param grafico imagen a colocar
	 */
	protected void reDimensionar(JLabel label, ImageIcon grafico) 
	{
		Image image = grafico.getImage();
		if (image != null) 
		{  
			Image newimg = image.getScaledInstance(label.getWidth(), label.getHeight(),  java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);
			label.repaint();
		}
	}
}
