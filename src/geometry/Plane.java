package geometry;

import utilities.Ray;
import utilities.Vector;

public abstract class Plane implements GeometricObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5748756262982643818L;
	private Vector normal;
	private double distance;
	
	/**
	 * Constructs a plane.
	 * @param normal Vector normal to the plane.
	 * @param distance Distance from plane to origin.
	 */
	public Plane(Vector normal, double distance){
		this.distance = distance;
		this.normal = new Vector(normal).normalize();
	}
	
	public double hit(Ray ray) {
		Vector direction = ray.getDirection();
		Vector origin = ray.getOrigin();
		double a = direction.dotProduct(normal);
		if(a == 0){
			return -1;
		} else {
			double b = normal.dotProduct(origin.add(normal.scalarMultiply(distance).negative()));
			return -b/a;
		}
	}

	public Vector normalAt(Vector point) {
		return normal;
	}
	
}
