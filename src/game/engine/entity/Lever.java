package game.engine.entity;

import game.engine.main.GameClass;
import game.worlds.WorldTemplate;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.PixelLocation;



public class Lever extends Entity{
	
	int leverIndex = 0;
	public Lever(int x, int y,int index) {
		super(x, y);
		leverIndex=index;
	}
	
	public String getTextureName() {
		if(!open)
			return "lever_0";
		else
			return "lever_2";
	}
	

	
	boolean open=false;
	public void tickTraveled(AbstractGameClass abs) {
		GameClass game = ((GameClass)abs); 
		
		if(PixelLocation.getDistance(getLocation().add(new PixelLocation(4,4)),game.getWorld().getPlayer().getLocation().add(new PixelLocation(8,8))) < 12)	
		{
			if(game.getWorld().getPlayer().getUse()) {
				open=!open;
				game.getWorld().getPlayer().setUse(false);
				if(open) {
					if(leverIndex==1) 
						WorldTemplate.BUBLE_BLASTER=true;
					else if(leverIndex==2) 
						WorldTemplate.COMBINDED=WorldTemplate.COMBINDED+1;
					else if(leverIndex==3) 
						WorldTemplate.COMBINDED=WorldTemplate.COMBINDED+10;
					else if(leverIndex==4) 
						WorldTemplate.COMBINDED=WorldTemplate.COMBINDED+100;
					else if(leverIndex==5) 
						WorldTemplate.BRIDGE=true;
					setAnimation(abs.getAnimationHandler().getAnimation("lever_on"));
					game.getSound().playClip("down",((GameClass)game).getWorld().getPlayer().getLocation(),getLocation());
				}else{
					if(leverIndex==1) 
						WorldTemplate.BUBLE_BLASTER=false;
					else if(leverIndex==2) 
						WorldTemplate.COMBINDED=WorldTemplate.COMBINDED-1;
					else if(leverIndex==3) 
						WorldTemplate.COMBINDED=WorldTemplate.COMBINDED-10;
					else if(leverIndex==4) 
						WorldTemplate.COMBINDED=WorldTemplate.COMBINDED-100;
					else if(leverIndex==5) 
						WorldTemplate.BRIDGE=false;
					setAnimation(abs.getAnimationHandler().getAnimation("lever_off"));
					game.getSound().playClip("down",((GameClass)game).getWorld().getPlayer().getLocation(),getLocation());
				}
			}
		}
		

		
	}
	
}
