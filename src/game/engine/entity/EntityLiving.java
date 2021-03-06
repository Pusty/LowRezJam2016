package game.engine.entity;


import game.classes.SpaceTick;
import game.engine.main.GameClass;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.PixelLocation;
import me.pusty.util.Velocity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class EntityLiving extends Entity {

	int direction = 0;
	int lastDirection = 1;
	boolean isJumping = false;
	boolean onGround = true;
	Velocity velocity = null;
	boolean inWater = false;
	public EntityLiving(int x, int z) {
		super(x, z);
	}
	public void setWater(boolean b) {
		inWater = b;
	}
	public boolean getWater() {
		return inWater;
	}
	
	public Velocity getVelocity() {
		return velocity;
	}
	
	public void setVelocity(Velocity v) {
		velocity = v;
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
	
	public void jump(AbstractGameClass e) {
		if(onGround && !isJumping) {
			traveled = 20;
			isJumping=true;
			onGround=false;
			e.getSound().playClip("jump",((GameClass)e).getWorld().getPlayer().getLocation(),getLocation());
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
	

	public void render(AbstractGameClass e,SpriteBatch g) {
		try {
			TextureRegion image = e.getImageHandler().getImage(getImage());
			PixelLocation cam = ((GameClass)e).getCamLocation();
			PixelLocation move = new PixelLocation(getX() - cam.getX(), getY() - cam.getY());
			if(getLastDirection()==2)
			image.flip(true, false);
			
			g.draw(image,move.getX(),move.getY());
			
			if(getLastDirection()==2)
			image.flip(true, false);
			
		} catch(Exception ex) { System.err.println(getImage()); }
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
	
	public Velocity getAddLocation(boolean tick) {
		
		Velocity location = new Velocity(0,0);
		if(direction==1)
			location.add(new Velocity(1,0));
		else if(direction==2)
			location.add(new Velocity(-1,0));
		
		if(isJumping && !canMoveVertical())
			if(inWater)
				location.add(new Velocity(0, (int)Math.ceil((float)traveled/20)));
			else
				location.add(new Velocity(0, (int)Math.ceil((float)traveled/10)));
		
		
		if(canMoveVertical() && directionVertical!=0)
			location.add(new Velocity(0,1*directionVertical));
			
		return location;
	}
	public boolean canMoveVertical() {
		return false;
	}
	int directionVertical=0;
	public void up(GameClass g) {
		directionVertical=1;
	}
	public void down(GameClass g) {
		directionVertical=-1;
	}
	public void setDirectionVertical(int i) {
		directionVertical=i;
	}
	public int getDirectionVertical() {
		return directionVertical;
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
	public void tickTraveled(AbstractGameClass game) {
		this.setSpeachText(null);
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

	public void renderExtra(AbstractGameClass e,SpriteBatch b) {
		if(speachText==null)return;
			try {
				PixelLocation cam = ((GameClass)e).getCamLocation();
				PixelLocation move = new PixelLocation(getX() - cam.getX(), getY() - cam.getY());
				move = move.add(new PixelLocation(-speachText.length()*5/2 + 8,8));
				int multip = getLastDirection()==1?1:-1;
				move = move.add(new PixelLocation(16*multip ,8));
				SpaceTick.renderSmallText(e, b, move, speachText);
			} catch(Exception ex) { System.err.println(getImage()); }
		
	}
	boolean useThings = false;
	public void use(GameClass gameClass) {
		useThings = true;
	}
	public boolean getUse() {
		return useThings;
	}
	public void setUse(boolean b) {
		useThings = b;
	}
	
}
