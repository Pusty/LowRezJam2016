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
import me.pusty.util.Tick;
import me.pusty.util.Velocity;

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
				player.jump();
			return true;
		case Keys.Q:
			if(type==0)
				player.skillQ(((GameClass)e));
			else if(type==1)
				player.skillUnQ(((GameClass)e));
			return true;
		case Keys.E:
			if(type==0)
				player.skillE(((GameClass)e));
			else if(type==1)
				player.skillUnE(((GameClass)e));
			return true;
		case Keys.NUM_1:
			return true;
		case Keys.NUM_2:
			((GameClass)e).setCameraPoint(1);
			((GameClass)e).setLastCameraPoint(((GameClass)e).getCamPointLocation(0));
			((GameClass)e).cameraTick=50;
			player.setAnimation(e.getAnimationHandler().getAnimation("test_player"));
			player.setSpeachText("TEXT");
			return true;
		case Keys.NUM_3:
			((GameClass)e).setCameraPoint(2);
			((GameClass)e).setLastCameraPoint(((GameClass)e).getCamPointLocation(0));
			((GameClass)e).cameraTick=50;
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
		
		game.cameraTick();
		Player player = world.getPlayer();
		player.tickTraveled(e);
		
		if(player.getDirection()!=0) {
			if(!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D))
				player.setDirection(0);
		}
		if(player.getDirectionVertical()!=0) {
			if(!Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.S))
				player.setDirectionVertical(0);
		}
		
		Velocity velo = player.getVelocity();
		if(velo==null) velo = new Velocity(0,0);
		velo.add(player.getAddLocation(true));
		
		if(!player.getJumping() && !player.isGhost())
			velo.add(new Velocity(0,-2));
		
		PixelLocation newLoc = player.getLocation().addVelocity(velo);
			if(newLoc.x != player.getX() || newLoc.y != player.getY()) {
				BlockLocation[] blocks = get2x2HitBox(newLoc);
				boolean collision = false;
				for(int b=0;b<blocks.length;b++)
					if(collisonBlock(player,newLoc,blocks[b].getX(),blocks[b].getY(),world.getBlockID(blocks[b].getX(),blocks[b].getY()))) {
						collision = true;
						break;
					}
				if(!collision) 
						player.getLocation().set(newLoc);
				else {
					collision = false;
					if(velo.getY() != 0f) {					
						newLoc = player.getLocation().addVelocity(new Velocity(0f,velo.getY()));
						BlockLocation[] blocksY = get2x2HitBox(newLoc);
						for(int b=0;b<blocksY.length;b++)
							if(collisonBlock(player,newLoc,blocksY[b].getX(),blocksY[b].getY(),world.getBlockID(blocksY[b].getX(),blocksY[b].getY()))) {
								collision = true;
								break;
							}
						if(!collision) 
								player.getLocation().set(newLoc);
						else if(velo.getY()<0)
								player.setGround(true);
						
					}
					if((collision || velo.getY() == 0f) && velo.getX() != 0f) {
						newLoc = player.getLocation().addVelocity(new Velocity(velo.getX(),0f));
						BlockLocation[] blocksX = get2x2HitBox(newLoc);
						collision = false;
						for(int b=0;b<blocksX.length;b++)
							if(collisonBlock(player,newLoc,blocksX[b].getX(),blocksX[b].getY(),world.getBlockID(blocksX[b].getX(),blocksX[b].getY()))) {
								collision = true;
								break;
							}
						if(!collision) 
								player.getLocation().set(newLoc);
					}
					
				}
			}
		
		
			
			
			for(int entityIndex=0;entityIndex<world.getEntityArray().length;entityIndex++) {
				Entity entity = world.getEntityArray()[entityIndex];
				if(entity==null)continue;
				entity.tickTraveled(e);
			}
		
	}
	
	public static boolean collisonBlock(Entity entity,PixelLocation loc,int x,int y,int id) {
		if(id==-1) return false;
		switch(id) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 10:
		case 11:
		case 12:
		case 13:
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
		case 20:
		case 21:
		case 22:
		case 23:
		case 24:
		case 25:
		case 28:
		case 29:
			
			return false;
		}
		if(id == 19) { //Mark
		
		}
		if(id == 9) { //Platform
			if(!(entity instanceof Player))
				return false;
			boolean ret = entity.getY()-6>y*Config.tileSize;			
			return ret;
		}
		
		if(id == 14 || id == 30) { //Water
			
		}
		
		if(id==1) {
			if(entity instanceof Player) {
				Player player = (Player)entity;
				if(player.isGhost()) {
					player.setGhostUsed(true);
					return false;
				}
			}
		}
		
		if(entity instanceof Player) {
			Player player = (Player)entity;
			if(player.isGhost()) {
				player.setGhostUsed(true);
				return false;
			}
		}
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
	
	

	@Override
	public void mouse(AbstractGameClass engine, int screenX, int screenY,
			int pointer, int button) {
		
	}

	@Override
	public void render(AbstractGameClass e, float delta) {
		SpriteBatch batch  = e.getBatch();
//		batch.setColor(0,0,0,1);
		batch.setColor(32f/255f,33f/255f,48f/255f,1f);
		batch.draw(e.getImageHandler().getImage("empty"),0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.setColor(1,1,1,1);
		
//		batch.draw(e.getImageHandler().getImage("tile_0"),0,0);
		GameClass game = (GameClass)e;	
		World world = game.getWorld();
//		int currentChunkIndex = -1;
		
		BlockLocation[] ghostBlocks = null;
		if(world.getPlayer().isGhost()) {
			ghostBlocks = GameTick.get2x2HitBox(world.getPlayer().getLocation());
		}
		
		for(int chunkIndex=0;chunkIndex<game.getWorld().getChunkArray().length;chunkIndex++) {
			Chunk c = game.getWorld().getChunkArray()[chunkIndex];
			int blockID = 0;
			BlockLocation blockLocation;
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
			for (int by = 0; by < c.getSizeY(); by++) {
				for (int bx = 0; bx < c.getSizeX(); bx++) {
					blockID =  c.getBlockID(bx, by);
					blockLocation = new BlockLocation(c.getChunkX() * c.getSizeX()
							+ bx, c.getChunkY() * c.getSizeY() + by);
					if(blockID!=1 || ghostBlocks==null)
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
		
	}

	private void renderBlock(AbstractGameClass e,SpriteBatch b,int x,int y,int id) {
		if(id<0) return;
		PixelLocation cam = ((GameClass)e).getCamLocation();
		PixelLocation move = new PixelLocation( x*Config.tileSize - cam.getX(), y*Config.tileSize - cam.getY());
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
