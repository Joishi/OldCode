package btd.paintable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public interface Paintable {

	// state accessor and mutator methods
	long getAge();
	void setAge(long age);
	
	Point2D getLocation();
	void setLocation(Point2D location);
	double getRotation();
	void setRotation(double rotation);
	Stroke getStroke();
	void setStroke(Stroke stroke);
	
	Image getImage();
	void setImage(Image picture);
	int getComplexity();
	void setComplexity(int complexity);
	Shape[] getSubShapes();
	void setSubShapes(Shape[] shapes);
	Point2D[] getSubLocations();
	void setSubLocations(Point2D[] locations);
	double[] getSubRotations();
	void setSubRotations(double[] rotations);
	Color[] getSubColors();
	void setSubColors(Color[] colors);
	boolean[] getSubFills();
	void setSubFills(boolean[] fills);
	
	boolean getDrawConcrete();
	void setDrawConcrete(boolean trueFalse);
	
	// helper methods
	Rectangle2D getBounds();
	boolean intersects(Rectangle2D rect);
	
	// workhorse methods
	void paint(Graphics g);
	void paintAt(Graphics g, Point2D location);
	void update();

}
