package utilities;

import java.io.Serializable;

/**
 * Class representing a vector.
 * @author Jay Desai.
 *
 */
public class Vector implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4519868366604178334L;
	private double x,y,z;
	
	/**
	 * Creates a vector at the origin.
	 */
	public Vector(){
		x = 0;
		y = 0;
		z = 0;
	}
	
	/**
	 * Creates a vector.
	 * @param x x component.
	 * @param y y component.
	 * @param z z component.
	 */
	public Vector(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Copy constructor.
	 * @param other To be copied.
	 */
	public Vector(Vector other){
		x = other.x;
		y = other.y;
		z = other.z;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getZ(){
		return z;
	}
	
	public Vector add(Vector other){
		return new Vector(x+other.x,y+other.y,z+other.z);
	}

	public Vector subtract(Vector other){
		return new Vector(x-other.x,y-other.y,z-other.z);
	}
	
	public double dotProduct(Vector other){
		return x*other.x + y*other.y + z*other.z;
	}
	
	public double magnitude(){
		return Math.sqrt(dotProduct(this));
	}
	
	public Vector normalize(){
		double mag = magnitude();
		return new Vector(x/mag,y/mag,z/mag);
	}
	
	public Vector negative(){
		return new Vector(-x,-y,-z);
	}
	
	public Vector crossProduct(Vector v){
		return new Vector(y*v.z-z*v.y, z*v.x-x*v.z, x*v.y-y*v.x);
	}
	
	public Vector scalarMultiply(double s){
		return new Vector(x*s,y*s,z*s);
	}
	
}
