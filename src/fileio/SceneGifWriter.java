
package fileio;

import gifs.GifDefinition;
import imgur.ImgurPoster;

import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;

import scene.Scene;

import java.awt.image.*;
import java.io.*;
import java.util.Iterator;
import java.util.Random;

public class SceneGifWriter {
	
	private ImageWriter gifWriter;
	private ImageWriteParam imageWriteParam;
	private IIOMetadata imageMetaData;
	private String gifName;
	private FileImageOutputStream outputStream;
  
  /**
   * Creates a new GifSequenceWriter
   * 
   * @param outputStream the ImageOutputStream to be written to
   * @param imageType one of the imageTypes specified in BufferedImage
   * @param timeBetweenFramesMS the time between frames in miliseconds
   * @param loopContinuously wether the gif should loop repeatedly
   * @throws IIOException if no gif ImageWriters are found
   *
   * @author Elliot Kroo (elliot[at]kroo[dot]net)
   */
	public SceneGifWriter(String gifName,int timeBetweenFramesMS,boolean loopContinuously) throws IIOException, IOException {
		
		this.gifName = gifName;
		this.outputStream  = new FileImageOutputStream(new File("temp/" + gifName));
		
		gifWriter = getWriter(); 
		imageWriteParam = gifWriter.getDefaultWriteParam();
		ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
		imageMetaData = gifWriter.getDefaultImageMetadata(imageTypeSpecifier,imageWriteParam);

		String metaFormatName = imageMetaData.getNativeMetadataFormatName();

		IIOMetadataNode root = (IIOMetadataNode)imageMetaData.getAsTree(metaFormatName);

		IIOMetadataNode graphicsControlExtensionNode = getNode(root,"GraphicControlExtension");

		graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
		graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
		graphicsControlExtensionNode.setAttribute("transparentColorFlag","FALSE");
		graphicsControlExtensionNode.setAttribute("delayTime",Integer.toString(timeBetweenFramesMS / 10));
		graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");

		IIOMetadataNode commentsNode = getNode(root, "CommentExtensions");
		commentsNode.setAttribute("CommentExtension", "Created by MAH");

		IIOMetadataNode appEntensionsNode = getNode(root, "ApplicationExtensions");

		IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");

		child.setAttribute("applicationID", "NETSCAPE");
		child.setAttribute("authenticationCode", "2.0");

		int loop = loopContinuously ? 0 : 1;

		child.setUserObject(new byte[]{ 0x1, (byte) (loop & 0xFF), (byte)((loop >> 8) & 0xFF)});
		appEntensionsNode.appendChild(child);

		imageMetaData.setFromTree(metaFormatName, root);

		gifWriter.setOutput(outputStream);
		gifWriter.prepareWriteSequence(null);
		
	}
  
	public void writeToSequence(RenderedImage img) throws IOException {
		gifWriter.writeToSequence(new IIOImage(img,null,imageMetaData),imageWriteParam);
	}
  

	public void close() throws IOException {
		gifWriter.endWriteSequence();    
	}

  /**
   * Returns the first available GIF ImageWriter using 
   * ImageIO.getImageWritersBySuffix("gif").
   * 
   * @return a GIF ImageWriter object
   * @throws IIOException if no GIF image writers are returned
   */
	private static ImageWriter getWriter() throws IIOException {
		Iterator<ImageWriter> iter = ImageIO.getImageWritersBySuffix("gif");
		if(!iter.hasNext()) {
			throw new IIOException("No GIF Image Writers Exist");
		} else {
			return iter.next();
		}
	}

  /**
   * Returns an existing child node, or creates and returns a new child node (if 
   * the requested node does not exist).
   * 
   * @param rootNode the <tt>IIOMetadataNode</tt> to search for the child node.
   * @param nodeName the name of the child node.
   * 
   * @return the child node, if found or a new node created with the given name.
   */
	private static IIOMetadataNode getNode(IIOMetadataNode rootNode,String nodeName) {
		int nNodes = rootNode.getLength();
		for (int i = 0; i < nNodes; i++) {
			if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName) == 0) {
				return((IIOMetadataNode) rootNode.item(i));
			}
		}
		IIOMetadataNode node = new IIOMetadataNode(nodeName);
		rootNode.appendChild(node);
		return(node);
	}
	
	/**
	 * Writes a given gif definition to a gif.
	 * @param gifName Name of the gif. Must be .gif extension probably. 
	 * @param definition Definition of the gif.
	 * @param numFrames Number of frames.
	 * @param threads Number of threads to run.
	 * @param timeBetweenFrames  Time between frames in milliseconds.
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public void writeGif(GifDefinition definition, int numFrames, int threads) throws IOException, InterruptedException  {
		
		long start = System.nanoTime();
		System.out.println("Beginning to render gif.");		
		for(int i = 0; i<numFrames; i++){
			Scene scene = definition.sceneAt(i);
			BufferedImage buffer = new SceneImageWriter(scene,null).sceneToBufferedImage(threads);
			writeToSequence(buffer);
			System.out.println(i+1 + " frames rendered out of " + numFrames + ".");
		}
			
		close();
		outputStream.close(); 
	
		long end = System.nanoTime();
		float elapsed = (float)((end-start)*1E-9);
		System.out.println("Gif rendered. Rendering time: " + elapsed + " seconds");
		System.out.println("Image saved as " + gifName);
		
	}
	
	public void writeGifSampled(GifDefinition definition, int numFrames, int samples, Random random) throws IOException {
		long start = System.nanoTime();
		System.out.println("Beginning to render gif.");				
		for(int i = 0; i<numFrames; i++){
			Scene scene = definition.sceneAt(i);
			BufferedImage buffer;
			if (random == null) {
				buffer = new SceneImageWriter(scene, null).sceneToBufferSampled();
			} else {
				buffer = new SceneImageWriter(scene,null).sceneToBufferSampled(samples, random);
			}
			writeToSequence(buffer);
			System.out.println(i+1 + " frames rendered out of " + numFrames + ".");
		}
			
		close();
		outputStream.close(); 
	
		long end = System.nanoTime();
		float elapsed = (float)((end-start)*1E-9);
		System.out.println("Gif rendered. Rendering time: " + elapsed + " seconds");
		System.out.println("Image saved as " + gifName);
	}
	
	public void writeGifSingleThreaded(GifDefinition definition, int numFrames) throws IOException, InterruptedException  {		
		long start = System.nanoTime();
		System.out.println("Beginning to render gif.");			
		for(int i = 0; i<numFrames; i++){
			Scene scene = definition.sceneAt(i);
			BufferedImage buffer = new SceneImageWriter(scene,null).sceneToBufferSingleThreaded();
			writeToSequence(buffer);
			System.out.println(i+1 + " frames rendered out of " + numFrames + ".");
		}
			
		close();
		outputStream.close(); 
	
		long end = System.nanoTime();
		float elapsed = (float)((end-start)*1E-9);
		System.out.println("Gif rendered. Rendering time: " + elapsed + " seconds");
		System.out.println("Image saved as " + gifName);
		
  }
	
  public String postToImgur(){
	  return ImgurPoster.postToImgur("temp/" + gifName);
  }
	
}