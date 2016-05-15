package geometry;

import java.io.Serializable;

import utilities.Color;
import utilities.Vector;

public class ConstantColorSphere extends Sphere implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7910551627811008653L;
	private Color color;
	
	public ConstantColorSphere(Vector center, double radius, Color color) {
		super(center, radius);
		this.color = color;
	}

	@Override
	public final Color colorAt(Vector location) {
		return color;
	}
	
}
