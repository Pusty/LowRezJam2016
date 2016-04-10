package game.engine.entity;

import game.engine.main.GameClass;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.PixelLocation;



public class Mark extends Entity{
	
	int near=0;
	int index=-1;
	public Mark(int x, int y,int index) {
		super(x, y);
		this.index = index;
	}
	
	public String getTextureName() {
		if(near==1)
			return "exclamation_1";
		else
			return "exclamation_0";
	}
	
	public void tickTraveled(AbstractGameClass abs) {
		if(near==2)return;
		GameClass game = ((GameClass)abs); 
		if(PixelLocation.getDistance(getLocation().add(new PixelLocation(4,4)),game.getWorld().getPlayer().getLocation().add(new PixelLocation(8,8))) < 8)	
			near = 1;
		else
			near = 0;
		
//		neari=2;
		
		if(near==1) {
			game.setCameraPoint(index);
			game.setLastCameraPoint(game.getCamPointLocation(0));
			game.cameraTick=50;
			near = 2;
			
		}
	}
	
}


