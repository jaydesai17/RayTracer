package geometry;

import gradients.Gradient;
import utilities.Color;
import utilities.Vector;

public class ConstantGradientTriangle extends Triangle {

	private Gradient grad;
	
	public ConstantGradientTriangle(Vector vertice1, Vector vertice2, Vector vertice3, Gradient grad) {
		super(vertice1, vertice2, vertice3);
		this.grad = grad;
	}

	@Override
	public Color colorAt(Vector location) {
		return grad.colorAt(location);
	}
	
}
