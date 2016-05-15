package threads;

import java.awt.image.BufferedImage;

import scene.Scene;

public class Tracer implements Runnable {
	
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private Scene scene;
	private BufferedImage bufferedImage;
	
	public Tracer(int startX, int startY, int endX, int endY, Scene scene, BufferedImage bufferedImage){
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.scene = scene;
		this.bufferedImage = bufferedImage;
	}
	
	/**
	 * This mutates the shit out of bufferedImage.
	 */
	@Override
	public void run() {
		long start = System.nanoTime();

		for(int x = startX; x<endX; x++){
			for(int y = startY; y<endY; y++){
				int colorValue = scene.getColor(x,y).toInt();
				bufferedImage.setRGB(x,scene.getHeight()-1-y, colorValue);
			}
		}
		
		long end = System.nanoTime();
		float elapsed = (float)((end-start)*1E-9);
		System.out.println("Section of image rendered sucessfully. Rendering time: " + elapsed + " seconds");
		
	}
	
}
