package utilities;

import java.io.Serializable;

public class Transformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8182324043457817637L;
	private Vector col1;
	private Vector col2;
	private Vector col3;
	
	public static final Transformation IDENTITY = new Transformation(new Vector(1,0,0), new Vector(0,1,0), new Vector(0, 0, 0));
	
	public Transformation(Vector col1, Vector col2, Vector col3) {
		this.col1 = col1; this.col2 = col2; this.col3 = col3;
	}
	
	public Vector multiply(Vector v) {
		Vector s1 = col1.scalarMultiply(v.getX());
		Vector s2 = col2.scalarMultiply(v.getY());
		Vector s3 = col3.scalarMultiply(v.getZ());
		return s1.add(s2).add(s3);
	}
	
	public Transformation add(Transformation t) {
		return new Transformation(col1.add(t.col1), col2.add(t.col2), col3.add(t.col3));
	}
	
	public Transformation scalarMultiply(double s) {
		return new Transformation(col1.scalarMultiply(s), col2.scalarMultiply(s), col3.scalarMultiply(s));
	}
	
	public Transformation leftMultiply(Transformation t) {
		return new Transformation(t.multiply(col1), t.multiply(col2), t.multiply(col3));
	}
	
}
