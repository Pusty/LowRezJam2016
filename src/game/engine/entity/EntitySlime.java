package game.engine.entity;

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
			return "tile_26";
		
			return "tile_25";
	}
	public String getMovingTexture() {
		return null;
	}
	

	public boolean hasDirections() { return false; }
	
	public void tickTraveled(AbstractGameClass game) {
		super.tickTraveled(game);
		
		Velocity velo = getVelocity();
		if(velo==null) velo = new Velocity(0,0);
		velo.add(getAddLocation(true));
	
		if(!getJumping())
				velo.add(new Velocity(0,-1));
		
		PixelLocation newLoc = getLocation().addVelocity(velo);
		World world = ((GameClass)game).getWorld();
		
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
						else if(velo.getY()<0) {
								setGround(true);
								jump();
						}
						
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
	
	public void jump() {
		if(onGround && !isJumping) {
			traveled = 10;
			isJumping=true;
			onGround=false;
		}
	}
	
	public Velocity getAddLocation(boolean tick) {
		if(animation!=null) return new Velocity(0,0);
		
		Velocity location = new Velocity(0,0);
		if(direction==1)
			location.add(new Velocity(1,0));
		else if(direction==2)
			location.add(new Velocity(-1,0));
		
		if(isJumping)
				location.add(new Velocity(0, (int)Math.ceil((float)traveled/5)));
		
		
	
		return location;
	}
	

}

