package game.main.start;

import game.engine.main.GameClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.JsonValue;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Title";
		config.width = 213*4;
		config.height = 120*4;
		config.resizable = true;
		config.foregroundFPS = 60;
		config.backgroundFPS = 30;
		
		GameClass gameclass = new GameClass();

		new LwjglApplication(gameclass, config);

		
	
		

	}
}
