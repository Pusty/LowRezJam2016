package game.worlds;

import game.engine.main.GameClass;
import me.pusty.util.BlockLocation;
import me.pusty.util.PixelLocation;

public class World1 extends WorldTemplate{
	public PixelLocation getCamPointLocation(GameClass game,int point) {
		PixelLocation goal = new PixelLocation(0,0);
		if(point==1) {
			goal = (new BlockLocation(5,5).toPixelLocation().add(new PixelLocation(-32 + 4,-32 + 4)));
		}else if(point==2) {
			goal =  (new BlockLocation(0,0).toPixelLocation().add(new PixelLocation(-32 + 4,-32 + 4)));
		}else if(point==0 || point==-1)
			goal = game.getWorld().getPlayer().getLocation().add(new PixelLocation(-26,-26));
		
		return goal;
	}
}
