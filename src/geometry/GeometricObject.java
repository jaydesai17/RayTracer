package geometry;

import java.io.Serializable;

import utilities.Color;
import utilities.Ray;
import utilities.Vector;

public interface GeometricObject extends Serializable {

	Color colorAt(Vector location);
	Vector normalAt(Vector location);
	double hit(Ray ray);

}
