package game.classes;

import game.engine.entity.Entity;
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
import me.pusty.util.RawAnimation;
import me.pusty.util.Tick;

public class GameTick extends Tick{

	public GameTick(AbstractGameClass engine) {
		super(engine);
	}

	

	@Override
	public boolean keyEvent(AbstractGameClass e,int type,int keycode) {
		Player player = ((GameClass)e).getWorld().getPlayer();
		switch(keycode) {
		case Keys.D:
			if(type==0)
				player.queueDirection(1);
			return true;
		case Keys.A:
			if(type==0)
				player.queueDirection(2);
			return true;
		case Keys.W:
			if(type==0)
				player.up();
			else if(type==1)
				if(player.getDirectionVertical()==1)
					player.setDirectionVertical(0);
			return true;
		case Keys.S:
			if(type==0)
				player.down();
			else if(type==1)
				if(player.getDirectionVertical()==-1)
					player.setDirectionVertical(0);
			return true;
		case Keys.SPACE:
			if(type==0)
				player.jump(e);
			return true;
		case Keys.LEFT:
			if(type==0)
				player.skillQ(((GameClass)e));
			else if(type==1)
				player.skillUnQ(((GameClass)e));
			return true;
		case Keys.RIGHT:
			if(type==0)
				player.skillE(((GameClass)e));
			else if(type==1)
				player.skillUnE(((GameClass)e));
			return true;
		case Keys.E:
			if(type==0)
				player.use(((GameClass)e));
			if(type==1)
				player.setUse(false);
			return true;
		case Keys.NUM_1:
			player.getLocation().set(new BlockLocation(50,114).toPixelLocation());
			return true;
		case Keys.NUM_2:
			player.getLocation().set(new BlockLocation(76,118).toPixelLocation());
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
		if(ticks==0)
			ticks=50;
		
		Player player = world.getPlayer();
		
		
		if(player.getHealth() <=0 && game.getTimeout()==-2) {
			game.setTimeRunning(true);
			game.getTemplate().loadWorld(game);
			game.setTimeout(-1);
		}
		
		game.cameraTick();

		
		if(player.getHealth()<=0 && game.getTimeout()==-1) {
			game.setTimeRunning(false);
			game.setTimeout(10);
			game.getSound().playClip("hit",null,null);
		}

		player.tickTraveled(e);
			
			for(int entityIndex=0;entityIndex<world.getEntityArray().length;entityIndex++) {
				Entity entity = world.getEntityArray()[entityIndex];
				if(entity==null)continue;
				entity.tickTraveled(e);
			}
		
	}
	
	public static boolean collisonBlock(Entity entity,PixelLocation loc,int x,int y,int id) {
		if(id==-1) return false;
		if(id==0) {
			if(entity instanceof Player) {
				Player player = (Player)entity;
				player.setDirection(1337);
			}
			return false;
		}
		if(id >= 0 && id<=7)return false;
		if(id>=10 && id<=29)return false;
		
		if(id >= 50 && id <= 57) return false;
		if(id == 19) { //Mark
		
		}
		if(id == 8 || id == 9) { //Platform
			if(!(entity instanceof Player))
				return false;
			boolean ret = entity.getY()-6>y*Config.tileSize;
			if(((Player)entity).getDirectionVertical()==-1)
				ret = false;
			return ret;
		}
		
		if((id ==62)) { //Water
			if(entity instanceof Player &&  entity.getY()<(y+1)*Config.tileSize) {
				Player player = (Player)entity;
				player.setWater(true);
			}
			return false;
		}
		
		if(id==384 || id ==432) {
			if(entity instanceof Player) {
				Player player = (Player)entity;
				if(player.isGhost()) {
					player.setGhostUsed(true);
					return false;
				}
			}
		}
		/*
		if(entity instanceof Player) {
			Player player = (Player)entity;
			if(player.isGhost()) {
				player.setGhostUsed(true);
				return false;
			}
		}*/
		return true;
	}
	
	public static BlockLocation[] get2x2HitBox(PixelLocation loc) {
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
	/** a=x , b=y*/
	public static BlockLocation[] getAxBHitBox(PixelLocation loc,int a,int b) {
		BlockLocation[][] bl = new BlockLocation[a*b][];
		int adding=0;
		int indexU=0;
		for(int aI=0;aI<a;aI++)
			for(int bI=0;bI<a;bI++) {
				bl[indexU] = loc.add(new PixelLocation(Config.tileSize*aI,Config.tileSize*bI)).toBlocks();
				adding=adding+bl[indexU].length;
				indexU++;
			}
		BlockLocation[] result = new BlockLocation[adding];
		int index = 0;
		for(int u=0;u<bl.length;u++)
			for(int i=0;i<bl[u].length;i++) {
				result[index] = bl[u][i];
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
		batch.setColor(32f/255f,33f/255f,48f/255f,1f);
		batch.draw(e.getImageHandler().getImage("empty"),0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.setColor(1,1,1,1);
		
		GameClass game = (GameClass)e;	
		World world = game.getWorld();
		
		BlockLocation[] ghostBlocks = null;
		if(world.getPlayer().isGhost()) {
			ghostBlocks = GameTick.get2x2HitBox(world.getPlayer().getLocation());
		}
		
//		System.out.println(game.getWorld().getPlayer().getLocation().toBlock());
		
		for(int chunkIndex=0;chunkIndex<game.getWorld().getChunkArray().length;chunkIndex++) {
			Chunk c = game.getWorld().getChunkArray()[chunkIndex];
			int blockID = 0;
			BlockLocation blockLocation;
			if(c.isEmptyBack())continue;
			if(PixelLocation.getDistance(new BlockLocation(c.getChunkX() * c.getSizeX()
					+ 8, c.getChunkY() * c.getSizeY() + 8).toPixelLocation(),game.getCamLocation()) > 8*8*3)continue;
			for (int by = 0; by < c.getSizeY(); by++) {
				for (int bx = 0; bx < c.getSizeX(); bx++) {
					blockID =  c.getBlockIDBack(bx, by);
					blockLocation = new BlockLocation(c.getChunkX() * c.getSizeX()
							+ bx, c.getChunkY() * c.getSizeY() + by);
					if(blockID!=1)
						renderBlock(e,batch,blockLocation.getX(), blockLocation.getY(),blockID);
				}
			}
		}
		
		for(int chunkIndex=0;chunkIndex<game.getWorld().getChunkArray().length;chunkIndex++) {
			Chunk c = game.getWorld().getChunkArray()[chunkIndex];
			int blockID = 0;
			BlockLocation blockLocation;
			if(c.isEmptyWorld())continue;
			if(PixelLocation.getDistance(new BlockLocation(c.getChunkX() * c.getSizeX()
					+ 8, c.getChunkY() * c.getSizeY() + 8).toPixelLocation(),game.getCamLocation()) > 8*8*3)continue;
			for (int by = 0; by < c.getSizeY(); by++) {
				for (int bx = 0; bx < c.getSizeX(); bx++) {
					blockID =  c.getBlockID(bx, by);
					blockLocation = new BlockLocation(c.getChunkX() * c.getSizeX()
							+ bx, c.getChunkY() * c.getSizeY() + by);
					//144,128
					if((blockID!=144 && blockID !=128) || ghostBlocks==null)
						renderBlock(e,batch,blockLocation.getX(), blockLocation.getY(),blockID);
					else
						renderGhostBlock(e,batch,blockLocation.getX(), blockLocation.getY(),blockID,ghostBlocks);
				}
			}
		}
		
		for(int entityIndex=0;entityIndex<world.getEntityArray().length;entityIndex++) {
			Entity entity = world.getEntityArray()[entityIndex];
			if(entity==null)continue;
			entity.renderTick(e, entityIndex);
			entity.render(e, batch);
			entity.renderExtra(e, batch);
		}
		world.getPlayer().renderTick(e, -1);
		world.getPlayer().render(e, batch);
		world.getPlayer().renderExtra(e, batch);
			
		for(int chunkIndex=0;chunkIndex<game.getWorld().getChunkArray().length;chunkIndex++) {
			Chunk c = game.getWorld().getChunkArray()[chunkIndex];
			int blockID = 0;
			BlockLocation blockLocation;
			if(c.isEmptyFore())continue;
			if(PixelLocation.getDistance(new BlockLocation(c.getChunkX() * c.getSizeX()
					+ 8, c.getChunkY() * c.getSizeY() + 8).toPixelLocation(),game.getCamLocation()) > 8*8*3)continue;
			for (int by = 0; by < c.getSizeY(); by++) {
				for (int bx = 0; bx < c.getSizeX(); bx++) {
					blockID =  c.getBlockIDFore(bx, by);
					blockLocation = new BlockLocation(c.getChunkX() * c.getSizeX()
							+ bx, c.getChunkY() * c.getSizeY() + by);
					if(blockID!=1)
						renderBlock(e,batch,blockLocation.getX(), blockLocation.getY(),blockID);
				}
			}
		}
		
		for(int i=0;i<8;i++)
			batch.draw(e.getImageHandler().getImage("hud_"+i), i*8, 0);
	
	}

	private void renderBlock(AbstractGameClass e,SpriteBatch b,int x,int y,int id) {
		if(id<0) return;
		PixelLocation cam = ((GameClass)e).getCamLocation();
		PixelLocation move = new PixelLocation( x*Config.tileSize - cam.getX(), y*Config.tileSize - cam.getY());
		if(id==12 || id==60) { //Animated block
			RawAnimation an = e.getAnimationHandler().getAnimation("tile_"+id);
			int frame = ((int)Math.floor(((float)(50-ticks)/50)*an.getFrameDelays().length));
			String textureName = an.getImage(frame);
			
			b.draw(e.getImageHandler().getImage(textureName), move.getX(), move.getY());
		}else
			b.draw(e.getImageHandler().getImage("tile_"+id), move.getX(), move.getY());
	}
	
	private void renderGhostBlock(AbstractGameClass e,SpriteBatch b,int x,int y,int id,BlockLocation[] ghosts) {
		if(id<0) return;
		PixelLocation cam = ((GameClass)e).getCamLocation();
		PixelLocation move = new PixelLocation( x*Config.tileSize - cam.getX(), y*Config.tileSize - cam.getY());
		
		for(int index=0;index<ghosts.length;index++)
			if(ghosts[index].getX()==x && ghosts[index].getY()==y)
				id = 0;
		
		b.draw(e.getImageHandler().getImage("tile_"+id), move.getX(), move.getY());
	}
}
