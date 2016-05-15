package utilities;

import java.io.Serializable;

/**
 * Class representing a light.
 * @author Jay Desai.
 *
 */
public class Light implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector position;
	private Color color;
	
	/**
	 * Creates a light.
	 * @param position Position of light.
	 * @param color Color of light. Doesn't need gradients or shit.
	 */
	public Light(Vector position, Color color){
		this.position = new Vector(position);
		this.color = new Color(color);
	}
	
	public Light(){
		position = new Vector(0,0,0);
		color = new Color(1,1,1,0);
	}
	
	public Vector getPosition(){
		return position;
	}
	
	public Color getColor(){
		return color;
	}
}