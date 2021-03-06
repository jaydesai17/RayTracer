package geometry;

import gradients.Gradient;
import utilities.Color;
import utilities.Vector;

public class ConstantGradientPlane extends Plane{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2736840389037213758L;
	private Gradient grad;
	
	public ConstantGradientPlane(Vector normal, double distance, Gradient grad) {
		super(normal, distance);
		this.grad = grad;
	}

	@Override
	public Color colorAt(Vector location) {
		return grad.colorAt(location);
	}

}
