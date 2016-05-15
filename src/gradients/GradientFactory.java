package gradients;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import utilities.Color;
import utilities.Transformation;
import utilities.Vector;

public class GradientFactory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 567280646121742181L;
	private Color[][] colors;
	private Color[][] colors2;
	private Color color1;
	private Color color2;
	private String image1;
	private String image2;
	private double special1;
	private double special2;
	
	private GradientFactory(String image1, double special1, String image2, double special2, Color color1, Color color2){
		this.color1 = color1;
		this.color2 = color2;
		this.image1 = image1;
		this.image2 = image2;
		this.special1 = special1;
		this.special2 = special2;
	}
	
	public static GradientFactory createInstance(double special1, double special2, String image1, String image2) throws IOException{
		GradientFactory fact = new GradientFactory(image1, special1, image2, special2, null, null);
		fact.initColors();
		return fact;
	}
	
	public static GradientFactory createInstance(double special1, String image, Color color) throws IOException{
		GradientFactory fact = new GradientFactory(image, special1, null, 0, null, color);
		fact.initColors();
		return fact;
	}
		
	public static GradientFactory createInstance(Color color1, Color color2) throws IOException{
		GradientFactory fact = new GradientFactory(null, 0, null, 0, color1, color2);
		fact.initColors();
		return fact;
	}
	
	private void initColors() throws IOException{
		
		final BufferedImage bi;
		final BufferedImage bi2;
		
		if(image1 != null){
			File img = new File(image1);
			bi = ImageIO.read(img);
			colors = new Color[bi.getWidth()][bi.getHeight()];
			for(int i = 0; i<colors.length; i++){
				for(int j = 0; j<colors[i].length; j++){
					colors[i][j] = new Color(bi.getRGB(i, j), special1);
				}
			}
		} else if(color1 != null){
			colors = new Color[1][1];
			colors[0][0] = color1;
		} else{
			throw new IllegalArgumentException("image1 and color1 cannot both be null");
		}
		
		if(image2 != null){
			File img2 = new File(image2);
			bi2 = ImageIO.read(img2);
			colors2 = new Color[bi2.getWidth()][bi2.getHeight()];
			for(int i = 0; i<colors2.length; i++){
				for(int j = 0; j<colors2[i].length; j++){
					colors2[i][j] = new Color(bi2.getRGB(i, j), special2);
				}
			}
		} else if(color2 != null){
			colors2 = new Color[1][1];
			colors2[0][0] = color2;
		} else{
			throw new IllegalArgumentException("image2 and color2 cannot both be null");
		}
	}
	
	public Gradient makeXY(final double scaleX, final double scaleY){
		return new XYGradient(scaleY, scaleY);	
	}
	
	public Gradient makeXZ(final double scaleX, final double scaleY){
		return new XZGradient(scaleY, scaleY);		
	}
	
	public Gradient makeYZ(final double scaleX, final double scaleY){
		return new YZGradient(scaleX, scaleY);	
	}
	
	public Gradient makeTransformationGradient(final Transformation t) {
		return new TransformationGradient(t);
	}
	
	public Gradient blendGradients(Map<Gradient,Double> components){
		return new BlendedGradient(components);
	}
	
	public Gradient blendGradients(Gradient grad1, Gradient grad2, double component1, double component2){
		Map<Gradient,Double> components = new HashMap<Gradient,Double>();
		components.put(grad1, component1);
		components.put(grad2, component2);
		return new BlendedGradient(components);
	}
		
	private class XYGradient implements Gradient{
		
		private final double scaleX;
		private final double scaleY;
		
		public XYGradient(double scaleX, double scaleY){
			this.scaleX = scaleX;
			this.scaleY = scaleY;
		}
		
		@Override
		public Color colorAt(Vector point) {
			double x = point.getX();
			double y = point.getY();
			x *= scaleX;
			y *= scaleY;
			int square =(int) (Math.floor(x) + Math.floor(y));
			if(square % 2 == 0){
				x -= Math.floor(x);
				y -= Math.floor(y);
				int xInt = (int)(x*colors.length);
				int yInt = (int)(y*colors[0].length);
				if (xInt >= colors.length){
					xInt = colors.length - 1;
				} 
				if(yInt >= colors[xInt].length){
					yInt = colors[xInt].length - 1;
				}
				return colors[xInt][yInt];
			} else{
				x -= Math.floor(x);
				y -= Math.floor(y);
				int xInt = (int)(x*colors2.length);
				int yInt = (int)(y*colors2[0].length);
				if (xInt >= colors2.length){
					xInt = colors2.length - 1;
				} 
				if(yInt >= colors2[xInt].length){
					yInt = colors2[xInt].length - 1;
				}
				return colors2[xInt][yInt];
			}
		}
		
	}
	
	private class XZGradient implements Gradient{
		
		private final double scaleX;
		private final double scaleY;
		
		public XZGradient(double scaleX, double scaleY){
			this.scaleX = scaleX;
			this.scaleY = scaleY;
		}
		
		@Override
		public Color colorAt(Vector point) {
			double x = point.getX();
			double y = point.getZ();
			x *= scaleX;
			y *= scaleY;
			int square =(int) (Math.floor(x) + Math.floor(y));
			if(square % 2 == 0){
				x -= Math.floor(x);
				y -= Math.floor(y);
				int xInt = (int)(x*colors.length);
				int yInt = (int)(y*colors[0].length);
				if (xInt >= colors.length){
					xInt = colors.length - 1;
				} 
				if(yInt >= colors[xInt].length){
					yInt = colors[xInt].length - 1;
				}
				return colors[xInt][yInt];
			} else{
				x -= Math.floor(x);
				y -= Math.floor(y);
				int xInt = (int)(x*colors2.length);
				int yInt = (int)(y*colors2[0].length);
				if (xInt >= colors2.length){
					xInt = colors2.length - 1;
				} 
				if(yInt >= colors2[xInt].length){
					yInt = colors2[xInt].length - 1;
				}
				return colors2[xInt][yInt];
			}
		}
		
	}
	
	private class YZGradient implements Gradient{
		
		private final double scaleX;
		private final double scaleY;
		
		public YZGradient(double scaleX, double scaleY){
			this.scaleX = scaleX;
			this.scaleY = scaleY;
		}
		
		@Override
		public Color colorAt(Vector point) {
			double x = point.getY();
			double y = point.getZ();
			x *= scaleX;
			y *= scaleY;
			int square =(int) (Math.floor(x) + Math.floor(y));
			if(square % 2 == 0){
				x -= Math.floor(x);
				y -= Math.floor(y);
				int xInt = (int)(x*colors.length);
				int yInt = (int)(y*colors[0].length);
				if (xInt >= colors.length){
					xInt = colors.length - 1;
				} 
				if(yInt >= colors[xInt].length){
					yInt = colors[xInt].length - 1;
				}
				return colors[xInt][yInt];
			} else{
				x -= Math.floor(x);
				y -= Math.floor(y);
				int xInt = (int)(x*colors2.length);
				int yInt = (int)(y*colors2[0].length);
				if (xInt >= colors2.length){
					xInt = colors2.length - 1;
				} 
				if(yInt >= colors2[xInt].length){
					yInt = colors2[xInt].length - 1;
				}
				return colors2[xInt][yInt];
			}
		}
		
	}
	
	private class TransformationGradient implements Gradient {

		private final Transformation t;
		
		public TransformationGradient(Transformation t){
			this.t = t;
		}
		
		@Override
		public Color colorAt(Vector point) {
			
			point = t.multiply(point);
			double x = point.getX();
			double y = point.getY();

			int square =(int) (Math.floor(x) + Math.floor(y));
			if(square % 2 == 0){
				x -= Math.floor(x);
				y -= Math.floor(y);
				int xInt = (int)(x*colors.length);
				int yInt = (int)(y*colors[0].length);
				if (xInt >= colors.length){
					xInt = colors.length - 1;
				} 
				if(yInt >= colors[xInt].length){
					yInt = colors[xInt].length - 1;
				}
				return colors[xInt][yInt];
			} else{
				x -= Math.floor(x);
				y -= Math.floor(y);
				int xInt = (int)(x*colors2.length);
				int yInt = (int)(y*colors2[0].length);
				if (xInt >= colors2.length){
					xInt = colors2.length - 1;
				} 
				if(yInt >= colors2[xInt].length){
					yInt = colors2[xInt].length - 1;
				}
				return colors2[xInt][yInt];
			}
		}

	}
	
	private class BlendedGradient implements Gradient{
		
		private final Map<Gradient, Double> components;
		
		public BlendedGradient(Map<Gradient,Double> components){
			this.components = components;
		}
		
		@Override
		public Color colorAt(Vector point) {
			Color finalColor = new Color(0,0,0,0);
			for(Entry<Gradient,Double> entry: components.entrySet()){
				Gradient g = entry.getKey();
				double p = entry.getValue();
				Color c = g.colorAt(point).colorScalar(p);
				finalColor = finalColor.add(c);
			}
			return finalColor.clip();
		}
	}
	
}
