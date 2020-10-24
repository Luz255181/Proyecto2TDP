package GUI;
import Logica.*;
import Exception.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

public class GUI extends JFrame {
	private static final long serialVersionUID = 4314923130438313397L;
	protected SBoton[][] botones;
	protected NBoton[][] numeros;
	protected Juego game;
	private JPanel contentPane;
	protected Reloj reloj;
	private int FilaB; //Fila a la que pertenece el boton pulsado
	private int ColumnaB; //Columna a la que pertenece el boton pulsado
	protected JButton Detener;
	protected JButton Inicio;
	protected JButton Reanudar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		Inicio = new JButton("Inicio"); //Creo el boton para iniciar el timer
		Reanudar = new JButton("Reanudar"); //Creo el boton reanudar
		Detener = new JButton("Detener"); //Creo el boton para detener el timer
		
		//Creo el Panel donde se va a encotrar el juego
		JPanel panelJuego = new JPanel();
		contentPane.add(panelJuego, BorderLayout.CENTER);
		panelJuego.setLayout(new GridLayout(9, 9, 0, 0));
		
		//Creo el panel donde se van a encontrar las opciones para completar
		JPanel panelNumeros = new JPanel();
		contentPane.add(panelNumeros, BorderLayout.EAST);
		panelNumeros.setLayout(new GridLayout(9, 1, 0, 0));
		
