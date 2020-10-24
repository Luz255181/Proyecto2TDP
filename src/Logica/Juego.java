package Logica;

import Exception.*;
import java.io.*;
import java.util.Random;


public class Juego 
{
	private Celda [][] tablero;
	private int [][] solucion;
	private int FilasColumnas;

	/**
	 * crea un tablero vacio de 9 x 9
	 * @throws JuegoException si el juego no se pudo cargar correctamente
	 */
	public Juego() throws JuegoException
	{
		FilasColumnas = 9;
		tablero = new Celda[FilasColumnas][FilasColumnas];
		for (int i =0; i<FilasColumnas; i++) 
		{
			for (int j =0; j<FilasColumnas; j++) 
			{
				tablero[i][j] = new Celda();
			}
		}
		if(cargarSolucion())
		{
			Random rand = new Random();
			for(int i = 0; i<FilasColumnas; i++)
			{
				for(int j = 0; j<FilasColumnas; j++)
				{
					try
					{
						if(rand.nextBoolean())
						{
							tablero[i][j].setImagen(solucion[i][j]);
							tablero[i][j].setNumero(solucion[i][j]);
						}
					}
					catch(ErrorIndexException e)
					{
						throw new JuegoException("");
					}
				}
			}
		}
		else
		{
			throw new JuegoException("Error al cargar el juego");
		}

	}

	/**
	 * Carga la solucion desde un archivo y controla que sea valida
	 * @return verdadero si la solucion es correcta o falso si no lo es
	 */
	private boolean cargarSolucion() throws JuegoException
	{
		boolean correcto = true;
		try
		{
			File archivo = new File ("src\\Solucion\\Solucion.txt");
			FileReader fr = new FileReader (archivo);
			BufferedReader br = new BufferedReader(fr);
			solucion = new int[FilasColumnas][FilasColumnas];
			String linea = "";
			String[] auxiliar;
			int valor = 0;
			int fila = 0;
			while((linea = br.readLine())!=null && correcto) 
			{
				auxiliar = linea.split(" ");
				if(auxiliar.length==FilasColumnas)
					for(int i=0; i<auxiliar.length && correcto; i++) 
					{
						valor = Integer.parseInt(auxiliar[i]);
						if(valor>0 && valor<10)
							solucion[fila][i] = valor;
						else
							correcto = false;
					}
				else
					correcto = false;
				fila++;
			}
			br.close();
		}
		catch(NullPointerException | IOException e)
		{
			e.printStackTrace();
			correcto = false;
		}
		for(int i = 0; i<FilasColumnas && correcto; i++)
		{
			for(int j = 0; j<FilasColumnas && correcto; j++)
			{
				correcto = verificarJuego(j,i,solucion[i][j]);
			}
		}
		return correcto;
	}

	/**
	 * verifica si el valor ingresado es valido 
	 * @param col columna a la que pertenece el valor
	 * @param fila fila a la que pertenece el valor
	 * @param val valor a verificar
	 * @return si el valido el valor en la posicion o no
	 * @throws JuegoException si el valor, fila o columna no son validos
	 */
	public boolean verificarJuego(int col, int fila, int val) throws JuegoException
	{
		boolean correcto = true;
		if(fila>=0 && fila<9 && col >=0 && col <9)
		{
			if(correcto)
				correcto = verificarPanel(col, fila, val);
 			if(correcto)
				correcto = verificarFila(fila, val, col);
			if(correcto)
				correcto = verificarColumna(col, val, fila);
		}
		else
			throw new JuegoException("Fila o columna Invalida");
		return correcto;
	}
	
	/**
	 * verifica si el valor dado ya se encuentra en el panel donde se escribio.
	 * @param col columna a la que pertenece el valor modificado
	 * @param fila fila a la que pertenece el valor modificado
	 * @param val valor modificado
	 * @return si el valor ya pertenecesia al panel o no
	 * @throws JuegoException si el valor dado no es valido
	 */
	private boolean verificarPanel(int col, int fila, int val) throws JuegoException
	{
		boolean correcto = valorValido(val);
		int pan = panel(fila,col);
		int f = 0;
		int c = 0;
		switch(pan)
		{
		case 0:
			f = 0; c = 0;
			break;
		case 1:
			f = 0; c = 3;
		break;
		case 2:
			f = 0; c = 6;
			break;
		case 3:
			f = 3; c = 0;
			break;
		case 4:
			f = 3; c = 3;
			break;
		case 5:
			f = 3; c = 6;
			break;
		case 6:
			f = 6; c = 0;
			break;
		case 7:
			f = 6; c = 3;
			break;
		case 8:
			f = 6; c = 6;
			break;
		}
		correcto = revisarPanel(f,c,col,fila,val);
		
		return correcto;
	}
	
	/**
	 * verifica si el valor dado es valido
	 * @param valor valor a verificar
	 * @return verdadero si el valor es valido
	 * @throws JuegoException si el valor no es valido
	 */
	private boolean valorValido(int valor) throws JuegoException
	{
		if(valor<=0 && valor>=10)
			throw new JuegoException("Valor Invalido");
		return true;
	}
	
