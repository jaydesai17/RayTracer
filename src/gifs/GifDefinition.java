package gifs;

import scene.Scene;

/**
 * Interface defining a gif.
 * @author Jay Desai
 *
 */
public interface GifDefinition{

	/**
	 * Method that gives the gif scene at a given time.
	 * @param time The frame of the gif.
	 * @return A scene of the time th frame.
	 */
	Scene sceneAt(int frame);
	
}