package geometry;

import gradients.Gradient;
import utilities.Vector;

public class ConstantGradientRandomSphere extends ConstantGradientSphere {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4861333672599235524L;

	public ConstantGradientRandomSphere(Vector center, double radius, Gradient grad) {
		super(center, radius, grad);
	}
	
	@Override
	public Vector normalAt(Vector point) {
		return super.normalAt(point).add(new Vector(Math.random() * 1.21, Math.random() * 1.21, Math.random() * 1.21));
	}

}
