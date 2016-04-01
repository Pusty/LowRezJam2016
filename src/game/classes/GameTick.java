package game.classes;

import game.engine.entity.Player;
import game.engine.main.Config;
import game.engine.main.GameClass;
import game.engine.world.Chunk;
import game.engine.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;









import me.pusty.util.AbstractGameClass;
import me.pusty.util.BlockLocation;
import me.pusty.util.PixelLocation;
import me.pusty.util.Tick;

public class GameTick extends Tick{

	public GameTick(AbstractGameClass engine) {
		super(engine);
	}

	

	@Override
	public boolean keyEvent(AbstractGameClass e,int type,int keycode) {
		Player player = ((GameClass)e).getWorld().getPlayer();
		switch(keycode) {
		case Keys.RIGHT:
		case Keys.D:
			if(type==0)
				player.queueDirection(1);
			return true;
		case Keys.LEFT:
		case Keys.A:
			if(type==0)
				player.queueDirection(2);
			return true;
		}
		
		return false;
	}
	
	
	
	@Override
	public void show() {
		
	}

	@Override
	public void tick(AbstractGameClass e, float delta) {
		GameClass game = (GameClass)e;	
		World world = game.getWorld();
		if(ticks>0)
			ticks--;
		
	
		Player player = world.getPlayer();
		player.tickTraveled();
		if(player.getTraveled()<=0 && player.getDirection()!=0) {
			if(!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D) 
					&& !Gdx.input.isKeyPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.RIGHT))
				player.setDirection(0);
		}
		if(player.getTraveled()<=0 && player.getDirection()!=0) {		
			PixelLocation newLoc = player.getLocation().add(player.getAddLocation(true));

			// COLLISION
			if(true) {
				player.getLocation().set(newLoc);
				player.startWalking(true);
			}else
				player.startWalking(false);

		}
		
		
	}

	@Override
	public void mouse(AbstractGameClass engine, int screenX, int screenY,
			int pointer, int button) {
		
	}

	@Override
	public void render(AbstractGameClass e, float delta) {
		SpriteBatch batch  = e.getBatch();
		batch.setColor(0,0,0,1);
		batch.draw(e.getImageHandler().getImage("empty"),0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.setColor(1,1,1,1);
		
//		batch.draw(e.getImageHandler().getImage("tile_0"),0,0);
		GameClass game = (GameClass)e;	
		World world = game.getWorld();
		int currentChunkIndex = -1;
		
		for(int chunkIndex=0;chunkIndex<game.getWorld().getChunkArray().length;chunkIndex++) {
			Chunk c = game.getWorld().getChunkArray()[chunkIndex];
			int blockID = 0;
			BlockLocation blockLocation;
			for (int by = 0; by < c.getSizeY(); by++) {
				for (int bx = 0; bx < c.getSizeX(); bx++) {
					blockID = c.getBlockID(bx, by);
					blockLocation = new BlockLocation(c.getChunkX() * c.getSizeX()
							+ bx, c.getChunkY() * c.getSizeY() + by);
					renderBlock(e,batch,blockLocation.getX(), blockLocation.getY(),blockID);
				}
			}
		}
	}

	private void renderBlock(AbstractGameClass e,SpriteBatch b,int x,int y,int id) {
		if(id<0) return;
		PixelLocation cam = ((GameClass)e).getCamLocation();
		b.draw(e.getImageHandler().getImage("tile_"+id), x*Config.tileSize - cam.getX(), y*Config.tileSize - cam.getY(), Config.tileSize ,Config.tileSize);
	}
}
