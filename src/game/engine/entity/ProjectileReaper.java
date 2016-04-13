package game.engine.entity;

import game.classes.GameTick;
import game.engine.main.GameClass;
import game.engine.world.World;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.BlockLocation;
import me.pusty.util.PixelLocation;



public class ProjectileReaper extends Entity{
	int direction;
	PixelLocation goal;
	PixelLocation startLoc;
	public ProjectileReaper(int x, int y,int d,PixelLocation goal) {
		super(x, y);
		direction=d;
		this.goal = goal.add(new PixelLocation(16,16));
		this.startLoc = new PixelLocation(x+20,y+20);
	}
	
	public int getDirection() {
		return direction;
	}
	
	public String getTextureName() {
		return "projectile_0";
	}
	
	int timeFlying=0;
	
	boolean start=false;
	
	int tempMultiY = 0;
	int tempMultiX = 0;
	int oldX = -1;
	int oldY = -1;
	
	public void tickTraveled(AbstractGameClass game) {
		
		if(isAnimationNull()) {
			if(!start){
				setAnimation(game.getAnimationHandler().getAnimation("proj_shoot"));
				start=true;
			} else
			setAnimation(game.getAnimationHandler().getAnimation("proj_air"));
		}
		
		PixelLocation l = this.getLocation();
		
		if(oldX == -1)
			oldX = l.getX();
		if(oldY == -1)
			oldY = l.getY();
		
		if(oldX == l.getX())
			tempMultiX++;
		if(oldY == l.getY())
			tempMultiY++;

		
		float addX = ((float)goal.getX()-startLoc.getX()) / Math.max(1,PixelLocation.getDistance(goal, startLoc)) * tempMultiX;
		float addY = ((float)goal.getY()-startLoc.getY())  / Math.max(1,PixelLocation.getDistance(goal, startLoc)) * tempMultiY;
		
		PixelLocation newLoc = new PixelLocation(l.x+(int)addX,l.y+(int)addY);
		
		if(oldX != newLoc.getX()) {
			tempMultiX=1;
			oldX=-1;
		}
		if(oldY != newLoc.getY()) {
			tempMultiY=1;
			oldY=-1;
		}

		//Collision
		BlockLocation[] blocks = newLoc.toBlocks();
		World world = ((GameClass)game).getWorld();
		for(int b=0;b<blocks.length;b++)
			if(GameTick.collisonBlock(this, newLoc, blocks[b].getX(), blocks[b].getY(), world.getBlockID(blocks[b].getX(), blocks[b].getY()))) {
				game.getSound().playClip("down",((GameClass)game).getWorld().getPlayer().getLocation(),getLocation());
				timeFlying=50;
			}
		this.getLocation().set(newLoc);
		timeFlying++;
		if(timeFlying>50)
			((GameClass)game).getWorld().removeEntity(this);
		
		if(PixelLocation.getDistance(getLocation().add(new PixelLocation(4,4)),
					world.getPlayer().getLocation().add(new PixelLocation(8,8))) < 8) {
			world.getPlayer().damage(1);
			timeFlying=50;
		}
	}
	
}


