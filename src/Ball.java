import java.awt.Color;
import java.awt.Graphics;

public class Ball extends Entity{
	private double x, y;
	private int radius;
	private Color color;
	private double vx, vy, mass;
	private boolean hasCheckedCollisions, killMe;
	private int[][] path;
	private final byte PATH_LEN = 75;
	
	public Ball(){
		x = Refs.canvas.getWidth()/2; //halfway horizontally
		y = Refs.canvas.getHeight()/2; //halfway vertically
		radius = (int)(Math.random()*31)+10; //random radius 10-40
		color = new Color((int)(Math.random()*256), //random color
				(int)(Math.random()*256),
				(int)(Math.random()*256));
		vx = (Math.random()*5)-2; //random initial x velocity
		vy = (Math.random()*5)-2; //random initial y velocity
		mass = 1*Math.pow(radius, 3); //volume of coefficient*r^3, uniform mass
		hasCheckedCollisions = false;
		killMe = false;
		path = new int[2][PATH_LEN];
	}
	
	public Ball(int x, int y){
		this.x = x;
		this.y = y;
		radius = (int)(Math.random()*31)+10; //random radius 10-40
		color = new Color((int)(Math.random()*256), //random color
				(int)(Math.random()*256),
				(int)(Math.random()*256));
		vx = vy = 0; //no initial velocity
		mass = 1*Math.pow(radius, 3); //volume of coefficient*r^3, uniform mass
		hasCheckedCollisions = false;
		killMe = false;
		path = new int[2][PATH_LEN];
	}
	
	public Ball(int x, int y, int radius, int vx, int vy){
		this.x = x;
		this.y = y;
		this.radius = radius;
		color = new Color((int)(Math.random()*256), //random color
				(int)(Math.random()*256),
				(int)(Math.random()*256));
		this.vx = vx;
		this.vy = vy;
		this.mass = 1*Math.pow(radius, 3);
		this.hasCheckedCollisions = false;
		this.killMe = false;
		path = new int[2][PATH_LEN];
	}
	
	public Ball(int x, int y, int radius, int vx, int vy, Color color){
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = color;
		this.vx = vx;
		this.vy = vy;
		this.mass = 1*Math.pow(radius, 3);
		this.hasCheckedCollisions = false;
		this.killMe = false;
		path = new int[2][PATH_LEN];
	}
	
	public void update(){
		double ax = Refs.sim.getEntityHandler().getNetGravAccel(x, y, 'x', this);
		double ay = Refs.sim.getEntityHandler().getNetGravAccel(x, y, 'y', this);
		vx += ax;
		vy += ay;
		correctVelocity();
		if(Refs.sim.isCollisionType(Simulation.COLLIDE) || //if current collision type is collide
			Refs.sim.isCollisionType(Simulation.ABSORB)) //if current collision type is absorb
				Refs.sim.getEntityHandler().checkCollisions(this);
		x += vx;
		y += vy;
		if(Refs.sim.isBound()) //if bound, then check for screen boundaries
			checkBoundaryCollisions();
		if(Refs.frame.showPaths())
			appendPath();
	}
	
	public void subliminalUpdate(){
		correctVelocity();
		x += vx/100.;
		y += vy/100.;
		checkBoundaryCollisions();
	}
	
	public void correctVelocity(){
		if(vx > 30)
			vx = 30;
		if(vx < -30)
			vx = -30;
		if(vy > 30)
			vy = 30;
		if(vy < -30)
			vy = -30;
	}
	
	public void checkBoundaryCollisions(){
		if(x-radius < 0){
			vx *= -.8;
			x = radius;
		}
		if(x+radius > Refs.canvas.getWidth()){
			vx *= -.8;
			x = Refs.canvas.getWidth()-radius;
		}
		if(y-radius < 0){
			vy *= -.8;
			y = radius;
		}
		if(y+radius > Refs.canvas.getHeight()){
			vy *= -.8;
			y = Refs.canvas.getHeight()-radius;
		}
	}

	public void paint(Graphics g){
		g.setColor(color);
		g.fillOval(getXOnScreen(), getYOnScreen(), radius*2, radius*2);
		if(Refs.frame.showVelocity())
			g.fillOval(getX()-2+((int)(vx*radius)), getY()-2+((int)(vy*radius)), 4, 4);
		if(Refs.frame.showPaths())
			for(int i = 0; i < PATH_LEN; i++)
				g.fillOval(path[0][i]-1, path[1][i]-1, 2, 2);
	}
	
	public int getX(){
		return (int)x;
	}

	public int getXOnScreen(){
		return (int)x-radius;
	}
	
	public double getVX(){
		return vx;
	}
	
	public void setVX(double vx){
		this.vx = vx;
	}
	
	public int getY(){
		return (int)y;
	}

	public int getYOnScreen(){
		return (int)y-radius;
	}
	
	public double getVY(){
		return vy;
	}
	
	public void setVY(double vy){
		this.vy = vy;
	}
	
	public int getRadius(){
		return radius;
	}
	
	public double getMass(){
		return mass;
	}
	
	public boolean hasCheckedCollisions(){
		return hasCheckedCollisions;
	}
	
	public void setCheckedCollisions(boolean b){
		hasCheckedCollisions = b;
	}
	
	public void collide(Entity other, int coeff){
//		vx *= -1;
//		vy *= -1;
		double dx =  (double)coeff * (other.getX() - this.getX());
		double dy =  (double)coeff * (other.getY() - this.getY());
		double r = Util.distance(this, other);
		double m1 = this.getMass();
		double m2 = other.getMass();
		double vxi = this.vx;
		double vyi = this.vy;
		this.vx = vxi*((-1.)*dy/r) +
				  vxi*((m1-m2)/(m1+m2))*(dx/r) + 
				  other.getVX()*(2.*m2/(m1+m2))*(dx/r);
		this.vy = vyi*(dx/r) +
		  		  vyi*((m1-m2)/(m1+m2))*(dy/r) + 
		  		  other.getVY()*(2.*m2/(m1+m2))*(dy/r);
		other.setVX(other.getVX()*((-1.)*dy/r) +
				 	vxi*(2.*m1/(m1+m2))*(dx/r) + 
				 	other.getVX()*((m2-m1)/(m1+m2))*(dx/r));
		other.setVY(other.getVY()*(dx/r) +
			 		vyi*(2.*m1/(m1+m2))*(dy/r) + 
			 		other.getVY()*((m2-m1)/(m1+m2))*(dy/r));
		
	}
	
	public void appendPath(){
		for(int i = PATH_LEN-1; i > 0; i--){
			path[0][i] = path[0][i-1];
			path[1][i] = path[1][i-1];
		}
		path[0][0] = getX();
		path[1][0] = getY();
	}
	
	public void setKillMe(boolean b){
		killMe = b;
	}
	
	public boolean shouldKill(){
		return killMe;
	}
	
	public Color getColor(){
		return color;
	}
}