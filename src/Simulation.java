import java.awt.Graphics;

import javax.swing.JOptionPane;

public class Simulation{
	public static final int ABSORB = 0, COLLIDE = 1, NONCOLLIDE = 2; //collision types
	public static final int BALL = 0, BLACK_HOLE = 1; //draw types
	private Frame frame;
	private EntityHandler ents;
	private final int DELAY = 1000/24; //24 fps
	private boolean play;
	private int collType, drawType;
	private boolean boundaries;
	private double gConstant;
	
	public Simulation(){
		Refs.setSimulation(this);
		frame = new Frame();
		ents = new EntityHandler();
		this.collType = ABSORB;
		this.drawType = BALL;
		this.boundaries = false;
		this.gConstant = .5;
	}
	
	public void run(){
		play = true;
		long timeMillisBefore, timeMillisAfter;
		while(true){
			timeMillisBefore = System.currentTimeMillis();
			this.updateSettings();
			this.update();
			Refs.canvas.repaint();
			timeMillisAfter = System.currentTimeMillis();
			try{ 
				Thread.sleep(Util.aboveZero(DELAY - (timeMillisAfter-timeMillisBefore)));
			}catch(Exception e){}
			while(!play){
				this.updateSettings();
				Refs.canvas.repaint();
				try{ Thread.sleep(DELAY*2); }catch(Exception e){}
			}
		}
	}
	
	public void updateSettings(){
		collType = frame.getSelectedCollisionType();
		drawType = frame.getSelectedDrawType();
		boundaries = frame.isBound();
		gConstant = frame.getGravitationConstant();
	}
	
	public void update(){
		ents.update();
	}
	
	public void paint(Graphics g){
		if(ents != null) ents.paint(g);
	}
	
	public EntityHandler getEntityHandler(){
		return ents;
	}
	
	public void setPlaying(boolean b){
		play = b;
	}
	
	public boolean isPlaying(){
		return play;
	}
	
	public int getCollisionType(){
		return collType;
	}
	
	public boolean isCollisionType(int type){
		return collType == type;
	}
	
	public int getDrawType(){
		return drawType;
	}
	
	public boolean isDrawType(int type){
		return drawType == type;
	}
	
	public boolean isBound(){
		return boundaries;
	}
	
	public double getGravitationConstant(){
		return gConstant;
	}
}