		//Creo el panel para manejar el timer y colocar el timer
		JPanel panelTimerInicio = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelTimerInicio.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panelTimerInicio, BorderLayout.NORTH);
		
		//creo el panel del reloj
		reloj = new Reloj();
		panelTimerInicio.add(reloj);
		reloj.setPreferredSize(new Dimension(300,80));
		
		//Inicializo el boton de Detener y le agrego el ActionListener
		Detener.setHorizontalAlignment(SwingConstants.LEFT);
		panelTimerInicio.add(Detener);
		Detener.setEnabled(false);
		Detener.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Detener.setEnabled(false);//desactivo el boton
				Reanudar.setEnabled(true);//Activo el boton reanudar
				//Desactivo todos los botones del juego, solo los que no poseen Icon o estan mal
				for(int i=0; i< botones.length;i++) 
				{ 
					for (int j=0; j< botones.length;j++) 
					{ 
						SBoton boton = botones[i][j];
						boton.setEnabled(false);
					}
				}
				reloj.detener();
			}
		});
		
		//Inicializo el boton de Reanudar y le agrego el ActionListener
		panelTimerInicio.add(Reanudar);
		Reanudar.setEnabled(false);
		Reanudar.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{
						Reanudar.setEnabled(false);//Desactivo el boton Reanudar
						Detener.setEnabled(true);//Activo el boton detener
						//Activo los botones del panel del juego, solo los que no poseen Icon o estan mal
						for(int i=0; i< botones.length;i++) 
						{ 
							for (int j=0; j< botones.length;j++) 
							{ 
								try
								{
									SBoton boton = botones[i][j];
									Celda cel = game.getCelda(i, j);
									if(cel.getNumero() == 10 || !game.verificarJuego(j, i, cel.getNumero()))
									{
										boton.setEnabled(true);
									}
								}
								catch(JuegoException h)
								{
									JOptionPane.showMessageDialog(null, "Error al iniciar el juego");
								}
							}
						}
						reloj.inicio();
					}
			
				});
		
		//Inicializo el boton de Inicio y le agrego el ActionListener
		panelTimerInicio.add(Inicio);
		Inicio.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				Inicio.setEnabled(false); //Desactivo el boton de inicio
				Detener.setEnabled(true); //Activo el boton de detener
				//Activo los botones del juego, solo los que no poseen Icon
				for(int i=0; i< botones.length;i++) 
				{ 
					for (int j=0; j< botones.length;j++) 
					{ 
						try
						{
							SBoton boton = botones[i][j];
							Celda cel = game.getCelda(i, j);
							if(cel.getNumero()==10)
							{
								boton.setEnabled(true);
							}
						}
						catch(JuegoException h)
						{
							JOptionPane.showMessageDialog(null, "Error al iniciar el juego");
						}
					}
				}
				reloj.inicio();
			}

		});


		

		//Creo un mensaje con las reglas del juego
		String s1 = "1) Completar las casillas vacías con un numero del 1 al 9";
		String s2 = "2) Una misma fila no puede contener numeros repetidos";
		String s3 = "3) Una misma columna no puede contener numeros repetidos";
		String s4 = "4) Un mismo panel no puede contener numeros repetidos";
		String s5 = "5) Gana cuando completa el juego sin errores";
		String s = s1+"\n"+s2+"\n"+s3+"\n"+s4+"\n"+s5+"\n";
		JOptionPane.showConfirmDialog(new JFrame(),"Reglas: \n"+s+"\n ¡Mucha Suerte!","Que comience el juego",JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);

		//creo el juego
		try
		{
			game = new Juego();
			//inicializar del juego
			botones = new SBoton[game.getCantFilasColumnas()][game.getCantFilasColumnas()];//Creo la botonera
			//Creo cada boton de la botonera y le asigno su imagen
			for(int i=0; i< game.getCantFilasColumnas();i++) 
			{ 
				for (int j=0; j< game.getCantFilasColumnas();j++) 
				{ 
					botones[i][j]=new SBoton(i,j); 
					panelJuego.add(botones[i][j]);
					SBoton boton = botones[i][j];
					Celda cel = game.getCelda(i, j);
					if(cel.getImagen() != null)
					{
						ImageIcon grafico = cel.getImagen().getNumero();
						boton.addComponentListener(new ComponentAdapter() 
						{
							public void componentResized(ComponentEvent e) 
							{
								boton.setIcon(grafico);
							}
						});
						boton.setDisabledIcon(grafico);
					}
					boton.setEnabled(false);
					boton.addActionListener(new SOyente(botones[i][j]));
					//Marco los lados correspondientes al los 9 paneles
					if(i==0 || i==3 || i==6) 
					{
						if(j==0 || j==3 || j==6)
							botones[i][j].setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, Color.black));
						else 
							if(j==8)
								botones[i][j].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 3, Color.black));
							else
								botones[i][j].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, Color.black));
					}
					else 
						if(i==8) 
						{
							if(j==0 || j==3 || j==6)
								botones[i][j].setBorder(BorderFactory.createMatteBorder(1, 3, 3, 1, Color.black));
							else 
								if(j==8)
									botones[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.black));
								else
									botones[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 1, Color.black));
						}
						else 
						{
							if(j==0 || j==3 || j==6)
								botones[i][j].setBorder(BorderFactory.createMatteBorder(1, 3, 1, 1, Color.black));
							else 
								if(j==8)
									botones[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 3, Color.black));
								else
									botones[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
						}
				}
			}

		}
		catch (JuegoException e)
		{
			JOptionPane.showMessageDialog(null, "El juego sufrio un error durante su carga");
		}
		
		//inicializar los botones de numeros disponibles
		numeros = new NBoton[game.getCantFilasColumnas()][1]; //Creo la botonera de numeros para completar
		//Creo los 9 botones de opciones con su Icon
		for(int i = 0; i<game.getCantFilasColumnas();i++)
		{
			numeros[i][0]=new NBoton(i+1); 
			panelNumeros.add(numeros[i][0]);
			NBoton boton = numeros[i][0];
			try
			{
				Celda cel = new Celda(i+1);
				ImageIcon grafico = cel.getImagen().getNumero();
				boton.addComponentListener(new ComponentAdapter() 
				{
					public void componentResized(ComponentEvent e) 
					{
						boton.setIcon(grafico);
					}
				});
			}
			catch(ErrorIndexException e)
			{
				JOptionPane.showMessageDialog(null, "El juego sufrio un error durante su carga");
			}
			boton.addActionListener(new NOyente(numeros[i][0]));
			boton.setEnabled(false);
		}
	}
	
	//Creo una clase interna para el oyente del boton de opciones
	class NOyente implements ActionListener
	{
		int valor;
		
		public NOyente(NBoton b)
		{
			valor = b.getValor();
		}
		
		public void actionPerformed(ActionEvent e) 
		{
			//Activa todos los botones del panel del juego
			for(int i=0; i< botones.length;i++) 
			{ 
				for (int j=0; j< botones.length;j++) 
				{ 
					try
					{
						SBoton boton = botones[i][j];
						Celda cel = game.getCelda(i, j);
						if(cel.getNumero() == 10 || !game.verificarJuego(j, i, cel.getNumero()))
						{
							boton.setEnabled(true);
						}
					}
					catch(JuegoException h)
					{
						JOptionPane.showMessageDialog(null, "Error");
					}
				}
			}
			
			//Desactiva los botones de las opciones
			for(int i = 0; i<game.getCantFilasColumnas(); i++)
			{
				numeros[i][0].setEnabled(false);
			}
			try
			{
				game.setCelda(valor, FilaB, ColumnaB);
				SBoton boton = botones[FilaB][ColumnaB];
				Celda cel =game.getCelda(FilaB, ColumnaB);
				Numero num = cel.getImagen();
				ImageIcon ico=num.getNumero();
				boton.setIcon(ico);
				if(!game.verificarJuego(ColumnaB, FilaB, valor))
				{ 
					//Pinta el boton de rojo si no es correcto
					boton.setBackground(Color.red);
				}
				else
				{
					//Pinta el boton de blanco si es correcto y lo desactiva
					boton.setBackground(Color.white);
					boton.setDisabledIcon(ico);
					boton.setEnabled(false);
				}
			}
			catch(CeldaInvalida | JuegoException h)
			{
				JOptionPane.showMessageDialog(null, "Error");
			}
			if(game.gano())
			{
				//si el jugador gana, se detiene el Reloj y se muestra un mensaje
				reloj.detener();
				Detener.setEnabled(false);
				JOptionPane.showMessageDialog(null, "FELICITACIONES \n ¡Ganaste!");
				
			}
		}
		
	}
	
	//Clase interna para el oyente de los botones del juego
	private class SOyente implements ActionListener
	{
		int fila;
		int columna;
		
		public SOyente(SBoton b)
		{
			fila = b.getFila();
			columna = b.getColumna();
			
		}
		
		public void actionPerformed(ActionEvent e) 
		{
			FilaB = fila; //Le dice al juego en que fila se encuentra
			ColumnaB = columna; //le dice al juego en que columna se encuentra
			//Desactiva los botones del juego
			for(int i=0; i< botones.length;i++) 
			{ 
				for (int j=0; j< botones.length;j++) 
				{ 
					botones[i][j].setEnabled(false);
				}
			}
			//Activa los botones de opciones
			for(int i = 0; i<game.getCantFilasColumnas(); i++)
			{
				numeros[i][0].setEnabled(true);
			}
		}
	}
}
