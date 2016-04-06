package game.engine.entity;

import game.classes.GameTick;
import game.engine.main.GameClass;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.BlockLocation;
import me.pusty.util.PixelLocation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;



public class Player extends EntityLiving {

	public Player(int x, int y) {
		super(x, y);
	}
	
	
	public String getTextureName() {
		return "player";
	}
	public String getMovingTexture() {
		return null;
	}
	
	public void skillQ(GameClass g) {
		if(shootCooldown==-1)
//			shootCasted=30*2;
			shootCasted=0;
	}
	public void skillE(GameClass e) {
		if(ghostCooldown==-1) {
			setGhost(true);
			ghostCasted=30*5; // 5 Seconds
		}
	}
	
	
	public void shootEnergyBall(GameClass g) {
//		g.getWorld().addEntity(new Projectile(this.getX()+4,this.getY()+4,this.getLastDirection()));
		g.getWorld().addEntity(new Buble(this.getX()+4,this.getY()+4,this.getLastDirection()));
	}


	public void skillUnQ(GameClass g) {
//		shootCasted=-1;
	}
	public void skillUnE(GameClass e) {
	}

	public boolean hasDirections() { return false; }

	public void render(AbstractGameClass e,SpriteBatch g) {
		try {
			TextureRegion image = e.getImageHandler().getImage(getImage());
			PixelLocation cam = ((GameClass)e).getCamLocation();
			PixelLocation move = new PixelLocation(getX() - cam.getX(), getY() - cam.getY());
			g.draw(image,move.getX(),move.getY());
		} catch(Exception ex) { System.err.println(getImage()); }
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
	public boolean canMoveVertical() {
		return ghost;
	}
	
	int ghostCooldown=-1;
	int ghostCasted=-1;
	int shootCooldown=-1;
	int shootCasted=-1;
	
	public void tickTraveled(AbstractGameClass e) {
		super.tickTraveled(e);
		GameClass game = (GameClass)e;
		
		if(ghostCooldown>=0)
			ghostCooldown--;
		if(shootCooldown>=0)
			shootCooldown--;
		
		if(ghostCasted>0 && ghost)
			ghostCasted--;
		else if(ghostCasted==0 && ghost) {
			ghostCasted--;
			if(ghostUsed==false) {
//				ghostCooldown=30*10; //10Seconds
//				ghost=false;
			}
		}
		
		if(shootCasted>0)
			shootCasted--;
		else if(shootCasted==0) {
			shootEnergyBall(game);
			shootCasted--;
//			shootCooldown=30*5;
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
//				ghostCooldown = 30*15;
			}
		}
	}
	
}


