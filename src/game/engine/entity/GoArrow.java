package game.engine.entity;

import game.engine.main.GameClass;
import me.pusty.util.AbstractGameClass;



public class GoArrow extends Entity{
	
	boolean passed=false;
	public GoArrow(int x, int y) {
		super(x, y);
	}
	
	public String getTextureName() {
		if(!passed)
			return "goarrow_0";
		else
			return "goarrow_1";
	}
	
	public void tickTraveled(AbstractGameClass abs) {
		GameClass game = ((GameClass)abs); 
		if(!passed && game.getWorld().getPlayer().getX() > this.getX()) {
			passed=true;
		}
		
	}
	
}


