package btd.paintable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class AbstractPaintable implements Paintable {

	protected long age;
	
	protected Point2D loc;     // overall translation
	protected double rot;      // overall rotation
	protected Stroke stroke;   // overall stroke
	
	protected Image picture;   // picture
	protected int complexity;  // how detailed
	protected Shape[] rShape;  // shapes to draw in addition to picture
	protected Point2D[] rLoc;  // relative translations of shapes
	protected double[] rRot;   // relative rotations of shapes
	protected Color[] rCol;    // relative colors of shapes
	protected boolean[] rFill; // relative fill request of shapes
	
	protected boolean drawConcrete;
	
	protected int shapeForBox;
	
	public AbstractPaintable(Image picture, int depth) {
		age = 0;
		
		loc = new Point2D.Double();
		rot = 0;
		stroke = new BasicStroke();
		
		this.picture = picture; // could potentially be null
		complexity = depth;
		rShape = new Shape[complexity];
		rLoc = new Point2D[complexity];
		rRot = new double[complexity];
		rCol = new Color[complexity];
		rFill = new boolean[complexity];
		
		drawConcrete = false;
		
		shapeForBox = 0;
	}
	
	@Override
	public Rectangle2D getBounds() {
		Rectangle2D rect = null;
		if (picture != null) {
			double h = picture.getHeight(null);
			double w = picture.getWidth(null);
			rect = new Rectangle2D.Double(loc.getX() - w/2, loc.getY() - h/2, w, h);
		} else if (rShape[shapeForBox] != null) {
			rect = rShape[shapeForBox].getBounds2D();
			double h = rect.getHeight();
			double w = rect.getBounds2D().getWidth();
			rect = new Rectangle2D.Double(loc.getX() - w/2, loc.getY() - h/2, w, h);
		}
		return rect;
	}
	
	@Override
	public boolean intersects(Rectangle2D rect) {
		return getBounds().intersects(rect);
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Stroke initialStroke = g2.getStroke();
		Color initialColor = g2.getColor();
		if (drawConcrete) {
			g2.setColor(Color.LIGHT_GRAY);
			g2.fill(getBounds());
		}
		g2.translate(loc.getX(), loc.getY());
		g2.rotate(rot);
		if (picture != null) {
			g2.drawImage(picture, -picture.getWidth(null)/2, -picture.getHeight(null)/2, null);
		}
		g2.setStroke(stroke);
		for (int i = 0; i < complexity; i++) {
			g2.translate(rLoc[i].getX(), rLoc[i].getY());
			g2.rotate(rRot[i]);
			g2.setColor(rCol[i]);
			if (rFill[i]) {
				g2.fill(rShape[i]);
			} else {
				g2.draw(rShape[i]);
			}
			g2.rotate(-rRot[i]);
			g2.translate(-rLoc[i].getX(), -rLoc[i].getY());
		}
		g2.setColor(initialColor);
		g2.setStroke(initialStroke);
		g2.rotate(-rot);
		g2.translate(-loc.getX(), -loc.getY());
	}
	
	@Override
	public void paintAt(Graphics g, Point2D location) {
		Graphics2D g2 = (Graphics2D) g;
		Stroke initialStroke = g2.getStroke();
		Color initialColor = g2.getColor();
		if (drawConcrete) {
			g2.setColor(Color.LIGHT_GRAY);
			Rectangle2D newBounds = getBounds();
			newBounds = new Rectangle2D.Double(newBounds.getMinX() + location.getX(), newBounds.getMinY() + location.getY(), newBounds.getWidth(), newBounds.getHeight());
			g2.fill(newBounds);
		}
		g2.translate(location.getX(), location.getY());
		g2.rotate(rot);
		if (picture != null) {
			g2.drawImage(picture, -picture.getWidth(null)/2, -picture.getHeight(null)/2, null);
		}
		g2.setStroke(stroke);
		for (int i = 0; i < complexity; i++) {
			g2.translate(rLoc[i].getX(), rLoc[i].getY());
			g2.rotate(rRot[i]);
			g2.setColor(rCol[i]);
			if (rFill[i]) {
				g2.fill(rShape[i]);
			} else {
				g2.draw(rShape[i]);
			}
			g2.rotate(-rRot[i]);
			g2.translate(-rLoc[i].getX(), -rLoc[i].getY());
		}
		g2.setColor(initialColor);
		g2.setStroke(initialStroke);
		g2.rotate(-rot);
		g2.translate(-location.getX(), -location.getY());
	}
	
	@Override
	public void update() {
		age++;
	}
	
	/////////////////////////////////////////////////////////////
	//                START OF ACCESSOR/MUTATOR                //
	/////////////////////////////////////////////////////////////
	@Override
	public long getAge() {
		return age;
	}
	
	@Override
	public void setAge(long age) {
		this.age = age;
	}
	
	@Override
	public Point2D getLocation() {
		return loc;
	}
	
	@Override
	public void setLocation(Point2D location) {
		loc.setLocation(location);
	}
	
	@Override
	public double getRotation() {
		return rot;
	}
	
	@Override
	public void setRotation(double rotation) {
		rot = rotation;
	}
	
	@Override
	public Stroke getStroke() {
		return stroke;
	}
	
	@Override
	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}
	
	@Override
	public Image getImage() {
		return picture;
	}
	
	@Override
	public void setImage(Image picture) {
		this.picture = picture;
	}
	
	@Override
	public int getComplexity() {
		return complexity;
	}
	
	@Override
	public void setComplexity(int complexity) {
		this.complexity = complexity;
	}
	
	@Override
	public Shape[] getSubShapes() {
		return rShape;
	}
	
	@Override
	public void setSubShapes(Shape[] shapes) {
		rShape = shapes;
	}
	
	@Override
	public Point2D[] getSubLocations() {
		return rLoc;
	}
	
	@Override
	public void setSubLocations(Point2D[] locations) {
		rLoc = locations;
	}
	
	@Override
	public double[] getSubRotations() {
		return rRot;
	}
	
	@Override
	public void setSubRotations(double[] rotations) {
		rRot = rotations;
	}
	
	@Override
	public Color[] getSubColors() {
		return rCol;
	}
	
	@Override
	public void setSubColors(Color[] colors) {
		rCol = colors;
	}
	
	@Override
	public boolean[] getSubFills() {
		return rFill;
	}
	
	@Override
	public void setSubFills(boolean[] fills) {
		rFill = fills;
	}
	
	@Override
	public boolean getDrawConcrete() {
		return drawConcrete;
	}
	
	@Override
	public void setDrawConcrete(boolean trueFalse) {
		drawConcrete = trueFalse;
	}
	/////////////////////////////////////////////////////////////
	//                 END OF ACCESSOR/MUTATOR                 //
	/////////////////////////////////////////////////////////////

}
