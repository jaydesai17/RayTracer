package geometry;

import java.io.Serializable;

import utilities.Color;
import utilities.Vector;

public class ConstantColorTriangle extends Triangle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2443128788182202083L;
	private Color color;
	
	public ConstantColorTriangle(Vector vertice1, Vector vertice2,
			Vector vertice3, Color color) {
		super(vertice1, vertice2, vertice3);
		this.color = color;
	}

	@Override
	public final Color colorAt(Vector location) {
		return color;
	}

}
