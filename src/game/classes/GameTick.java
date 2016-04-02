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
		case Keys.SPACE:
			if(type==0)
				player.jump();
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
		PixelLocation newLoc = player.getLocation().add(player.getAddLocation(true));
		if(true) {
			if(newLoc.x != player.getX() || newLoc.y != player.getY()) {
				BlockLocation[] blocks = get2x2HitBox(newLoc);
				boolean collision = false;
				for(int b=0;b<blocks.length;b++)
					if(world.getBlockID(blocks[b].getX(),blocks[b].getY())!=-1)
						collision = true;
				if(!collision) 
						player.getLocation().set(newLoc);
				else
					player.setJumping(false);
			}
			{
				PixelLocation newLocGravity = player.getLocation();
				if(!player.getJumping())
					newLocGravity = newLocGravity.add(new PixelLocation(0,-1));
				BlockLocation[] blocks = get2x2HitBox(newLocGravity);
				boolean collision = false;
				for(int b=0;b<blocks.length;b++)
					if(world.getBlockID(blocks[b].getX(),blocks[b].getY())!=-1)
						collision = true;
				if(!collision)
					player.getLocation().set(newLocGravity);
				else
					player.setGround(true);
			}
		}
		
		
	}
	
	private static BlockLocation[] get2x2HitBox(PixelLocation loc) {
		BlockLocation[][] b = new BlockLocation[4][];
		b[0] = loc.toBlocks();
		b[1] = loc.add(new PixelLocation(Config.tileSize,0)).toBlocks();
		b[2] = loc.add(new PixelLocation(0,Config.tileSize)).toBlocks();
		b[3] = loc.add(new PixelLocation(Config.tileSize,Config.tileSize)).toBlocks();
		BlockLocation[] result = new BlockLocation[b[0].length + b[1].length + b[2].length + b[3].length];
		int index = 0;
		for(int a=0;a<b.length;a++)
			for(int i=0;i<b[a].length;i++) {
				result[index] = b[a][i];
				index++;
			}
		return result;
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
//		int currentChunkIndex = -1;
		
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
		
		world.getPlayer().render(e, batch);
	}

	private void renderBlock(AbstractGameClass e,SpriteBatch b,int x,int y,int id) {
		if(id<0) return;
		PixelLocation cam = ((GameClass)e).getCamLocation();
		b.draw(e.getImageHandler().getImage("tile_"+id), x*Config.tileSize - cam.getX(), y*Config.tileSize - cam.getY(), Config.tileSize ,Config.tileSize);
	}
}
