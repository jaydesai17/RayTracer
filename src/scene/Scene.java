package scene;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

import utilities.Camera;
import utilities.Color;
import utilities.Light;
import utilities.Ray;
import utilities.Vector;
import geometry.GeometricObject;

/**
 * A scene to be written to the image.
 * @author Jay Desai.
 *
 */
public class Scene implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6653462378706144975L;
	private int width, height, maxReflections;
	private double aspectRatio, ambientLight, error;
	private Camera cam;
	private List<Light> lights;
	private List<GeometricObject> sceneObjects;
	private Function<Vector, Double> indexOfRefraction;
	
	/**
	 * Creates a scene.
	 * @param width Width of image to be drawn.
	 * @param height Height of image to be drawn.
	 * @param maxReflections Maximum number of reflections (10 is good).
	 * @param ambientLight Ambient light of scene. Double less than 1. Something like 0.2 works well.
	 * @param error Error allowed (something like .00001)
	 * @param cam Camera of scene.
	 * @param lights List of lights in scene.
	 * @param sceneObjects List of geometric objects in the scene.
	 */
	public Scene(int width, int height, int maxReflections, double ambientLight, double error, Camera cam, List<Light> lights, List<GeometricObject> sceneObjects){
		this.width = width;
		this.height = height;
		this.maxReflections = maxReflections;
		this.aspectRatio = (double)width/height;
		this.ambientLight = ambientLight;
		this.error = error;
		this.cam = cam;
		this.lights = lights;
		this.sceneObjects = sceneObjects;
	}
	
	/**
	 * Creates a scene. Makes ambientLight .2. Error something (don't worry) and maxReflections 10.
	 * @param width Width of image to be drawn.
	 * @param height Height of image to be drawn.
	 * @param cam Camera of scene.
	 * @param lights List of lights in the scene.
	 * @param sceneObjects List of geometric objects in the scene.
	 */	
	public Scene(int width, int height, Camera cam, List<Light> lights, List<GeometricObject> sceneObjects){
		this.width = width;
		this.height = height;
		this.maxReflections = 10;
		this.aspectRatio = (double)width/height;
		this.ambientLight = .4;
		this.error = .000001;
		this.cam = cam;
		this.lights = lights;
		this.sceneObjects = sceneObjects;
	}
	
	/**
	 * 
	 * @return the width of scene.
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * 
	 * @return the width of scene.
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * Gets the color at point x,y.
	 * @param x Point x.
	 * @param y Point y.
	 * @return The color at point x,y.
	 */
	public Color getColor(double x, double y){
		
		double xamnt = getXamnt(x,y);
		double yamnt = getYamnt(x,y);
		Vector cameraRayOrigin = cam.getCamPos();
		Vector cameraRayDir = cam.getCamDir().add(cam.getCamRight().scalarMultiply(xamnt-0.5).add(cam.getCamDown().scalarMultiply(yamnt-.5))).normalize();
		Ray  cameraRay = new Ray(cameraRayOrigin, cameraRayDir);
		
		double[] intersections = new double[sceneObjects.size()];
		for(int i = 0; i<sceneObjects.size(); i++){
			double intersection = sceneObjects.get(i).hit(cameraRay);
			if (intersection > error){
				intersections[i] = intersection;
			} else{
				intersections[i] = -1;
			}
		}
		
		int index = minIndex(intersections);
		
		if(index == -1){
			return new Color(0,0,0,0);
		} else {
			double distance = intersections[index];
			if(distance > error){
				Vector interPos = cameraRayOrigin.add(cameraRayDir.scalarMultiply(distance));
				Vector interDir = cameraRayDir;
				Color intersectionColor = getColorAtReflect(interPos,interDir,index,maxReflections);
				return intersectionColor;
			}
		}
		
		return new Color(0,0,0,0);
	}
	
	/**
	 * Gets the color at point x,y quickly (no reflections).
	 * @param x Point x.
	 * @param y Point y.
	 * @return The color at point x,y.
	 */
	public Color fastGetColor(double x, double y){
		double xamnt = getXamnt(x,y);
		double yamnt = getYamnt(x,y);
		Vector cameraRayOrigin = cam.getCamPos();
		Vector cameraRayDir = cam.getCamDir().add(cam.getCamRight().scalarMultiply(xamnt-0.5).add(cam.getCamDown().scalarMultiply(yamnt-.5))).normalize();
		Ray  cameraRay = new Ray(cameraRayOrigin, cameraRayDir);
		
		double[] intersections = new double[sceneObjects.size()];
		for(int i = 0; i<sceneObjects.size(); i++){
			double intersection = sceneObjects.get(i).hit(cameraRay);
			if (intersection > error){
				intersections[i] = intersection;
			} else{
				intersections[i] = -1;
			}
		}
		
		int index = minIndex(intersections);
		
		if(index == -1){
			return new Color(0,0,0,0);
		} else {
			double distance = intersections[index];
			if(distance > error){
				Vector interPos = cameraRayOrigin.add(cameraRayDir.scalarMultiply(distance));
				Vector interDir = cameraRayDir;
				Color intersectionColor = getColorAt(interPos,interDir,index);
				return intersectionColor;
			}
		}
		
		return new Color(0,0,0,0);
	}
	
	private Color getColorAt(Vector interPos, Vector interDir, int index){
		
		Color color = new Color(sceneObjects.get(index).colorAt(interPos));
		Vector normal = sceneObjects.get(index).normalAt(interPos);
		Color finalColor = color.colorScalar(ambientLight);
		
		for(int i = 0; i<lights.size(); i++){
			Vector lightDir = lights.get(i).getPosition().subtract(interPos).normalize();
			float cosine = (float)normal.dotProduct(lightDir);
			
			if (cosine > 0){
				boolean shadowed = false;
				Vector distanceToLight = lights.get(i).getPosition().subtract(interPos);
				float distanceToLightMag = (float)distanceToLight.magnitude();
				Ray shadowRay = new Ray(interPos,lights.get(i).getPosition().subtract(interPos).normalize());
				double[] secondaryIntersections = new double[sceneObjects.size()];
				for(int j = 0; j<sceneObjects.size(); j++){
					secondaryIntersections[j] = sceneObjects.get(j).hit(shadowRay);
				}
				
				for(int j = 0; j<secondaryIntersections.length;j++){
					if (secondaryIntersections[j] > error){
						if(secondaryIntersections[j] < distanceToLightMag){
							shadowed = true;
						}
					}
				}
				
				if(!shadowed){
					finalColor = finalColor.add(color.multiply(lights.get(i).getColor()).colorScalar(cosine));
					if(color.getSpecial()<=1 & color.getSpecial() > 0){
						double dot1 = normal.dotProduct(interDir.negative());
						Vector scalar1 = normal.scalarMultiply(dot1);
						Vector add1 = scalar1.add(interDir);
						Vector scalar2 = add1.scalarMultiply(2);
						Vector add2 = scalar2.subtract(interDir);
						Vector reflDir = add2.normalize();
						double specular = reflDir.dotProduct(lightDir);
						if(specular > 0){
							specular = Math.pow(specular,10);
							finalColor = finalColor.add(lights.get(i).getColor().colorScalar(specular*color.getSpecial()));
						}
					}
				}
			}
		}
		
		return finalColor.clip();
	}
	
	private Color getColorAtReflect(Vector interPos, Vector interDir, int index, int maxReflections){

		if(maxReflections == 0){
			return getColorAt(interPos,interDir,index);
		}
		
		Color color = new Color(sceneObjects.get(index).colorAt(interPos));
		Vector normal = sceneObjects.get(index).normalAt(interPos);
		Color finalColor = color.colorScalar(ambientLight);
		
		if(color.getSpecial()>0 & color.getSpecial() <= 1){
			double dot1 = normal.dotProduct(interDir.negative()); //what
			Vector scalar1 = normal.scalarMultiply(dot1); //I don't know
			Vector add1 = scalar1.add(interDir); // Ok sure
			Vector scalar2 = add1.scalarMultiply(2);
			Vector add2 = interDir.negative().add(scalar2); //wut
			Vector refl = add2.normalize();
			
			Ray reflRay = new Ray(interPos,refl);
			
			double[] intersections = new double[sceneObjects.size()];
			
			for(int i = 0; i<sceneObjects.size(); i++){
				intersections[i] = sceneObjects.get(i).hit(reflRay);
			}
			
			int minIndex = minIndex(intersections);
			
			if(minIndex != -1){
				if (intersections[minIndex] > error){
					Vector reflInterPos = interPos.add(refl.scalarMultiply(intersections[minIndex]));
					Color reflColor = getColorAtReflect(reflInterPos,refl,minIndex, maxReflections-1);
					finalColor = finalColor.add(reflColor.colorScalar(color.getSpecial()));
				}
			}
			
			if (color.getIndex() > 0) {
				Ray transRay = new Ray(interPos, interDir);
				for(int i = 0; i<sceneObjects.size(); i++){
					intersections[i] = sceneObjects.get(i).hit(transRay);
				}
				minIndex = minIndex(intersections);
				if(minIndex != -1){
					if (intersections[minIndex] > error){
						interDir = interDir.normalize();
						Vector transInterPos = interPos.add(interDir.scalarMultiply(intersections[minIndex]));
						Color reflColor = getColorAtReflect(transInterPos,interDir,minIndex, maxReflections-1);
						finalColor = finalColor.add(reflColor.colorScalar(color.getSpecial()));
					}
				}
			}
		}
	
		for(int i = 0; i<lights.size(); i++){
			Vector lightDir = lights.get(i).getPosition().subtract(interPos).normalize();
			float cosine = (float)normal.dotProduct(lightDir);
			
			if (cosine > 0){
				boolean shadowed = false;
				Vector distanceToLight = lights.get(i).getPosition().subtract(interPos);//.normalize();
				float distanceToLightMag = (float)distanceToLight.magnitude();
				Ray shadowRay = new Ray(interPos,lights.get(i).getPosition().subtract(interPos).normalize());
				double[] secondaryIntersections = new double[sceneObjects.size()];
				for(int j = 0; j<sceneObjects.size(); j++){
					secondaryIntersections[j] = sceneObjects.get(j).hit(shadowRay);//huh?
				}
				
				for(int j = 0; j<secondaryIntersections.length;j++){
					if (secondaryIntersections[j] > error){
						if(secondaryIntersections[j] < distanceToLightMag){
							shadowed = true;
						}
					}
				}
				
				if(!shadowed){
					finalColor = finalColor.add(color.multiply(lights.get(i).getColor()).colorScalar(cosine)); //what
					if(color.getSpecial()<=1 & color.getSpecial() > 0){
						double dot1 = normal.dotProduct(interDir.negative());
						Vector scalar1 = normal.scalarMultiply(dot1);
						Vector add1 = scalar1.add(interDir);
						Vector scalar2 = add1.scalarMultiply(2);
						Vector add2 = scalar2.subtract(interDir);
						Vector reflDir = add2.normalize();
						double specular = reflDir.dotProduct(lightDir);
						if(specular > 0){
							specular = Math.pow(specular,10); //the fucks a specular?
							finalColor = finalColor.add(lights.get(i).getColor().colorScalar(specular*color.getSpecial()));//sure
						}
					}
				}
			}
		}
		
		return finalColor.clip(); //wtf is clip
	}
	
	private double getXamnt(double x, double y){
		if(width > height){
			return ((x+0.5)/width)*aspectRatio - (((width-height)/(double)height)/2);
		} else if (height > width) {
			return (x+.5)/width;
		} else {
			return  (x+.5)/width;
		}
	}
	
	private double getYamnt(double x, double y){
		if(width > height){
			return ((height - y) + .5)/height;
		} else if (height > width) {
			return (((height-y)+.5)/height)/aspectRatio - (((height-width)/(double)width)/2);
		} else {
			return ((height-y) + .5)/height;
		}
	}
	
	private static int minIndex(double[] vals){
		int maxIn = 0;
		for(int i = 0; i<vals.length; i++){
			if (vals[i] > vals[maxIn]){
				maxIn = i;
			}
		}
		if (vals[maxIn] < 0){
			return -1;
		}
		
		int minIn = maxIn;
		for(int i = 0; i< vals.length; i++){
			if (vals[i] < vals[minIn] & vals[i] > 0){
				minIn = i;
			}
		}
		return minIn;
	}
	
	private class Tuple<X, Y> { 
		  public final X x; 
		  public final Y y; 
		  public Tuple(X x, Y y) { 
		    this.x = x; 
		    this.y = y; 
		  } 
		} 
	
	private static Tuple<Double, Integer> getHitWithRefract(Ray ray) {
		return null;
	}
}