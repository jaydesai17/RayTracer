package client;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import fileio.SceneGifWriter;
import geometry.ConstantColorSphere;
import geometry.ConstantGradientPlane;
import geometry.GeometricObject;
import geometry.Plane;
import geometry.Sphere;
import gifs.GifDefinition;
import gradients.Gradient;
import gradients.GradientFactory;
import scene.Scene;
import utilities.Camera;
import utilities.Color;
import utilities.Light;
import utilities.Vector;

/*

Just run it and it will render a huge gif of a ball bouncing.
You can edit it to do what you want. 

*/
public class Main {
	
	public static void main(String[] args) throws IOException {
		new Main().run();
	}

	private void run() throws IOException {
		GifDefinition bounce = new BounceGif();
		SceneGifWriter writer = new SceneGifWriter("abcefg.gif", 1, true);
		writer.writeGifSampled(bounce, 1, 1, null);
	}
	
	private class BounceGif implements GifDefinition {
		
		private double height;
		private double vel;
		private static final double GRAVITY = .055;
		
		private BounceGif() {
			height = 5.;
			vel = .16;
		}

		
		@Override
		public Scene sceneAt(int frame) {
			
			Color planeColorOne = new Color(.5, .2, .5, .4);
			Color planeColorTwo = Color.white(.2);
			Color weighted1 = planeColorOne.colorScalar(2*frame/100.0).add(planeColorTwo.colorScalar(1 - frame/100.0));
			Color weighted2 = planeColorTwo.colorScalar(frame/100.0).add(planeColorOne.colorScalar(1 - frame/100.0));
			Gradient planeGrad = null;
			try {
				planeGrad = GradientFactory.createInstance(weighted1, weighted2).makeXZ(1, 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Vector planeVector = new Vector(0, 1, 0);
			Plane plane = new ConstantGradientPlane(planeVector, -1, planeGrad);
			
			Vector spherePos = new Vector(0,height,0);
			Color sphereColor = new Color(.6, .9, .1, .5);
			Sphere sphere = new ConstantColorSphere(spherePos, 1, sphereColor);
			
			Color c = new Color(.3, .26, .1, .99);
			Sphere sphere2 = new ConstantColorSphere(new Vector(3, 1.8, -3), .2 + frame/100.0, c);
			
			Light light = new Light(new Vector(4, 4, 4), Color.white(.5));
			
			Camera camera = new Camera(new Vector(-2 + Math.sin(frame/20.0), 10 + Math.cos(frame/20.0), -4), new Vector(2 - Math.sin(frame/20.0), -5 + Math.cos(frame/20.0), 4));
			
			List<GeometricObject> objects = Arrays.asList(plane, sphere, sphere2);
			List<Light> lights = Arrays.asList(light);
			
			Scene scene = new Scene(600, 400, camera, lights, objects);
			
			height -= vel;
			vel += GRAVITY;
			if (height < 0) {
				vel *= -.7;
			}
			
			return scene;
		}
		
	}
	
}
