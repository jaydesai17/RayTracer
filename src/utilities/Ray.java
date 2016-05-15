package utilities;

import java.io.Serializable;

public class Ray implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9210177806651889082L;
	private Vector origin, direction;
	
	public Ray(){
		origin = new Vector(0.0,0.0,0.0);
		direction = new Vector(1,0,0);
	}
	
	public Ray(Vector origin, Vector direction){
		this.origin = new Vector(origin);
		this.direction = new Vector(direction);
	}
	
	public Vector getOrigin(){
		return origin;
	}
	
	public Vector getDirection(){
		return direction;
	}
}
