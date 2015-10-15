import java.awt.Color;
import java.awt.Graphics;

public abstract class Entity {

	public abstract void update();
	
	public abstract void subliminalUpdate();
	
	public abstract void paint(Graphics g);
	
	public abstract int getX();
	
	public abstract int getXOnScreen();
	
	public abstract double getVX();
	
	public abstract void setVX(double vx);
	
	public abstract int getY();
	
	public abstract int getYOnScreen();
	
	public abstract double getVY();
	
	public abstract void setVY(double vy);
	
	public abstract int getRadius();
	
	public abstract double getMass();
	
	public abstract boolean hasCheckedCollisions();
	
	public abstract void setCheckedCollisions(boolean b);
	
	public abstract void collide(Entity other, int coeff);
	
	public abstract void setKillMe(boolean b);
	
	public abstract boolean shouldKill();
	
	public abstract Color getColor();
}