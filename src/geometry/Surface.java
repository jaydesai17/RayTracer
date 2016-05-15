package geometry;

import java.util.ArrayList;
import java.util.List;

import utilities.Vector;
import geometry.GeometricObject;
import geometry.Triangle;
import gradients.Gradient;

/**
 * Abstract class representing a parameterized surface.
 * @author Jay Desai.
 * 
 *
 */
public abstract class Surface {
	
	/**
	 * Creates a list of triangles used to approximate a parameterized surface. 
	 * @param startR R value where surface starts.
	 * @param startS S value where surface starts.
	 * @param rSteps Number of R intervals.
	 * @param sSteps Number of S intervals.
	 * @param deltaR Size of R intervals.
	 * @param deltaS Size of S intervals.
	 * @param grad The Gradient of the surface.
	 * @return
	 */
	public List<GeometricObject> mesh(double startR, double startS, int rSteps, int sSteps, double deltaR, double deltaS,final Gradient grad){
		
		List<GeometricObject> mesh = new ArrayList<GeometricObject>();
		
		for(int i = 0; i<sSteps; i++){
			for(int j = 0; j<rSteps; j++){
				double s0 = startS + i*deltaS;
				double s1 = s0 + deltaS;
				double r0 = startR + j*deltaR;
				double r1 = r0 + deltaR;
				Vector p1 = evaluate(r0,s0);
				Vector p2 = evaluate(r0,s1);
				Vector p3 = evaluate(r1,s0);
				Vector p4 = evaluate(r1,s1);
				Triangle t1 = new ConstantGradientTriangle(p1,p2,p3,grad);
				Triangle t2 = new ConstantGradientTriangle(p2,p3,p4,grad);
				mesh.add(t1);
				mesh.add(t2);
			}
		}
		return mesh;
	}
	
	/**
	 * Returns a the vector of the surface at point r,s in parameterization.
	 * @param r
	 * @param s
	 * @return
	 */
	public abstract Vector evaluate(double r, double s);

}