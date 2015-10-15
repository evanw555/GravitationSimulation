import java.awt.Graphics;
import java.util.ArrayList;

public class EntityHandler{
	ArrayList<Entity> ents, purgatory;
	private boolean createDust, clear;
	
	public EntityHandler(){
		ents = new ArrayList<Entity>();
		purgatory = new ArrayList<Entity>();
		createDust = false;
		clear = false;
	}
	
	public void update(){
		transferPurgatory();
		for(Entity e : ents)
			e.setCheckedCollisions(false);
		for(Entity e : ents)
			e.update();
		//check for alterations
		if(clear){
			ents.clear();
			purgatory.clear();
			clear = false;
		}
		if(createDust){
			createDust(25);
			createDust = false;
		}
		cleanUp();
	}
	
	public void transferPurgatory(){
		for(Entity e : purgatory)
			ents.add(e);
		purgatory.clear();
	}
	
	public void cleanUp(){
		for(int i = 0; i < ents.size(); i++)
			if(ents.get(i).shouldKill()){
				ents.remove(i);
				i--;
			}
	}
	
	public void paint(Graphics g){
		for(Entity e : ents)
			try{ e.paint(g); }catch(Exception ex){}
		try{ //in case of concurrent modification (user may add while list is being traversed)
			for(Entity e : purgatory)
				e.paint(g);
		}catch(Exception e){}
	}
	
	public void clear(){
		if(Refs.sim.isPlaying())
			clear = true;
		else{
			ents.clear();
			purgatory.clear();
		}
	}
	
	public void addBall(int x, int y){
		purgatory.add(new Ball(x, y));
	}
	
	public void addBall(Ball b){
		purgatory.add(b);
	}
	
	public void addBlackHole(BlackHole b){
		purgatory.add(b);
	}
	
	public void setCreateDust(){
		createDust = true;
	}
	
	public void createDust(int quantity){
		for(int i = 0; i < quantity; i++)
			purgatory.add(new Ball(Util.randomIntInclusive(0, Refs.canvas.getWidth()-1),
								   Util.randomIntInclusive(0, Refs.canvas.getHeight()-1),
								   Util.randomIntInclusive(3, 5),
								   Util.randomIntInclusive(-2, 2),
								   Util.randomIntInclusive(-2, 2)));
	}
	
	public double getNetGravAccel(double x1, double y1, char component, Entity e){
		double net = 0;
		
		if(ents.size() <= 1)
			return 0;
		
		try{
			for(Entity other : ents){
				double x2 = other.getX();
				double y2 = other.getY();
				if(x1 == x2 && y1 == y2) //if distance is 0 (testing itself) then it will divide by 0
					continue;
				double distance = Util.distance(x1, y1, x2, y2);
				//if(Refs.sim.isCollisionType(Simulation.NONCOLLIDE)) //if current collision type is noncollide
					if(Util.areColliding(e, other)) //if colliding, no gravitational effect
						continue;
				double accel = 0;
				accel = other.getMass()/Math.pow(distance, 2);
				double proportion = 1;
				if(component == 'x')
					proportion = ((x2-x1))/distance;
				if(component == 'y')
					proportion = ((y2-y1))/distance;
				net += accel*proportion;
			}
		}catch(Exception ex){}
		
		return Refs.sim.getGravitationConstant()*net;
	}
	
	public void checkCollisions(Entity e){
		try{
			for(Entity other : ents)
				if(e != other){
					if(Refs.sim.isCollisionType(Simulation.COLLIDE)){ //collision type collide
						try{
							//e.subliminalUpdate();
							if(!other.hasCheckedCollisions() && !other.shouldKill())
								if(Util.areColliding(e, other)){
									//e.subliminalUpdate();
									e.collide(other, 1);
//									other.collide(e, 1);
									while(Util.areColliding(e, other)){
										e.subliminalUpdate();
										other.subliminalUpdate();
									}
//									e.subliminalUpdate();
//									other.subliminalUpdate();
									continue;
								}
							throw new Exception();
						}catch(Exception ex){}
					}else if(Refs.sim.isCollisionType(Simulation.ABSORB)){ //collision type absorb
						if(!other.hasCheckedCollisions() && !other.shouldKill())
							if(Util.areColliding(e, other)){
								Collision coll = Util.getCollisionType(e, other);
								//if collision is between 2 balls
								if(coll == Collision.BALL_X_BALL){
									e.setKillMe(true);
									other.setKillMe(true);
									Ball product = new Ball(
											(int)Util.average(e.getX(), other.getX(), e.getMass(), other.getMass()),
											(int)Util.average(e.getY(), other.getY(), e.getMass(), other.getMass()),
											(int)Math.sqrt(Math.pow(e.getRadius(), 2) + Math.pow(other.getRadius(), 2)),
											(int)Util.average(e.getVX(), other.getVX(), e.getMass(), other.getMass()),
											(int)Util.average(e.getVY(), other.getVY(), e.getMass(), other.getMass()),
											Util.average(e.getColor(), other.getColor(), e.getMass(), other.getMass()));
									purgatory.add(product);
								//if collision is between a ball and a black hole
								}else if(coll == Collision.BALL_X_BH){
									if(e instanceof Ball)
										e.setKillMe(true);
									else
										other.setKillMe(true);
								//if collision is between a black hole and a black hole
								}else if(coll == Collision.BH_X_BH){
									e.setKillMe(true);
									other.setKillMe(true);
									BlackHole product = new BlackHole(
											(int)Util.average(e.getX(), other.getX(), e.getMass(), other.getMass()),
											(int)Util.average(e.getY(), other.getY(), e.getMass(), other.getMass()),
											(int)Math.sqrt(Math.pow(e.getRadius(), 2) + Math.pow(other.getRadius(), 2)));
									purgatory.add(product);
								}
							}
					}
				}
		}catch(Exception ex){}
		e.setCheckedCollisions(true);
	}
}