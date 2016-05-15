package geometry;

import utilities.Ray;
import utilities.Vector;

public abstract class Triangle implements GeometricObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4856555238544390534L;
	private Vector vertice1, vertice2, vertice3, edge1, edge2, normal;
	
	/**
	 * Constructs triangle.
	 * @param vertice1 First vertex.
	 * @param vertice2 Second vertex.
	 * @param vertice3 Third vertex.
	 * @param color
	 */
	public Triangle(Vector vertice1, Vector vertice2, Vector vertice3){
		this.vertice1 = vertice1;
		this.vertice2 = vertice2;
		this.vertice3 = vertice3;
		edge1 = this.vertice2.subtract(this.vertice1);
		edge2 = this.vertice3.subtract(this.vertice1);
		normal = edge1.crossProduct(edge2);
	}
	
	@Override
	public double hit(Ray ray) {
		if (Math.abs(normal.magnitude()) == 0){
			return -1;
		}
		
		Vector direction = ray.getDirection();
		Vector w0 = ray.getOrigin().subtract(vertice1);
		double a = -normal.dotProduct(w0);
		double b = normal.dotProduct(direction);
		
		if(Math.abs(b) <  0.00000001){
			if(a == 0){
				return 0;
			} else{
				return -1;
			}
		}
		double r = a/b;
		if(r < 0){
			return -1;
		}
		Vector intersection = ray.getOrigin().add(direction.scalarMultiply(r));
		double uu, uv, vv, wu, wv, d;
		Vector w;
		uu = edge1.dotProduct(edge1);
		uv = edge1.dotProduct(edge2);
		vv = edge2.dotProduct(edge2);
		w = intersection.subtract(vertice1);
		wu = w.dotProduct(edge1);
		wv = w.dotProduct(edge2);
		d = uv * uv - uu * vv;
		double s, t;
		s = (uv * wv - vv * wu) / d;
		if (s < 0.0 || s > 1.0){
			return -1;
		}
		t = (uv * wu - uu * wv) / d;	
		if (t < 0.0 || (s + t) > 1.0){
			return -1;
		}
		a = direction.dotProduct(normal);
		if(a == 0){
			return -1;
		} else {
			Vector origin = ray.getOrigin();
			b = normal.dotProduct(origin.subtract(vertice1));
			return -b/a;
		}
	}

	@Override
	public Vector normalAt(Vector point) {
		return normal;
	}

}
