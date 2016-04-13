package game.engine.entity;

import game.engine.main.GameClass;
import game.worlds.WorldTemplate;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.PixelLocation;



public class Key extends Entity{
	
	int near=0;
	public Key(int x, int y) {
		super(x, y);
	}
	
	public String getTextureName() {
			return "key_0";
	}
	
	public void tickTraveled(AbstractGameClass abs) {
		if(isAnimationNull())
			this.setAnimation(abs.getAnimationHandler().getAnimation("key"));
		if(near==2)return;
		GameClass game = ((GameClass)abs); 
		if(PixelLocation.getDistance(getLocation().add(new PixelLocation(4,4)),game.getWorld().getPlayer().getLocation().add(new PixelLocation(8,8))) < 8)	
			near = 1;
		else
			near = 0;
		
		if(near==1) {
			WorldTemplate.KEY = true;
			game.getWorld().removeEntity(this);
			game.getSound().playClip("powerup",((GameClass)game).getWorld().getPlayer().getLocation(),getLocation());
			near = 2;
			
		}
	}
	
}


