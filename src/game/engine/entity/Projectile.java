package game.engine.entity;

import game.classes.GameTick;
import game.engine.main.GameClass;
import game.engine.world.World;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.BlockLocation;
import me.pusty.util.PixelLocation;



public class Projectile extends Entity{
	int direction;
	public Projectile(int x, int y,int d) {
		super(x, y);
		direction=d;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public String getTextureName() {
		return "projectile_0";
	}
	
	int timeFlying=0;
	
	boolean start=false;
	public void tickTraveled(AbstractGameClass game) {
		
		if(isAnimationNull()) {
			if(!start){
				setAnimation(game.getAnimationHandler().getAnimation("proj_shoot"));
				start=true;
			} else
			setAnimation(game.getAnimationHandler().getAnimation("proj_air"));
		}
		
		PixelLocation l = this.getLocation();
		//f(x) = ((x-2)^2)*0.5f
//		float addX = (float)Math.sin(Math.toRadians(45+timeFlying))*3f;
//		float addY = (float)Math.cos(Math.toRadians(45+timeFlying*4))*5f;
		float addX = 3f;
		if(direction==2)
			addX=addX*-1;
		float addY = -2*(timeFlying/10f -1.5f) * 0.5f; //f(x) = -(x-3)^2 +5  //f'(x) = 
		PixelLocation newLoc = new PixelLocation(l.x+(int)addX,l.y+(int)addY);
		//Collision
		BlockLocation[] blocks = newLoc.toBlocks();
		World world = ((GameClass)game).getWorld();
		for(int b=0;b<blocks.length;b++)
			if(GameTick.collisonBlock(this, newLoc, blocks[b].getX(), blocks[b].getY(), world.getBlockID(blocks[b].getX(), blocks[b].getY())))
				timeFlying=50;
		this.getLocation().set(newLoc);
		timeFlying++;
		if(timeFlying>50)
			((GameClass)game).getWorld().removeEntity(this);
		
		for(int e=0;e<world.getEntityArray().length;e++) {
			Entity en = world.getEntityArray()[e];
			if(en==null)continue;
			if(!(en instanceof EntitySlime))continue;
			if(PixelLocation.getDistance(getLocation().add(new PixelLocation(4,4)),
					en.getLocation().add(new PixelLocation(8,8))) < 8) {
				world.removeEntity(en);
				game.getSound().playClip("hit",((GameClass)game).getWorld().getPlayer().getLocation(),en.getLocation());
				timeFlying=50;
			}
		}
	}
	
}


