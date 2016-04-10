package game.worlds;

import game.engine.entity.Bridge;
import game.engine.entity.BubbleSpreader;
import game.engine.entity.EntitySlime;
import game.engine.entity.GoArrow;
import game.engine.entity.Lever;
import game.engine.entity.Mark;
import game.engine.entity.Player;
import game.engine.main.GameClass;
import game.engine.world.WorldLoader;
import me.pusty.util.BlockLocation;
import me.pusty.util.PixelLocation;

public class World1 extends WorldTemplate{

	@Override
	public PixelLocation getCamPointLocation(GameClass game, int point) {
		PixelLocation goal = new PixelLocation(0,0);
		if(point==1) {
			goal = (new BlockLocation(20,121).toPixelLocation().add(new PixelLocation(-28,-28)));
		}else if(point==2) {
			goal =  (new BlockLocation(64,111).toPixelLocation().add(new PixelLocation(-28,-28)));
		}else if(point==3) {
			goal =  (new BlockLocation(105,120).toPixelLocation().add(new PixelLocation(-28,-28)));
		}
		return goal;
	}

	@Override
	public String getWorldName() {
		return "world1";
	}

	@Override
	public String getFolderName() {
		return "world1";
	}

	@Override
	public void loadWorld(GameClass game) {
		game.setWorld(WorldLoader.loadWorldComplete(game, getWorldName(),getFolderName()));
		game.getWorld().setPlayer(new Player(8,8*124));
		game.getWorld().addEntity(new EntitySlime(8*20,8*121));
		game.getWorld().addEntity(new EntitySlime(8*97,8*119));
		game.getWorld().addEntity(new BubbleSpreader(8*62,8*110));
		for(int i=0;i<7;i++)
			game.getWorld().addEntity(new Bridge((24+i)*8,120*8,false));
		game.getWorld().addEntity(new Lever(65*8 + 4,8*111,1));
		
		game.getWorld().addEntity(new GoArrow(5*8,8*122));
		game.getWorld().addEntity(new Mark(10*8,8*122,1));
		game.getWorld().addEntity(new GoArrow(21*8,8*122));
		game.getWorld().addEntity(new GoArrow(35*8,8*122));
		game.getWorld().addEntity(new Mark(37*8,8*111,2));
		game.getWorld().addEntity(new Mark(84*8,8*120,3));
		
		WorldTemplate.BUBLE_BLASTER=false;
		WorldTemplate.KEY=false;
		WorldTemplate.COMBINDED=0;
		WorldTemplate.BRIDGE=false;
	}

	@Override
	public void portal(GameClass game) {
		game.setTemplate(WorldTemplate.WORLD2);
		game.getTemplate().loadWorld(game);
	}
	
}
