package game.worlds;

import game.engine.entity.Bridge;
import game.engine.entity.Door;
import game.engine.entity.Key;
import game.engine.entity.Lever;
import game.engine.entity.Mark;
import game.engine.entity.Player;
import game.engine.main.GameClass;
import game.engine.world.WorldLoader;
import me.pusty.util.BlockLocation;
import me.pusty.util.PixelLocation;

public class World2 extends WorldTemplate{

	@Override
	public PixelLocation getCamPointLocation(GameClass game, int point) {
		PixelLocation goal = new PixelLocation(0,0);
		if(point==1) {
			goal = (new BlockLocation(30,111).toPixelLocation().add(new PixelLocation(-28,-28)));
		}else if(point==2) {
			goal =  (new BlockLocation(80,118).toPixelLocation().add(new PixelLocation(-28,-28)));
		}
		return goal;
	}

	@Override
	public String getWorldName() {
		return "world2";
	}

	@Override
	public String getFolderName() {
		return "world2";
	}

	@Override
	public void loadWorld(GameClass game) {
		game.setWorld(WorldLoader.loadWorldComplete(game, getWorldName(),getFolderName()));
		game.getWorld().setPlayer(new Player(8*4,8*121));
		game.getWorld().addEntity(new Key(8*30,8*112));
		game.getWorld().addEntity(new Mark(15*8,8*112,1));
		game.getWorld().addEntity(new Door(19*8,8*100));
		game.getWorld().addEntity(new Lever(51*8,8*114,2));
		game.getWorld().addEntity(new Lever(55*8,8*114,3));
		game.getWorld().addEntity(new Lever(59*8,8*114,4));
		game.getWorld().addEntity(new Mark(62*8,8*117,2));
		for(int i=0;i<21;i++)
		game.getWorld().addEntity(new Bridge((77+i)*8,8*117,true));
		game.getWorld().addEntity(new Lever(76*8,8*118,5));

		WorldTemplate.BUBLE_BLASTER=false;
		WorldTemplate.KEY=false;
		WorldTemplate.COMBINDED=0;
		WorldTemplate.BRIDGE=false;
	}
	
	@Override
	public void portal(GameClass game) {
		game.setTemplate(WorldTemplate.WORLD3);
		game.getTemplate().loadWorld(game);
	}
}
