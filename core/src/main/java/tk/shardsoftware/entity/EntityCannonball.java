package tk.shardsoftware.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import tk.shardsoftware.World;
import tk.shardsoftware.util.SoundManager;

/** @author James Burnell */
public class EntityCannonball extends Entity {

	private ICannonCarrier parentObj;

	public EntityCannonball(World worldObj, float x, float y, ICannonCarrier parentObj) {
		super(worldObj, 5, 5);
		this.setCenter(x, y);
		this.setMaxSpeed(250);
		this.setHitboxScale(0.5f);
		this.setIgnoreWorldCollision(true);
		this.setIgnoreEntityCollision(true);
		this.setTexture("textures/entity/cannonball.png");
		this.setSolid(false);
		this.parentObj = parentObj;
	}

	/**
	 * Constructor for EntityCannonball
	 * @param worldObj Instance of World, the world the cannonball will be a part of
	 * @param x the x-position of the cannonball
	 * @param y the y-position of the cannonball
	 * @param dirVec the initial direction of the cannonball
	 * @param parentObj the entity which shot the cannonball
	 */
	public EntityCannonball(World worldObj, float x, float y, Vector2 dirVec,
			ICannonCarrier parentObj) {
		this(worldObj, x, y, parentObj);
		setDirection(dirVec);
	}

	/**
	 * Sets the direction of the cannonball.
	 * @param dirVec the new direction for the cannonball.
	 */
	public void setDirection(Vector2 dirVec) {
		this.setVelocity(dirVec.setLength(maximumSpeed));
	}

	/**
	 *
	 * @param delta the time between the previous update and this one
	 */
	@Override
	public void update(float delta) {
		// Cannonballs spin through the air
		this.setDirection(direction + delta * 60 * 15);
		super.update(delta);
	}

	/**
	 * Called when the cannonball touches an entity which implements IDamageable
	 * @param obj
	 */

	/**
	 * Check that the cannonball is able to damage the object
	 * @param obj object to be damaged
	 * @return boolean
	 */
	public boolean checkCanDamage(IDamageable obj){
		//if parent is a college, then make sure it can't damage other colleges
		if(parentObj instanceof College && (obj instanceof College || (obj instanceof EntityAIShip))){
			return false;
		}
		//if parent is the player, make sure they can't damage a friendly college.
		if(parentObj instanceof EntityShip && ((EntityShip) parentObj).isPlayer && obj instanceof College && ((College) obj).isFriendly == true){
			return false;
		}
		return true;
	}

	public void onTouchingDamageable(IDamageable obj) {

		if(!checkCanDamage(obj)){
			return;
		}


		obj.damage(
				MathUtils.random(parentObj.getCannonDamage() - 2, parentObj.getCannonDamage() + 2));
		if(obj instanceof College){
			SoundManager.playSound(((College) obj).hitSound);
		}
		this.remove = true;
	}

	/**
	 * Checks whether an object which implements IDamageable is the same as the object which shot this cannonball
	 * @param dmgObj Object which implements IDamageable
	 * @return boolean
	 */
	@SuppressWarnings("unlikely-arg-type")
	public boolean isObjParent(IDamageable dmgObj) {
		return dmgObj.equals(parentObj);
	}

	/**
	 * Called when the cannonball touches the borders of the map.
	 */
	@Override
	public void onTouchingBorder() {
		this.remove = true;
	}

}
