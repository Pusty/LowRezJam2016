package game.engine.entity;

import game.engine.main.Config;
import game.engine.main.GameClass;
import game.worlds.WorldTemplate;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.BlockLocation;



public class Bridge extends Entity{
	
	boolean openAble=false;
	public Bridge(int x, int y,boolean open) {
		super(x, y);
		openAble=open;
	}
	
	public String getTextureName() {
		if(!open)
			return "bridge_0";
		else
			return "bridge_3";
	}
	
	int timeFlying=0;
	
	boolean open=true;
	public void tickTraveled(AbstractGameClass abs) {
		GameClass game = ((GameClass)abs); 
		BlockLocation l = getLocation().toBlock();	
		if(openAble && (!WorldTemplate.BRIDGE ||  WorldTemplate.COMBINDED==0)) { open=true; game.getWorld().setBlockID(l.x,l.y,10); return; }
		if(timeFlying>0)timeFlying--;
		if(timeFlying==0) {
			if(openAble)
				if(WorldTemplate.COMBINDED > 100)
					timeFlying=(int)((Math.abs(Math.tan((getX()-76*Config.tileSize)/Config.tileSize)*50)));
				else if(WorldTemplate.COMBINDED <= 10 && WorldTemplate.COMBINDED!=0)
					timeFlying=(int)((getX()-76*Config.tileSize))+25;
				else if(WorldTemplate.COMBINDED == 11)
					timeFlying=(int)((getX()-76*Config.tileSize)/Config.tileSize)+25;
				else
					timeFlying=(int)Math.max(Math.abs((Math.sin((getX())) * 50)),Math.abs((Math.cos((getX())) * 50)));
			else
				timeFlying--;
			open=!open;
			if(open) {
				game.getWorld().setBlockID(l.x,l.y,10);
				setAnimation(abs.getAnimationHandler().getAnimation("bridge_open"));
				game.getSound().playClip("down",((GameClass)game).getWorld().getPlayer().getLocation(),getLocation());
			}else{
				game.getWorld().setBlockID(l.x,l.y,9);
				setAnimation(abs.getAnimationHandler().getAnimation("bridge_close"));
				game.getSound().playClip("down",((GameClass)game).getWorld().getPlayer().getLocation(),getLocation());
			}
		}
	}
	
}


