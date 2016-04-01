package game.engine.entity;


import me.pusty.util.AbstractGameClass;
import me.pusty.util.PixelLocation;


public class EntityLiving extends Entity {

	int direction = 0;
	int lastDirection = 1;
	public EntityLiving(int x, int z) {
		super(x, z);
	}
	public int getLastDirection() {
		return lastDirection;
	}
	public int getDirection() {
		return direction;
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
	
	int walkAnimationRunned = 0;
	public String getImage() {
		if(img!=null)
			return img;
		if(getDirection() == 0 || getMovingTexture() == null) {
			if(!hasDirections())
				return getTextureName();
			else
				return getTextureName()+"_"+(this.getLastDirection()-1);
		}else if(getDirection() != 0) {
			float percent = ((float)getSpeed()-getTraveled())/getSpeed();
			int frame = Math.round(percent * 1) + walkAnimationRunned ; // frame = process * framecount
			if(!hasDirections())
				return getMovingTexture()+"_"+frame;
			else
				return getMovingTexture()+"_"+((this.getDirection()-1)*4 + frame);
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
		if(tick)
			walkAnimationRunned = walkAnimationRunned==0?2:0;
		if(animation!=null) return new PixelLocation(0,0);
		else if(direction==1)
			return new PixelLocation(1,0);
		else if(direction==2)
			return new PixelLocation(-1,0);
		return new PixelLocation(0,0);
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
	public void startWalking(boolean wall) {
		traveled = getSpeed();
		moving = wall;
	}
	boolean moving=true;
	public boolean isMoving() {
		return moving;
	}
	//Get Speed 30 = 1 sec
	public int getSpeed() {
		return 10;
	}
	public int getTraveled() {
		return traveled;
	}
	public void tickTraveled() {
		if(animation!=null) return;
		
		if(traveled > 0)
			traveled--;
		
		if(traveled <= 0 && (setDirection || setDirectionNull) ) {
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
