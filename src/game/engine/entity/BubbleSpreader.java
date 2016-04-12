package game.engine.entity;

import game.engine.main.GameClass;
import game.worlds.WorldTemplate;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.PixelLocation;



public class BubbleSpreader extends Entity{
	
	public BubbleSpreader(int x, int y) {
		super(x, y);
	}
	
	public String getTextureName() {
		return "bubbleshooter_0";
	}
	
	int timeFlying=0;
	public void tickTraveled(AbstractGameClass abs) {
		if(!WorldTemplate.BUBLE_BLASTER) return;
		if(isAnimationNull()) {
				setAnimation(abs.getAnimationHandler().getAnimation("bubbleshooter"));
		}
		PixelLocation l = this.getLocation();
		GameClass game = ((GameClass)abs); 
		if(timeFlying>0)timeFlying--;
		if(timeFlying==0) {
			timeFlying=50;
			
			game.getWorld().addEntity(new Bubble(l.getX()-4,l.getY(),2));
			game.getSound().playClip("shot",((GameClass)game).getWorld().getPlayer().getLocation(),getLocation());
		}
		
	}
	
}


