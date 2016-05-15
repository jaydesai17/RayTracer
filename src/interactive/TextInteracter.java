package interactive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import geometry.GeometricObject;
import gradients.Gradient;
import utilities.Camera;
import utilities.Color;
import utilities.Light;
import utilities.Vector;

public class TextInteracter {

	private Map<String, Gradient> gradients;
	private Map<String, Color> colors;
	private Map<String, GeometricObject> objects;
	private Map<String, Light> lights;
	private Camera camera;
	private int height;
	private int width;
	private Scanner scanner;
	
	public TextInteracter() {
		gradients = new HashMap<String, Gradient>();
		colors = new HashMap<String, Color>();
		scanner = new Scanner(System.in);
	}
	
	private void setDimensions() {
		System.out.println("Enter width, height");
		width = scanner.nextInt();
		height = scanner.nextInt();
	}
	
	private void setCamera() {
		System.out.println("x, y, z?");
		int x = scanner.nextInt(); int y = scanner.nextInt(); int z = scanner.nextInt();
		Vector loc = new Vector(x, y, z);
		System.out.println("Angle x, y, z?");
		x = scanner.nextInt(); y = scanner.nextInt(); z = scanner.nextInt();
		Vector ang = new Vector(x,y,z);
		camera = new Camera(loc, ang);

	}
	
	private void makeColor() {
		System.out.println("Enter color name.");
		String name = scanner.next();
		System.out.println("Enter r, g, b, special values.");
		double r = scanner.nextDouble(); double g = scanner.nextDouble(); double b = scanner.nextDouble(); 
		double special = scanner.nextDouble();
		Color color = new Color(r,g,b,special);
		colors.put(name, color);
	}
	
	private void makeSphere() {
		System.out.println("Enter sphere name.");
		String name = scanner.next();
		System.out.println("Enter x, y, z, rad");
	}
	
}
