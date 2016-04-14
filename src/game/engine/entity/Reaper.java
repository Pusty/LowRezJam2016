package game.engine.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import game.classes.GameTick;
import game.engine.main.GameClass;
import game.engine.world.World;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.BlockLocation;
import me.pusty.util.PixelLocation;
import me.pusty.util.Velocity;

public class Reaper extends EntityLiving {

	int health = 5;

	public Reaper(int x, int z) {
		super(x, z);
		this.setDirection(0);
		inWater = false;
	}

	public String getTextureName() { 
			return "reaper";
	}
	public String getMovingTexture() {
		return null;
	}
	

	public boolean hasDirections() { return false; }
	
	private void sprayAttack(GameClass g,World w) {
		PixelLocation loc = w.getPlayer().getLocation().clone();
		w.addEntity(new ProjectileReaper(getX() + 20, getY() + 18, this.getLastDirection(),loc));
		w.addEntity(new ProjectileReaper(getX() + 20, getY() + 20, this.getLastDirection(),loc));
		w.addEntity(new ProjectileReaper(getX() + 20, getY() + 22, this.getLastDirection(),loc));
	}
	
	boolean shown=false;
	
	boolean dir=false;
	int tickRunning=0;
	boolean moving=false;
	public void tickTraveled(AbstractGameClass a) {
		GameClass game = ((GameClass)a);
		super.tickTraveled(game);
	
		World world = game.getWorld();
		
		if(tickRunning>0)
			tickRunning--;
		
		if(health<=0 && shown) {
			world.removeEntity(this);
			game.getSound().playClip("ghost", world.getPlayer().getLocation(), this.getLocation());
			game.initStartScreen();
		}
		
		if(this.health<=0 &&! shown) {
			this.setAnimation(a.getAnimationHandler().getAnimation("reaper_death"));
			
			BlockLocation l1 = this.getLocation().toBlock();
			game.getWorld().setBlockID(l1.getX()+3, l1.getY()+3, 12);
			game.getWorld().setBlockID(l1.getX()+2, l1.getY()+1, 60);
			game.getWorld().setBlockID(l1.getX()+1, l1.getY()+4, 60);
			game.getWorld().setBlockID(l1.getX()+5, l1.getY()+2, 12);
			game.setCameraPoint(1);
			game.setLastCameraPoint(game.getCamPointLocation(0));
			game.cameraTick=50;
			shown=true;
		}

		
		for(int e=0;e<world.getEntityArray().length;e++) {
			Entity en = world.getEntityArray()[e];
			if(en==null)continue;
			if(this.health<=0) {
				if(en instanceof Projectile || en instanceof ProjectileReaper)
					world.removeEntity(en);
			}
			if(!(en instanceof Projectile))continue;
			if(PixelLocation.getDistance(getLocation().add(new PixelLocation(20,20)),
					en.getLocation().add(new PixelLocation(4,4))) < 14) {
				Projectile proj = (Projectile)en;
				world.removeEntity(proj);
				game.getSound().playClip("hit",world.getPlayer().getLocation(),en.getLocation());
				proj.timeFlying=50;
				health--;
			}
		}
		
		dir=world.getPlayer().getX()<this.getX();
		if(Math.abs((world.getPlayer().getX()+8)-(this.getX()+20)) > 32){
			if(!dir) 
				setDirection(1);
			else 
				setDirection(2);
		}else
			setDirection(0);
		
		if(tickRunning==0) {
			sprayAttack(((GameClass)game),world);
			tickRunning=50;
		}
		
		Velocity velo = getVelocity();
		if(velo==null) velo = new Velocity(0,0);
		if(moving) 
			velo.add(getAddLocation(true));
		moving=!moving;
	
		if(!getJumping())
				velo.add(new Velocity(0,-1));
		
		PixelLocation newLoc = getLocation().addVelocity(velo);
		
		if(newLoc.x != getX() || newLoc.y != getY()) {
				BlockLocation[] blocks = GameTick.getAxBHitBox(newLoc,5,5);
				boolean collision = false;
				for(int b=0;b<blocks.length;b++)
					if(GameTick.collisonBlock(this,newLoc,blocks[b].getX(),blocks[b].getY(),world.getBlockID(blocks[b].getX(),blocks[b].getY()))) {
						collision = true;
						break;
					}
				if(!collision) 
						getLocation().set(newLoc);
				else {
					
					collision = false;
					if(velo.getY() != 0f) {					
						newLoc = getLocation().addVelocity(new Velocity(0f,velo.getY()));
						BlockLocation[] blocksY = GameTick.getAxBHitBox(newLoc,5,5);
						for(int b=0;b<blocksY.length;b++)
							if(GameTick.collisonBlock(this,newLoc,blocksY[b].getX(),blocksY[b].getY(),world.getBlockID(blocksY[b].getX(),blocksY[b].getY()))) {
								collision = true;
								break;
							}
						if(!collision) 
								getLocation().set(newLoc);
						else if(velo.getY()<0) 
								setGround(true);
							
					}
					if((collision || velo.getY() == 0f) && velo.getX() != 0f) {
						newLoc = getLocation().addVelocity(new Velocity(velo.getX(),0f));
						BlockLocation[] blocksX = GameTick.getAxBHitBox(newLoc,5,5);
						collision = false;
						for(int b=0;b<blocksX.length;b++)
							if(GameTick.collisonBlock(this,newLoc,blocksX[b].getX(),blocksX[b].getY(),world.getBlockID(blocksX[b].getX(),blocksX[b].getY()))) {
								collision = true;
								break;
							}
						if(!collision) 
								getLocation().set(newLoc);
					}
					
				}
			}
		

	}
	
	public void jump() {
		if(onGround && !isJumping) {
			traveled = 10;
			isJumping=true;
			onGround=false;
		}
	}
	
	
	public Velocity getAddLocation(boolean tick) {

		Velocity location = new Velocity(0,0);
		if(direction==1)
			location.add(new Velocity(1,0));
		else if(direction==2)
			location.add(new Velocity(-1,0));
		
		if(isJumping)
				location.add(new Velocity(0, (int)Math.ceil((float)traveled/5)));
		
		
	
		return location;
	}
	
	
	public void render(AbstractGameClass e,SpriteBatch g) {
		try {
			TextureRegion image = e.getImageHandler().getImage(getImage());
			PixelLocation cam = ((GameClass)e).getCamLocation();
			PixelLocation move = new PixelLocation(getX() - cam.getX(), getY() - cam.getY());
			if(getLastDirection()==1)
			image.flip(true, false);
			
			g.draw(image,move.getX(),move.getY());
			
			if(getLastDirection()==1)
			image.flip(true, false);
			
		} catch(Exception ex) { System.err.println(getImage()); }
	}
	

}

