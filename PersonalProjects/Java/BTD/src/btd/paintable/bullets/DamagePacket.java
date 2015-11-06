package btd.paintable.bullets;

public class DamagePacket {

	private long damage;
	private int element;
	
	public static final int PHYSICAL = 0;
	public static final int COLD = 1;
	public static final int FIRE = 2;
	public static final int POISON = 3;
	public static final int RADIATION = 4;
	
	public DamagePacket(long damage, int elementCode) {
		this.damage = damage;
		element = elementCode;
	}
	
	public long getDamage() {
		return damage;
	}
	
	public int getElement() {
		return element;
	}

}
