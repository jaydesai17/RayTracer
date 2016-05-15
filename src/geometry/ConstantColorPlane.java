package geometry;

import java.io.Serializable;

import utilities.Color;
import utilities.Vector;

public class ConstantColorPlane extends Plane implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -661105669082398644L;
	private Color color;
	
	public ConstantColorPlane(Vector normal, double distance, Color color) {
		super(normal, distance);
		this.color = color;
	}

	@Override
	public final Color colorAt(Vector location) {
		return color;
	}

}
