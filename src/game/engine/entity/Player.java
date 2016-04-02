package game.engine.entity;

import me.pusty.util.AbstractGameClass;

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
	
	public boolean hasDirections() { return false; }

	public void render(AbstractGameClass e,SpriteBatch g) {
		try {
			TextureRegion image = e.getImageHandler().getImage(getImage());
			g.draw(image,26,26);
		} catch(Exception ex) { System.err.println(getImage()); }
	}
	
}


