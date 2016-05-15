package geometry;

import utilities.Ray;
import utilities.Vector;

public abstract class Quadratic implements GeometricObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8495249200508859930L;
	private double a,b,c,d,e,f,g,h,i,j;
		
	/**
	 * Constructs a quadratic surface in form ax^2 + by^2 + cz^2 + dxy + eyz + fxz + gx + hy + iz + j = 0.
	 * @param a Coefficient of x^2 term. 
	 * @param b Coefficient of y^2 term.
	 * @param c Coefficient of z^2 term.
	 * @param d Coefficient of xy term.
	 * @param e Coefficient of yz term.
	 * @param f Coefficient of xz term.
	 * @param g Coefficient of x term.
	 * @param h Coefficient of y term.
	 * @param i Coefficient of z term.
	 * @param j Constant j.
	 * @param color Color of surface.
	 */
	public Quadratic(double a, double b,
			double c, double d,
			double e, double f,
			double g, double h,
			double i, double j){	
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
		this.g = g;
		this.h = h;
		this.i = i;
		this.j = j;
	}
	
	@Override
	public double hit(Ray ray) {
		Vector origin = ray.getOrigin();
		Vector direction = ray.getDirection();
		double px = origin.getX();
		double py = origin.getY();
		double pz = origin.getZ();
		double dx = direction.getX();
		double dy = direction.getY();
		double dz = direction.getZ();
		
		double A = a*dx*dx + b*dy*dy + c*dz*dz + d*dx*dy + e*dy*dz + f*dx*dz;
		double B = 2*a*px*dx + 2*b*py*dy + 2*c*pz*dz + d*(py*dx + px*dy) + e*(py*dz+pz*dy) + f*(px*dz+pz*dx) + g*dx + h*dy + i*dz;
		double C = a*px*px + b*py*py + c*pz*pz + d*px*py + e*py*pz + f*px*pz + g*px + h*py + i*pz + j;
		
		double discriminant = B*B - 4*A*C;
		if(discriminant < 0){
			return -1;
		} 
		
		double r = (-B - Math.sqrt(discriminant))/(2*A);
		return r*direction.magnitude(); 
		
	}

	@Override
	public Vector normalAt(Vector point) {
		
		double x = point.getX();
		double y = point.getY();
		double z = point.getZ();
		
		double nx = 2*a*x + d*y + f*z + e;
		double ny = 2*b*y + d*x + e*z + h;
		double nz = 2*c*z + e*y + f*x + i;

		return new Vector(nx,ny,nz).normalize().negative();

	}

	
}
