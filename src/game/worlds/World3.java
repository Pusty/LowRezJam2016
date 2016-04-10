package game.worlds;

import game.engine.entity.Player;
import game.engine.entity.Reaper;
import game.engine.main.GameClass;
import game.engine.world.WorldLoader;
import me.pusty.util.PixelLocation;

public class World3 extends WorldTemplate{

	@Override
	public PixelLocation getCamPointLocation(GameClass game, int point) {
		PixelLocation goal = new PixelLocation(0,0);

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
		game.getWorld().setPlayer(new Player(8*10,8*118));
		
		game.getWorld().addEntity(new Reaper(8*20,8*118));
	
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
