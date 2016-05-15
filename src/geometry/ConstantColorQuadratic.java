package geometry;

import java.io.Serializable;

import utilities.Color;
import utilities.Vector;

public class ConstantColorQuadratic extends Quadratic implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6573866507708807128L;
	private Color color;
	
	public ConstantColorQuadratic(double a, double b, double c, double d,
			double e, double f, double g, double h, double i, double j, Color color) {
		super(a, b, c, d, e, f, g, h, i, j);
		this.color = color;
	}

	@Override
	public final Color colorAt(Vector location) {
		return color;
	}

}
