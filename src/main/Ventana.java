package main;


import javax.swing.JFrame;

import logicaDeJuego.MouseInfo;
import logicaDeJuego.PanelDeJuego;

public class Ventana extends JFrame{
	
	public static final int ANCHO = 800, ALTO = 600;
	
	private PanelDeJuego panelJuego;
	private MouseInfo mouse;
	
	public Ventana()
	{
		setTitle("Juego De Memoria // JOSHUA HERNANDEZ");
		setSize(ANCHO, ALTO);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		mouse = new MouseInfo();
		
		panelJuego = new PanelDeJuego();
		
		addMouseMotionListener(mouse);
		addMouseListener(mouse);
		add(panelJuego);
		
		setVisible(true);
	}
	
}
