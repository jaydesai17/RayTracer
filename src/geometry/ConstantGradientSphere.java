package geometry;

import gradients.Gradient;
import utilities.Color;
import utilities.Vector;

public class ConstantGradientSphere extends Sphere {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5527867507415833132L;
	private Gradient grad;
	
	public ConstantGradientSphere(Vector center, double radius, Gradient grad) {
		super(center, radius);
		this.grad = grad;
	}

	@Override
	public Color colorAt(Vector location) {
		return grad.colorAt(location);
	}

}
