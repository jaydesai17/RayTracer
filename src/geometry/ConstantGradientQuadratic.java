package geometry;

import gradients.Gradient;
import utilities.Color;
import utilities.Vector;

public class ConstantGradientQuadratic extends Quadratic{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6098859491793075714L;
	private Gradient grad;
	
	public ConstantGradientQuadratic(double a, double b, double c, double d, double e, double f, double g, double h,
			double i, double j, Gradient grad) {
		super(a, b, c, d, e, f, g, h, i, j);
		this.grad = grad;
	}

	@Override
	public Color colorAt(Vector location) {
		return grad.colorAt(location);
	}

}
