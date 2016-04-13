package game.engine.entity;

import game.engine.main.GameClass;
import game.worlds.WorldTemplate;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.BlockLocation;
import me.pusty.util.PixelLocation;



public class Door extends Entity{
	int index=0;
	public Door(int x, int y,int ind) {
		super(x, y);
		index=ind;
	}
	
	public String getTextureName() {
		if(!open)
			return "door_0";
		else
			return "door_7";
	}
	

	boolean open=true;
	
	boolean changed=true;
	public void tickTraveled(AbstractGameClass abs) {
		GameClass game = ((GameClass)abs); 
		
		if(index==0)
			if(changed==false && open == false &&  WorldTemplate.KEY==true &&
				PixelLocation.getDistance(getLocation().add(new PixelLocation(4,4)),game.getWorld().getPlayer().getLocation().add(new PixelLocation(8,8))) < 16)
				changed=true;
		
		if(index==1)
			if(changed==false && open == false &&  WorldTemplate.COMBINDED==101)
				changed=true;
		
		if(changed==true) {
			open=!open;
			changed=false;
			BlockLocation l = getLocation().toBlock();	
			if(open) {
				game.getWorld().setBlockID(l.x,l.y,10);
				game.getWorld().setBlockID(l.x,l.y+1,10);
				game.getWorld().setBlockID(l.x,l.y+2,10);
				setAnimation(abs.getAnimationHandler().getAnimation("door_open"));
				game.getSound().playClip("shot",((GameClass)game).getWorld().getPlayer().getLocation(),getLocation());
			}else{
				game.getWorld().setBlockID(l.x,l.y,100);
				game.getWorld().setBlockID(l.x,l.y+1,100);
				game.getWorld().setBlockID(l.x,l.y+2,100);
				setAnimation(abs.getAnimationHandler().getAnimation("door_close"));
				game.getSound().playClip("shot",((GameClass)game).getWorld().getPlayer().getLocation(),getLocation());
			}
		}
	}
	
}


