package geometry;

import utilities.Color;
import utilities.Ray;
import utilities.Vector;

public interface GeometricObject {

	Color colorAt(Vector location);
	Vector normalAt(Vector location);
	double hit(Ray ray);

}
