package gradients;

import java.util.function.Function;

import utilities.Color;
import utilities.Vector;

public class SimpleGradient implements Gradient{

	private Function<Vector,Color> function;

	public SimpleGradient(Function<Vector,Color> function){
		this.function = function;
	}
	
	@Override
	public Color colorAt(Vector point) {
		return function.apply(point);
	}
	
}
