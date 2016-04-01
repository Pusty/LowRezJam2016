package game.engine.entity;



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

	//Get Speed 30 = 1 sec
	public int getSpeed() {
		return 1;
	}
	
}


