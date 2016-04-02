package game.engine.entity;


import me.pusty.util.AbstractGameClass;
import me.pusty.util.PixelLocation;


public class EntityLiving extends Entity {

	int direction = 0;
	int lastDirection = 1;
	boolean isJumping = false;
	boolean onGround = true;
	public EntityLiving(int x, int z) {
		super(x, z);
	}
	public int getLastDirection() {
		return lastDirection;
	}
	public int getDirection() {
		return direction;
	}
	public void setJumping(boolean b) {
		isJumping=b;
	}
	
	public boolean getJumping() {
		return isJumping;
	}
	
	public boolean onGround() {
		return onGround;
	}
	
	public void setGround(boolean b) {
		onGround=b;
	}
	
	public void jump() {
		if(onGround && !isJumping) {
			traveled = 20;
			isJumping=true;
			onGround=false;
		}
	}
	
	public void setDirection(int d) {
		direction = d;
		if(direction!=0)
			lastDirection=direction;
	}
	public void setLastDirection(int d) {
			lastDirection=d;
	}
	public String getMovingTexture() {
		return null;
	}
	
	public boolean hasDirections() { return false; }
	
	public String getImage() {
		if(img!=null)
			return img;
		if(getDirection() == 0 || getMovingTexture() == null) {
//				return getTextureName()+"_"+(this.getLastDirection()-1);
		}else if(getDirection() != 0) {
//				return getMovingTexture()+"_"+((this.getDirection()-1)*4 + frame);
		}
		return getTextureName();
	}
	
	
	public void renderTick(AbstractGameClass engine,int ind){
		if(animation!=null) {
			
			String img = animation.getFrame();
			if(img!=null)
			setImage(img);
			else
			{setAnimation(null);setDefault();}
		}else if(img!=null)
			setDefault();
	}
	
	public PixelLocation getAddLocation(boolean tick) {
		if(animation!=null) return new PixelLocation(0,0);
		
		PixelLocation location = new PixelLocation(0,0);
		if(direction==1)
			location = location.add(new PixelLocation(1,0));
		else if(direction==2)
			location = location.add(new PixelLocation(-1,0));
		
		if(isJumping)
			location = location.add(new PixelLocation(0, (int)Math.ceil((float)traveled/10)));
		return location;
	}

	
	

	boolean setDirection = false;
	int setDirectionInt = 0;
	boolean setDirectionNull = false;
	
	public void queueDirection(int d) {
		setDirection = true;
		if(d == 0) {
			setDirectionNull = true;
		}else {
			setDirectionNull = false;
			setDirectionInt = d;
		}
		
	}
	int traveled = 0;

	public int getTraveled() {
		return traveled;
	}
	public void tickTraveled() {
		if(animation!=null) return;
		
		if(traveled > 0)
			traveled--;
		
		if(traveled == 0 && isJumping)
			isJumping = false;
		
		if(setDirection || setDirectionNull) {
			if(setDirectionNull && setDirectionInt==0) 
				setDirection(0);
			else
				setDirection(setDirectionInt);
			setDirectionInt = 0;
			setDirection = false;
			setDirectionNull = false;
		}
	}
}
