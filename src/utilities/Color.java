package utilities;

import java.io.Serializable;

/**
 * Represents a color.
 * @author Jay Desai.
 *
 */
public class Color implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8727435193324470403L;
	private double r,g,b;
	private double special;
	
	/**
	 * Creates a black color with no special or gradient.
	 */
	public Color(){
		r = 0.0;
		g = 0.0;
		b = 0.0;
	}
	
	public Color(int colorValue, double special){
		int red = (colorValue & 0xFF000000) >> 24;
		int green = (colorValue & 0x00FF0000) >> 16;
		int blue = (colorValue & 0x0000FF00) >> 8;
		this.r = (double)red/256;
		this.g = (double)green/256;
		this.b = (double)blue/256;
		this.special = special;
	}
	
	/**
	 * Creates a color with no gradient.
	 * @param r Red factor. A double between 0 and 1.
	 * @param g Green factor. A double between 0 and 1.
	 * @param b Blue factor. A double between 0 and 1.
	 * @param special. Shininess factor. A double between 0 and 1.
	 */
	public Color(double r, double g, double b, double special){
		this.r = r;
		this.g = g;
		this.b = b;
		this.special = special;
	}
	
	/**
	 * Copy constructor.
	 * @param other Color to be copied.
	 */
	public Color(Color other){
		r = other.r;
		g = other.g;
		b = other.b;
		special = other.special;
	}
	
	public Color add(Color other){
		return new Color(r+other.r,g+other.g,b+other.b,special);
	}
	
	public Color multiply(Color other){
		return new Color(r*other.r,g*other.g,b*other.b,special);
	}
	
	public Color divide(int scalar){
		return new Color(r/scalar,g/scalar,b/scalar,special);
	}
	
	public double brightness(){
		return (r+b+g)/3;
	}
	
	public Color colorScalar(double scalar){
		return new Color(r*scalar,g*scalar,b*scalar, special);
	}
	
	public Color average(Color other){
		return new Color((r+other.r)/2,(g+other.g)/2,(b+other.b)/2,special);
	}
	
	public Color clip(){
		double alight = r + g + b;
		double excess = alight - 3;
		if (excess > 0){
			r += excess*r/alight;
			g += excess*g/alight;
			b += excess*b/alight;
		}
		if (r>1){
			r = 1;
		} 
		if (g>1){
			g = 1;
		}
		if (b>1){
			b = 1;
		}
		if (r<0){
			r = 0;
		} 
		if (g<0){
			g = 0;
		}
		if (b<0){
			b = 0;
		}
		
		return new Color(r,g,b,special);
	}
		
	public double getSpecial(){
		return special;
	}
	
	/**
	 * Returns int value of color. 
	 * @return Int value of color. Can be passed into javax.awt.Color or somthing to make a Java default color.
	 */
	public int toInt(){
		return (int)(r*255)<<16|(int)(g*255)<<8|(int)(b*255);
	}
	
	public static Color blue(double special){
		return new Color(0,0,.8,special);
	}
	
	public static Color white(double special){
		return new Color(.99,.99,.99,special);
	}
	
	public static Color black(double special){
		return new Color(0,0,0,special);
	}
	
}
