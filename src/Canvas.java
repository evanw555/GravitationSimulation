import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseListener, KeyListener{
	private int pWidth, pHeight;
	private Vector vec;
	
	public Canvas (int pWidth, int pHeight){
		Refs.setCanvas(this);
		this.pWidth = pWidth;
		this.pHeight = pHeight;
		this.vec = null;
		addMouseListener(this);
		addKeyListener(this);
		this.setBackground(Color.BLACK);
	}
	
	public boolean isFocusable(){
		return true;
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(pWidth, pHeight);
	}
	
	public Dimension getMinimumSize(){
		return new Dimension(pWidth, pHeight);
	}
	
	public Dimension getMaximumSize(){
		return new Dimension(pWidth, pHeight);
	}
	
	public int getWidth(){
		return pWidth;
	}
	
	public int getHeight(){
		return pHeight;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Refs.sim.paint(g);
		if(vec != null){ //painting vector (proto-ball)
			if(vec.hasRadius()){ //radius has been set
				g.setColor(Color.WHITE);
				g.fillOval(vec.getX()-vec.getRadius(), vec.getY()-vec.getRadius(),
						vec.getRadius()*2, vec.getRadius()*2);
				g.setColor(Color.BLACK);
				g.drawLine(vec.getX()-vec.getRadius()/2, vec.getY()-vec.getRadius()/2,
						vec.getX()+vec.getRadius()/2, vec.getY()+vec.getRadius()/2);
				g.drawLine(vec.getX()-vec.getRadius()/2, vec.getY()+vec.getRadius()/2,
						vec.getX()+vec.getRadius()/2, vec.getY()-vec.getRadius()/2);
			}else{ //radius hasn't been set
				g.setColor(Color.WHITE);
				g.drawLine(vec.getX()-5, vec.getY()-5,
						vec.getX()+5, vec.getY()+5);
				g.drawLine(vec.getX()-5, vec.getY()+5,
						vec.getX()+5, vec.getY()-5);
			}
		}
		if(!Refs.sim.isPlaying()){
			g.setColor(Color.WHITE);
			g.setFont(new Font("Impact", Font.BOLD, 50));
			g.drawString("PAUSED", 64, 64);
		}
	}

	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){
		//Refs.sim.getEntityHandler().addBall(e.getX(), e.getY());
		if(vec == null){
			vec = new Vector(e.getX(), e.getY());
		}else if(vec.phase() == 0){
			vec.setRadius((int)Util.distance(vec.getX(), vec.getY(), e.getX(), e.getY()));
			vec.advance();
		}else if(vec.phase() == 1){
			vec.setX2(e.getX());
			vec.setY2(e.getY());
			if(Refs.sim.isDrawType(Simulation.BALL))
				Refs.sim.getEntityHandler().addBall(vec.makeBall());
			if(Refs.sim.isDrawType(Simulation.BLACK_HOLE))
				Refs.sim.getEntityHandler().addBlackHole(vec.makeBlackHole());
			vec = null;
		}
	}
	public void mouseReleased(MouseEvent e){}

	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_P) //p pauses/unpauses
			Refs.sim.setPlaying(!Refs.sim.isPlaying());
	}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
}
