import java.awt.Color;
import java.awt.Graphics;


public class BlackHole extends Entity{
	private double x, y, mass;
	private int radius;
	private boolean hasCheckedCollisions, killMe;
	
	public BlackHole(){
		x = Refs.canvas.getWidth()/2; //halfway horizontally
		y = Refs.canvas.getHeight()/2; //halfway vertically
		radius = (int)(Math.random()*31)+10; //random radius 10-40
		mass = 1*Math.pow(radius, 3); //volume of coefficient*r^3, uniform mass
		hasCheckedCollisions = false;
		killMe = false;
	}
	
	public BlackHole(int x, int y, int radius){
		this.x = x;
		this.y = y;
		this.radius = radius;
		mass = 1*Math.pow(radius, 3); //volume of coefficient*r^3, uniform mass
		hasCheckedCollisions = false;
		killMe = false;
	}
	
	public void update(){
		if(Refs.sim.isCollisionType(Simulation.COLLIDE) || //if current collision type is collide
				Refs.sim.isCollisionType(Simulation.ABSORB)) //if current collision type is absorb
					Refs.sim.getEntityHandler().checkCollisions(this);
		if(Refs.sim.isBound()) //if bound, then check for screen boundaries
			checkBoundaryCollisions();
	}

	public void subliminalUpdate(){}
	
	public void checkBoundaryCollisions(){
		if(x-radius < 0)
			x = radius;
		if(x+radius > Refs.canvas.getWidth())
			x = Refs.canvas.getWidth()-radius;
		if(y-radius < 0)
			y = radius;
		if(y+radius > Refs.canvas.getHeight())
			y = Refs.canvas.getHeight()-radius;
	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawOval(getXOnScreen(), getYOnScreen(), radius*2, radius*2);
	}

	public int getX() {
		return (int)x;
	}

	public int getXOnScreen() {
		return (int)x-radius;
	}

	public double getVX() {
		return 0;
	}
	
	public void setVX(double vx){}

	public int getY() {
		return (int)y;
	}

	public int getYOnScreen() {
		return (int)y-radius;
	}

	public double getVY() {
		return 0;
	}
	
	public void setVY(double vy){}

	public int getRadius() {
		return radius;
	}

	public double getMass() {
		return mass;
	}

	public boolean hasCheckedCollisions() {
		return hasCheckedCollisions;
	}

	public void setCheckedCollisions(boolean b) {
		hasCheckedCollisions = b;
	}

	public void collide(Entity other, int coeff){}

	public void setKillMe(boolean b) {
		killMe = b;
	}

	public boolean shouldKill() {
		return killMe;
	}

	public Color getColor() {
		return Color.BLACK;
	}

}