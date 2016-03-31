package game.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.pusty.util.AbstractGameClass;
import me.pusty.util.Tick;

public class GameRender extends Tick{

	public GameRender(AbstractGameClass engine) {
		super(engine);
	}

	@Override
	public void show() {
		
	}

	@Override
	public void tick(AbstractGameClass engine, float delta) {
		
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
		
		batch.draw(e.getImageHandler().getImage("tile_0"),0,0);
	}

}
