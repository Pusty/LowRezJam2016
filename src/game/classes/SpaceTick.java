package game.classes;

import game.engine.main.GameClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.PixelLocation;
import me.pusty.util.Tick;

public class SpaceTick extends Tick{

	public SpaceTick(AbstractGameClass engine) {
		super(engine);
	}

	

	@Override
	public boolean keyEvent(AbstractGameClass e,int type,int keycode) {
			if(type==0) {
				((GameClass)e).startGame();
				return true;
			}
		
		return false;
	}
	
	
	
	@Override
	public void show() {
		
	}

	@Override
	public void tick(AbstractGameClass e, float delta) {
	
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
		
		renderSmallText(e,batch,new PixelLocation(1,34),"Press Any Key");
		renderSmallText(e,batch,new PixelLocation(2,28),"to Continue!");
		
		
	}
	
	
	public static void renderSmallText(AbstractGameClass en,SpriteBatch g,PixelLocation loc,String txt){
		if(txt == null || txt.trim() == "")return;
		for(int a=0;a<txt.length();a++) {
			TextureRegion image = en.getImageHandler().getImage("small_"+txt.toUpperCase().toCharArray()[a]);
			if(image==null) continue;
			g.draw(image, loc.getX() + a*5, loc.getY() ,image.getRegionWidth(),image.getRegionHeight());
		}
	}



}
