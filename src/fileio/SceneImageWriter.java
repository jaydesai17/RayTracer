package fileio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import imgur.ImgurPoster;
import scene.Scene;
import threads.Tracer;
import utilities.Color;

/**
 * Writes an image based on a scene and image name.
 * @author Jay Desai
 *
 */
public class SceneImageWriter {

	private Scene scene;
	private String imageName;
	private boolean rendered;
	
	private static final int maxThreads = 3;
	
	public SceneImageWriter(Scene scene, String imageName){
		this.scene = scene;
		this.imageName = imageName;
		this.rendered = false;
	}
	/**
	 * Writes the image to a new file with the name given.
	 * @param scene Scene to be drawn on image.
	 * @param imageName What to call the image. Include file extension. It has to be a png or jpg.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void writeImage() throws IOException, InterruptedException{
		
		String fileExtension;
		if(imageName.toLowerCase().contains(".png")){
			fileExtension = "PNG";
		} else if(imageName.toLowerCase().contains(".jpg")){
			fileExtension = "JPG";
		} else{
			System.out.println("Not a valid file extension.");
			return;
		}
		
		long start = System.nanoTime();
		
		File image = new File(imageName);
		BufferedImage buffer = sceneToBufferedImage(maxThreads);
		ImageIO.write(buffer,fileExtension,image);
		long end = System.nanoTime();
		float elapsed = (float)((end-start)*1E-9);
		this.rendered = true;
		System.out.println("Image rendered. Rendering time: " + elapsed + " seconds");
		System.out.println("Image saved as " + imageName);
		
	}
	
	public void writeImage(int samples, Random random) throws IOException {
		String fileExtension;
		if(imageName.toLowerCase().contains(".png")){
			fileExtension = "PNG";
		} else if(imageName.toLowerCase().contains(".jpg")){
			fileExtension = "JPG";
		} else{
			System.out.println("Not a valid file extension.");
			return;
		}
		
		long start = System.nanoTime();
		
		File image = new File(imageName);
		BufferedImage buffer;
		if (random != null) {
			buffer = sceneToBufferSampled(samples, random);
		} else {
			buffer = sceneToBufferSampled();
		}
		ImageIO.write(buffer,fileExtension,image);
		long end = System.nanoTime();
		float elapsed = (float)((end-start)*1E-9);
		this.rendered = true;
		System.out.println("Image rendered. Rendering time: " + elapsed + " seconds");
		System.out.println("Image saved as " + imageName);
	}
	
	public void writeImage(int threads) throws IOException, InterruptedException{
		String fileExtension;
		if(imageName.toLowerCase().contains(".png")){
			fileExtension = "PNG";
		} else if(imageName.toLowerCase().contains(".jpg")){
			fileExtension = "JPG";
		} else{
			System.out.println("Not a valid file extension.");
			return;
		}
		
		long start = System.nanoTime();
		
		File image = new File(imageName);
		BufferedImage buffer = sceneToBufferedImage(threads);
		ImageIO.write(buffer,fileExtension,image);
		long end = System.nanoTime();
		float elapsed = (float)((end-start)*1E-9);
		this.rendered = true;
		System.out.println("Image rendered. Rendering time: " + elapsed + " seconds");
		System.out.println("Image saved as " + imageName);
	}
	
	public void writeImageSingleThreaded() throws IOException{
		String fileExtension;
		if(imageName.toLowerCase().contains(".png")){
			fileExtension = "PNG";
		} else if(imageName.toLowerCase().contains(".jpg")){
			fileExtension = "JPG";
		} else{
			System.out.println("Not a valid file extension.");
			return;
		}
		
		long start = System.nanoTime();
		
		File image = new File(imageName);
		BufferedImage buffer = sceneToBufferSingleThreaded();
		ImageIO.write(buffer,fileExtension,image);
		long end = System.nanoTime();
		float elapsed = (float)((end-start)*1E-9);
		this.rendered = true;
		System.out.println("Image rendered. Rendering time: " + elapsed + " seconds");
		System.out.println("Image saved as " + imageName);
	}
	
	protected BufferedImage sceneToBufferedImage(int threads) throws InterruptedException{
		
		int width = scene.getWidth();
		int height = scene.getHeight();
		BufferedImage buffer = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		
		List<Thread> workers=  new ArrayList<Thread>();
		int dx = width/threads;
				
		for(int i = 0; i<threads; i++){
			int startX = i*dx;
			int endX;
			if(i == threads - 1){
				endX = width;
			} else{
				endX = (i+1)*dx;
			}
			int startY = 0;
			int endY = height;
			Tracer tracer = new Tracer(startX,startY,endX,endY,scene,buffer);
			Thread worker = new Thread(tracer);
			workers.add(worker);
			worker.start();
		}
						
		for(Thread worker: workers){
			worker.join();
		}
		
		return buffer;
	}
	
	protected BufferedImage sceneToBufferSingleThreaded(){
		int width = scene.getWidth();
		int height = scene.getHeight();
		BufferedImage buffer = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		for(int i = 0; i < width; i++){
			for(int j = 0; j<height; j++){
				int colorValue = scene.getColor(i, j).toInt();
				buffer.setRGB(i, height - 1 - j, colorValue);
			}
		}
		return buffer;
	}
	
	protected BufferedImage sceneToBufferSampled(int samples, Random random) {
		int width = scene.getWidth();
		int height = scene.getHeight();
		BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Color color = new Color();
				int dx = random.nextInt(8);
				int dy = random.nextInt(8);
				for (int k = 0; k < samples; k++) {
					double x = i + dx * 0.125;
					double y = j + dy * 0.125;
					Color sample = scene.getColor(x, y);
					color = color.add(sample);
				}
				color = color.colorScalar(1.0/samples);
				buffer.setRGB(i, height - 1 - j, color.toInt());
			}
		}
		return buffer;
	}
	
	protected BufferedImage sceneToBufferSampled() {
		int width = scene.getWidth();
		int height = scene.getHeight();
		BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Color color = new Color();
				for (int k = 0; k < 8; k++) {
					for (int l = 0; l < 8; l++) {
						double x = i + k * 0.125;
						double y = j + l * 0.125;
						Color sample = scene.getColor(x, y);
						color = color.add(sample);
					}
				}
				color = color.colorScalar(1.0/64);
				buffer.setRGB(i, height - 1 - j, color.toInt());
			}
		}
		return buffer;
	}
	
	public String postToImgur(){
		if(rendered){
			return ImgurPoster.postToImgur(imageName);
		} else{
			return null;
		}
	}
	
}
