package logicaDeJuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelDeJuego extends JPanel{
	
	private final int cantidadFiguras = 22;
	
	private BufferedImage[] texturas = new BufferedImage[cantidadFiguras];
	
	private BufferedImage bocaAbajo, fondo;
	
	private Timer pintador;
	
	private final int FPS = 30;
	
	private final int delay = 1000/FPS;
	
	private ArrayList<Tarjeta> tarjetas = new ArrayList<Tarjeta>();
	
	private Tarjeta t1, t2;
	
	private Timer timer = new Timer(500, new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			t1.setBocaAbajo();
			t2.setBocaAbajo();
			timer.stop();
			
		}});
	
	private ArrayList<Tarjeta> pareja = new ArrayList<Tarjeta>();
	
	private int intentos = 0;
	
	private boolean gameOver = false;
	
	public PanelDeJuego()
	{
		for(int i = 0; i < texturas.length; i++)
			try {
				texturas[i] = ImageIO.read(PanelDeJuego.class.getResource("/sprites/"+i+".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		try {
			bocaAbajo = ImageIO.read(PanelDeJuego.class.getResource("/sprites/bocaAbajo.png"));
			fondo = ImageIO.read(PanelDeJuego.class.getResource("/sprites/fondo.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		pintador = new Timer(delay, new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
			
		});
		pintador.start();
		
		for(int i = 0; i < 10; i++)
		{
			bucle:
			while(true)
			{
				int random = (int)(Math.random()*cantidadFiguras);
				
				Tarjeta tarjeta = new Tarjeta(texturas[random], this);
				
				for(int j = 0; j < tarjetas.size(); j++)
				{
					if(tarjetas.get(j).getTextura().equals(tarjeta.getTextura()))
					{
						continue bucle;
					}
				}
				tarjetas.add(tarjeta);
				break;
				
			}
		}
		
		int tamano = tarjetas.size();
		
		for(int i = 0; i < tamano; i++)
		{
			tarjetas.add(new Tarjeta(tarjetas.get(i).getTextura(), this));
		}
		
		Collections.shuffle(tarjetas);
		
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.drawImage(fondo, 0, 0, null);
		
		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString("INTENTOS: "+intentos, 20, 550);
		int x = 0;
		int y = 0;
		
		if(pareja.size() == 2)
		{
			
			t1 = pareja.get(0);
			t2 = pareja.get(1);
			
			if(!t1.getTextura().equals(t2.getTextura()))
				timer.start();
			
			intentos ++;
			pareja.clear();
		}
		
		
		for(int i = 0; i < tarjetas.size(); i++)
		{
			tarjetas.get(i).render(g, 100 + x*120, 50 + y*120);
			x++;
			if(x == 5)
			{
				x = 0;
				y++;
			}
		}
		
		// Comprobar respuesta
		
		for(int i = 0; i < tarjetas.size(); i++)
		{
			if(tarjetas.get(i).isBocaAbajo())
			{
				return;
			}
				
		}
		gameOver = true;
		
		if(gameOver)
		{
			for(int i = 0; i < tarjetas.size(); i++)
			{
				tarjetas.get(i).setBocaAbajo();
			}
			intentos = 0;
			JOptionPane.showMessageDialog(new JPanel(), "Congratulations", "Puzzle completed", JOptionPane.INFORMATION_MESSAGE);
			gameOver = false;
			MouseInfo.izq = false;
		}
	}
	
	public BufferedImage getBocaAbajo()
	{
		return bocaAbajo;
	}
	
	public Timer getTimer()
	{
		return timer;
	}

	public ArrayList<Tarjeta> getPareja() {
		return pareja;
	}
	
	
}