	/**
	 * devuelve el panel en le que se encuentra la celda dada
	 * @param fila fila a la que pertenece la celda
	 * @param columna columna a la que pertenece la celda
	 * @return panel a la que pertenece la celda
	 * @throws JuegoException si la fila o la columna no son validas
	 */
	private int panel(int fila, int columna) throws JuegoException
	{
		int pan = 0;
			if(fila>=0 && fila<3)
			{
				if(columna>=0 && columna<3)
					pan = 0;
				else
				{
					if (columna>=3 && columna<6)
						pan = 1;
					else
					{
						if(columna>=6 && columna<9)
							pan = 2;
						else
							throw new JuegoException("Columna Invalida");
					}
				}
			}
			else
			{
				if(fila>=3 && fila<6)
				{
					if(columna>=0 && columna<3)
						pan = 3;
					else
					{
						if (columna>=3 && columna<6)
							pan = 4;
						else
						{
							if(columna>=6 && columna<9)
								pan = 5;
							else
								throw new JuegoException("Columna Invalida");
						}
					}
				}
				else
				{
					if(fila>=6 && fila<9)
					{
						if(columna>=0 && columna<3)
							pan = 6;
						else
						{
							if (columna>=3 && columna<6)
								pan = 7;
							else
							{
								if(columna>=6 && columna<9)
									pan = 8;
								else
									throw new JuegoException("Columna Invalida");
							}
						}
					}
					else
						throw new JuegoException("Fila Invalida");
				}
			}
		return pan;
	}
	
	/**
	 * verifica si el valor dado se encuentra en el panel dado
	 * @param f fila donde arranca el panel
	 * @param c columna donde arranca el panel
	 * @param col columna donde se encuentra el valor
	 * @param fila fila dopnde se encuentra el valor
	 * @param val valor a comparar
	 * @return retorna verdadero si el valor dado no esta en el panel, en caso contrario retorna falso.
	 */
	private boolean revisarPanel(int f, int c, int col, int fila, int val)
	{
		boolean correcto = true;
		for(int i = f; i<(f+3); i++)
		{
			for(int j = c; j<(c+3); j++)
			{
				if(fila!=i || col!=j)
				{
					if(solucion[i][j] == val)
						correcto = false;
				}
			}
		}
		return correcto;
	}
	
	/**
	 * verifica si el valor dado pertenece a la fila
	 * @param fila fila a controlar
	 * @param val valor a verificar
	 * @return retorna si el valor ya pertenece ya pertenecia a la fila o no
	 * @throws JuegoException si el valor dado no es valido o si la fila dada no es valida
	 */
	private boolean verificarFila(int fila, int val, int col) throws JuegoException
	{
		boolean correcto = valorValido(val);
				for(int i = 0; i<FilasColumnas && correcto; i++)
				{
					if(i != col)
					{
						if(solucion[fila][i] == val)
							correcto = false;
					}
				}
		return correcto;
	}
	
	/**
	 * verifica si el valor dado pertenece a la columna dada
	 * @param col columna a verificar
	 * @param val valor a controlar
	 * @return si el valor dado ya pertenece a la columna o no
	 * @throws JuegoException si el valor dado es invalido o si la columna dada es invalida
	 */
	private boolean verificarColumna(int col, int val, int fila) throws JuegoException
	{
		boolean correcto = valorValido(val);
				for(int i = 0; i<FilasColumnas;i++)
				{
					if(i != fila)
					{
						if(solucion[i][col] == val)
							correcto = false;
					}
				}
		return correcto;
	}

	/**
	 * inicial el juego en nivel facil
	 */
	public void iniciarJuego()
	{
		Random rand = new Random();
		for(int i = 0; i<FilasColumnas; i++)
		{
			for(int j = 0; j<FilasColumnas; j++)
			{
				if(rand.nextBoolean())
				{ 
					try
					{
						setCelda(i,j,solucion[i][j]);
					}
					catch(CeldaInvalida e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * devulve la celda en la posicion ij
	 * @param i columna en la que esta la celda
	 * @param j fila en la que esta la celda
	 * @return celda en la posicion ij
	 */
	public Celda getCelda(int i, int j) throws JuegoException
	{
		if(0<=i && i<9 && 0<=j && j<9)
			return tablero[i][j];
		else
			throw new JuegoException("La fila o la columna no son validas");
	}
	
	/**
	 * devuelve la cantidfad de filas y columnas de la matriz
	 * @return cantidad de filas y columnas
	 */
	public int getCantFilasColumnas() 
	{
		return FilasColumnas;
	}

	/**
	 * setea los valores de la celda en la posicion ij
	 * @param valor valor a setear
	 * @param fila fila a la que pertenece la celda
	 * @param columna columna a la que pertenece la celda
	 * @throws CeldaInvalida no es posible setear la celda
	 */
	public void setCelda(int valor, int fila, int columna) throws CeldaInvalida
	{
		try
		{
				tablero[fila][columna].setNumero(valor);
				tablero[fila][columna].setImagen(valor);
		}
		catch(ErrorIndexException e)
		{
			throw new CeldaInvalida("");
		}
	}
	
	/**
	 * retorna el tablero del juego
	 * @return
	 */
	public Celda[][] getTablero()
	{
		return tablero;
	}
	
	/**
	 * verifica si el jugador gano el juego
	 * @return verdadero si el jugador gano, falso en caso contrario
	 */
	public boolean gano()
	{
		boolean ganar = true;
		for(int i = 0; i<FilasColumnas && ganar; i++)
		{
			for(int j = 0; j<FilasColumnas && ganar; j++)
			{
				if(tablero[i][j].getNumero() != solucion[i][j])
					ganar = false;
			}
		}
		return ganar;
	}
}
