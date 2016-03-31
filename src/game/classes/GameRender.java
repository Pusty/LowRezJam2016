package game.classes;

import game.engine.main.Config;
import game.engine.main.GameClass;
import game.engine.world.Chunk;
import game.engine.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;




import me.pusty.util.AbstractGameClass;
import me.pusty.util.BlockLocation;
import me.pusty.util.Tick;

public class GameRender extends Tick{

	public GameRender(AbstractGameClass engine) {
		super(engine);
	}

	@Override
	public void show() {
		
	}

	@Override
	public void tick(AbstractGameClass engine, float delta) {
		
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
//		y = y - 57;
//		x = x - 1;
		b.draw(e.getImageHandler().getImage("tile_"+id), x*Config.tileSize, y*Config.tileSize, Config.tileSize,Config.tileSize);
	}
}
