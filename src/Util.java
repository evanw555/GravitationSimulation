import java.awt.Color;


public class Util{

	public static double distance(int x1, int y1, int x2, int y2){
		return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
	}
	
	public static double distance(double x1, double y1, double x2, double y2){
		return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
	}
	
	public static double distance(Entity e1, Entity e2){
		return Math.sqrt(Math.pow(e2.getX()-e1.getX(), 2) +
						 Math.pow(e2.getY()-e1.getY(), 2));
	}
	
	public static boolean areColliding(Entity e1, Entity e2){
		return Util.distance(e1, e2) < e1.getRadius()+e2.getRadius();
	}
	
	public static double average(double a, double b){
		return (a + b)/2.;
	}
	
	public static double average(double a, double b, double ma, double mb){
		return (a*ma + b*mb)/(ma+mb);
	}
	
	public static Color average(Color a, Color b, double ma, double mb){
		int red = (int)((a.getRed()*ma + b.getRed()*mb)/(ma+mb));
		int green = (int)((a.getGreen()*ma + b.getGreen()*mb)/(ma+mb));
		int blue = (int)((a.getBlue()*ma + b.getBlue()*mb)/(ma+mb));
		return new Color(red, green, blue);
	}
	
	/**
	 * Returns a random integer between the two numbers inclusively.
	 * @param lower minimum value
	 * @param upper maximum value
	 * @return random value between lower and upper, inclusive
	 */
	public static int randomIntInclusive(int lower, int upper){
		assert upper >= lower;
		return (int)(Math.random()*(1+upper-lower))+lower;
	}
	
	public static Collision getCollisionType(Entity e1, Entity e2){
		if(e1 instanceof Ball && e2 instanceof Ball)
			return Collision.BALL_X_BALL;
		if(e1 instanceof Ball && e2 instanceof BlackHole ||
		   e1 instanceof BlackHole && e2 instanceof Ball)
			return Collision.BALL_X_BH;
		if(e1 instanceof BlackHole && e2 instanceof BlackHole)
			return Collision.BH_X_BH;
		return null;
	}
	
	public static long aboveZero(long x){
		if(x > 0)
			return x;
		else
			return 0;
	}
}