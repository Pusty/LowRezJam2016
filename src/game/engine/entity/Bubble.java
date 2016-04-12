package game.engine.entity;

import game.engine.main.GameClass;
import game.engine.world.World;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.PixelLocation;



public class Bubble extends Entity{
	
	int direction;
	public Bubble(int x, int y,int d) {
		super(x, y);
		direction=d;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public String getTextureName() {
		return "tile_15";
	}
	
	int timeFlying=0;
	public void tickTraveled(AbstractGameClass game) {
		PixelLocation l = this.getLocation();
		
		float addX = 1f;
		if(direction==2)
			addX=addX*-1;
		float addY = (float)Math.cos((timeFlying/10f) *3.7f) * 2.5f; //f(x) = -(x-3)^2 +5  //f'(x) = 
		PixelLocation newLoc = new PixelLocation(l.x+(int)addX,l.y+(int)addY);
		//Collision
//		BlockLocation[] blocks = newLoc.toBlocks();
		World world = ((GameClass)game).getWorld();
//		for(int b=0;b<blocks.length;b++)
//			if(GameTick.collisonBlock(this, newLoc, blocks[b].getX(), blocks[b].getY(), world.getBlockID(blocks[b].getX(), blocks[b].getY())))
//				timeFlying=100;
		
		if(!world.getPlayer().inBubble() && PixelLocation.getDistance(getLocation().add(new PixelLocation(4,4)),world.getPlayer().getLocation().add(new PixelLocation(8,8))) < 8) {
			world.getPlayer().setBubble(true);
			game.getSound().playClip("bubble",((GameClass)game).getWorld().getPlayer().getLocation(),getLocation());
			timeFlying=100;
		}
		this.getLocation().set(newLoc);
		timeFlying++;
		if(timeFlying>100) {
			((GameClass)game).getWorld().removeEntity(this);
			game.getSound().playClip("down",((GameClass)game).getWorld().getPlayer().getLocation(),getLocation());
		}
	}
	
}


