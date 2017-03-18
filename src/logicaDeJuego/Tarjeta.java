package logicaDeJuego;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

public class Tarjeta {
	
	public static final int TAMANO = 100;
	
	private BufferedImage textura;
	
	private boolean bocaAbajo;
	
	private boolean interactuando;
	
	private Rectangle dimension;
	
	private PanelDeJuego tablero;
	
	public Tarjeta(BufferedImage textura, PanelDeJuego tablero)
	{
		this.textura = textura;
		this.tablero = tablero;
		
		bocaAbajo = true;
		interactuando = false;
		
	}
	Timer timer = new Timer(100, new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			timer.stop();
		}});
	
	
	public void render(Graphics g, int x, int y)
	{
		dimension = new Rectangle(x, y, TAMANO, TAMANO);
		
		if(dimension.contains(MouseInfo.posicion) && bocaAbajo)
			interactuando = true;
		else
			interactuando = false;
		
		if(dimension.contains(MouseInfo.posicion) && MouseInfo.izq && !tablero.getTimer().isRunning()
				&& bocaAbajo)
		{
			bocaAbajo = false;
			tablero.getPareja().add(this);
		}
			
		if(bocaAbajo)
			g.drawImage(tablero.getBocaAbajo(), x, y, null);
		else
			g.drawImage(textura, x, y, null);
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setStroke(new BasicStroke(3));
		
		g2d.setColor(Color.RED);
		
		if(interactuando)
			g2d.drawRect(x, y, TAMANO, TAMANO);
		
	}
	
	public BufferedImage getTextura()
	{
		return textura;
	}
	
	public boolean isBocaAbajo()
	{
		return bocaAbajo;
	}
	
	public void setBocaAbajo()
	{
		bocaAbajo = true;
	}
}
