package geometry;

import utilities.Ray;
import utilities.Vector;

public abstract class Sphere implements GeometricObject{

	private Vector center;
	private double radius;
	
	/**
	 * 
	 * @param center Vector representing center of the sphere.
	 * @param radius Radius of the sphere.
	 */
	public Sphere(Vector center, double radius){
		this.center = new Vector(center);
		this.radius = radius;
	}
	
	public double hit(Ray ray) {
		
		Vector direction = ray.getDirection();
		Vector origin = ray.getOrigin();
		
		double a = direction.dotProduct(direction);
		double b = 2*origin.subtract(center).dotProduct(direction);
		double c = origin.subtract(center).dotProduct(origin.subtract(center))-radius*radius;
		double discriminant = b*b-4*a*c;
		if (discriminant < 0.0){
			return -1;
		} else {
			return (-b-Math.sqrt(discriminant))/(2*a);
		}

	}

	public Vector normalAt(Vector point) {
		return point.subtract(center).normalize();
	}

}
