import java.awt.Graphics;

public class Vector{
	private int x, y, radius, x2, y2, phase;
	
	public Vector(int x, int y){
		this.x = x;
		this.y = y;
		this.radius = -1;
		this.x2 = -1;
		this.y2 = -1;
		this.phase = 0;
	}
	
	public void paint(Graphics g){
		
	}
	
	public void setRadius(int radius){
		this.radius = radius;
	}
	
	public boolean hasRadius(){
		return radius > 0;
	}
	
	public int getRadius(){
		return radius;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX2(int x2){
		this.x2 = x2;
	}
	
	public void setY2(int y2){
		this.y2 = y2;
	}
	
	public void advance(){
		phase++;
	}
	
	public int phase(){
		return phase;
	}
	
	public Ball makeBall(){
		Ball b = new Ball(x, y, radius, (x2-x)/10, (y2-y)/10);
		return b;
	}
	
	public BlackHole makeBlackHole(){
		BlackHole b = new BlackHole(x, y, radius);
		return b;
	}
}
