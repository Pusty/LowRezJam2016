package game.engine.entity;

import game.classes.GameTick;
import game.engine.main.GameClass;
import game.engine.world.World;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.BlockLocation;
import me.pusty.util.PixelLocation;
import me.pusty.util.Velocity;

public class Reaper extends EntityLiving {

	public Reaper(int x, int z) {
		super(x, z);
		this.setDirection(0);
		inWater = false;
	}

	public String getTextureName() { 
			return "reaper";
	}
	public String getMovingTexture() {
		return null;
	}
	

	public boolean hasDirections() { return false; }
	
	boolean dir=false;
	int tickRunning=0;
	boolean moving=false;
	public void tickTraveled(AbstractGameClass game) {
		super.tickTraveled(game);
		
		World world = ((GameClass)game).getWorld();
		
//		if(tickRunning>0)
//			tickRunning--;
		
		dir=world.getPlayer().getX()<this.getX();
		if(Math.abs((world.getPlayer().getX()+8)-(this.getX()+20)) > 32){
			if(!dir) 
				setDirection(1);
			else 
				setDirection(2);
//			tickRunning=50;
		}else
			setDirection(0);
		
		Velocity velo = getVelocity();
		if(velo==null) velo = new Velocity(0,0);
		if(moving) 
			velo.add(getAddLocation(true));
		moving=!moving;
	
		if(!getJumping())
				velo.add(new Velocity(0,-1));
		
		PixelLocation newLoc = getLocation().addVelocity(velo);
		
		if(newLoc.x != getX() || newLoc.y != getY()) {
				BlockLocation[] blocks = GameTick.getAxBHitBox(newLoc,5,5);
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
						BlockLocation[] blocksY = GameTick.getAxBHitBox(newLoc,5,5);
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
						BlockLocation[] blocksX = GameTick.getAxBHitBox(newLoc,5,5);
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
