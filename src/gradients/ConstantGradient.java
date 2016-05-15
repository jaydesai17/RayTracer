package gradients;

import utilities.Color;
import utilities.Vector;

public class ConstantGradient implements Gradient {

	private Color color;
	
	public ConstantGradient(Color color){
		this.color = color;
	}
	
	@Override
	public Color colorAt(Vector point) {
		return color;
	}

}
