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
		g.getWorld().addEntity(new Projectile(this.getX()+4,this.getY()+4,this.getLastDirection()));
	}
	public void skillE(GameClass e) {
		setGhost(true);
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
	
	public void tickTraveled(AbstractGameClass e) {
		super.tickTraveled(e);
		GameClass game = (GameClass)e;
		
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
		}
	}
	
}


