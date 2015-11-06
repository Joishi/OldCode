package btd.paintable.towers;

import java.awt.Image;

import btd.paintable.bullets.Bullet;

public class BasicTower extends AbstractTower {

	public BasicTower(Image picture, int depth) {
		super(picture, depth);
	}
	
	@Override
	protected Bullet getBullet() {
		return null;
	}
	
	@Override
	public Tower makeBlankCopy() {
		return null;
	}
	
//	public void setBulletDepth(int depth) {
//		bulletDepth = depth;
//	}
//	
//	public void setBulletType(int type) {
//		bulletType = type;
//	}
//	
//	public void setBulletStroke(BasicStroke stroke) {
//		bulletStroke = stroke;
//	}
//	
//	public boolean setBulletShapes(Shape[] shapes) {
//		if (shapes.length == bulletDepth) {
//			bulletShapes = shapes;
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	public boolean setBulletTranslations(Point2D[] translations) {
//		if (translations.length == bulletDepth) {
//			bulletTranslations = translations;
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	public boolean setBulletRotations(double[] rotations) {
//		if (rotations.length == bulletDepth) {
//			bulletRotations = rotations;
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	public boolean setBulletColors(Color[] colors) {
//		if (colors.length == bulletDepth) {
//			bulletColors = colors;
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	public boolean setBulletFills(boolean[] fills) {
//		if (fills.length == bulletDepth) {
//			bulletFills = fills;
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	public void setStroke(BasicStroke stroke) {
//		this.stroke = stroke;
//	}
//	
//	public boolean setShapes(Shape[] shapes) {
//		if (shapes.length == complexity) {
//			this.rShape = shapes;
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	public boolean setTranslations(Point2D[] translations) {
//		if (translations.length == complexity) {
//			this.rTran = translations;
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	public boolean setRotations(double[] rotations) {
//		if (rotations.length == complexity) {
//			this.rRot = rotations;
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	public boolean setColors(Color[] colors) {
//		if (colors.length == complexity) {
//			this.rCol = colors;
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	public boolean setFills(boolean[] fills) {
//		if (fills.length == complexity) {
//			this.rFill = fills;
//			return true;
//		} else {
//			return false;
//		}
//	}

}
