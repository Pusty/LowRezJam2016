package game.engine.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import game.classes.GameTick;
import game.engine.main.Config;
import game.engine.main.GameClass;
import game.engine.world.World;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.BlockLocation;
import me.pusty.util.PixelLocation;
import me.pusty.util.Velocity;



public class Player extends EntityLiving {

	public Player(int x, int y) {
		super(x, y);
		setHealth(1);
	}
	
	boolean inBubble = false;
	public boolean inBubble() {
		return inBubble;
	}
	public void setBubble(boolean b)  {
		inBubble = b;
	}
	public String getTextureName() {
		if(inBubble)
			return "player_bubble";
		return "player_0";
	}
	public String getMovingTexture() {
		return null;
	}
	
	public void skillQ(GameClass g) {
		if(shootCooldown==-1) {
			shootCasted=30*1;
			setAnimation(g.getAnimationHandler().getAnimation("player_attack"));
			g.getSound().playClip("charge", null, null);
		}
	}
	public void skillE(GameClass e) {
		if(ghostCooldown==-1) {
			setGhost(true);
			e.getSound().playClip("ghost", null, null);
			ghostCasted=30*5; // 5 Seconds
		}
	}
	@Override
	public void jump(AbstractGameClass e) {
		if(onGround && !isJumping) {
			traveled = 20;
			isJumping=true;
			onGround=false;
			e.getSound().playClip("jump_player",((GameClass)e).getWorld().getPlayer().getLocation(),getLocation());
		}
	}
	
	
	
	
	public void shootEnergyBall(GameClass g) {
		g.getSound().playClip("shot", null, null);
		g.getWorld().addEntity(new Projectile(this.getX()+4,this.getY()+4,this.getLastDirection()));
//		g.getWorld().addEntity(new Bubble(this.getX()+4,this.getY()+4,this.getLastDirection()));
	}


	public void skillUnQ(GameClass g) {
		shootCasted=-1;
		setAnimation(null);
	}
	public void skillUnE(GameClass e) {
	}

	public boolean hasDirections() { return false; }

	
	int health=0;
	public int getHealth() {
		return health;
	}
	public void setHealth(int h) {
		health = h;
	}
	public void damage(int d) {
		health = health - d;
	}
	
	boolean ghost=false;
	boolean ghostUsed=false;
	public boolean isGhost() {
		return ghost;
	}
	public void setGhost(boolean b) {
		ghost=b;
		ghostUsed=false;
	}

	public void setGhostUsed(boolean b) {
		ghostUsed=b;
	}
	public boolean isGhostUsed() {
		return ghostUsed;
	}
	public boolean canMoveVertical() {
		return ghost&&ghostUsed;
	}
	
	int ghostCooldown=-1;
	int ghostCasted=-1;
	int shootCooldown=-1;
	int shootCasted=-1;
	
	public void down(GameClass g) {
		if(directionVertical != -1) {
			directionVertical=-1;
			g.getSound().playClip("down", null, null);
		}
	}
	
	public void tickTraveled(AbstractGameClass e) {
		
		GameClass game = (GameClass)e;
		if(this.getDirection()==1337) {
			game.getTemplate().portal(game);
			return;
		}
		
		
		super.tickTraveled(e);
		
		if(isAnimationNull()) {
			if(!inBubble)
				setAnimation(e.getAnimationHandler().getAnimation("player_idle"));
		}
		if(inBubble)
			setAnimation(null);
		
		
		World world = game.getWorld();
		
		if(ghostCooldown>=0)
			ghostCooldown--;
		if(shootCooldown>=0)
			shootCooldown--;
		
		if(ghostCasted>0 && ghost)
			ghostCasted--;
		else if(ghostCasted==0 && ghost) {
			ghostCasted--;
			if(ghostUsed==false) {
				ghostCooldown=30*2; //10Seconds
				ghost=false;
			}
		}
		
		if(shootCasted>0)
			shootCasted--;
		else if(shootCasted==0) {
			shootEnergyBall(game);
			shootCasted--;
			shootCooldown=30*2;
		}
	
		
		
		//Try to disable Ghost Ability
		if(ghost && ghostUsed) {
			BlockLocation[] blocks = GameTick.get2x2HitBox(this.getLocation());
			ghost=false;
			boolean collision = false;
			for(int b=0;b<blocks.length;b++)
				if(GameTick.collisonBlock(this,getLocation(),blocks[b].getX(),blocks[b].getY(),game.getWorld().getBlockID(blocks[b].getX(),blocks[b].getY()))) {
					collision = true;
					break;
				}
			if(collision)  
				ghost=true;
			else {
				ghostCooldown = 30*3;
			}
		}
		
		
		
		
		
		{
			if(getDirection()!=0) {
				if(!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D))
					setDirection(0);
			}
			if(getDirectionVertical()!=0) {
				if(!Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.S))
					setDirectionVertical(0);
			}
			
			Velocity velo = getVelocity();
			if(velo==null) velo = new Velocity(0,0);
			velo.add(getAddLocation(true));

			
			if(!getJumping() && !(isGhost() && isGhostUsed()))
				if(getWater())
					velo.add(new Velocity(0,-1));
				else {
					if(getY()-(getY()/Config.tileSize*Config.tileSize) == 1)
						velo.add(new Velocity(0,-1));
					else
						velo.add(new Velocity(0,-2));
				}
			
			if(getWater()) {
				if(!inBubble())
					damage(1);
				setGround(true);
			}
			
			setWater(false);
			PixelLocation newLoc = getLocation().addVelocity(velo);
				if(newLoc.x != getX() || newLoc.y != getY()) {
					BlockLocation[] blocks = GameTick.get2x2HitBox(newLoc);
					boolean collision = false;
					if(newLoc.x < 0 || newLoc.x > world.getSizeX()*Config.tileSize-Config.tileSize) collision = true;
					if(newLoc.y < 0 || newLoc.y > world.getSizeY()*Config.tileSize-Config.tileSize) collision = true;
					if(!collision)
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
							BlockLocation[] blocksY = GameTick.get2x2HitBox(newLoc);
							if(newLoc.y < 0 || newLoc.y > world.getSizeY()*Config.tileSize-Config.tileSize) collision = true;
							if(!collision)
							for(int b=0;b<blocksY.length;b++)
								if(GameTick.collisonBlock(this,newLoc,blocksY[b].getX(),blocksY[b].getY(),world.getBlockID(blocksY[b].getX(),blocksY[b].getY()))) {
									collision = true;
									break;
								}
							if(!collision) 
									getLocation().set(newLoc);
							else if(velo.getY()<0) {
									setGround(true);
									if(inBubble())
										setBubble(false);
							}
							else if(velo.getY()>0) {
								setJumping(false);
							if(inBubble())
								setBubble(false);
							}
						
							
						}
						if((collision || velo.getY() == 0f) && velo.getX() != 0f) {
							newLoc = getLocation().addVelocity(new Velocity(velo.getX(),0f));
							BlockLocation[] blocksX = GameTick.get2x2HitBox(newLoc);
							collision = false;
							if(newLoc.x < 0 || newLoc.x > world.getSizeX()*Config.tileSize-Config.tileSize) collision = true;
							if(!collision)
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
	}

	
}


