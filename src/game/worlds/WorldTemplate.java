package game.worlds;

import game.engine.main.GameClass;
import me.pusty.util.PixelLocation;

public abstract class WorldTemplate {
	
	public static final WorldTemplate WORLD1 = new World2();
	public static final WorldTemplate WORLD2 = new World2();
	public static final WorldTemplate WORLD3 = new World3();
	
	public static boolean BUBLE_BLASTER = false;
	public static boolean KEY = false;
	public static int COMBINDED = 0;
	public static boolean BRIDGE = false;
	
	
	public abstract PixelLocation getCamPointLocation(GameClass game,int point);
	public abstract String getWorldName();
	public abstract String getFolderName();
	public abstract void loadWorld(GameClass game);
	public abstract void portal(GameClass game);
}
