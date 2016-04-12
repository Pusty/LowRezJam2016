package game.engine.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import game.classes.GameTick;
import game.engine.main.GameClass;
import game.engine.world.World;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.BlockLocation;
import me.pusty.util.PixelLocation;
import me.pusty.util.Velocity;

public class EntitySlime extends EntityLiving {

	public EntitySlime(int x, int z) {
		super(x, z);
		this.setDirection(0);
		inWater = false;
	}

	public String getTextureName() {
		if(onGround || !isJumping)
			return "slime_3";
		
			return "slime_3";
	}
	public String getMovingTexture() {
		return null;
	}
	

	public boolean hasDirections() { return false; }
	
	boolean dir=false;
	
	public void tickTraveled(AbstractGameClass game) {
		super.tickTraveled(game);
		
		World world = ((GameClass)game).getWorld();
		
		
		if(PixelLocation.getDistance(getLocation().add(new PixelLocation(4,4)),world.getPlayer().getLocation().add(new PixelLocation(8,8))) < 8) {
			world.getPlayer().damage(1);
		}
		
		if(onGround){
			if(!dir) 
				setDirection(1);
			else 
				setDirection(2);
			jump(game);
			setAnimation(game.getAnimationHandler().getAnimation("slime_jump"));
			dir=!dir;
		}
		
		if(isAnimationNull()) {
			if(onGround())
				setAnimation(game.getAnimationHandler().getAnimation("slime_idle"));
		}
		
		Velocity velo = getVelocity();
		if(velo==null) velo = new Velocity(0,0);
		velo.add(getAddLocation(true));
	
		if(!getJumping())
				velo.add(new Velocity(0,-1));
		
		PixelLocation newLoc = getLocation().addVelocity(velo);
		if(newLoc.x != getX() || newLoc.y != getY()) {
				BlockLocation[] blocks = newLoc.toBlocks();
				boolean collision = false;
				for(int b=0;b<blocks.length;b++)
					if(GameTick.collisonBlock(this,newLoc,blocks[b].getX(),blocks[b].getY(),world.getBlockID(blocks[b].getX(),blocks[b].getY()))) {
						collision = true;
						break;
					}
				if(!collision) 
						getLocation().set(newLoc);
				else {
					
					collision = false;
					if(velo.getY() != 0f) {					
						newLoc = getLocation().addVelocity(new Velocity(0f,velo.getY()));
						BlockLocation[] blocksY = newLoc.toBlocks();
						for(int b=0;b<blocksY.length;b++)
							if(GameTick.collisonBlock(this,newLoc,blocksY[b].getX(),blocksY[b].getY(),world.getBlockID(blocksY[b].getX(),blocksY[b].getY()))) {
								collision = true;
								break;
							}
						if(!collision) 
								getLocation().set(newLoc);
						else if(velo.getY()<0) 
								setGround(true);
							
					}
					if((collision || velo.getY() == 0f) && velo.getX() != 0f) {
						newLoc = getLocation().addVelocity(new Velocity(velo.getX(),0f));
						BlockLocation[] blocksX = newLoc.toBlocks();
						collision = false;
						for(int b=0;b<blocksX.length;b++)
							if(GameTick.collisonBlock(this,newLoc,blocksX[b].getX(),blocksX[b].getY(),world.getBlockID(blocksX[b].getX(),blocksX[b].getY()))) {
								collision = true;
								break;
							}
						if(!collision) 
								getLocation().set(newLoc);
					}
					
				}
			}
		

	}
	@Override
	public void jump(AbstractGameClass e) {
		if(onGround && !isJumping) {
			traveled = 10;
			isJumping=true;
			onGround=false;
			e.getSound().playClip("jump",((GameClass)e).getWorld().getPlayer().getLocation(),getLocation());
		}
	}
	
	public Velocity getAddLocation(boolean tick) {

		Velocity location = new Velocity(0,0);
		if(direction==1)
			location.add(new Velocity(1,0));
		else if(direction==2)
			location.add(new Velocity(-1,0));
		
		if(isJumping)
				location.add(new Velocity(0, (int)Math.ceil((float)traveled/5)));
		
		
	
		return location;
	}
	
	
	public void render(AbstractGameClass e,SpriteBatch g) {
		try {
			TextureRegion image = e.getImageHandler().getImage(getImage());
			PixelLocation cam = ((GameClass)e).getCamLocation();
			PixelLocation move = new PixelLocation(getX() - cam.getX(), getY() - cam.getY());
			if(getLastDirection()==1)
			image.flip(true, false);
			
			g.draw(image,move.getX(),move.getY());
			
			if(getLastDirection()==1)
			image.flip(true, false);
			
		} catch(Exception ex) { System.err.println(getImage()); }
	}
	

}

