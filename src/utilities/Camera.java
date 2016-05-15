package utilities;

import java.io.Serializable;

/**
 * Represents a camera.
 * @author Jay Desai
 *
 */
public class Camera implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5408923069455331279L;
	private Vector camPos, camDir, camRight, camDown;
	
	/**
	 * Creates a camera. Thats what constructors do you idiot.
	 * @param camPos Vector position of the camera.
	 * @param lookAt Vector camera is looking at.
	 */
	public Camera(Vector camPos, Vector lookAt){
		Vector Y = new Vector(0,1,0);
		Vector diffBtw = camPos.subtract(lookAt);
		this.camDir = diffBtw.negative().normalize();
		this.camRight = Y.crossProduct(camDir).normalize();
		this.camDown = camRight.crossProduct(camDir);	
		this.camPos = camPos;
	}
	
	/**
	 * Default constructor creating camera at origin looking in x direction or something im not sure.
	 */
	public Camera(){
		camPos = new Vector(0,0,0);
		camDir = new Vector(0,0,1);
		camRight = new Vector(0,0,0);
		camDown = new Vector(0,0,0);
	}
	
	public void moveForward(){
		
	}
	
	public void turn(){
		
	}
	
	public Vector getCamPos(){
		return camPos;
	}
	
	public Vector getCamDir(){
		return camDir;
	}
	
	public Vector getCamRight(){
		return camRight;
	}
	
	public Vector getCamDown(){
		return camDown;
	}
}