package tk.shardsoftware.entity;

/**
 * @author James Burnell
 */
public class EntityShip extends Entity {

	public EntityShip() {
		super(0, 0, 50, 50);
	}

	public void update(float delta) {
		super.update(delta);
	}

	@Override
	public void setVelocity(float x, float y) {
		super.setVelocity(x, y);
		direction = velocityVec.angleDeg();
	}

	/**
	 * Does nothing as ships cannot have their direction manually set. The
	 * direction is based on the velocity vector.
	 */
	@Override
	public void setDirection(float angle) {
	}

}