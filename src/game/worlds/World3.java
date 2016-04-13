package game.worlds;

import game.engine.entity.Entity;
import game.engine.entity.Player;
import game.engine.entity.Reaper;
import game.engine.main.GameClass;
import game.engine.world.World;
import game.engine.world.WorldLoader;
import me.pusty.util.PixelLocation;

public class World3 extends WorldTemplate{

	@Override
	public PixelLocation getCamPointLocation(GameClass game, int point) {
		PixelLocation goal = new PixelLocation(0,0);
		World world = game.getWorld();
		
		if(point==1) {
		for(int e=0;e<world.getEntityArray().length;e++) {
			Entity en = world.getEntityArray()[e];
			if(en==null)continue;
			if(en instanceof Reaper)
				goal =  en.getLocation().add(new PixelLocation(-28 + 20,-28 + 20));
			}
		}
		return goal;
	}

	@Override
	public String getWorldName() {
		return "world3";
	}

	@Override
	public String getFolderName() {
		return "world3";
	}

	@Override
	public void loadWorld(GameClass game) {
		game.setWorld(WorldLoader.loadWorldComplete(game, getWorldName(),getFolderName()));
		game.getWorld().setPlayer(new Player(8*10,8*116));
		
		game.getWorld().addEntity(new Reaper(8*20,8*116));
		
		game.setCameraPoint(1);
		game.setLastCameraPoint(game.getCamPointLocation(0));
		game.cameraTick=50;
	
		WorldTemplate.BUBLE_BLASTER=false;
		WorldTemplate.KEY=false;
		WorldTemplate.COMBINDED=0;
		WorldTemplate.BRIDGE=false;
	}

	@Override
	public void portal(GameClass game) {
		game.setTemplate(WorldTemplate.WORLD1);
		game.getTemplate().loadWorld(game);
	}
}
