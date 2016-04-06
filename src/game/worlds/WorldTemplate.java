package game.worlds;

import game.engine.main.GameClass;
import me.pusty.util.PixelLocation;

public abstract class WorldTemplate {
	
	public abstract PixelLocation getCamPointLocation(GameClass game,int point);
}
